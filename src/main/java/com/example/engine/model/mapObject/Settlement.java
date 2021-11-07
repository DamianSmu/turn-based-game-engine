package com.example.engine.model.mapObject;

import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.TileType;
import org.springframework.data.annotation.PersistenceConstructor;

public class Settlement extends MapObject implements GoldApplier, IronApplier {

    private static final double INIT_DEFENCE = 20;
    private static final int GOLD_PRODUCTION_WITH_GOLD_DEPOSIT = 50;
    private static final int GOLD_PRODUCTION_WITHOUT_GOLD_DEPOSIT = 5;
    private static final int IRON_PRODUCTION_WITH_IRON_DEPOSIT = 50;

    private String name;
    private double defence;

    @PersistenceConstructor
    public Settlement(PlayerSession playerSession, int actionInTurnNumber, String name, double defence) {
        super(playerSession, actionInTurnNumber);
        this.name = name;
        this.defence = defence;
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
        return super.getTile().getType() == TileType.IRON ? IRON_PRODUCTION_WITH_IRON_DEPOSIT : 0;
    }
}
