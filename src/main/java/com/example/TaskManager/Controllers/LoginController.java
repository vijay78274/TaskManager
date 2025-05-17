package com.example.TaskManager.Controllers;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Exception ex = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (ex != null) {
                model.addAttribute("error", ex.getMessage());
                session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            }
        }
        return "Login"; 
    }

    @GetMapping("/forgot")
    public String LinkGmail(Model model, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        model.addAttribute("_csrf", csrfToken);
        return "ForgotPassword";
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email, Model model) {
        Optional<Users> userOpt = usersRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found with this email.");
            return "ForgotPassword";
        }

        Users user = userOpt.get();
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        usersRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);

        model.addAttribute("email", email);
        model.addAttribute("message", "OTP sent to your email.");
        return "ForgotPassword";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String otp,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {

        Optional<Users> userOpt = usersRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found.");
            return "ForgotPassword";
        }

        Users user = userOpt.get();

        if (!user.getOtp().equals(otp)) {
            model.addAttribute("error", "Invalid OTP.");
            model.addAttribute("email", email);
            return "ForgotPassword";
        }

        if (user.getOtpGeneratedTime().isBefore(LocalDateTime.now().minusMinutes(10))) {
            model.addAttribute("error", "OTP expired.");
            model.addAttribute("email", email);
            return "ForgotPassword";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("email", email);
            return "ForgotPassword";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        user.setOtpGeneratedTime(null);
        usersRepository.save(user);

        model.addAttribute("message", "Password reset successful.");
        return "Login"; // or any confirmation page
    }
}
