package com.example.engine.model.mapObject;

import com.example.engine.model.GameMap;
import com.example.engine.model.User;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.tile.TileType;
import com.example.engine.model.utils.PositionXY;
import com.example.engine.model.utils.noise.OpenSimplex2S;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ObjectsGenerator {

    public static List<Tile> generateTiles(int size, long seed) {
        final double FREQUENCY = 8d;
        final double LEVEL = 0.05d;

        OpenSimplex2S noise = new OpenSimplex2S(seed);
        List<Tile> result = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {

                double value = noise.noise2(x / FREQUENCY, y / FREQUENCY);
                TileType type = value < LEVEL ? TileType.WATER : TileType.LAND;
                result.add(new Tile(type, new PositionXY(x, y)));
            }
        }
        return result;
    }

    public static void createInitialSettlersUnit(GameMap gameMap, User user, long seed) {
        placeObjectOnMap(gameMap, new Settlers(user), seed);
    }

    private static void placeObjectOnMap(GameMap gameMap, MapObject mapObject, long seed) {
        List<Tile> list = gameMap.getTiles().stream().filter(t -> t.getType() == TileType.LAND).collect(Collectors.toList());
        Random random = new Random(seed);
        boolean placed = false;
        while (!placed) {
            Tile tile = list.get(random.nextInt(list.size()));
            if (tile.isEmpty()) {
                tile.setMapObject(mapObject);
                mapObject.setTile(tile);
                placed = true;
            }
        }
    }

    public static void placeResources(GameMap gameMap, long seed, TileType type) {
        int RATE = gameMap.getSize() / 5;
        List<Tile> list = gameMap.getTiles().stream().filter(t -> t.getType() == TileType.LAND).collect(Collectors.toList());
        Random random = new Random(seed);
        for (int i = 0; i < RATE; i++) {
            Tile tile = list.get(random.nextInt(list.size()));
            tile.setType(type);
        }

    }
}
