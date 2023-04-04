/*
 * GridSquare.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This is similar to the Tile class in the previous project.
 * It is an abstract class that is used to create the grid.
 */

public abstract class GridSquare {
    protected Terrain terrain;                      // Terrain of the grid square (common, market, restricted, etc)
    protected boolean isOccupied;
    
    // Constructor
    public GridSquare() {
        this.terrain = new Terrain();
        this.isOccupied = false;
    }

    // Constructor that takes in a terrain type
    public GridSquare(int type) {
        this.terrain = new Terrain(type);
        this.isOccupied = false;
    }

    // Accessor
    public Terrain getTerrain() {
        return terrain;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    // Mutator, that makes it common
    public void resetGridSquare() {
        this.terrain = new Terrain();
        this.isOccupied = false;
    }

    // These behave differently for each subclass
    abstract public boolean moveHeroTeam(HeroTeam ht);
    abstract public void removeHeroTeam();
    abstract public String toString();
    abstract public HeroTeam getHeroTeam();
    

}
