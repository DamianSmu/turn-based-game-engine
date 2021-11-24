package com.example.engine.model.actions;

import com.example.engine.model.Game;
import com.example.engine.model.User;
import com.example.engine.model.logs.GameLog;
import com.example.engine.model.logs.LogEntry;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.mapObject.units.Type;
import com.example.engine.model.mapObject.units.Warriors;
import com.example.engine.model.utils.PositionXY;

public class RecruitUnit implements UserAction {
    private final Settlement settlement;
    private final Type type;

    public RecruitUnit(Settlement settlement, Type type) {
        this.settlement = settlement;
        this.type = type;
    }

    @Override
    public void act(User user, Game game) {
        if (!settlement.getUser().equals(user)) {
            throw new CannotResolveActionException("Object does not belong to player.");
        }

        if (settlement.actionInTurnNumber() == game.getTurnNumber()) {
            throw new CannotResolveActionException("Unit has taken action in current turn.");
        }

        Unit unit;
        switch (type) {
            case SETTLERS:
                if( settlement.getGoldAmount() < Settlers.RECRUIT_GOLD_COST) {
                    throw new CannotResolveActionException("Not enough gold to recruit settlers.");
                }
                unit = new Settlers(user);
                break;
            case WARRIORS:
                if(settlement.getIronAmount() < Warriors.RECRUIT_IRON_COST) {
                    throw new CannotResolveActionException("Not enough iron to recruit warriors.");
                }
                unit = new Warriors(user);
                break;
            default:
                throw new CannotResolveActionException("Unknown unit type.");
        }

        int[] xx = new int[]{0, 1, -1};
        int[] yy = new int[]{1, 0, -1};

        boolean placed = false;
        for (int x : xx){
            for (int y : yy){
                PositionXY newPos = new PositionXY(settlement.getTile().getPosition().getX() + x, settlement.getTile().getPosition().getY() + y);
                if(game.getMap().getTileFromPosition(newPos).isEmpty()) {
                    game.getMap().getTileFromPosition(newPos).setMapObject(unit);
                    placed = true;
                    break;
                }
                if(placed) {
                    break;
                }
            }
        }
        if(!placed) {
            throw new CannotResolveActionException("No tile to place unit.");
        }

        settlement.setActionInTurnNumber(game.getTurnNumber());
    }
}
