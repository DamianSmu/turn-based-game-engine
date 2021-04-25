package model.actions;

import model.Game;
import model.Player;
import model.PositionXY;
import model.Tile;
import model.logs.GameLog;
import model.logs.LogEntry;
import model.units.Unit;

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
    public void act(Player player, Game game) {
        if (!unit.getPlayer().equals(player)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(player, game.getTurnNumber()));
            return;
        }

        if (unit.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(player, game.getTurnNumber()));
            return;
        }

        PositionXY pos = unit.getTile().getPosition();
        if (Math.abs(pos.getX() - newX) > 1 || Math.abs(pos.getY() - newY) > 1) {
            GameLog.getInstance().addEntry(new LogEntry(player, game.getTurnNumber(), "Cannot move, too far."));
            return;
        }

        Tile newTile = game.getMap().getTileXY(newX, newY);
        unit.move(newTile);
        unit.setActionInTurnNumber(game.getTurnNumber());
    }
}
