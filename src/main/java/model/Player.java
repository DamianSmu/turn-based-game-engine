package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<MapObject> mapObjects;

    public Player(String name) {
        this.name = name;
        mapObjects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    public void addMapObject(MapObject mapObject){
        this.mapObjects.add(mapObject);
    }

    public void setMapObjects(List<MapObject> mapObjects) {
        this.mapObjects = mapObjects;
    }
}
