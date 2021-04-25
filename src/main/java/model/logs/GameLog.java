package model.logs;

import java.util.ArrayList;
import java.util.List;

public class GameLog {
    private final List<LogEntry> log;
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
