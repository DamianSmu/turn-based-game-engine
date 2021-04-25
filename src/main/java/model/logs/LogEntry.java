package model.logs;

import model.Player;

public class LogEntry {
    private final Player player;
    private final int turnNumber;
    private final String message;

    public LogEntry(Player player, int turnNumber, String message) {
        this.player = player;
        this.turnNumber = turnNumber;
        this.message = message;
    }


    public static LogEntry INVALID_ACTION(Player player, int turnNumber) {
        return new LogEntry(player, turnNumber, "Invalid action.");
    }

    public static LogEntry OBJECT_DOES_NOT_BELONG_TO_PLAYER(Player player, int turnNumber) {
        return new LogEntry(player, turnNumber, "Invalid action.");
    }

    public static LogEntry UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(Player player, int turnNumber) {
        return new LogEntry(player, turnNumber, "Unit has taken action in current turn.");
    }
}
