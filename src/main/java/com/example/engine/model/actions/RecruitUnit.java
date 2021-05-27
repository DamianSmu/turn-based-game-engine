package com.example.engine.model.actions;

import com.example.engine.model.Game;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.mapObject.units.UnitType;
import com.example.engine.model.mapObject.units.Warriors;

public class RecruitUnit implements UserAction {
    private final Settlement settlement;
    private final UnitType unitType;

    public RecruitUnit(Settlement settlement, UnitType unitType) {
        this.settlement = settlement;
        this.unitType = unitType;
    }

    @Override
    public void act(PlayerSession playerSession, Game game) {
        if (!settlement.getPlayerSession().equals(playerSession)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(playerSession, game.getTurnNumber()));
            return;
        }

        if (settlement.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(playerSession, game.getTurnNumber()));
            return;
        }

        Unit unit;
        switch (unitType) {
            case SETTLERS:
                unit = new Settlers(playerSession);
                break;
            case WARRIORS:
                unit = new Warriors(playerSession);
                break;
            default:
                GameLog.getInstance().addEntry(new LogEntry(playerSession, game.getTurnNumber(), "Invalid unit type."));
                return;
        }

        settlement.getTile().addMapObject(unit);
        settlement.setActionInTurnNumber(game.getTurnNumber());
        unit.setActionInTurnNumber(game.getTurnNumber());
    }
}
