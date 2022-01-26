package com.example.engine.model.actions;

import com.example.engine.model.Game;
import com.example.engine.model.User;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.tile.TileType;


public class PutOnSettlement implements UserAction {
    private final Settlers settlers;

    public PutOnSettlement(Settlers settlers) {
        this.settlers = settlers;
    }

    @Override
    public void act(User user, Game game) {
        if (!settlers.getUser().equals(user)) {
            throw new CannotResolveActionException("Object does not belong to player.");
        }
        if (settlers.actionInTurnNumber() == game.getTurnNumber()) {
            throw new CannotResolveActionException("Unit has taken action in current turn.");
        }
        if (settlers.getTile().getType() == TileType.WATER) {
            throw new CannotResolveActionException("Cannot put on settlement on water.");
        }

        Tile tile = settlers.getTile();
        if (tile.getSettlement() == null) {
            tile.setSettlement(new Settlement(user));
        } else {
            throw new CannotResolveActionException("Cannot put on non empty tile.");
        }
    }
}
