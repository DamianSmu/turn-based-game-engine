package com.example.engine.model.mapObject;

import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.TileType;
import org.springframework.data.annotation.PersistenceConstructor;

public class Settlement extends MapObject implements GoldApplier, IronApplier {

    private static final double INIT_DEFENCE = 200;
    private static final int GOLD_PRODUCTION_WITH_GOLD_DEPOSIT = 50;
    private static final int GOLD_PRODUCTION_WITHOUT_GOLD_DEPOSIT = 5;
    private static final int IRON_PRODUCTION_WITH_IRON_DEPOSIT = 50;

    @PersistenceConstructor
    public Settlement(String id, PlayerSession playerSession, int actionInTurnNumber, double defence, double offence) {
        super(id, playerSession, actionInTurnNumber, defence, offence);

    }

    public Settlement(PlayerSession playerSession) {
        super(playerSession, INIT_DEFENCE, INIT_DEFENCE);
    }

    @Override
    public int applyGold() {
        return super.getTile().getType() == TileType.GOLD ? GOLD_PRODUCTION_WITH_GOLD_DEPOSIT : GOLD_PRODUCTION_WITHOUT_GOLD_DEPOSIT;
    }

    @Override
    public int applyIron() {
        return super.getTile().getType() == TileType.IRON ? IRON_PRODUCTION_WITH_IRON_DEPOSIT : 0;
    }

    public String getUnitType() {
        return "SETTLEMENT";
    }
}
