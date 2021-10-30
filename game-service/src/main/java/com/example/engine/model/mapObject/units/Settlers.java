package com.example.engine.model.mapObject.units;

import com.example.engine.model.PlayerSession;
import com.example.engine.model.mapObject.GoldApplier;
import org.springframework.data.annotation.PersistenceConstructor;

public class Settlers extends Unit implements GoldApplier {
    private static final double DEFENCE = 10;
    private static final double OFFENCE = 5;
    private static final int GOLD_COST_PER_TURN = -5;

    public Settlers(PlayerSession playerSession) {
        super(playerSession, DEFENCE, OFFENCE, UnitType.SETTLERS);
    }


    @PersistenceConstructor
    public Settlers(PlayerSession playerSession, int actionInTurnNumber, double defence, double offence, UnitType unitType) {
        super(playerSession, actionInTurnNumber, defence, offence, unitType);
    }

    @Override
    public int applyGold() {
        return GOLD_COST_PER_TURN;
    }
}
