/*
 * GridSquareNexus.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * Subclass of GridSquareLegend that represents a nexus. Abstract because behavior for hero and 
 * monster teams are different.
 */

public abstract class GridSquareNexus extends GridSquareLegend{
    // Constructor
    public GridSquareNexus(int terrain){
        super(terrain);
    }

    // Removes hero from grid square
    @Override
    public void removeHeroTeam() {
        this.setHeroTeam(null);
        this.isOccupied = false;
    }

    // Adds hero team to grid square
    @Override
    public boolean moveHeroTeam(HeroTeam ht) {
        this.setHeroTeam(ht);
        this.isOccupied = true;
        return true;
    }

    public abstract int checkWin();                         // Checks if win condition is met ie enemy is in nexus
}
