package com.example.engine.model.mapObject;

import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;
import org.springframework.data.annotation.Id;

import java.util.UUID;


public class MapObject {
    @Id
    private String id = UUID.randomUUID().toString();

    private Tile tile;
    private PlayerSession playerSession;
    private int actionInTurnNumber;

    public MapObject(Tile tile) {
        this.tile = tile;
    }

    public MapObject(Tile tile, PlayerSession playerSession) {
        this.tile = tile;
        this.playerSession = playerSession;
        this.actionInTurnNumber = -1;
        playerSession.addMapObject(this);
    }

    public MapObject(PlayerSession playerSession) {
        this.playerSession = playerSession;
        this.actionInTurnNumber = -1;
        playerSession.addMapObject(this);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void move(Tile tile) {
        this.tile.deleteMapObject(this);
        tile.addMapObject(this);
        this.tile = tile;
    }

    public PlayerSession getPlayer() {
        return playerSession;
    }

    public void setPlayer(PlayerSession playerSession) {
        this.playerSession = playerSession;
    }

    public void delete() {
        playerSession.getMapObjects().remove(this);
        tile.getMapObjects().remove(this);
    }

    public int actionInTurnNumber() {
        return actionInTurnNumber;
    }

    public void setActionInTurnNumber(int turnNumber) {
        this.actionInTurnNumber = turnNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
