package com.musicify.musicapp.controller;

import com.musicify.musicapp.model.User;
import com.musicify.musicapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserFromToken(token));
    }
}
