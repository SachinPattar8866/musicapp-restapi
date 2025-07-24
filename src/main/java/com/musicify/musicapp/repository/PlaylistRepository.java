package com.musicify.musicapp.repository;

import com.musicify.musicapp.model.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlaylistRepository extends MongoRepository<Playlist, String> {
    List<Playlist> findTop20ByUserIdOrderByCreatedAtDesc(String userId);
}
