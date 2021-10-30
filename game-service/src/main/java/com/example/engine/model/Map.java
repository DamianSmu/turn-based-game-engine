package com.example.engine.model;


import com.example.engine.model.mapObject.ObjectsGenerator;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.utils.PositionXY;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private List<Tile> tiles = new ArrayList<>();

    private int size;

    private long seed;

    public Map(int size, long seed) {
        this.size = size;
        this.seed = seed;
    }


    @PersistenceConstructor
    public Map(List<Tile> tiles, int size, long seed) {
        this.tiles = tiles;
        this.size = size;
        this.seed = seed;
    }

    public void createTiles() {
        this.tiles = ObjectsGenerator.generateTiles(size, seed);
    }

    public Tile getTileXY(int x, int y) {
        Tile tile = tiles.get(x * size + y);
        if (tile.getPosition().getX() != x || tile.getPosition().getY() != y) {
            throw new RuntimeException("Invalid order in tiles list");
        }
        return tile;
    }

    public Tile getTileFromPosition(PositionXY positionXY) {
        Tile tile = tiles.get(positionXY.getX() * size + positionXY.getY());
        if (tile.getPosition().getX() != positionXY.getX() || tile.getPosition().getY() != positionXY.getY()) {
            throw new RuntimeException("Invalid order in tiles list");
        }
        return tile;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tileMatrix) {
        this.tiles = tileMatrix;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
