package model;

public class Settlement extends MapObject{
    private String name;

    public Settlement(Tile tile, Player player) {
        super(tile, player);
    }

    public Settlement(Player player) {
        super(player);
    }
}
