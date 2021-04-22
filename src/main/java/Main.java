import model.Game;
import model.Map;
import model.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Player playerA = new Player("PlayerAAA");
        Player playerB = new Player("PlayerBBB");
        Player playerC = new Player("PlayerCCC");
        Map map = new Map(40);
        Game game = new Game(List.of(playerA, playerB, playerC), map);
        game.createInitialSettlersUnit(playerA);
        game.createInitialSettlersUnit(playerB);
        game.createInitialSettlersUnit(playerC);

        Map.saveToPNG(map);
    }
}
