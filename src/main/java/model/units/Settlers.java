package model.units;

import model.Player;
import model.Tile;

public class Settlers extends Unit {
    private static final double DEFENCE = 10;
    private static final double OFFENCE = 5;

    public Settlers(Player player) {
        super(player, DEFENCE, OFFENCE);
    }
}
