package com.musicify.musicapp.controller;

import com.musicify.musicapp.model.User;
import com.musicify.musicapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // <-- NEW IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.Optional; // <-- NEW IMPORT for Optional

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService; // Assuming UserService has findByEmail

    @GetMapping("/me")
    // CHANGE: Use Spring Security's Authentication object directly
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // This should ideally be handled by Spring Security filters before reaching here,
            // but as a fallback, ensure unauthenticated requests are handled.
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // The 'authentication.getName()' typically returns the username/email
        // that was used to authenticate the user (as defined in CustomUserDetailsService).
        String userEmail = authentication.getName();

        // Fetch the full User object from your service using the email
        // Assuming UserService has a findByEmail method that returns Optional<User>
        Optional<User> userOptional = userService.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            // This would be an unexpected scenario if user was authenticated but not found in DB
            return new ResponseEntity<>("User profile not found in database", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(userOptional.get());
    }
}