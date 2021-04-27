package model.actions;

import model.Game;
import model.Player;
import model.Settlement;
import model.logs.GameLog;
import model.logs.LogEntry;
import model.units.Settlers;
import model.units.Unit;
import model.units.UnitType;

public class RecruitUnit implements UserAction {
    private Settlement settlement;
    private UnitType unitType;

    public RecruitUnit(Settlement settlement, UnitType unitType) {
        this.settlement = settlement;
        this.unitType = unitType;
    }

    @Override
    public void act(Player player, Game game) {
        if (!settlement.getPlayer().equals(player)) {
            GameLog.getInstance().addEntry(LogEntry.OBJECT_DOES_NOT_BELONG_TO_PLAYER(player, game.getTurnNumber()));
            return;
        }

        if (settlement.actionInTurnNumber() == game.getTurnNumber()) {
            GameLog.getInstance().addEntry(LogEntry.UNIT_HAS_TAKEN_ACTION_IN_CURRENT_TURN(player, game.getTurnNumber()));
            return;
        }

        Unit unit;
        switch (unitType){
            case SETTLERS:
                unit = new Settlers(player);
                break;
            default:
                GameLog.getInstance().addEntry(new LogEntry(player, game.getTurnNumber(), "Invalid unit type."));
                return;
        }

        settlement.getTile().addMapObject(unit);
        settlement.setActionInTurnNumber(game.getTurnNumber());
        unit.setActionInTurnNumber(game.getTurnNumber());
    }
}
