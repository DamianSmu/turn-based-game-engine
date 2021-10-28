package com.example.engine.model.utils;

import com.example.engine.model.Map;
import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.mapObject.units.Warriors;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.tile.TileType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MapToPNG {
    private static Image SETTLERS_TEX;
    private static Image SETTLEMENT_TEX;
    private static Image WATER_TEX;
    private static Image LAND_TEX;
    private static Image GOLD_TEX;
    private static Image IRON_TEX;
    private static Image WARRIORS_TEX;

    static
    {
        try
        {
            SETTLERS_TEX = ImageIO.read(new File("flag.png"));
            SETTLEMENT_TEX = ImageIO.read(new File("castle.png"));
            WATER_TEX = ImageIO.read(new File("water_tex.png"));
            LAND_TEX = ImageIO.read(new File("land_tex.png"));
            GOLD_TEX = ImageIO.read(new File("gold_tex.png"));
            IRON_TEX = ImageIO.read(new File("iron_tex.png"));
            WARRIORS_TEX = ImageIO.read(new File("tent.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void saveToFile(Map map, int turnNumber) throws IOException {
        BufferedImage image = getImage(map);
        ImageIO.write(image, "png", new File("map" + turnNumber + ".png"));
    }

    public static byte[] getBytes(Map map) throws IOException {
        BufferedImage image = getImage(map);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    private static BufferedImage getImage(Map map) {
        int size = map.getSize();
        final int TEX_SIZE = 64;
        List<Tile> tiles = map.getTiles();
        BufferedImage image = new BufferedImage(size * TEX_SIZE, size * TEX_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4f);
        g.setColor(Color.BLACK);
        g.setFont(newFont);

        for (Tile t : tiles)
        {
            int x = t.getPosition().getX();
            int y = t.getPosition().getY();
            drawTile(g, x, y, TEX_SIZE, t.getType());
            MapObject o = t.getMapObject();
            if (o instanceof Settlers)
            {
                g.drawImage(SETTLERS_TEX, x * TEX_SIZE, y * TEX_SIZE, null);
                g.drawString(o.getPlayerSession().getUser().getUsername(), x * TEX_SIZE, y * TEX_SIZE);
            } else if (o instanceof Warriors)
            {
                g.drawImage(WARRIORS_TEX, x * TEX_SIZE, y * TEX_SIZE, null);
                g.drawString(o.getPlayerSession().getUser().getUsername(), x * TEX_SIZE, y * TEX_SIZE);
            } else if (o instanceof Settlement)
            {
                g.drawImage(SETTLEMENT_TEX, x * TEX_SIZE, y * TEX_SIZE, null);
                g.drawString(o.getPlayerSession().getUser().getUsername(), x * TEX_SIZE, y * TEX_SIZE);
            }
        }

        return image;
    }

    private static void drawTile(Graphics2D graphics, int x, int y, int texSize, TileType type) {
        switch (type)
        {
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
