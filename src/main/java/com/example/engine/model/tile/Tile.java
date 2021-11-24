package com.example.engine.model.tile;


import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.utils.PositionXY;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;


public class Tile {
    private TileType type;
    private PositionXY position;

    private MapObject mapObject;

    public Tile(TileType type, PositionXY position) {
        this.type = type;
        this.position = position;

    }

    @PersistenceConstructor
    public Tile(TileType type, PositionXY position, MapObject mapObject) {
        this.type = type;
        this.position = position;
        this.mapObject = mapObject;
        if(mapObject != null){
            mapObject.setTile(this);
        }
    }

    @JsonIgnore
    public boolean isEmpty() {
        return mapObject == null;
    }

    public void setMapObject(MapObject mapObject) {
        this.mapObject = mapObject;
        mapObject.setTile(this);
    }

    @JsonIgnore
    public boolean isFreeToPlaceObject() {
        return mapObject == null;
    }

    public void removeMapObject(MapObject mapObject) {
        this.mapObject = null;
        mapObject.setTile(null);
    }

    public void moveMapObject(MapObject mapObject, Tile tile) {
        removeMapObject(mapObject);
        tile.setMapObject(mapObject);
    }

    public void deleteMapObject(MapObject mapObject) {
        this.mapObject = null;
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

    public MapObject getMapObject() {
        return mapObject;
    }

}
