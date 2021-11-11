package com.example.engine.api.dto.request;

import com.example.engine.model.actions.ActionType;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.utils.PositionXY;

public class ActionRequestDTO {
    private ActionType actionType;
    private PositionXY from;

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
