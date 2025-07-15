package com.musicify.musicapp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    public String extractUserId(String token) {
        return getClaims(token).getSubject();
    }

    public String extractEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }
}
