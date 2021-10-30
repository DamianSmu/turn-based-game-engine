package com.example.engine.model.actions;

import com.example.engine.model.Game;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.utils.PositionXY;

import java.util.Random;

public class AttackSettlement implements UserAction {
    private final Unit unit;
    private final Settlement settlement;

    public AttackSettlement(Unit unit, Settlement settlement) {
        this.unit = unit;
        this.settlement = settlement;
    }

    @Override
    public void act(PlayerSession playerSession, Game game) {
        if (!unit.getPlayerSession().equals(playerSession)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(playerSession, game.getTurnNumber()));
            return;
        }

        if (unit.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(playerSession, game.getTurnNumber()));
            return;
        }

        PositionXY settlementPos = settlement.getTile().getPosition();
        PositionXY pos = unit.getTile().getPosition();
        if (Math.abs(pos.getX() - settlementPos.getX()) > 1 || Math.abs(pos.getY() - settlementPos.getY()) > 1) {
            GameLog.getInstance().addEntry(new LogEntry(playerSession, game.getTurnNumber(), "Cannot attack, too far."));
            return;
        }

        Random rand = new Random();
        settlement.setDefence(settlement.getDefence() - unit.getOffence() * (rand.nextDouble() + 0.5d));

        if (settlement.getDefence() <= 0) {
            unit.getTile().moveMapObject(unit, settlement.getTile());
            settlement.getTile().removeMapObject(settlement);
            unit.setActionInTurnNumber(game.getTurnNumber());
        }
    }
}
