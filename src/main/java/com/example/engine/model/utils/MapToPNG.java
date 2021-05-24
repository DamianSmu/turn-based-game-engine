package com.example.engine.model.utils;

import com.example.engine.model.Map;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.tile.TileType;
import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Unit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapToPNG {
    private static Image UNIT_TEX;
    private static Image SETTLEMENT_TEX;
    private static Image WATER_TEX;
    private static Image LAND_TEX;
    private static Image GOLD_TEX;
    private static Image IRON_TEX;

    static {
        try {
            UNIT_TEX = ImageIO.read(new File("flag.png"));
            SETTLEMENT_TEX = ImageIO.read(new File("castle.png"));
            WATER_TEX = ImageIO.read(new File("water_tex.png"));
            LAND_TEX = ImageIO.read(new File("land_tex.png"));
            GOLD_TEX = ImageIO.read(new File("gold_tex.png"));
            IRON_TEX= ImageIO.read(new File("iron_tex.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToPNG(Map map, int turnNumber) throws IOException {
        int size = map.getSize();
        final int TEX_SIZE = 64;
        Tile[][] tiles = map.getTileMatrix();
        BufferedImage image = new BufferedImage(size * TEX_SIZE, size * TEX_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4f);
        g.setColor(Color.BLACK);
        g.setFont(newFont);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                drawTile(g, x, y, TEX_SIZE, tiles[x][y].getType());
                for (MapObject o : tiles[x][y].getMapObjects()) {
                    if (o instanceof Unit) {
                        g.drawImage(UNIT_TEX, x * TEX_SIZE, y * TEX_SIZE, null);
                        g.drawString(o.getPlayer().getName(), x * TEX_SIZE, y * TEX_SIZE);
                    } else if (o instanceof Settlement) {
                        g.drawImage(SETTLEMENT_TEX, x * TEX_SIZE, y * TEX_SIZE, null);
                        g.drawString(o.getPlayer().getName(), x * TEX_SIZE, y * TEX_SIZE);
                    }
                }
            }
        }
        ImageIO.write(image, "png", new File("map" + turnNumber + ".png"));
    }


    private static void drawTile(Graphics2D graphics, int x, int y, int texSize, TileType type) {
        switch (type) {
            case LAND:
                graphics.drawImage(LAND_TEX, x * texSize, y * texSize, null);
                break;
            case WATER:
                graphics.drawImage(WATER_TEX, x * texSize, y * texSize, null);
                break;
            case IRON:
                graphics.drawImage(IRON_TEX, x * texSize, y * texSize, null);
                break;
            case GOLD:
                graphics.drawImage(GOLD_TEX, x * texSize, y * texSize, null);
                break;
        }
    }
}
