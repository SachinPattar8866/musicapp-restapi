package com.musicify.musicapp.service;

import com.musicify.musicapp.model.User;
import com.musicify.musicapp.repository.UserRepository;
import com.musicify.musicapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional; // <-- Make sure this import is present!

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider; // Keep this if getUserFromToken is still used elsewhere

    // Keep your existing method if it's used elsewhere in your application
    public User getUserFromToken(String token) {
        String cleanToken = token.replace("Bearer ", "");
        String userId = jwtTokenProvider.getUserIdFromToken(cleanToken);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}