package com.example.engine.model.actions;

import com.example.engine.api.exception.ResponseException;
import com.example.engine.model.Map;
import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.mapObject.units.UnitType;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.utils.PositionXY;
import org.springframework.http.HttpStatus;

public class ActionResolver {
    public static UserAction resolve(Map map, ActionType type, PositionXY posFrom, PositionXY posTo) throws CannotResolveActionException {
        int size = map.getSize();
        if (posFrom.getX() > size || posFrom.getX() < 0)
            throw new ResponseException("Invalid position in requested action", HttpStatus.BAD_REQUEST);
        if (posFrom.getY() > size || posFrom.getY() < 0)
            throw new ResponseException("Invalid position in requested action", HttpStatus.BAD_REQUEST);
        if (posTo.getX() > size || posTo.getX() < 0)
            throw new ResponseException("Invalid position in requested action", HttpStatus.BAD_REQUEST);
        if (posTo.getY() > size || posTo.getY() < 0)
            throw new ResponseException("Invalid position in requested action", HttpStatus.BAD_REQUEST);

        Tile from = map.getTileFromPosition(posFrom);
        Tile to = map.getTileFromPosition(posTo);

        UserAction userAction = null;
        Settlement settlement;
        Unit unit1;
        Unit unit2;
        switch (type) {
            case MOVE_UNIT:
                unit1 = (Unit) getObject(from, Unit.class);
                userAction = new MoveUnit(unit1, posTo);
                break;
            case ATTACK_UNIT:
                unit1 = (Unit) getObject(from, Unit.class);
                unit2 = (Unit) getObject(to, Unit.class);
                userAction = new AttackUnit(unit1, unit2);
                break;
            case RECRUIT_UNIT_SETTLERS:
                settlement = (Settlement) getObject(from, Settlement.class);
                userAction = new RecruitUnit(settlement, UnitType.SETTLERS);
                break;
            case RECRUIT_UNIT_WARRIORS:
                settlement = (Settlement) getObject(from, Settlement.class);
                userAction = new RecruitUnit(settlement, UnitType.WARRIORS);
                break;
            case ATTACK_SETTLEMENT:
                unit1 = (Unit) getObject(from, Unit.class);
                settlement = (Settlement) getObject(to, Settlement.class);
                userAction = new AttackSettlement(unit1, settlement);
                break;
            case PUT_ON_SETTLEMENT:
                Settlers settlers = (Settlers) getObject(from, Settlers.class);
                userAction = new PutOnSettlement(settlers);
                break;
            default:
                throw new CannotResolveActionException();
        }
        return userAction;
    }

    public static UserAction resolve(Map map, ActionRequest actionRequest) {
        return resolve(map, actionRequest.getActionType(), actionRequest.getFrom(), actionRequest.getTo());
    }

    private static MapObject getObject(Tile tile, Class<?> ofClass) {
        if(tile.isEmpty()){
            throw new CannotResolveActionException();
        }
        return tile.getMapObject();
    }
}
