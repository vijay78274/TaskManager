package com.example.TaskManager.Security;

import java.io.IOException;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService service; 
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                System.out.println("Entering jwtFilter");
                final String jwt = getJwtFromCookies(request);
                String authHeader = request.getHeader("Authorization");
                String token = null;
                String username = null;
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    token = authHeader.substring(7);
                    username = service.extractUsername(token);
                }
                else{
                    System.out.println("UserName is null or not bearer");
                }
                if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if(service.validateToken(token,userDetails)){
                        UsernamePasswordAuthenticationToken token2 = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        token2.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(token2);
                    }
                    else{
                        System.out.println("token is not valid");
                    }
                }
                else{
                    System.out.println("UserName is null or not authenticed");
                }
            filterChain.doFilter(request, response);
        // throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }
    private String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
}
