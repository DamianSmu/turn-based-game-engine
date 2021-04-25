package model.actions;

import model.*;
import model.logs.GameLog;
import model.logs.LogEntry;
import model.units.Settlers;

public class PutOnSettlement implements UserAction {
    private final Settlers settlers;

    public PutOnSettlement(Settlers settlers) {
        this.settlers = settlers;
    }

    @Override
    public void act(Player player, Game game) {
        if (!settlers.getPlayer().equals(player)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(player, game.getTurnNumber()));
            return;
        }
        if (settlers.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(player, game.getTurnNumber()));
            return;
        }
        if(settlers.getTile().getType() == TileType.WATER){
            GameLog.getInstance().addEntry(new LogEntry(player, game.getTurnNumber(), "Cannot put on settlement on water."));
        }

        Tile tile = settlers.getTile();
        if (tile.getMapObjects().size() == 1) {
            tile.addMapObject(new Settlement(player));
            settlers.delete();
        }
        settlers.setActionInTurnNumber(game.getTurnNumber());
    }
}
