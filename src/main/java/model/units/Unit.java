package model.units;

import model.MapObject;
import model.Player;
import model.Tile;

public class Unit extends MapObject {
    private double defence;
    private double offence;
    private int actionInTurnNumber;

    public Unit(Tile tile, Player player, double defence, double offence) {
        super(tile, player);
        this.defence = defence;
        this.offence = offence;
        actionInTurnNumber = -1;
    }

    public Unit(Tile tile, Player player) {
        super(tile, player);
        actionInTurnNumber = -1;
    }

    public int actionInTurnNumber() {
        return actionInTurnNumber;
    }

    public void setActionInTurnNumber(int turnNumber) {
        this.actionInTurnNumber = turnNumber;
    }
}
