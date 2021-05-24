package com.example.engine.model.actions;

import com.example.engine.model.Game;
import com.example.engine.model.PlayerSession;

public interface UserAction {
    void act(PlayerSession playerSession, Game game);
}
