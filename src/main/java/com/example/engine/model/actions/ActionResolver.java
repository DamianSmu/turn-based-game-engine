package com.example.engine.model.actions;

import com.example.engine.api.exception.ResponseException;
import com.example.engine.model.GameMap;
import com.example.engine.model.mapObject.MapObject;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.mapObject.units.Type;
import com.example.engine.model.tile.Tile;
import com.example.engine.model.utils.PositionXY;
import org.springframework.http.HttpStatus;

public class ActionResolver {
    public static UserAction resolve(GameMap gameMap, ActionRequest actionRequest) throws CannotResolveActionException {
        UserAction userAction;
        PositionXY direction;
        MapObject mapObject = getObject(gameMap.getTileFromPosition(actionRequest.getFrom()));
        if (!(mapObject instanceof Unit)) {
            throw new CannotResolveActionException("Map object is not a unit.");
        }
        Unit unit = (Unit) mapObject;
        ActionType type = actionRequest.getActionType();
        switch (type) {
            case MOVE_DOWN:
            case MOVE_UP:
            case MOVE_LEFT:
            case MOVE_RIGHT:
                direction = resolveDirection(gameMap, type, unit);
                userAction = new MoveUnit(unit, direction);
                break;
            case ATTACK_DOWN:
            case ATTACK_UP:
            case ATTACK_LEFT:
            case ATTACK_RIGHT:
                direction = resolveDirection(gameMap, type, unit);
                userAction = new Attack(unit, getObject(gameMap.getTileFromPosition(direction)));
                break;
//            case RECRUIT_UNIT_SETTLERS:
//                settlement = (Settlement) getObject(from);
//                userAction = new RecruitUnit(settlement, UnitType.SETTLERS);
//                break;
//            case RECRUIT_UNIT_WARRIORS:
//                settlement = (Settlement) getObject(from);
//                userAction = new RecruitUnit(settlement, UnitType.WARRIORS);
//                break;
            case PUT_ON_SETTLEMENT:
                if (unit.getType() != Type.SETTLERS)
                {
                    throw new CannotResolveActionException();
                }
                Settlers settlers = (Settlers) unit;
                userAction = new PutOnSettlement(settlers);
                break;
            default:
                throw new CannotResolveActionException();
        }
        return userAction;
    }

    private static MapObject getObject(Tile tile) {
        if (tile.isEmpty()) {
            throw new CannotResolveActionException("Tile in given direction is empty.");
        }
        return tile.getMapObject();
    }

    private static PositionXY resolveDirection(GameMap gameMap, ActionType type, Unit unit) {
        PositionXY direction = new PositionXY(unit.getTile().getPosition().getX(), unit.getTile().getPosition().getY());
        switch (type) {
            case MOVE_UP:
            case ATTACK_UP:
                direction.setY(direction.getY() - 1);
                break;
            case MOVE_DOWN:
            case ATTACK_DOWN:
                direction.setY(direction.getY() + 1);
                break;
            case MOVE_LEFT:
            case ATTACK_LEFT:
                direction.setX(direction.getX() - 1);
                break;
            case MOVE_RIGHT:
            case ATTACK_RIGHT:
                direction.setX(direction.getX() + 1);
                break;
            default:
                throw new CannotResolveActionException();
        }
        if (direction.getX() > gameMap.getSize() || direction.getX() < 0)
            throw new CannotResolveActionException("Invalid position in requested action");
        if (direction.getY() > gameMap.getSize() || direction.getY() < 0)
            throw new CannotResolveActionException("Invalid position in requested action");
        return direction;
    }
}
