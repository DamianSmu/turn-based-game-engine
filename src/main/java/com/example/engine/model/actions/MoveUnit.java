package com.example.engine.model.actions;

import com.example.engine.model.Game;
import com.example.engine.model.User;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.utils.PositionXY;

public class MoveUnit implements UserAction {
    private final Unit unit;
    private final int newX;
    private final int newY;

    public MoveUnit(Unit unit, PositionXY positionXY) {
        this.unit = unit;
        this.newX = positionXY.getX();
        this.newY = positionXY.getY();
    }

    @Override
    public void act(User user, Game game) {
        if (!unit.getUser().equals(user)) {
            throw new CannotResolveActionException("Object does not belong to player.");
        }

        if (unit.actionInTurnNumber() == game.getTurnNumber()) {
            throw new CannotResolveActionException("Unit has taken action in current turn.");
        }

        PositionXY pos = unit.getTile().getPosition();
        if (Math.abs(pos.getX() - newX) > 1 || Math.abs(pos.getY() - newY) > 1) {
            throw new CannotResolveActionException("Cannot move, too far.");

        }

        Tile newTile = game.getMap().getTileXY(newX, newY);
        if (newTile.isFreeToPlaceObject()) {
            unit.getTile().moveMapObject(unit, newTile);
            unit.setActionInTurnNumber(game.getTurnNumber());
        } else {
            throw new CannotResolveActionException("Cannot put on non empty tile.");
        }
    }
}
