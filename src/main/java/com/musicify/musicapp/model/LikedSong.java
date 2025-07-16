package com.musicify.musicapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "liked_songs")
public class LikedSong {

    @Id
    private String id;

    private String userId;
    private String trackId;
    private Date likedAt = new Date();

    public LikedSong() {}

    public LikedSong(String userId, String trackId) {
        this.userId = userId;
        this.trackId = trackId;
        this.likedAt = new Date();
    }

    // Getters and setters
    public String getId() { return id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTrackId() { return trackId; }
    public void setTrackId(String trackId) { this.trackId = trackId; }

    public Date getLikedAt() { return likedAt; }
    public void setLikedAt(Date likedAt) { this.likedAt = likedAt; }
}
