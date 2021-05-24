package com.example.engine.model;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

public class Session {

    @Id
    private String id = UUID.randomUUID().toString();

    private List<Player>
}
