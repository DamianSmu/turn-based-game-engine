package com.example.engine.model.mapObject.units;

import com.example.engine.model.User;
import org.springframework.data.annotation.PersistenceConstructor;

public class Warriors extends Unit {
    public static final double DEFENCE = 50;
    public static final double OFFENCE = 30;
    public static final double RECRUIT_IRON_COST = 200;

    public Warriors(User user) {
        super(user, DEFENCE, OFFENCE, Type.WARRIORS);
    }

    @PersistenceConstructor
    public Warriors(String id, User user, int actionInTurnNumber, double defence, double offence, Type type) {
        super(id, user, actionInTurnNumber, defence, offence, type);
    }
}
