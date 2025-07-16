package com.musicify.musicapp.dto;

import jakarta.validation.constraints.NotBlank;

public class HistoryRequest {

    @NotBlank(message = "Track ID must not be empty")
    private String trackId;

    public HistoryRequest() {}

    public HistoryRequest(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}
