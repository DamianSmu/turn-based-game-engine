package com.example.engine.api.dto.response;

public class TurnResponseDTO {
    private Long invalidActions;
    private Boolean isAlive;

    public TurnResponseDTO(Long invalidActions, Boolean isAlive) {
        this.invalidActions = invalidActions;
        this.isAlive = isAlive;
    }

    public Long getInvalidActions() {
        return invalidActions;
    }

    public void setInvalidActions(Long invalidActions) {
        this.invalidActions = invalidActions;
    }

    public Boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(Boolean isAlive) {
        this.isAlive = isAlive;
    }
}
