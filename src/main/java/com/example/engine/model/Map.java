package com.example.engine.model;


import com.example.engine.model.mapObject.ObjectsGenerator;
import com.example.engine.model.tile.Tile;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Map {

    @Id
    private String id = UUID.randomUUID().toString();

    private Tile[][] tileMatrix;
    private int size;
    private long seed;

    public Map(int size, long seed) {
        this.size = size;
        this.seed = seed;
        this.tileMatrix = ObjectsGenerator.generateTiles(this, size, seed);
    }

    public Tile getTileXY(int x, int y) {
        return this.tileMatrix[x][y];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Tile[][] getTileMatrix() {
        return tileMatrix;
    }

    public void setTileMatrix(Tile[][] tileMatrix) {
        this.tileMatrix = tileMatrix;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
