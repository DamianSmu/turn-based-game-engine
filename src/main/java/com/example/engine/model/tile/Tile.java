package com.example.engine.model.tile;


import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.utils.PositionXY;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;


public class Tile {
    private TileType type;
    private PositionXY position;

    private Unit unit;
    private Settlement settlement;

    public Tile(TileType type, PositionXY position) {
        this.type = type;
        this.position = position;
    }

    @PersistenceConstructor
    public Tile(TileType type, PositionXY position, Unit unit, Settlement settlement) {
        this.type = type;
        this.position = position;
        this.unit = unit;
        this.settlement = settlement;
        if(unit != null){
            unit.setTile(this);
        }
        if(settlement != null){
            settlement.setTile(this);
        }
    }

    @JsonIgnore
    public boolean isEmpty() {
        return (unit == null && settlement == null);
    }

    @JsonIgnore
    public boolean isFreeToMove() {
        return unit == null;
    }

    public void removeUnit(Unit unit) {
        this.unit = null;
        unit.setTile(null);
    }

    public void removeSettlement(Settlement settlement) {
        this.settlement = null;
        settlement.setTile(null);
    }

    public void moveUnit(Unit unit, Tile tile) {
        removeUnit(unit);
        tile.setUnit(unit);
    }

    public void deleteMapObject() {
        this.unit = null;
        this.settlement = null;
    }


    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public PositionXY getPosition() {
        return position;
    }

    public void setPosition(PositionXY position) {
        this.position = position;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
        unit.setTile(this);

    }

    public Settlement getSettlement() {
        return settlement;

    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
        settlement.setTile(this);
    }

    @Override
    public String toString() {
        return "Tile{" + "type=" + type + ", position=" + position.toString() + ", unit=" + unit + ", settlement=" + settlement + '}';
    }
}
