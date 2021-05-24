package com.example.engine.model.tile;

import com.example.engine.model.Map;
import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.utils.PositionXY;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.*;


public class Tile {

    @DBRef(lazy = true)
    @JsonIgnore
    private Map map;

    private TileType type;
    private PositionXY position;
    @DBRef(lazy = true)
    private Set<MapObject> mapObjects;

    public Tile() {
    }

    public Tile(Map map, TileType type, int x, int y) {
        this.map = map;
        this.type = type;
        this.position = new PositionXY(x, y);
        mapObjects = new HashSet<>();
    }

    @JsonIgnore
    public List<Tile> getNeighbours() {
        int size = map.getSize();
        PositionXY pos = this.getPosition();
        int x = pos.getX();
        int y = pos.getY();
        List<Tile> result = new ArrayList<>();
        if (x > 0) result.add(map.getTileXY(x - 1, y));
        if (y > 0) result.add(map.getTileXY(x, y - 1));
        if (x < size) result.add(map.getTileXY(x + 1, y));
        if (y < size) result.add(map.getTileXY(x, y + 1));
        if (x > 0 && y < size) result.add(map.getTileXY(x - 1, y + 1));
        if (x > 0 && y > 0) result.add(map.getTileXY(x - 1, y - 1));
        if (x < size && y < size) result.add(map.getTileXY(x + 1, y + 1));
        if (y < size && x > 0) result.add(map.getTileXY(x + 1, y - 1));
        return result;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return mapObjects.isEmpty();
    }

    public void addMapObject(MapObject mapObject) {
        mapObjects.add(mapObject);
        mapObject.setTile(this);
    }

    @JsonIgnore
    public boolean isFreeToPlaceObject(){
        return mapObjects.isEmpty();
    }

    public void deleteMapObject(MapObject mapObject) {
        mapObjects.remove(mapObject);
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

    public com.example.engine.model.Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Set<MapObject> getMapObjects() {
        return mapObjects;
    }

    public void setMapObjects(Set<MapObject> mapObjects) {
        this.mapObjects = mapObjects;
    }


}
