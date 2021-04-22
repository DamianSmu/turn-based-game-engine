package model;

public class MapObject {
    private Tile tile;
    private Player player;

    public MapObject(Tile tile, Player player) {
        this.tile = tile;
        this.player = player;
        player.getMapObjects().add(this);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
