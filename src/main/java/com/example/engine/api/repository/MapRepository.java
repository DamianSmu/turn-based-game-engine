package com.example.engine.api.repository;

import com.example.engine.model.Map;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MapRepository extends MongoRepository<Map, String> {
}
