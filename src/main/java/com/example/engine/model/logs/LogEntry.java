package com.example.engine.model.logs;

import com.example.engine.model.PlayerSession;

public class LogEntry {
    private final PlayerSession playerSession;
    private final int turnNumber;
    private final String message;

    public LogEntry(PlayerSession playerSession, int turnNumber, String message) {
        this.playerSession = playerSession;
        this.turnNumber = turnNumber;
        this.message = message;
    }


    public static LogEntry INVALID_ACTION(PlayerSession playerSession, int turnNumber) {
        return new LogEntry(playerSession, turnNumber, "Invalid action.");
    }

    public static LogEntry OBJECT_DOES_NOT_BELONG_TO_PLAYER(PlayerSession playerSession, int turnNumber) {
        return new LogEntry(playerSession, turnNumber, "Invalid action.");
    }

    public static LogEntry UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(PlayerSession playerSession, int turnNumber) {
        return new LogEntry(playerSession, turnNumber, "Unit has taken action in current turn.");
    }
}
