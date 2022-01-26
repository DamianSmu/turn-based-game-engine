package com.example.engine.api.dto.request;

import com.example.engine.model.actions.ActionType;

public class ActionRequestDTO {
    private ActionType actionType;
    private int x;
    private int y;

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
