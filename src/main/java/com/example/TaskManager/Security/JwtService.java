package com.example.TaskManager.Security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {
    
    public String secret = "sjflkdsjlkfjsoifjlsdkjflksjkdlfjdlskjflksjlkfjlksjdflksjlkj";
    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    private final long JWT_EXPIRATION_MS = 1000 * 60 * 60; // 1 hour

    public String generateToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
     public boolean validateToken(String token, UserDetails userDetails) {
        // System.out.println("Validating Token");
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        // System.out.println("Extracting Username");
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}
