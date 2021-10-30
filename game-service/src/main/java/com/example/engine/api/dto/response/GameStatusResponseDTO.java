package com.example.engine.api.dto.response;

import com.example.engine.model.GameState;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.User;

import java.util.List;

public class GameStatusResponseDTO {

    private String id;
    private List<PlayerSession> playerSessions;
    private int turnNumber;
    private User founder;
    private GameState state;
    private PlayerSession currentTurn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PlayerSession> getPlayerSessions() {
        return playerSessions;
    }

    public void setPlayerSessions(List<PlayerSession> playerSessions) {
        this.playerSessions = playerSessions;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public PlayerSession getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(PlayerSession currentTurn) {
        this.currentTurn = currentTurn;
    }
}
