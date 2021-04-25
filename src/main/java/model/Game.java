package model;

import model.actions.UserAction;
import model.logs.GameLog;
import model.logs.LogEntry;
import model.units.Settlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private List<Player> players;
    private Map map;
    private final GameLog gameLog;
    private int turnNumber;
    private final List<UserAction> actions;

    public Game() {
        this.players = new ArrayList<>();
        this.turnNumber = 0;
        this.actions = new ArrayList<>();
        this.gameLog = GameLog.getInstance();
    }

    public void registerPlayer(Player player) {
        if (players.stream().anyMatch(p -> player.getName().equals(p.getName()))) {
            players.add(player);
        } else {
            gameLog.addEntry(new LogEntry(player, turnNumber, "Cannot register player. Name is already taken"));
        }
    }

    public void createInitialSettlersUnit(Player player) {
        Tile[][] tiles = this.map.getTileMatrix();
        List<Tile> list = Arrays
                .stream(tiles)
                .flatMap(Arrays::stream)
                .filter(x -> x.getType() == TileType.LAND)
                .collect(Collectors.toList());
        Collections.shuffle(list);
        boolean placed = false;
        int idx = 0;
        while (!placed) {
            Tile tile = list.get(idx);
            if (tile.getMapObjects().size() == 0) {
                tile.getMapObjects().add(new Settlers(list.get(idx), player));
                placed = true;
            }
            idx++;
        }
    }

    public void takeTurn(Player player) {
        for (UserAction action : actions) {
            action.act(player, this);
        }
    }

    public void nextTurn() {
        turnNumber++;
    }

    public void addUserAction(UserAction action) {
        actions.add(action);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
