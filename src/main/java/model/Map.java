package model;

import model.units.Unit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Map {
    private Tile[][] tileMatrix;
    private int size;

    public Map(int size) {
        this.size = size;
        this.tileMatrix = MapTilesGenerator.generate(this, size);
    }

    public Tile getTileXY(int x, int y){
        return this.tileMatrix[y][x];
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
        Image imageFlag = ImageIO.read(new File("flag.png"));
        Image imageSettlement = ImageIO.read(new File("castle.png"));

        int size = map.getSize();
        int texSize = 64;
        Tile[][] tiles = map.getTileMatrix();
        BufferedImage image = new BufferedImage(size * texSize, size * texSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.8f);
        g.setColor(Color.BLACK);
        g.setFont(newFont);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                //TileTypes
                int rgb = tiles[x][y].getType() == TileType.LAND ? 0x808080 : 0x99CCFF;
                int[] rgbArr = new int[texSize * texSize];
                Arrays.fill(rgbArr, rgb);
                image.setRGB(x * texSize, y * texSize, texSize, texSize, rgbArr, 0, 0);
                //MapObjects
                for (MapObject o : tiles[x][y].getMapObjects()) {
                    if (o instanceof Unit) {
                        g.drawImage(imageFlag, x * texSize, y * texSize, null);
                        g.drawString(o.getPlayer().getName(), x * texSize, y * texSize);
                    } else if (o instanceof Settlement) {
                        g.drawImage(imageSettlement, x * texSize, y * texSize, null);
                        g.drawString(o.getPlayer().getName(), x * texSize, y * texSize);
                    }
                }
            }
        }
        ImageIO.write(image, "png", new File("map.png"));
    }


}
