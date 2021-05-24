package com.example.engine.model.mapObject.units;

import com.example.engine.model.PlayerSession;
import com.example.engine.model.mapObject.GoldApplier;

public class Warriors extends Unit implements GoldApplier {
    private static final double DEFENCE = 50;
    private static final double OFFENCE = 30;
    private final int GOLD_COST_PER_TURN = -20;


    public Warriors(PlayerSession playerSession) {
        super(playerSession, DEFENCE, OFFENCE, UnitType.WARRIORS);
    }

    @Override
    public int applyGold() {
        return GOLD_COST_PER_TURN;
    }
}
