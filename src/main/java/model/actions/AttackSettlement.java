package model.actions;

import model.Game;
import model.Player;
import model.Settlement;
import model.Tile;
import model.logs.GameLog;
import model.logs.LogEntry;
import model.units.Unit;
import model.utils.PositionXY;

import java.util.Random;

public class AttackSettlement implements UserAction {
    private Unit unit;
    private Settlement settlement;

    public AttackSettlement(Unit unit, Settlement settlement) {
        this.unit = unit;
        this.settlement = settlement;
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

        PositionXY settlementPos = settlement.getTile().getPosition();
        PositionXY pos = unit.getTile().getPosition();
        if (Math.abs(pos.getX() - settlementPos.getX()) > 1 || Math.abs(pos.getY() - settlementPos.getY()) > 1) {
            GameLog.getInstance().addEntry(new LogEntry(player, game.getTurnNumber(), "Cannot attack, too far."));
            return;
        }

        Random rand = new Random();
        settlement.setDefence(settlement.getDefence() - unit.getOffence() * (rand.nextDouble() + 0.5d));

        if(settlement.getDefence() <= 0){
            unit.move(settlement.getTile());
            settlement.delete();

            Tile newTile = game.getMap().getTileXY(settlementPos.getX(), settlementPos.getY());
            unit.move(newTile);
            unit.setActionInTurnNumber(game.getTurnNumber());
        }
    }
}
