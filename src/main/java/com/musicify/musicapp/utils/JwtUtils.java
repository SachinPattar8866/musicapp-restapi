package com.musicify.musicapp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        // Convert the secret string into a secure SecretKey
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractUserId(String token) {
        return getClaims(token).getSubject();
    }

    public String extractEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }
}
