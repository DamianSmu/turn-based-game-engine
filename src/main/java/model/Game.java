package model;

import model.units.Settlers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private List<Player> players;
    private Map map;

    public Game(List<Player> players, Map map) {
        this.players = players;
        this.map = map;
    }

    public void createInitialSettlersUnit(Player player){
        Tile[][] tiles = this.map.getTileMatrix();
        List<Tile> list = Arrays
                                .stream(tiles)
                                .flatMap(Arrays::stream)
                                .filter(x -> x.getType() == TileType.LAND)
                                .collect(Collectors.toList());
        Collections.shuffle(list);
        boolean placed = false;
        int idx = 0;
        while(!placed){
            Tile tile = list.get(idx);
            if(tile.getMapObjects().size() == 0){
                tile.getMapObjects().add(new Settlers(list.get(idx), player));
                placed = true;
            }
           idx++;
        }

    }
}
