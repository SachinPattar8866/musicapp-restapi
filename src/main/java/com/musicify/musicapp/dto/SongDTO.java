package com.musicify.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object representing a song fetched from Jamendo API
 * or stored in user playlists, likes, or history.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {

    private String id;           // Unique ID of the song (Jamendo track ID)
    private String name;         // Title of the song
    private String artistName;   // Artist or band name
    private String audioUrl;     // Direct streaming URL
    private String coverImage;   // URL to album or track image
}
