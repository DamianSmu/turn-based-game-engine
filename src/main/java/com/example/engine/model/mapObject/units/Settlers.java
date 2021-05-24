package com.example.engine.model.mapObject.units;

import com.example.engine.model.PlayerSession;
import com.example.engine.model.mapObject.GoldApplier;

public class Settlers extends Unit implements GoldApplier {
    private static final double DEFENCE = 10;
    private static final double OFFENCE = 5;
    private final int GOLD_COST_PER_TURN = -5;

    public Settlers(PlayerSession playerSession) {
        super(playerSession, DEFENCE, OFFENCE, UnitType.SETTLERS);
    }

    @Override
    public int applyGold() {
        return GOLD_COST_PER_TURN;
    }
}
