package com.example.engine.model.mapObject.units;

import com.example.engine.model.PlayerSession;
import com.example.engine.model.mapObject.MapObject;
import org.springframework.data.annotation.PersistenceConstructor;

public class Unit extends MapObject {
    private final UnitType unitType;

    @PersistenceConstructor
    public Unit(String id, PlayerSession playerSession, int actionInTurnNumber, double defence, double offence, UnitType unitType) {
        super(id, playerSession, actionInTurnNumber, defence, offence);
        this.unitType = unitType;
    }

    public Unit(PlayerSession playerSession, double defence, double offence, UnitType unitType) {
        super(playerSession, defence, offence);
        this.unitType = unitType;
    }

    public UnitType getUnitType() {
        return unitType;
    }
}
