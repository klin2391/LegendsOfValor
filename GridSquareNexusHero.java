/*
 * GridSquareNexusHero.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * Subclass of GridSquareNexus that represents a heroes nexus. 
 * Color of hero nexus is blue
 * if monster is in nexus, returns 2 for monster win
 */
public class GridSquareNexusHero extends GridSquareNexus{
    private Marketplace marketplace;

    // Constructor that takes in a marketplace and coordinates
    public GridSquareNexusHero(Marketplace marketplace, int x, int y) {
        super(7, x ,y );
        this.marketplace = marketplace;
    }
    
    // Accessors
    public Marketplace getMarket() {
        return marketplace;
    }

    // Removes team for when they leave tile
    public void removeHeroTeam(){
        super.removeHeroTeam();
    }

    // Shows nexus as blue N
    public String toString() {
        Color color = new Color("cyan");
        return color.getColor() + "N" + color.getBlack();
    }

    // Checks if monster is in nexus
    public int checkWin() {
        if (this.getMonster() != null) {
            return 2;
        }
        else {
            return 0;
        }
    }

   
}
