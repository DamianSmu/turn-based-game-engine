package model;

public class MapObject {
    private Tile tile;
    private Player player;
    private int actionInTurnNumber;

    public MapObject(Tile tile, Player player) {
        this.tile = tile;
        this.player = player;
        this.actionInTurnNumber = -1;
        player.addMapObject(this);
    }

    public MapObject(Player player) {
        this.player = player;
        this.actionInTurnNumber = -1;
        player.addMapObject(this);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void move(Tile tile) {
        this.tile.deleteMapObject(this);
        tile.addMapObject(this);
        this.tile = tile;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void delete() {
        player.getMapObjects().remove(this);
        tile.getMapObjects().remove(this);
    }

    public int actionInTurnNumber() {
        return actionInTurnNumber;
    }

    public void setActionInTurnNumber(int turnNumber) {
        this.actionInTurnNumber = turnNumber;
    }
}
