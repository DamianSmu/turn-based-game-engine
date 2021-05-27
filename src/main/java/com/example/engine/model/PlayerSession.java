package com.example.engine.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Objects;


public class PlayerSession {
    @JsonIgnore
    private static final int INIT_GOLD_AMOUNT = 100;
    @JsonIgnore
    private static final int INIT_IRON_AMOUNT = 100;
    @Id
    private String id;
    private int goldAmount;
    private int ironAmount;

    @DBRef
    private User user;

    public PlayerSession(User user) {
        this.user = user;
        goldAmount = INIT_GOLD_AMOUNT;
        ironAmount = INIT_IRON_AMOUNT;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public int getIronAmount() {
        return ironAmount;
    }

    public void setIronAmount(int ironAmount) {
        this.ironAmount = ironAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSession session = (PlayerSession) o;
        return goldAmount == session.goldAmount && ironAmount == session.ironAmount && Objects.equals(id, session.id) && Objects.equals(user, session.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, goldAmount, ironAmount, user);
    }
}
