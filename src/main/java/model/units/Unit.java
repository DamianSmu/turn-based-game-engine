package model.units;

import model.MapObject;
import model.Player;
import model.Tile;

public class Unit extends MapObject {
    private double defence;
    private double offence;
    private boolean moved;

    public Unit(Tile tile, Player player) {
        super(tile, player);
    }
}
