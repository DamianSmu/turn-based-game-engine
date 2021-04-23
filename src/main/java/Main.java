import model.Game;
import model.Map;
import model.Player;
import model.actions.PutOnSettlement;
import model.units.Settlers;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Player playerA = new Player("PlayerA");
        Player playerB = new Player("PlayerB");
        Player playerC = new Player("PlayerC");

        List<Player> players = List.of(playerA, playerB, playerC);
        Game game = new Game();
        game.setMap(new Map(40));
        for (Player p : players) {
            game.registerPlayer(p);
        }
        for (Player p : players) {
            game.createInitialSettlersUnit(p);
        }

        game.addUserAction(new PutOnSettlement((Settlers) players.get(0).getMapObjects().get(0)));
        for (Player p : players) {
            game.takeTurn(p);
        }

        Map.saveToPNG(game.getMap());
    }
}
