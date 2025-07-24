package com.musicify.musicapp.controller;

import com.musicify.musicapp.dto.PlaylistRequest;
import com.musicify.musicapp.model.Playlist;
import com.musicify.musicapp.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistRequest request) {
        return ResponseEntity.ok(playlistService.createPlaylist(request));
    }

    // GET /api/playlists/user - get playlists for current authenticated user
    @GetMapping("/user")
    public ResponseEntity<List<Playlist>> getCurrentUserPlaylists() {
        long start = System.currentTimeMillis();
        List<Playlist> playlists = playlistService.getCurrentUserPlaylists();
        long end = System.currentTimeMillis();
        System.out.println("getCurrentUserPlaylists response time: " + (end - start) + "ms");
        return ResponseEntity.ok(playlists);
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<Playlist> updatePlaylist(
            @PathVariable String playlistId,
            @RequestBody PlaylistRequest request) {
        return ResponseEntity.ok(playlistService.updatePlaylist(playlistId, request));
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.noContent().build();
    }
}
