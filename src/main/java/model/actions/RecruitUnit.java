package model.actions;

import model.Game;
import model.Player;
import model.Settlement;

public class RecruitUnit implements UserAction {
    private Settlement settlement;

    public RecruitUnit(Settlement settlement) {
        this.settlement = settlement;
    }

    @Override
    public void act(Player player, Game game) {
        //TODO
    }
}
