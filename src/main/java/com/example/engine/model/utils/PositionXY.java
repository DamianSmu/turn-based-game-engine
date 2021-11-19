package com.example.engine.model.utils;

import java.io.Serializable;

public class PositionXY implements Serializable {

    private int x;
    private int y;

    public PositionXY(int x, int y) {
        this.x = x;
        this.y = y;
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

    @Override
    public String toString() {
        return "XY{" + "x=" + x + ", y=" + y + '}';
    }
}
