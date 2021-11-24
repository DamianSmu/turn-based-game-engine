package com.example.engine.model;

import com.example.engine.model.actions.ActionRequest;
import com.example.engine.model.actions.ActionResolver;
import com.example.engine.model.actions.CannotResolveActionException;
import com.example.engine.model.actions.UserAction;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.ObjectsGenerator;
import com.example.engine.model.mapObject.ResourceApplier;
import com.example.engine.model.tile.Tile;
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
    private List<User> users;
    private GameMap gameMap;
    private int turnNumber;
    private List<ActionRequest> actionRequests;
    private long seed;
    @DBRef
    private User founder;
    private GameState state;
    @DBRef
    private User currentTurnUser;

    @PersistenceConstructor
    public Game(String id, GameLog gameLog, List<User> users, GameMap gameMap, int turnNumber, List<ActionRequest> actionRequests, long seed, User founder, GameState state, User currentTurnUser) {
        this.id = id;
        this.gameLog = gameLog;
        this.users = users;
        this.gameMap = gameMap;
        this.turnNumber = turnNumber;
        this.actionRequests = actionRequests;
        this.seed = seed;
        this.founder = founder;
        this.state = state;
        this.currentTurnUser = currentTurnUser;
    }

    public Game(long seed, User founder) {
        this.state = GameState.CREATED;
        this.founder = founder;
        this.users = new ArrayList<>();
        this.turnNumber = 0;
        this.actionRequests = new ArrayList<>();
        this.gameLog = new GameLog();
        this.seed = seed;
    }

    public void start() {
        gameLog = new GameLog();
        state = GameState.STARTED;
        gameMap = new GameMap(20, seed);
        gameMap.createTiles();
        ObjectsGenerator.placeResources(gameMap, seed, TileType.GOLD);
        ObjectsGenerator.placeResources(gameMap, seed, TileType.IRON);

        for (User p : users) {
            ObjectsGenerator.createInitialSettlersUnit(gameMap, p, seed);
        }

        currentTurnUser = users.get(0);
    }

    public User registerPlayer(User user) {
        if (users.stream().noneMatch(user::equals)) {
            users.add(user);
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot register player.");
        }
    }

    public Game takeTurn() {
        for (ActionRequest actionRequest : actionRequests) {
            try {
                UserAction action = ActionResolver.resolve(gameMap, actionRequest);
                action.act(currentTurnUser, this);
            } catch (CannotResolveActionException e) {
                gameLog.addEntry(LogEntry.INVALID_ACTION(currentTurnUser, turnNumber, e.getMessage()));
            } catch (Exception e) {
                System.err.println("RESOLVING ACTION ERROR " + actionRequest.toString());
                gameLog.addEntry(LogEntry.INVALID_ACTION(currentTurnUser, turnNumber, e.getMessage()));
            }
        }
        updateResources(currentTurnUser);

        actionRequests = new ArrayList<>();
        turnNumber++;

        int idx = users.indexOf(currentTurnUser);
        currentTurnUser = users.get(idx == users.size() - 1 ? 0 : ++idx);

        //checkEndConditions
        return this;
    }

    private void updateResources(User user) {
        gameMap.getTiles().stream().map(Tile::getMapObject).filter(x -> x instanceof ResourceApplier && x.getUser().equals(user)).forEach(x -> ((ResourceApplier) x).applyResources());

    }

    private GameState checkEndConditions() {
        int playersAlive = 0;
        for (User user : users) {
            if (gameMap.getTiles().stream().anyMatch(x -> x.getMapObject().getUser().equals(user))) {
                playersAlive++;
            }
        }
        if (playersAlive < 2) {
            return GameState.FINISHED;
        }
        if (turnNumber > 200) {
            return GameState.FINISHED;
        }
        return GameState.STARTED;
    }

    public void addUserActionRequest(ActionRequest action) {
        actionRequests.add(action);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public GameMap getMap() {
        return gameMap;
    }

    public void setMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void setPlayers(List<User> users) {
        this.users = users;
    }

    public int getPlayersCount() {
        return users.size();
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

    public User getCurrentTurnUser() {
        return currentTurnUser;
    }

    public void setCurrentTurnUser(User currentTurnUser) {
        this.currentTurnUser = currentTurnUser;
    }
}
