package com.example.engine.model.mapObject;


import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class MapObject {

    @DBRef
    private PlayerSession playerSession;

    private int actionInTurnNumber;

    @Transient
    @JsonIgnore
    private Tile tile;

    public MapObject(PlayerSession playerSession) {
        this.playerSession = playerSession;
        this.actionInTurnNumber = -1;
    }

    @PersistenceConstructor
    public MapObject(PlayerSession playerSession, int actionInTurnNumber) {
        this.playerSession = playerSession;
        this.actionInTurnNumber = actionInTurnNumber;
    }

    public PlayerSession getPlayerSession() {
        return playerSession;
    }

    public void setPlayerSession(PlayerSession playerSession) {
        this.playerSession = playerSession;
    }

    public int actionInTurnNumber() {
        return actionInTurnNumber;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getActionInTurnNumber() {
        return actionInTurnNumber;
    }

    public void setActionInTurnNumber(int turnNumber) {
        this.actionInTurnNumber = turnNumber;
    }
}
