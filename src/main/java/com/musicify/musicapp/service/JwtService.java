package com.musicify.musicapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String extractUserId(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }
}
