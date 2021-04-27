package model.actions;

import model.Game;
import model.Player;
import model.utils.PositionXY;
import model.Tile;
import model.logs.GameLog;
import model.logs.LogEntry;
import model.units.Unit;

import java.util.Random;

public class AttackUnit implements UserAction {
    private final Unit attacker;
    private final Unit attacked;

    public AttackUnit(Unit attacker, Unit attacked) {
        this.attacker = attacker;
        this.attacked = attacked;
    }

    @Override
    public void act(Player player, Game game) {
        if (!attacker.getPlayer().equals(player)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(player, game.getTurnNumber()));
            return;
        }

        if (attacker.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(player, game.getTurnNumber()));
            return;
        }

        PositionXY attackedPos = attacked.getTile().getPosition();
        PositionXY pos = attacker.getTile().getPosition();
        if (Math.abs(pos.getX() - attackedPos.getX()) > 1 || Math.abs(pos.getY() - attackedPos.getY()) > 1) {
            GameLog.getInstance().addEntry(new LogEntry(player, game.getTurnNumber(), "Cannot attack, too far."));
            return;
        }

        Random rand = new Random();
        attacked.setDefence(attacked.getDefence() - attacker.getOffence() * (rand.nextDouble() + 0.5d));
        attacker.setDefence(attacker.getDefence() - attacked.getOffence() * (rand.nextDouble() + 0.5d));

        if(attacked.getDefence() <= 0){
            attacker.move(attacked.getTile());
            attacked.delete();

            Tile newTile = game.getMap().getTileXY(attackedPos.getX(), attackedPos.getY());
            attacker.move(newTile);
            attacker.setActionInTurnNumber(game.getTurnNumber());
        }

        if(attacker.getDefence() < 0){
            attacker.delete();
        }
    }
}
