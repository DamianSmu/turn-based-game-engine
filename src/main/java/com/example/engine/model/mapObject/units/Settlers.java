package com.example.engine.model.mapObject.units;

import com.example.engine.model.User;
import org.springframework.data.annotation.PersistenceConstructor;

public class Settlers extends Unit {
    public static final double DEFENCE = 5;
    public static final double OFFENCE = 1;
    public static final double RECRUIT_GOLD_COST = 200;

    public Settlers(User user) {
        super(user, DEFENCE, OFFENCE, Type.SETTLERS);
    }

    @PersistenceConstructor
    public Settlers(User user, int actionInTurnNumber, double defence, double offence, Type type) {
        super(user, actionInTurnNumber, defence, offence, type);
    }
}
