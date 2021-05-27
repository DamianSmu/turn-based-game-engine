package com.example.engine.model.mapObject;

import com.example.engine.model.Map;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.utils.noise.OpenSimplex2S;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.tile.TileType;
import com.example.engine.model.utils.PositionXY;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ObjectsGenerator {

    public static List<Tile> generateTiles(int size, long seed) {
        final double FREQUENCY = 18d;
        final double LEVEL = 0.1d;

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

    public static void createInitialSettlersUnit(Map map, PlayerSession playerSession, long seed) {
        placeObjectOnMap(map, new Settlers(playerSession), seed);
    }

    private static void placeObjectOnMap(Map map, MapObject mapObject, long seed) {
        List<Tile> list = map.getTiles().stream().filter(t -> t.getType() == TileType.LAND).collect(Collectors.toList());
        Random random = new Random(seed);
        boolean placed = false;
        while (!placed) {
            Tile tile = list.get(random.nextInt(list.size()));
            if (tile.getMapObjects().size() == 0) {
                tile.addMapObject(mapObject);
                mapObject.setTile(tile);
                placed = true;
            }
        }
    }

    public static void placeResources(Map map, long seed, TileType type) {
        int RATE = map.getSize() / 5;
        List<Tile> list = map.getTiles().stream().filter(t -> t.getType() == TileType.LAND).collect(Collectors.toList());
        Random random = new Random(seed);
        for(int i = 0; i < RATE; i++){
            Tile tile = list.get(random.nextInt(list.size()));
            tile.setType(type);
        }

    }
}
