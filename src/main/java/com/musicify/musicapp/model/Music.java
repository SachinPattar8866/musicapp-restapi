package com.musicify.musicapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "music")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Music {
    @Id
    private String id;

    private String title;
    private String artistName;
    private String albumName;
    private String duration;
    private String audioUrl;
    private String imageUrl;
}
