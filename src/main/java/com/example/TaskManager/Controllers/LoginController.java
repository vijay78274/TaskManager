package com.example.TaskManager.Controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.TaskManager.Models.Role;
import com.example.TaskManager.Security.JwtService;
import com.example.TaskManager.Security.MyUserDetails;
import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;
import com.example.TaskManager.Service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService service;
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
        return "Login"; 
    }

    @PostMapping("/jwt_login")
    public String login(@RequestBody Users request, HttpServletResponse response,  RedirectAttributes redirectAttributes) {
        try{
            authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmpId(), request.getPassword())
            );
            String token = service.generateToken(request.getEmpId());
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true); 
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); 
            response.addCookie(cookie);
            System.out.println("Authentication Commpleted");
            return "redirect:/home";
        }
        catch (BadCredentialsException e) {
            redirectAttributes.addAttribute("error","true");
            return "redirect:/login";
        }
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
    
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getMethodName() {
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Role role = userDetails.getRole();
        System.out.println(role);
        if (role.equals(Role.MANAGER)) {
            System.out.println("manager role");
            return "redirect:/manager/dashboard";
        } else if (role.equals(Role.TEAM_LEAD)) {
            return "lead-dashboard";
        } else {
            return "member-dashboard";
        }
    }
}
