package com.example.engine.model.mapObject;

import com.example.engine.model.Map;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.tile.TileType;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.noise.OpenSimplex2S;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ObjectsGenerator {

    public static Tile[][] generateTiles(Map map, int size, long seed) {
        final double FREQUENCY = 18d;
        final double LEVEL = 0.1d;

        OpenSimplex2S noise = new OpenSimplex2S(seed);
        Tile[][] result = new Tile[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {

                double value = noise.noise2(x / FREQUENCY, y / FREQUENCY);
                TileType type = value < LEVEL ? TileType.WATER : TileType.LAND;
                result[x][y] = new Tile(map, type, x, y);
            }
        }
        return result;
    }

    public static void createInitialSettlersUnit(Map map, PlayerSession playerSession, long seed) {
        placeObjectOnMap(map, new Settlers(playerSession), seed);
    }

    private static void placeObjectOnMap(Map map, MapObject mapObject, long seed) {
        Tile[][] tiles = map.getTileMatrix();
        List<Tile> list = Arrays
                .stream(tiles)
                .flatMap(Arrays::stream)
                .filter(x -> x.getType() == TileType.LAND)
                .collect(Collectors.toList());
        Collections.shuffle(list, new Random(seed));
        boolean placed = false;
        int idx = 0;
        while (!placed) {
            Tile tile = list.get(idx);
            if (tile.getMapObjects().size() == 0) {
                tile.addMapObject(mapObject);
                placed = true;
            }
            idx++;
        }
    }

    public static void placeResources(Map map, long seed, TileType type) {
        int RATE = map.getSize() / 5;
        Tile[][] tiles = map.getTileMatrix();
        List<Tile> list = Arrays
                .stream(tiles)
                .flatMap(Arrays::stream)
                .filter(x -> x.getType() == TileType.LAND)
                .collect(Collectors.toList());
        Collections.shuffle(list, new Random(seed));
        list.subList(0, RATE).forEach(x -> x.setType(type));
    }
}
