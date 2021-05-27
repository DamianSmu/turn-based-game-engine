package com.example.engine.api.repository;


import com.example.engine.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}
