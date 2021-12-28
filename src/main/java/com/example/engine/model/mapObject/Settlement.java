package com.example.engine.model.mapObject;

import com.example.engine.model.User;
import com.example.engine.model.tile.TileType;
import org.springframework.data.annotation.PersistenceConstructor;

public class Settlement extends MapObject implements ResourceApplier {

    private static final double INIT_DEFENCE = 10;
    private static final int GOLD_PRODUCTION_WITH_GOLD_DEPOSIT = 50;
    private static final int GOLD_PRODUCTION_WITHOUT_GOLD_DEPOSIT = 10;
    private static final int IRON_PRODUCTION_WITH_IRON_DEPOSIT = 50;

    private int goldAmount;
    private int ironAmount;

    @PersistenceConstructor
    public Settlement(User user, int actionInTurnNumber, double defence, double offence, int goldAmount, int ironAmount) {
        super(user, actionInTurnNumber, defence, offence);
        this.goldAmount = goldAmount;
        this.ironAmount = ironAmount;

    }

    public Settlement(User user) {
        super(user, INIT_DEFENCE, 0);
        this.goldAmount = 0;
        this.ironAmount = 0;
    }

    @Override
    public void applyResources() {
        goldAmount += super.getTile().getType() == TileType.GOLD ? GOLD_PRODUCTION_WITH_GOLD_DEPOSIT : GOLD_PRODUCTION_WITHOUT_GOLD_DEPOSIT;
        ironAmount += super.getTile().getType() == TileType.IRON ? IRON_PRODUCTION_WITH_IRON_DEPOSIT : 0;
    }

    public String getType() {
        return "SETTLEMENT";
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public int getIronAmount() {
        return ironAmount;
    }

    public void setIronAmount(int ironAmount) {
        this.ironAmount = ironAmount;
    }
}
