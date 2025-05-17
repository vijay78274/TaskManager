package com.example.TaskManager.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException exception) throws IOException, ServletException {
        String errorMessage="Invalid Username or Password";
        request.getSession().setAttribute("error", errorMessage);
        getRedirectStrategy().sendRedirect(request, response, "/login?error");
    }
}
