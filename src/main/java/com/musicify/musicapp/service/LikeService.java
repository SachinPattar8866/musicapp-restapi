package com.musicify.musicapp.service;

import com.musicify.musicapp.dto.SongDTO;
import com.musicify.musicapp.exception.BadRequestException;
import com.musicify.musicapp.model.LikedSong;
import com.musicify.musicapp.repository.LikedSongRepository;
import com.musicify.musicapp.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikedSongRepository likedSongRepository;

    @Autowired
    private MusicService musicService;

    @Autowired
    private JwtUtils jwtUtils;

    public void likeSong(String token, String trackId) {
        String userId = jwtUtils.extractUserId(token);
        boolean alreadyLiked = likedSongRepository
                .findByUserIdAndTrackId(userId, trackId)
                .isPresent();

        if (alreadyLiked) {
            throw new BadRequestException("Song already liked");
        }

        likedSongRepository.save(new LikedSong(userId, trackId));
    }

    public void unlikeSong(String token, String trackId) {
        String userId = jwtUtils.extractUserId(token);
        likedSongRepository.deleteByUserIdAndTrackId(userId, trackId);
    }

    public List<SongDTO> getLikedSongs(String token) {
        String userId = jwtUtils.extractUserId(token);
        List<LikedSong> likedSongs = likedSongRepository.findTop20ByUserIdOrderByLikedAtDesc(userId);
        return likedSongs.stream()
                .map(song -> musicService.getSongById(song.getTrackId()))
                .collect(Collectors.toList());
    }
}
