package com.example.engine.model.actions;

public class CannotResolveActionException extends RuntimeException {
    public CannotResolveActionException() {
        super("Cannot resolve action");
    }
}
