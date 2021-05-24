package com.example.engine.model;

import com.example.engine.model.mapObject.GoldApplier;
import com.example.engine.model.mapObject.IronApplier;
import com.example.engine.model.mapObject.MapObject;

import java.util.ArrayList;
import java.util.List;

public class PlayerSession {
    private final int INIT_GOLD_AMOUNT = 100;
    private final int INIT_IRON_AMOUNT = 100;

    private List<MapObject> mapObjects;
    private int goldAmount;
    private int ironAmount;

    private Player player;
    private Session session;


    public PlayerSession() {
        mapObjects = new ArrayList<>();
        goldAmount = INIT_GOLD_AMOUNT;
        ironAmount = INIT_IRON_AMOUNT;
    }

    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    public void addMapObject(MapObject mapObject){
        this.mapObjects.add(mapObject);
    }

    public void setMapObjects(List<MapObject> mapObjects) {
        this.mapObjects = mapObjects;
    }

    public void updateGoldAmount(){
        goldAmount += mapObjects.stream().filter(x -> x instanceof GoldApplier).mapToInt(x -> ((GoldApplier) x).applyGold()).sum();
    }
    public void updateIronAmount(){
        ironAmount += mapObjects.stream().filter(x -> x instanceof IronApplier).mapToInt(x -> ((IronApplier) x).applyIron()).sum();
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
}
