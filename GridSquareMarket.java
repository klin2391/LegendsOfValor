/*
 * GridSquareMarket.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Subclass of GridSquare that represents a market grid.
 * Has a marketplace.
 */

public class GridSquareMarket extends GridSquare{
    private HeroTeam heroTeam;
    private Marketplace market;

    // Constructor
    public GridSquareMarket(Marketplace market) {
        super(2);
        this.heroTeam = null;
        this.market = market;
    }

    // Heroes arrive. No monsters
    public boolean moveHeroTeam(HeroTeam ht) {
        this.heroTeam = ht;
        this.isOccupied = true;
        return true;
    }

    // Heroes leave this grid square
    public void removeHeroTeam() {
        this.heroTeam = null;
        this.isOccupied = false;
    }

    public HeroTeam getHeroTeam() {
        return this.heroTeam;
    }

    public Marketplace getMarket() {
        return this.market;
    }

    public String toString() {
        if (this.isOccupied) {
            return this.heroTeam.toString();
        }
        else {
            return this.terrain.getSymbol();
        }
    }



}
