package com.example.engine.model.logs;

import com.example.engine.model.User;
import org.springframework.data.annotation.PersistenceConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameLog implements Serializable {
    private static GameLog instance;
    private final List<LogEntry> log;

    @PersistenceConstructor
    public GameLog(List<LogEntry> log) {
        this.log = log;
    }

    public GameLog() {
        this.log = new ArrayList<>();
    }

    public void addEntry(LogEntry entry) {
        log.add(entry);
    }

    public Stream<LogEntry> getForTurnAndUser(int turn, User user) {
        return log.stream().filter(entry -> entry.getTurnNumber() == turn && entry.getUser().equals(user));
    }
}
