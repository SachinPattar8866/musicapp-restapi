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
        List<ListeningHistory> history = historyRepository.findByUserIdOrderByPlayedAtDesc(userId);
        return history.stream()
                .map(h -> musicService.getSongById(h.getTrackId()))
                .collect(Collectors.toList());
    }
}
