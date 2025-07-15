package com.musicify.musicapp.utils;

public class TokenUtils {

    public static String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header format");
    }
}
