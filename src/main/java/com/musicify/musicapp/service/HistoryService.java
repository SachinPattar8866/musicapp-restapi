package com.musicify.musicapp.service;

import com.musicify.musicapp.dto.SongDTO;
import com.musicify.musicapp.model.ListeningHistory;
import com.musicify.musicapp.repository.ListeningHistoryRepository;
import com.musicify.musicapp.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    @Autowired
    private ListeningHistoryRepository historyRepository;

    @Autowired
    private MusicService musicService;

    @Autowired
    private JwtUtils jwtUtils;

    public void addToHistory(String token, String trackId) {
        String userId = jwtUtils.extractUserId(token);
        ListeningHistory entry = new ListeningHistory(userId, trackId);
        historyRepository.save(entry);
    }

    public List<SongDTO> getHistory(String token) {
    String userId = jwtUtils.extractUserId(token);
    System.out.println("[DEBUG] getHistory for userId: " + userId);
    List<ListeningHistory> history = historyRepository.findTop20ByUserIdOrderByPlayedAtDesc(userId);
    System.out.println("[DEBUG] ListeningHistory records: " + history.size());
    List<String> trackIds = history.stream()
        .map(ListeningHistory::getTrackId)
        .collect(Collectors.toList());
    System.out.println("[DEBUG] Track IDs: " + trackIds);
        List<SongDTO> songs = musicService.getSongsByIds(trackIds);
        System.out.println("[DEBUG] Songs returned: " + songs.size());
        for (SongDTO song : songs) {
            System.out.println("[DEBUG] SongDTO: " + song);
        }
        // Fallback: If bulk API returns zero songs, fetch each individually
        if (songs.isEmpty() && !trackIds.isEmpty()) {
            System.out.println("[DEBUG] Bulk API returned zero songs, trying individual fetches...");
            songs = trackIds.stream()
                .map(id -> {
                    SongDTO song = musicService.getSongById(id);
                    if (song != null) {
                        System.out.println("[DEBUG] Individual SongDTO: " + song);
                    }
                    return song;
                })
                .filter(s -> s != null)
                .collect(Collectors.toList());
            System.out.println("[DEBUG] Songs after individual fetch: " + songs.size());
        }
        return songs;
    }
}