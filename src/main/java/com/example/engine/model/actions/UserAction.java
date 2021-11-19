package com.example.engine.model.actions;

import com.example.engine.model.Game;
import com.example.engine.model.User;

public interface UserAction {
    void act(User user, Game game);
}
