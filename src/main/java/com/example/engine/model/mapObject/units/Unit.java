package com.example.engine.model.mapObject.units;

import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;

public class Unit extends MapObject {
    private double defence;
    private double offence;
    private final UnitType unitType;

    public Unit(Tile tile, PlayerSession playerSession, double defence, double offence, UnitType unitType) {
        super(tile, playerSession);
        this.defence = defence;
        this.offence = offence;
        this.unitType = unitType;
    }

    public Unit(Tile tile, PlayerSession playerSession, UnitType unitType) {
        super(tile, playerSession);
        this.unitType = unitType;
    }

    public Unit(PlayerSession playerSession, double defence, double offence, UnitType unitType) {
        super(playerSession);
        this.defence = defence;
        this.offence = offence;
        this.unitType = unitType;
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

    public UnitType getUnitType() {
        return unitType;
    }
}
