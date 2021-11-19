package com.example.engine.api.dto.response;

import com.example.engine.model.GameState;
import com.example.engine.model.User;

import java.util.List;

public class GameStatusResponseDTO {

    private String id;
    private List<User> users;
    private int turnNumber;
    private User founder;
    private GameState state;
    private User currentTurn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public User getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(User currentTurn) {
        this.currentTurn = currentTurn;
    }
}
