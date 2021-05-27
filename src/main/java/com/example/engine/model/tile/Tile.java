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

    private List<MapObject> mapObjects = new ArrayList<>();

    public Tile(TileType type, PositionXY position) {
        this.type = type;
        this.position = position;

    }

    @PersistenceConstructor
    public Tile(TileType type, PositionXY position, List<MapObject> mapObjects) {
        this.type = type;
        this.position = position;
        this.mapObjects = mapObjects;
        mapObjects.forEach(m -> m.setTile(this));
    }

    //    @JsonIgnore
//    public List<Tile> getNeighbours() {
//        int size = map.getSize();
//        PositionXY pos = this.getPosition();
//        int x = pos.getX();
//        int y = pos.getY();
//        List<Tile> result = new ArrayList<>();
//        if (x > 0) result.add(map.getTileXY(x - 1, y));
//        if (y > 0) result.add(map.getTileXY(x, y - 1));
//        if (x < size) result.add(map.getTileXY(x + 1, y));
//        if (y < size) result.add(map.getTileXY(x, y + 1));
//        if (x > 0 && y < size) result.add(map.getTileXY(x - 1, y + 1));
//        if (x > 0 && y > 0) result.add(map.getTileXY(x - 1, y - 1));
//        if (x < size && y < size) result.add(map.getTileXY(x + 1, y + 1));
//        if (y < size && x > 0) result.add(map.getTileXY(x + 1, y - 1));
//        return result;
//    }

    @JsonIgnore
    public boolean isEmpty() {
        return mapObjects.isEmpty();
    }

    public void addMapObject(MapObject mapObject) {
        mapObjects.add(mapObject);
        mapObject.setTile(this);
    }

    @JsonIgnore
    public boolean isFreeToPlaceObject() {
        return mapObjects.isEmpty();
    }

    public void removeMapObject(MapObject mapObject) {
        mapObjects.remove(mapObject);
        mapObject.setTile(null);
    }

    public void moveMapObject(MapObject mapObject, Tile tile) {
        mapObjects.remove(mapObject);
        tile.addMapObject(mapObject);
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

    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    public void setMapObjects(List<MapObject> mapObjects) {
        this.mapObjects = mapObjects;
    }

}
