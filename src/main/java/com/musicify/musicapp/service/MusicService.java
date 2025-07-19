// src/main/java/com/musicify/musicapp/service/MusicService.java (UPDATED)

package com.musicify.musicapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicify.musicapp.config.JamendoConfig;
import com.musicify.musicapp.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Added for stream operations

@Service
@RequiredArgsConstructor
public class MusicService {

    private final JamendoConfig jamendoConfig;
    // Initialize RestTemplate here or inject it if you have a RestTemplate bean
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper(); // Initialize ObjectMapper once

    /**
     * Search tracks from Jamendo based on a text query
     * Returns a List of SongDTOs
     */
    public List<SongDTO> searchTracks(String query) { // Changed return type to List<SongDTO>
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&limit=20&namesearch=" + query;

        return fetchAndParseJamendoSongs(url); // Use the new helper method
    }

    /**
     * Get popular tracks from Jamendo
     * Returns a List of SongDTOs
     */
    public List<SongDTO> getPopularTracks() { // Changed return type to List<SongDTO>
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&limit=20&order=popularity_total";

        return fetchAndParseJamendoSongs(url); // Use the new helper method
    }

    /**
     * Helper method to fetch JSON from Jamendo and parse the "results" array into a List<SongDTO>
     */
    private List<SongDTO> fetchAndParseJamendoSongs(String url) {
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode resultsNode = root.path("results");

            List<SongDTO> songs = new ArrayList<>();
            if (resultsNode.isArray()) {
                for (JsonNode trackNode : resultsNode) {
                    songs.add(new SongDTO(
                            trackNode.path("id").asText(),           // Jamendo: "id" -> SongDTO: id
                            trackNode.path("name").asText(),         // Jamendo: "name" -> SongDTO: name (song title)
                            trackNode.path("artistName").asText(),   // Jamendo: "artistName" -> SongDTO: artistName
                            trackNode.path("audio").asText(),
                            trackNode.path("coverTenant").asText()   // Jamendo: "coverTenant" -> SongDTO: albumImage (assuming this is the field in your DTO)
                    ));
                }
            }
            return songs;
        } catch (Exception e) {
            System.err.println("Error fetching or parsing Jamendo API response: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Get details of a single track by its ID and convert to SongDTO
     * (This method was mostly fine, just ensuring it uses the class-level ObjectMapper)
     */
    public SongDTO getSongById(String trackId) {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&id=" + trackId;

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            // Assuming get(0) for ID search will return a single track in results
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