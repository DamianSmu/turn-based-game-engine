package com.example.engine.model.actions;

import com.example.engine.model.utils.PositionXY;

public class ActionRequest {
    private ActionType actionType;
    private PositionXY from;

    public ActionRequest(ActionType actionType, PositionXY from) {
        this.actionType = actionType;
        this.from = from;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public PositionXY getFrom() {
        return from;
    }

    public void setFrom(PositionXY from) {
        this.from = from;
    }

}
