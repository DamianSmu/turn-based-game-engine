package com.example.engine.model;

import com.example.engine.model.actions.UserAction;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.ObjectsGenerator;
import com.example.engine.model.tile.TileType;
import org.springframework.data.annotation.Id;

import java.util.*;

public class Game {

    @Id
    private final String id = UUID.randomUUID().toString();

    private final GameLog gameLog;
    private List<PlayerSession> playerSessions;
    private com.example.engine.model.Map map;
    private int turnNumber;
    private List<UserAction> actions;
    private long seed;

    public Game(long seed) {
        this.playerSessions = new ArrayList<>();
        this.turnNumber = 0;
        this.actions = new ArrayList<>();
        this.gameLog = GameLog.getInstance();
        this.seed = seed;
    }

    public void registerPlayer(PlayerSession playerSession) {
        if (playerSessions.stream().anyMatch(p -> playerSession.getName().equals(p.getName()))) {
            playerSessions.add(playerSession);
        } else {
            gameLog.addEntry(new LogEntry(playerSession, turnNumber, "Cannot register player. Name is already taken"));
        }
    }

    public void createInitialSettlersUnit(PlayerSession playerSession){
        ObjectsGenerator.createInitialSettlersUnit(map, playerSession, seed);
    }

    public void createResources(){
        ObjectsGenerator.placeResources(map, seed, TileType.GOLD);
        ObjectsGenerator.placeResources(map, seed, TileType.IRON);
    }

    public void takeTurn(PlayerSession playerSession) {
        for (UserAction action : actions) {
            action.act(playerSession, this);
        }
        playerSession.updateGoldAmount();
        playerSession.updateIronAmount();
    }

    public void nextTurn() {
        actions = new ArrayList<>();
        turnNumber++;
    }

    public void addUserAction(UserAction action) {
        actions.add(action);
    }

    public List<PlayerSession> getPlayers() {
        return playerSessions;
    }

    public void setPlayers(List<PlayerSession> playerSessions) {
        this.playerSessions = playerSessions;
    }

    public com.example.engine.model.Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
