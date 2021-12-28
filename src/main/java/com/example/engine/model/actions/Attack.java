package com.example.engine.model.actions;


import com.example.engine.model.Game;
import com.example.engine.model.User;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.MapObject;
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
    public void act(User user, Game game) {
        if (!unit.getUser().equals(user)) {
            throw new CannotResolveActionException("Object does not belong to player.");
        }

        if (unit.actionInTurnNumber() == game.getTurnNumber()) {
            throw new CannotResolveActionException("Unit has taken action in current turn.");
        }

        if (unit.getUser().equals(mapObject.getUser())) {
            throw new CannotResolveActionException("Cannot attack. this object belongs to you.");
        }

        PositionXY settlementPos = mapObject.getTile().getPosition();
        PositionXY pos = unit.getTile().getPosition();
        if (Math.abs(pos.getX() - settlementPos.getX()) > 1 || Math.abs(pos.getY() - settlementPos.getY()) > 1) {
            throw new CannotResolveActionException("Cannot attack, too far.");
        }

        Random rand = new Random();
        mapObject.setDefence(mapObject.getDefence() - unit.getOffence() * (rand.nextDouble() + 0.5d));
        unit.setDefence(unit.getDefence() - mapObject.getOffence() * (rand.nextDouble() + 0.5d));

        if (mapObject.getDefence() <= 0) {
            unit.getTile().moveUnit(unit, mapObject.getTile());
            mapObject.getTile().deleteMapObject();
            unit.setActionInTurnNumber(game.getTurnNumber());
        }

        if (unit.getDefence() <= 0) {
            unit.getTile().deleteMapObject();
        }
    }
}
