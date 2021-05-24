package com.example.engine.model.actions;

import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.utils.PositionXY;
import com.example.engine.model.Game;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.mapObject.units.Unit;

public class MoveUnit implements UserAction {
    private final Unit unit;
    private final int newX;
    private final int newY;

    public MoveUnit(Unit unit, int newX, int newY) {
        this.unit = unit;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void act(PlayerSession playerSession, Game game) {
        if (!unit.getPlayer().equals(playerSession)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(playerSession, game.getTurnNumber()));
            return;
        }

        if (unit.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(playerSession, game.getTurnNumber()));
            return;
        }

        PositionXY pos = unit.getTile().getPosition();
        if (Math.abs(pos.getX() - newX) > 1 || Math.abs(pos.getY() - newY) > 1) {
            GameLog.getInstance().addEntry(new LogEntry(playerSession, game.getTurnNumber(), "Cannot move, too far."));
            return;
        }

        Tile newTile = game.getMap().getTileXY(newX, newY);
        if(newTile.isFreeToPlaceObject()) {
            unit.move(newTile);
            unit.setActionInTurnNumber(game.getTurnNumber());
        } else {
            GameLog.getInstance().addEntry(new LogEntry(playerSession, game.getTurnNumber(), "Cannot put on non empty tile."));
        }
    }
}
