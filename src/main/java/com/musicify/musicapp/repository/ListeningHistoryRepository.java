package com.musicify.musicapp.repository;

import com.musicify.musicapp.model.ListeningHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ListeningHistoryRepository extends MongoRepository<ListeningHistory, String> {

    List<ListeningHistory> findByUserIdOrderByPlayedAtDesc(String userId);
}
