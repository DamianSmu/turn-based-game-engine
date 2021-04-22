package model;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private String name;
    private Set<MapObject> mapObjects;

    public Player(String name) {
        this.name = name;
        mapObjects = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MapObject> getMapObjects() {
        return mapObjects;
    }

    public void setMapObjects(Set<MapObject> mapObjects) {
        this.mapObjects = mapObjects;
    }
}
