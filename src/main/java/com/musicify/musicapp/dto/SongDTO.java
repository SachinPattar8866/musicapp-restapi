package com.musicify.musicapp.dto;

import lombok.Data;

@Data
public class SongDTO {
    private String id;
    private String name;
    private String artistName;
    private String audioUrl;
    private String coverImage;
}
