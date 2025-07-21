package com.musicify.musicapp.controller;

import com.musicify.musicapp.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/search")
    public ResponseEntity<?> searchMusic(@RequestParam("q") String query)  {
        return ResponseEntity.ok(musicService.searchTracks(query));
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularTracks() {
        return ResponseEntity.ok(musicService.getPopularTracks());
    }
}
