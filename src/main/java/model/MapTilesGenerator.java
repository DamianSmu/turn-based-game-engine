package model;

import model.noise.OpenSimplex2S;

import java.util.Random;

public class MapTilesGenerator {
    private static final double FREQUENCY = 18d;
    private static final double LEVEL = 0.1d;

    public static Tile[][] generate(Map map, int size, long seed) {
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
}
