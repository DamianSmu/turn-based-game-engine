package com.example.engine.model.mapObject;

import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.tile.TileType;

public class Settlement extends MapObject implements GoldApplier, IronApplier {

    private final double INIT_DEFENCE = 20;
    private final int GOLD_PRODUCTION_WITH_GOLD_DEPOSIT = 50;
    private final int GOLD_PRODUCTION_WITHOUT_GOLD_DEPOSIT = 5;
    private final int IRON_PRODUCTION = 50;

    private String name;
    private double defence;

    public Settlement(Tile tile, PlayerSession playerSession) {
        super(tile, playerSession);
    }

    public Settlement(PlayerSession playerSession) {
        super(playerSession);
        this.defence = INIT_DEFENCE;
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    @Override
    public int applyGold() {
        return super.getTile().getType() == TileType.GOLD ? GOLD_PRODUCTION_WITH_GOLD_DEPOSIT : GOLD_PRODUCTION_WITHOUT_GOLD_DEPOSIT;
    }

    @Override
    public int applyIron() {
        return super.getTile().getType() == TileType.IRON ? IRON_PRODUCTION : 0;
    }
}
