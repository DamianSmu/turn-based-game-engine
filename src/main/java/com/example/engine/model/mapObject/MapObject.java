package com.example.engine.model.mapObject;


import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.UUID;


public class MapObject {

    private String id = UUID.randomUUID().toString();

    @DBRef
    private PlayerSession playerSession;

    private int actionInTurnNumber;
    private double defence;
    private double offence;

    @Transient
    @JsonIgnore
    private Tile tile;

    public MapObject(PlayerSession playerSession, double defence, double offence) {
        this.playerSession = playerSession;
        this.actionInTurnNumber = -1;
        this.defence = defence;
        this.offence = offence;
    }

    @PersistenceConstructor
    public MapObject(String id, PlayerSession playerSession, int actionInTurnNumber, double defence, double offence) {
        this.id = id;
        this.playerSession = playerSession;
        this.actionInTurnNumber = actionInTurnNumber;
        this.defence = defence;
        this.offence = offence;
    }

    public String getId() {
        return id;
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

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public double getOffence() {
        return offence;
    }

    public void setOffence(double offence) {
        this.offence = offence;
    }
}
