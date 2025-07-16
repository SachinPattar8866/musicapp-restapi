package com.musicify.musicapp.controller;

import com.musicify.musicapp.dto.LikeRequest;
import com.musicify.musicapp.dto.SongDTO;
import com.musicify.musicapp.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> likeSong(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody LikeRequest request
    ) {
        likeService.likeSong(token, request.getTrackId());
        return ResponseEntity.ok("Song liked successfully");
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<String> unlikeSong(
            @RequestHeader("Authorization") String token,
            @PathVariable String trackId
    ) {
        likeService.unlikeSong(token, trackId);
        return ResponseEntity.ok("Song unliked successfully");
    }

    @GetMapping
    public ResponseEntity<List<SongDTO>> getLikedSongs(
            @RequestHeader("Authorization") String token
    ) {
        List<SongDTO> songs = likeService.getLikedSongs(token);
        return ResponseEntity.ok(songs);
    }
}
