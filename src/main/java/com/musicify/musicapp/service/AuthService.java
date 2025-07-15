package com.musicify.musicapp.service;

import com.musicify.musicapp.dto.AuthRequest;
import com.musicify.musicapp.dto.AuthResponse;
import com.musicify.musicapp.dto.RegisterRequest;
import com.musicify.musicapp.model.User;
import com.musicify.musicapp.repository.UserRepository;
import com.musicify.musicapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRoles());

        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRoles());

        return new AuthResponse(token);
    }
}
