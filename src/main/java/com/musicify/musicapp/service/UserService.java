package com.musicify.musicapp.service;

import com.musicify.musicapp.model.User;
import com.musicify.musicapp.repository.UserRepository;
import com.musicify.musicapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public User getUserFromToken(String token) {
        String cleanToken = token.replace("Bearer ", "");
        String userId = jwtTokenProvider.getUserIdFromToken(cleanToken);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
