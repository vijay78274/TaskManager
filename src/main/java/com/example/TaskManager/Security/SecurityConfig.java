package com.example.TaskManager.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
    
    @Autowired
    private MyUserDetailsServices userDetailsServices;

    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
        // .csrf(csrf -> csrf.disable()) 
        .authorizeHttpRequests(request -> 
            request
            .requestMatchers("/css/**", "/javascript/**","/images/**","/login","/forgot","/send-otp","/reset-password").permitAll() 
            // .requestMatchers("/tenet/**").hasRole("Tenet") 
            // .requestMatchers("/landlord/**").hasRole("Landlord") 
                .anyRequest().authenticated())
        .formLogin(form -> 
            form
                .loginPage("/login") 
                .loginProcessingUrl("/perform_login") 
                .successHandler(authenticationSuccessHandler()) 
                .failureHandler(failureHandler) 
                .permitAll() 
            )
            .logout(logout -> logout
            .logoutUrl("/logout")  
            .logoutSuccessUrl("/login?logout")  
            .permitAll()
            )
            .httpBasic(Customizer.withDefaults())
        .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        System.out.println("Entering user details service");
        provider.setUserDetailsService(userDetailsServices);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

     @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

                if (userDetails.getRole().equals("Team_Lead")) {
                    response.sendRedirect("/landlord/home");
                } else if (userDetails.getRole().equals("Employee")) {
                    response.sendRedirect("/tenet/home");
                } else {
                    response.sendRedirect("/default");
                }
            }
        };
    }
}