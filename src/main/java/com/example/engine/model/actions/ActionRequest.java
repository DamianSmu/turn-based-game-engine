package com.example.engine.model.actions;

import com.example.engine.model.utils.PositionXY;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class ActionRequest {
    private ActionType actionType;
    private PositionXY from;
    private PositionXY to;

    public ActionRequest(ActionType actionType, PositionXY from, PositionXY to) {
        this.actionType = actionType;
        this.from = from;
        this.to = to;
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

    public PositionXY getTo() {
        return to;
    }

    public void setTo(PositionXY to) {
        this.to = to;
    }
}
