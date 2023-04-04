/*
 * GridSquareSeverlyRestricted.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Subclass of GridSquare that represents severly restricted
 * Used for border and cannot be altered
 */
public class GridSquareSeverelyRestricted extends GridSquare {
    public GridSquareSeverelyRestricted() {
        super(3);
    }
    public boolean moveHeroTeam(HeroTeam ht) {
        System.out.println("HeroTeam cannot move to this grid square.");
        return false;
    }
    public void removeHeroTeam() {
        System.out.println("HeroTeam cannot move to this grid square.");
    }
    public HeroTeam getHeroTeam() {
        return new HeroTeam();
    }
    public String toString() {
        return this.terrain.getSymbol();
    }
}
    
