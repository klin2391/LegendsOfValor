/*
 * GridSquareCommon.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Subclass of GridSquare that represents a common grid square.
 * Has a battleground.
 */

public class GridSquareCommon extends GridSquare{
    private HeroTeam heroTeam;
    private Battleground battleground;

    // Constructor
    public GridSquareCommon() {
        super(0);
        this.heroTeam = null;
        this.battleground = new Battleground();
    }

    // Battle happens as soon as move on
    public boolean moveHeroTeam(HeroTeam ht) {
        this.heroTeam = ht;
        this.isOccupied = true;
        battleground.battle(heroTeam);
        return false;
    }

    // Heroes leave this grid square
    public void removeHeroTeam() {
        this.heroTeam = null;
        this.isOccupied = false;
    }

    // Accessor
    public HeroTeam getHeroTeam() {
        return this.heroTeam;
    }

    // Returns the symbol of the terrain
    public String toString() {
        if (this.isOccupied) {
            return this.heroTeam.toString();
        }
        else {
            return this.terrain.getSymbol();
        }
    }
}
