package com.musicify.musicapp.repository;

import com.musicify.musicapp.model.Music;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicRepository extends MongoRepository<Music, String> {
}
