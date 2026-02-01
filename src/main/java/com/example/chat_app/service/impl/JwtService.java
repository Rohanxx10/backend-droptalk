package com.example.chat_app.service.impl;



import com.example.chat_app.enums.Role;
import com.example.chat_app.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtService {

    public String generateJwtToken(User user, Role role) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername(), role.name());
    }

    private String createToken(Map<String, Object> claims, String username, String role) {
        List<String> roles = Collections.singletonList(role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public List<SimpleGrantedAuthority> extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<String> roles = claims.get("roles", List.class);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    private Key getKey() {
        byte[] keyByte = Decoders.BASE64.decode("6oj00nartOzTjAwOCiomJg1tO1YUEwYWpD+I+MZUCWM=");
        return Keys.hmacShaKeyFor(keyByte);
    }
}