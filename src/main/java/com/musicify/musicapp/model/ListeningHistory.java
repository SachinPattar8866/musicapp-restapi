package com.musicify.musicapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "listening_history")
public class ListeningHistory {

    @Id
    private String id;

    private String userId;
    private String trackId;
    private Date playedAt = new Date();

    public ListeningHistory() {}

    public ListeningHistory(String userId, String trackId) {
        this.userId = userId;
        this.trackId = trackId;
        this.playedAt = new Date();
    }

    // Getters and setters
    public String getId() { return id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTrackId() { return trackId; }
    public void setTrackId(String trackId) { this.trackId = trackId; }

    public Date getPlayedAt() { return playedAt; }
    public void setPlayedAt(Date playedAt) { this.playedAt = playedAt; }
}
