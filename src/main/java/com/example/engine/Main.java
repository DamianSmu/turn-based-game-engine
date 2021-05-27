package com.example.engine;

import com.example.engine.api.security.Role;
import com.example.engine.model.*;
import com.example.engine.model.utils.MapToPNG;
import com.example.engine.model.mapObject.Settlement;
import com.example.engine.model.actions.AttackUnit;
import com.example.engine.model.actions.MoveUnit;
import com.example.engine.model.actions.PutOnSettlement;
import com.example.engine.model.actions.RecruitUnit;
import com.example.engine.model.mapObject.units.Settlers;
import com.example.engine.model.mapObject.units.Unit;
import com.example.engine.model.mapObject.units.UnitType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {

//        User userA = new User("UserA", "e", "pass", Role.ROLE_CLIENT);
//        User userB = new User("UserB", "e", "pass", Role.ROLE_CLIENT);
//        User userC = new User("UserC", "e", "pass", Role.ROLE_CLIENT);
//
//        long seed = new Random().nextLong();
//
//        Game game = new Game(seed, userA);
//
//
//        PlayerSession playerSessionA = game.registerPlayer(userA);
//        PlayerSession playerSessionB = game.registerPlayer(userB);
//        PlayerSession playerSessionC = game.registerPlayer(userC);
//
//        game.start();
//
//        List<PlayerSession> playerSessions = game.getPlayerSessions();
//
//        MapToPNG.saveToPNG(game.getMap(), game.getTurnNumber());
//        Unit sA = (Unit) playerSessionA.getMapObjects().get(0);
//        Unit sC = (Unit) playerSessionC.getMapObjects().get(0);
//
//        //T1
//        game.addUserAction(new PutOnSettlement((Settlers) playerSessionB.getMapObjects().get(0)));
//        MoveUnit mA = new MoveUnit(sA, sA.getTile().getPosition().getX(), sA.getTile().getPosition().getY() - 1);
//        MoveUnit mC = new MoveUnit(sC, sC.getTile().getPosition().getX() - 1, sC.getTile().getPosition().getY());
//        game.addUserAction(mA);
//        game.addUserAction(mC);
//        for (PlayerSession p : playerSessions) {
//            game.takeTurn(p);
//        }
//        game.nextTurn();
//        MapToPNG.saveToPNG(game.getMap(), game.getTurnNumber());
//
//        //T2
//        mA = new MoveUnit(sA, sA.getTile().getPosition().getX(), sA.getTile().getPosition().getY() - 1);
//        mC = new MoveUnit(sC, sC.getTile().getPosition().getX() - 1, sC.getTile().getPosition().getY());
//        game.addUserAction(mA);
//        game.addUserAction(mC);
//        for (PlayerSession p : playerSessions) {
//            game.takeTurn(p);
//        }
//        game.nextTurn();
//        MapToPNG.saveToPNG(game.getMap(), game.getTurnNumber());
//
//        //T3
//        RecruitUnit r = new RecruitUnit((Settlement) playerSessionB.getMapObjects().get(0), UnitType.SETTLERS);
//        mA = new MoveUnit(sA, sA.getTile().getPosition().getX(), sA.getTile().getPosition().getY() - 1);
//        mC = new MoveUnit(sC, sC.getTile().getPosition().getX() - 1, sC.getTile().getPosition().getY());
//        game.addUserAction(r);
//        game.addUserAction(mA);
//        game.addUserAction(mC);
//        for (PlayerSession p : playerSessions) {
//            game.takeTurn(p);
//        }
//        game.nextTurn();
//        MapToPNG.saveToPNG(game.getMap(), game.getTurnNumber());
//
//        //T4
//        AttackUnit a = new AttackUnit(sA, sC);
//        Unit recr = (Unit) playerSessionB.getMapObjects().get(1);
//        MoveUnit mB = new MoveUnit(recr, recr.getTile().getPosition().getX(), recr.getTile().getPosition().getY() + 1);
//        game.addUserAction(a);
//        game.addUserAction(mB);
//        for (PlayerSession p : playerSessions) {
//            game.takeTurn(p);
//        }
//        game.nextTurn();
//        MapToPNG.saveToPNG(game.getMap(), game.getTurnNumber());
//
//        //T5
//        a = new AttackUnit(sA, sC);
//        mB = new MoveUnit(recr, recr.getTile().getPosition().getX(), recr.getTile().getPosition().getY() + 1);
//        game.addUserAction(a);
//        game.addUserAction(mB);
//        for (PlayerSession p : playerSessions) {
//            game.takeTurn(p);
//        }
//        game.nextTurn();
//        MapToPNG.saveToPNG(game.getMap(), game.getTurnNumber());
   }
}
