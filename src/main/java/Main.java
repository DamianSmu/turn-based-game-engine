import model.Game;
import model.Map;
import model.Player;
import model.actions.MoveUnit;
import model.actions.PutOnSettlement;
import model.units.Settlers;
import model.units.Unit;

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

        game.addUserAction(new PutOnSettlement((Settlers) playerA.getMapObjects().get(0)));

        MoveUnit m = new MoveUnit(
                (Unit) playerB.getMapObjects().get(0),
                playerB.getMapObjects().get(0).getTile().getPosition().getX() + 1,
                playerB.getMapObjects().get(0).getTile().getPosition().getY());

        game.addUserAction(m);
        Map.saveToPNG(game.getMap(), game.getTurnNumber());
        for (Player p : players) {
            game.takeTurn(p);
        }
        game.nextTurn();
        Map.saveToPNG(game.getMap(), game.getTurnNumber());
    }
}
