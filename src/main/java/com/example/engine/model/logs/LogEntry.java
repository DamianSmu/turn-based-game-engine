package com.example.engine.model.logs;

import com.example.engine.model.User;

public class LogEntry {
    public static final String INVALID_ACTION = "INVALID_ACTION";
    private final String user;
    private final int turnNumber;
    private final String message;
    private final String tag;

    public LogEntry(String user, int turnNumber, String message, String tag) {
        this.user = user;
        this.turnNumber = turnNumber;
        this.message = message;
        this.tag = tag;
    }

    public static LogEntry INVALID_ACTION(User user, int turnNumber, String message) {
        return new LogEntry(user.getUsername(), turnNumber, message, INVALID_ACTION);
    }

    public static LogEntry OBJECT_DOES_NOT_BELONG_TO_PLAYER(User user, int turnNumber) {
        return new LogEntry(user.getUsername(), turnNumber, "Object does not belong to player.", null);
    }

    public static LogEntry UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(User user, int turnNumber) {
        return new LogEntry(user.getUsername(), turnNumber, "Unit has taken action in current turn.", null);
    }

    public static LogEntry CANNOT_REGISTER_PLAYER(String name) {
        return new LogEntry(null, 0, "Cannot register player: ", null);
    }

    public String getUser() {
        return user;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public String getMessage() {
        return message;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "player= " + user +
                ", turnNumber= " + turnNumber +
                ", message= " + message + '\'' +
                '}';
    }
}
