import model.Game;
import model.Map;
import model.Player;
import model.Settlement;
import model.actions.AttackUnit;
import model.actions.MoveUnit;
import model.actions.PutOnSettlement;
import model.actions.RecruitUnit;
import model.units.Settlers;
import model.units.Unit;
import model.units.UnitType;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        Player playerA = new Player("PlayerA");
        Player playerB = new Player("PlayerB");
        Player playerC = new Player("PlayerC");

        List<Player> players = List.of(playerA, playerB, playerC);

        long seed = new Random(1).nextLong();

        Game game = new Game();
        game.setMap(new Map(40, seed));
        for (Player p : players) {
            game.registerPlayer(p);
        }
        for (Player p : players) {
            game.createInitialSettlersUnit(p, seed);
        }
        Map.saveToPNG(game.getMap(), game.getTurnNumber());
        Unit sA = (Unit) playerA.getMapObjects().get(0);
        Unit sC = (Unit) playerC.getMapObjects().get(0);

        //T1
        game.addUserAction(new PutOnSettlement((Settlers) playerB.getMapObjects().get(0)));
        MoveUnit mA = new MoveUnit(sA, sA.getTile().getPosition().getX(), sA.getTile().getPosition().getY() - 1);
        MoveUnit mC = new MoveUnit(sC, sC.getTile().getPosition().getX() - 1, sC.getTile().getPosition().getY());
        game.addUserAction(mA);
        game.addUserAction(mC);
        for (Player p : players) {
            game.takeTurn(p);
        }
        game.nextTurn();
        Map.saveToPNG(game.getMap(), game.getTurnNumber());

        //T2
        mA = new MoveUnit(sA, sA.getTile().getPosition().getX(), sA.getTile().getPosition().getY() - 1);
        mC = new MoveUnit(sC, sC.getTile().getPosition().getX() - 1, sC.getTile().getPosition().getY());
        game.addUserAction(mA);
        game.addUserAction(mC);
        for (Player p : players) {
            game.takeTurn(p);
        }
        game.nextTurn();
        Map.saveToPNG(game.getMap(), game.getTurnNumber());

        //T3
        RecruitUnit r = new RecruitUnit((Settlement) playerB.getMapObjects().get(0), UnitType.SETTLERS);
        mA = new MoveUnit(sA, sA.getTile().getPosition().getX(), sA.getTile().getPosition().getY() - 1);
        mC = new MoveUnit(sC, sC.getTile().getPosition().getX() - 1, sC.getTile().getPosition().getY());
        game.addUserAction(r);
        game.addUserAction(mA);
        game.addUserAction(mC);
        for (Player p : players) {
            game.takeTurn(p);
        }
        game.nextTurn();
        Map.saveToPNG(game.getMap(), game.getTurnNumber());

        //T4
        AttackUnit a = new AttackUnit(sA, sC);
        Unit recr = (Unit) playerB.getMapObjects().get(1);
        MoveUnit mB = new MoveUnit(recr, recr.getTile().getPosition().getX(), recr.getTile().getPosition().getY() + 1);
        game.addUserAction(a);
        game.addUserAction(mB);
        for (Player p : players) {
            game.takeTurn(p);
        }
        game.nextTurn();
        Map.saveToPNG(game.getMap(), game.getTurnNumber());

        //T5
        a = new AttackUnit(sA, sC);
        mB = new MoveUnit(recr, recr.getTile().getPosition().getX(), recr.getTile().getPosition().getY() + 1);
        game.addUserAction(a);
        game.addUserAction(mB);
        for (Player p : players) {
            game.takeTurn(p);
        }
        game.nextTurn();
        Map.saveToPNG(game.getMap(), game.getTurnNumber());
    }
}
