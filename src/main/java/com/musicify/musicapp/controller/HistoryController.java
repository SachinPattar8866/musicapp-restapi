package com.musicify.musicapp.controller;

import com.musicify.musicapp.dto.HistoryRequest;
import com.musicify.musicapp.dto.SongDTO;
import com.musicify.musicapp.service.HistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping
    public ResponseEntity<String> addToHistory(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody HistoryRequest request
    ) {
        historyService.addToHistory(token, request.getTrackId());
        return ResponseEntity.ok("Track added to history");
    }

    @GetMapping
    public ResponseEntity<List<SongDTO>> getHistory(
            @RequestHeader("Authorization") String token
    ) {
        List<SongDTO> history = historyService.getHistory(token);
        return ResponseEntity.ok(history);
    }
}
