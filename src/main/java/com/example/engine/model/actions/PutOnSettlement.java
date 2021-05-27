package com.example.engine.model.actions;

import com.example.engine.model.Game;
import com.example.engine.model.PlayerSession;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.tile.TileType;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Settlers;


public class PutOnSettlement implements UserAction {
    private final Settlers settlers;

    public PutOnSettlement(Settlers settlers) {
        this.settlers = settlers;
    }

    @Override
    public void act(PlayerSession playerSession, Game game) {
        if (!settlers.getPlayerSession().equals(playerSession)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(playerSession, game.getTurnNumber()));
            return;
        }
        if (settlers.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(playerSession, game.getTurnNumber()));
            return;
        }
        if(settlers.getTile().getType() == TileType.WATER){
            GameLog.getInstance().addEntry(new LogEntry(playerSession, game.getTurnNumber(), "Cannot put on settlement on water."));
            return;
        }

        Tile tile = settlers.getTile();
        if (tile.getMapObjects().size() == 1) {
            tile.addMapObject(new Settlement(playerSession));
            tile.removeMapObject(settlers);
        } else {
            GameLog.getInstance().addEntry(new LogEntry(playerSession, game.getTurnNumber(), "Cannot put on non empty tile."));
        }
    }
}
