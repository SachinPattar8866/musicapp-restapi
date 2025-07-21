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
// import java.util.stream.Collectors; // Not used in this version, can be removed

@Service
@RequiredArgsConstructor
public class MusicService {

    private final JamendoConfig jamendoConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<SongDTO> searchTracks(String query) {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&limit=20&namesearch=" + query;

        return fetchAndParseJamendoSongs(url);
    }

    public List<SongDTO> getPopularTracks() {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&limit=20&order=popularity_total";

        return fetchAndParseJamendoSongs(url);
    }

    private List<SongDTO> fetchAndParseJamendoSongs(String url) {
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode resultsNode = root.path("results");

            List<SongDTO> songs = new ArrayList<>();
            if (resultsNode.isArray()) {
                for (JsonNode trackNode : resultsNode) {
                    songs.add(new SongDTO(
                            trackNode.path("id").asText(),
                            trackNode.path("name").asText(),
                            trackNode.path("artistName").asText(),   // Jamendo: "artistName" -> SongDTO: artistName
                            trackNode.path("audio").asText(),        // Jamendo: "audio" -> SongDTO: audioUrl
                            trackNode.path("coverTenant").asText()   // Jamendo: "coverTenant" -> SongDTO: coverImage
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

    public SongDTO getSongById(String trackId) {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&id=" + trackId;

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode track = root.path("results").get(0);

            if (track == null || track.isMissingNode()) return null;

            return new SongDTO(
                    track.path("id").asText(),
                    track.path("name").asText(),
                    track.path("artistName").asText(),     // <--- CHANGE: Jamendo uses "artistName"
                    track.path("audio").asText(),          // <--- CHANGE: Jamendo uses "audio"
                    track.path("coverTenant").asText()     // <--- CHANGE: Jamendo uses "coverTenant"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}