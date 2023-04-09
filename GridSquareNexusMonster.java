/*
 * GridSquareNexusMonster.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * Subclass of GridSquareNexus that represents a monsters nexus. 
 * Color of monster nexus is red
 * if hero is in nexus, returns 1 for hero win
 */
public class GridSquareNexusMonster extends GridSquareNexus{

    // Constructor that takes in coordinates
    public GridSquareNexusMonster() {
        super(8);
    }

    // Shows nexus as red N
    @Override
    public String toString() {
        Color color = new Color("red");
        return color.getColor() + "N" + color.getBlack();
    }
    
    // Checks if hero is in nexus. If so, outputs 1 for hero win
    public int checkWin() {
        if (this.getHeroTeam() != null) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
