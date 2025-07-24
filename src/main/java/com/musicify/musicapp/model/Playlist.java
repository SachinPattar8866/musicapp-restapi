package com.musicify.musicapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "playlists")
public class Playlist {

    @Id
    private String id;

    private String userId;          // Reference to owner user

    private String name;            // Playlist name
    private List<String> songIds;   // Jamendo song IDs
    private long createdAt;         // Timestamp for creation (epoch millis)
}
