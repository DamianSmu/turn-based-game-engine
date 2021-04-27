package model;

public class Settlement extends MapObject {
    private String name;
    private double defence = 20;

    public Settlement(Tile tile, Player player) {
        super(tile, player);
    }

    public Settlement(Player player) {
        super(player);
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }
}
