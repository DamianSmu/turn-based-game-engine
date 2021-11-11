package com.example.engine.model.actions;


import com.example.engine.model.Game;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.utils.PositionXY;

import java.util.Random;

public class Attack implements UserAction {
    private final Unit unit;
    private final MapObject mapObject;

    public Attack (Unit unit, MapObject mapObject) {
        this.unit = unit;
        this.mapObject = mapObject;
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

        PositionXY settlementPos = mapObject.getTile().getPosition();
        PositionXY pos = unit.getTile().getPosition();
        if (Math.abs(pos.getX() - settlementPos.getX()) > 1 || Math.abs(pos.getY() - settlementPos.getY()) > 1) {
            GameLog.getInstance().addEntry(new LogEntry(playerSession, game.getTurnNumber(), "Cannot attack, too far."));
            return;
        }

        Random rand = new Random();
        mapObject.setDefence(mapObject.getDefence() - unit.getOffence() * (rand.nextDouble() + 0.5d));
        unit.setDefence(unit.getDefence() - mapObject.getOffence() * (rand.nextDouble() + 0.5d));

        if (mapObject.getDefence() <= 0) {
            unit.getTile().moveMapObject(unit, mapObject.getTile());
            mapObject.getTile().deleteMapObject(mapObject);
            unit.setActionInTurnNumber(game.getTurnNumber());
        }

        if (unit.getDefence() <= 0) {
            unit.getTile().deleteMapObject(unit);
        }
    }
}
