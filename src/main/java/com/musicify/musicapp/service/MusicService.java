package com.musicify.musicapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicify.musicapp.config.JamendoConfig;
import com.musicify.musicapp.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final JamendoConfig jamendoConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Search tracks from Jamendo based on a text query
     */
    public Object searchTracks(String query) {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&limit=20&namesearch=" + query;

        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Get popular tracks from Jamendo
     */
    public Object getPopularTracks() {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&limit=20&order=popularity_total";

        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Get details of a single track by its ID and convert to SongDTO
     */
    public SongDTO getSongById(String trackId) {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&id=" + trackId;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(response);
            JsonNode track = root.path("results").get(0);

            if (track == null || track.isMissingNode()) return null;

            return new SongDTO(
                    track.path("id").asText(),
                    track.path("name").asText(),
                    track.path("artist_name").asText(),
                    track.path("audio").asText(),
                    track.path("album_image").asText()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
