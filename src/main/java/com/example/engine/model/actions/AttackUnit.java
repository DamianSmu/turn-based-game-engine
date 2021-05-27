package com.example.engine.model.actions;

import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.utils.PositionXY;
import com.example.engine.model.Game;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;

import java.util.Random;

public class AttackUnit implements UserAction {
    private final Unit attacker;
    private final Unit attacked;

    public AttackUnit(Unit attacker, Unit attacked) {
        this.attacker = attacker;
        this.attacked = attacked;
    }

    @Override
    public void act(PlayerSession playerSession, Game game) {
        if (!attacker.getPlayerSession().equals(playerSession)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(playerSession, game.getTurnNumber()));
            return;
        }

        if (attacker.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(playerSession, game.getTurnNumber()));
            return;
        }

        PositionXY attackedPos = attacked.getTile().getPosition();
        PositionXY pos = attacker.getTile().getPosition();
        if (Math.abs(pos.getX() - attackedPos.getX()) > 1 || Math.abs(pos.getY() - attackedPos.getY()) > 1) {
            GameLog.getInstance().addEntry(new LogEntry(playerSession, game.getTurnNumber(), "Cannot attack, too far."));
            return;
        }

        Random rand = new Random();
        attacked.setDefence(attacked.getDefence() - attacker.getOffence() * (rand.nextDouble() + 0.5d));
        attacker.setDefence(attacker.getDefence() - attacked.getOffence() * (rand.nextDouble() + 0.5d));

        if(attacked.getDefence() <= 0){
            attacker.getTile().moveMapObject(attacker, attacked.getTile());
            attacked.getTile().deleteMapObject(attacked);
            attacker.setActionInTurnNumber(game.getTurnNumber());
        }

        if(attacker.getDefence() < 0){
            attacker.getTile().deleteMapObject(attacker);
        }
    }
}
