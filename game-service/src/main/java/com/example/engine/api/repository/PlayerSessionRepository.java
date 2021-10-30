package com.example.engine.api.repository;

import com.example.engine.model.PlayerSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerSessionRepository extends MongoRepository<PlayerSession, String> {
}
