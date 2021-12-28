package com.example.engine.model.mapObject.units;

import com.example.engine.model.User;
import com.example.engine.model.mapObject.MapObject;
import org.springframework.data.annotation.PersistenceConstructor;

public class Unit extends MapObject {
    private final Type type;

    @PersistenceConstructor
    public Unit(User user, int actionInTurnNumber, double defence, double offence, Type type) {
        super(user, actionInTurnNumber, defence, offence);
        this.type = type;
    }

    public Unit(User user, double defence, double offence, Type type) {
        super(user, defence, offence);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
