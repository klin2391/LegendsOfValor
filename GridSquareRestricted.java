/*
 * GridSquareRestricted.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Subclass of GridSquare that represents restricted terrain
 */

public class GridSquareRestricted extends GridSquare{
    public GridSquareRestricted() {
        super(1);
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
