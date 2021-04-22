package model;

public class Settlement extends MapObject{
    private String name;
    private Player owner;
    private Tile tile;

    public Settlement(Tile tile, Player player) {
        super(tile, player);
    }
}
