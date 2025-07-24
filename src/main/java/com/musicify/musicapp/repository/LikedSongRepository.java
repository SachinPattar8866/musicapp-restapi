package com.musicify.musicapp.repository;

import com.musicify.musicapp.model.LikedSong;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LikedSongRepository extends MongoRepository<LikedSong, String> {

    List<LikedSong> findTop20ByUserIdOrderByLikedAtDesc(String userId);

    Optional<LikedSong> findByUserIdAndTrackId(String userId, String trackId);

    void deleteByUserIdAndTrackId(String userId, String trackId);
}
