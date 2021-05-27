package com.example.engine.model;

import com.example.engine.model.actions.ActionRequest;
import com.example.engine.model.actions.ActionResolver;
import com.example.engine.model.actions.CannotResolveActionException;
import com.example.engine.model.actions.UserAction;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.GoldApplier;
import com.example.engine.model.mapObject.IronApplier;
import com.example.engine.model.mapObject.ObjectsGenerator;
import com.example.engine.model.tile.TileType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


public class Game {

    @Id
    private String id;

    @JsonIgnore
    private GameLog gameLog;

    @DBRef
    private List<PlayerSession> playerSessions;

    private Map map;

    private int turnNumber;

    private List<ActionRequest> actionRequests;

    private long seed;

    @DBRef
    private User founder;

    private GameState state;

    @DBRef
    private PlayerSession currentTurnPlayerSession;

    @PersistenceConstructor
    public Game(String id, GameLog gameLog, List<PlayerSession> playerSessions, Map map, int turnNumber, List<ActionRequest> actionRequests, long seed, User founder, GameState state, PlayerSession currentTurnPlayerSession) {
        this.id = id;
        this.gameLog = gameLog;
        this.playerSessions = playerSessions;
        this.map = map;
        this.turnNumber = turnNumber;
        this.actionRequests = actionRequests;
        this.seed = seed;
        this.founder = founder;
        this.state = state;
        this.currentTurnPlayerSession = currentTurnPlayerSession;
    }

    public Game(long seed, User founder) {
        state = GameState.CREATED;
        this.founder = founder;
        this.playerSessions = new ArrayList<>();
        this.turnNumber = 0;
        this.actionRequests = new ArrayList<>();
        this.gameLog = GameLog.getInstance();
        this.seed = seed;
    }

    public void start() {
        state = GameState.STARTED;
        map = new Map(40, seed);
        map.createTiles();
        ObjectsGenerator.placeResources(map, seed, TileType.GOLD);
        ObjectsGenerator.placeResources(map, seed, TileType.IRON);

        for (PlayerSession p : playerSessions) {
            ObjectsGenerator.createInitialSettlersUnit(map, p, seed);
        }

        currentTurnPlayerSession = playerSessions.get(0);
    }

    public PlayerSession registerPlayer(User user) {
        if (playerSessions.stream().noneMatch(p -> user.equals(p.getUser()))) {
            PlayerSession session = new PlayerSession(user);
            playerSessions.add(session);
            return session;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot register player.");
        }
    }

    public PlayerSession takeTurn() {
        for (ActionRequest actionRequest : actionRequests) {
            try {
                UserAction action = ActionResolver.resolve(map, actionRequest);
                action.act(currentTurnPlayerSession, this);
            } catch (CannotResolveActionException ex) {
                GameLog.getInstance().addEntry(new LogEntry(currentTurnPlayerSession, turnNumber, "Cannot resolve action"));
            }
        }
        updateGoldAmount(currentTurnPlayerSession);
        updateIronAmount(currentTurnPlayerSession);


        actionRequests = new ArrayList<>();
        turnNumber++;

        int idx = playerSessions.indexOf(currentTurnPlayerSession);
        PlayerSession sessionToReturn = currentTurnPlayerSession;
        currentTurnPlayerSession = playerSessions.get(idx == playerSessions.size() - 1 ? 0 : ++idx);
        return sessionToReturn;
    }

    public void updateGoldAmount(PlayerSession playerSession) {
        int goldAmount = playerSession.getGoldAmount();
        goldAmount += map.getTiles().stream().flatMap(x -> x.getMapObjects().stream()).filter(x -> x instanceof GoldApplier && x.getPlayerSession().equals(playerSession)).mapToInt(x -> ((GoldApplier) x).applyGold()).sum();
        playerSession.setGoldAmount(goldAmount);
    }

    public void updateIronAmount(PlayerSession playerSession) {
        int ironAmount = playerSession.getGoldAmount();
        ironAmount += map.getTiles().stream().flatMap(x -> x.getMapObjects().stream()).filter(x -> x instanceof IronApplier && x.getPlayerSession().equals(playerSession)).mapToInt(x -> ((IronApplier) x).applyIron()).sum();
        playerSession.setGoldAmount(ironAmount);
    }


    //Getters, setters

    public void addUserActionRequest(ActionRequest action) {
        actionRequests.add(action);
    }

    public List<PlayerSession> getPlayerSessions() {
        return playerSessions;
    }

    public void setPlayerSessions(List<PlayerSession> playerSessions) {
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

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void setPlayers(List<PlayerSession> playerSessions) {
        this.playerSessions = playerSessions;
    }

    public int getPlayersCount() {
        return playerSessions.size();
    }

    public User getFounder() {
        return founder;
    }

    public void setFounder(User founder) {
        this.founder = founder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameLog getGameLog() {
        return gameLog;
    }

    public void setGameLog(GameLog gameLog) {
        this.gameLog = gameLog;
    }

    public List<ActionRequest> getActionRequests() {
        return actionRequests;
    }

    public void setActionRequests(List<ActionRequest> actions) {
        this.actionRequests = actions;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public PlayerSession getCurrentTurnPlayerSession() {
        return currentTurnPlayerSession;
    }

    public void setCurrentTurnPlayerSession(PlayerSession currentTurnPlayerSession) {
        this.currentTurnPlayerSession = currentTurnPlayerSession;
    }
}
