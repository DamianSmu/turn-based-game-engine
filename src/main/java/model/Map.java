package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Map {

    private Tile[][] tileMatrix;
    private int size;

    public Map(int size){
        this.size = size;
        this.tileMatrix = MapTilesGenerator.generate(size);
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

    public static void saveToPNG(Map map) throws IOException {
        int size = map.getSize();
        Tile[][] tiles = map.getTileMatrix();
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                int v = tiles[x][y].getType() == TileType.LAND ? 1 : 0;
                int rgb = 0x010101 * (int)((v + 1) * 127.5);
                image.setRGB(x, y, rgb);
            }
        }
        ImageIO.write(image, "png", new File("map.png"));
    }
}
