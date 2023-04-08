/*
 * GridSquareLegend.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * Subclass of GridSquare that represents a grid square for Legends game
 */
import java.util.Objects;

public class GridSquareLegend extends GridSquare{
    private int amplify;                                    // Which attribute to amplify
    private HeroTeam heroTeam;
    private Monster monster;
    private int xcoord;
    private int ycoord;

    // Constructor
    public GridSquareLegend(){
        super();
        this.amplify = 0;
    }

    // Constructor that takes in data
    public GridSquareLegend(int amplify, int x, int y){
        super(amplify);
        this.amplify = amplify;
        this.xcoord = x;
        this.ycoord = y;
    }

    // Accessors
    public Monster getMonster(){
        return monster;
    }
    
    public HeroTeam getHeroTeam(){
        return heroTeam;
    }

    // Checks if monster is in grid square
    public boolean hasMonster() {
        return !Objects.isNull(this.monster);
    }

    // Mutators
    public void setMonster(Monster m){
        this.monster = m;
    }

    public void setHeroTeam(HeroTeam ht){
        this.heroTeam = ht;
    }

    public void addMonster(Monster m) {
        this.monster = m;
    }

    // Adds hero team to grid square
    public boolean moveHeroTeam(HeroTeam ht){
        this.heroTeam = ht;
        increaseAttributes(amplify);            // Amplify attributes
        this.isOccupied = true;
        return false;
    }

    // Removes hero team from grid square
    public void removeHeroTeam(){
        decreaseAttributes(amplify);            // De-amplify attributes
        this.heroTeam = null;
        this.isOccupied = false;
    }

    // Returns symbol of terrain if not occupied. Otherwise, returns symbol of hero team
    public String toString() {
        if (this.isOccupied) {
            return this.heroTeam.toString();
        }
        else {
            return this.terrain.getSymbol();
        }
    }

    // Amplifies attributes of hero team
    private void increaseAttributes(int amplify){
        String effect = "";
        switch (amplify) {
            case 4:
                effect = "Dexterity";
                break;
            case 5:
                effect = "Agility";
                break;
            case 6:
                effect = "Strength";
                break;
            default:
                return;}
        for (Hero h : this.heroTeam.getHeroes()){                               // For hero in team
            double effectValue = h.getAttributes().get(effect).getMax();        // Get max value of attribute
            effectValue = (effectValue * 0.1);                                  // Multiply by 10%
            h.getAttributes().get(effect).increaseCurrent(effectValue);         // Increase current value by 10%
        }
    }

    // De-amplifies attributes of hero team
    private void decreaseAttributes(int amplify){
        String effect = "";
        switch (amplify) {
            case 4:
                effect = "Dexterity";
                break;
            case 5:
                effect = "Agility";
                break;
            case 6:
                effect = "Strength";
                break;
            default:
                return;}
        for (Hero h : this.heroTeam.getHeroes()){                           // For hero in team
            double effectValue = h.getAttributes().get(effect).getMax();    // Get max value of attribute
            effectValue = (effectValue * 0.1);                              // Multiply by 10%
            h.getAttributes().get(effect).decreaseCurrent(effectValue);     // Decrease current value by 10%
        }
    }
}
