package com.example.engine.model.logs;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLog implements Serializable {
    private List<LogEntry> log;
    private static GameLog instance;

    public GameLog() {
        this.log = new ArrayList<>();
    }

    public void addEntry(LogEntry entry) {
        log.add(entry);
    }

    public static GameLog getInstance() {
        if (instance == null) {
            instance = new GameLog();
        }
        return instance;
    }
}
