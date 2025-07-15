package com.musicify.musicapp.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Data
public class PlaylistRequest {
    @NotBlank(message = "Playlist name is required")
    private String name;

    private String userId;  // required to associate playlist with user

    private List<String> songIds;  // Jamendo track IDs
}
