package model.logs;

import model.Player;

public class LogEntry {
    private Player player;
    private int turnNumber;
    private String message;

    public LogEntry(Player player, int turnNumber, String message) {
        this.player = player;
        this.turnNumber = turnNumber;
        this.message = message;
    }


    public static LogEntry INVALID_ACTION(Player player, int turnNumber){
        return new LogEntry(player, turnNumber, "Invalid action.");
    }

    public static LogEntry OBJECT_DOES_NOT_BELONG_TO_PLAYER(Player player, int turnNumber){
        return new LogEntry(player, turnNumber, "Invalid action.");
    }
}
