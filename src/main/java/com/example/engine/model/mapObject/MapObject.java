package com.example.engine.model.mapObject;


import com.example.engine.model.User;
import com.example.engine.model.tile.Tile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class MapObject {

    @DBRef
    private User user;

    private int actionInTurnNumber;
    private double defence;
    private double offence;

    @Transient
    @JsonIgnore
    private Tile tile;

    public MapObject(User user, double defence, double offence) {
        this.user = user;
        this.actionInTurnNumber = -1;
        this.defence = defence;
        this.offence = offence;
    }

    @PersistenceConstructor
    public MapObject(User user, int actionInTurnNumber, double defence, double offence) {
        this.user = user;
        this.actionInTurnNumber = actionInTurnNumber;
        this.defence = defence;
        this.offence = offence;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
