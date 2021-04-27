package model.units;

import model.MapObject;
import model.Player;
import model.Tile;

public class Unit extends MapObject {
    private double defence;
    private double offence;

    public Unit(Tile tile, Player player, double defence, double offence) {
        super(tile, player);
        this.defence = defence;
        this.offence = offence;
    }

    public Unit(Tile tile, Player player) {
        super(tile, player);
    }

    public Unit(Player player, double defence, double offence) {
        super(player);
        this.defence = defence;
        this.offence = offence;
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public double getOffence() {
        return offence;
    }

    public void setOffence(double offence) {
        this.offence = offence;
    }
}
