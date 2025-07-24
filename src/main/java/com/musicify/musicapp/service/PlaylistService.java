package com.musicify.musicapp.service;

import com.musicify.musicapp.dto.PlaylistRequest;
import com.musicify.musicapp.model.Playlist;
import com.musicify.musicapp.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    // Get playlists for the current authenticated user
    public List<Playlist> getCurrentUserPlaylists() {
        // Example: get userId from Spring Security context
        String userId = null;
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            userId = authentication.getName();
        }
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        return playlistRepository.findTop20ByUserIdOrderByCreatedAtDesc(userId);
    }

    private final PlaylistRepository playlistRepository;

    public Playlist createPlaylist(PlaylistRequest request) {
        Playlist playlist = Playlist.builder()
                .name(request.getName())
                .userId(request.getUserId())
                .songIds(request.getSongIds())
                .createdAt(System.currentTimeMillis())
                .build();
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getPlaylistsByUserId(String userId) {
        return playlistRepository.findTop20ByUserIdOrderByCreatedAtDesc(userId);
    }

    public Playlist updatePlaylist(String playlistId, PlaylistRequest request) {
        Playlist existing = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        existing.setName(request.getName());
        existing.setSongIds(request.getSongIds());

        return playlistRepository.save(existing);
    }

    public void deletePlaylist(String playlistId) {
        playlistRepository.deleteById(playlistId);
    }
}
