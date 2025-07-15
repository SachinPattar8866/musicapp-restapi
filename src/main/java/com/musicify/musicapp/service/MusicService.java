package com.musicify.musicapp.service;

import com.musicify.musicapp.config.JamendoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final JamendoConfig jamendoConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    public Object searchTracks(String query) {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&limit=20&namesearch=" + query;
        return restTemplate.getForObject(url, Object.class);
    }

    public Object getPopularTracks() {
        String url = jamendoConfig.getApiUrl()
                + "/tracks/?client_id=" + jamendoConfig.getClientId()
                + "&format=json&limit=20&order=popularity_total";
        return restTemplate.getForObject(url, Object.class);
    }
}
