/*
 * GridSquareNexus.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * Subclass of GridSquareLegend that represents a nexus. Abstract because behavior for hero and 
 * monster teams are different.
 */

public abstract class GridSquareNexus extends GridSquareLegend{
    private Boolean winConditionMet = false;

    // Constructor
    public GridSquareNexus(int terrain, int x, int y){
        super(terrain, x ,y );
    }

    // Removes hero from grid square
    public void removeHeroTeam() {
        this.setHeroTeam(null);
        this.isOccupied = false;
    }

    // Adds hero team to grid square
    public boolean moveHeroTeam(HeroTeam ht) {
        this.setHeroTeam(ht);
        this.isOccupied = true;
        return true;
    }

    abstract public int checkWin();                         // Checks if win condition is met ie enemy is in nexus
}
