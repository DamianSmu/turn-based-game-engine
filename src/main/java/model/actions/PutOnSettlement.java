package model.actions;

import model.Game;
import model.Player;
import model.Settlement;
import model.Tile;
import model.logs.GameLog;
import model.logs.LogEntry;
import model.units.Settlers;

public class PutOnSettlement implements UserAction{
    private final Settlers settlers;

    public PutOnSettlement(Settlers settlers) {
        this.settlers = settlers;
    }

    @Override
    public void act(Player player, Game game) {
        if (settlers.getPlayer().equals(player)) {
            Tile tile = settlers.getTile();
            if(tile.getMapObjects().size() == 1){
                tile.addMapObject(new Settlement(player));
                settlers.delete();
            }
        } else {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(player, game.getTurnNumber()));
        }
    }
}
