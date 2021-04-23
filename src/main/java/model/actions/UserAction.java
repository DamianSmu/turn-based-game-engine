package model.actions;

import model.Game;
import model.Player;

public interface UserAction {
    void act(Player player, Game game);
}
