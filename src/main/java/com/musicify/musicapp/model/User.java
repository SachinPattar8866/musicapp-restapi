package com.musicify.musicapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;

    private List<String> roles;         // Example: ["USER"]
    private List<String> likedSongIds;  // Jamendo song IDs
    private List<String> playlistIds;   // IDs of user's playlists
}
