/*
 * Terrain.java
 * by Kevin Lin (lin2391@bu.edu)
 * 18MAR2023
 * 
 * This class represents the type of terrain in a grid
 * It contains the symbol, type, color, and whether or not it is walkable
 */

 public class Terrain {
    private String symbol;          // The symbol that gets printed
    private int type;               // The type of terrain (0 = common, 1 = restricted terrain, 2 = market, 3 = severely restricted terrain)
    private Color color;            // Green or yellow
    private boolean walkable;       // If restricted, cannot walk
    private boolean tradeAllowed;   // If market, can trade
    private boolean battleAllowed;  // If common, can battle

    // Constructor
    public Terrain() {
        this.symbol = " ";
        this.type = 0;
        this.color = new Color();
        this.walkable = true;
        this.tradeAllowed = false;
        this.battleAllowed = true;
    }

    // Constructor that takes in int
    public Terrain(int type) {
        switch (type) {
            case 0:
                this.symbol = " ";
                this.type = 0;
                this.color = new Color();
                this.walkable = true;
                this.tradeAllowed = false;
                this.battleAllowed = true;
                break;
            case 1:
                this.symbol = "/";
                this.type = 1;
                this.color = new Color("green");
                this.walkable = false;
                this.tradeAllowed = false;
                this.battleAllowed = false;
                break;
            case 2:
                this.symbol = "M";
                this.type = 2;
                this.color = new Color("yellow");
                this.walkable = true;
                this.tradeAllowed = true;
                this.battleAllowed = false;
                break;
            case 3:
                this.symbol = "X";
                this.type = 3;
                this.color = new Color("green");
                this.walkable = false;
                this.tradeAllowed = false;
                this.battleAllowed = false;
                break;
            case 4:
                this.symbol = "B";
                this.type = 4;
                this.color = new Color("purple");
                this.walkable = true;
                this.tradeAllowed = false;
                this.battleAllowed = true;
                break;
            case 5:
                this.symbol = "C";
                this.type = 5;
                this.color = new Color("purple");
                this.walkable = true;
                this.tradeAllowed = false;
                this.battleAllowed = true;
                break;
            case 6:
                this.symbol = "K";
                this.type = 6;
                this.color = new Color("purple");
                this.walkable = true;
                this.tradeAllowed = false;
                this.battleAllowed = true;
                break;
            case 7:
                this.symbol = "N";
                this.type = 7;
                this.color = new Color("blue");
                this.walkable = true;
                this.tradeAllowed = true;
                this.battleAllowed = true;
                break;
            case 8:
                this.symbol = "N";
                this.type = 8;
                this.color = new Color("red");
                this.walkable = true;
                this.tradeAllowed = false;
                this.battleAllowed = true;
                break;
            default:
                this.symbol = " ";
                this.type = 0;
                this.color = new Color();
                this.walkable = true;
                this.tradeAllowed = false;
                this.battleAllowed = true;
                break;}
    }

    // Accessors
    public String getSymbol() {
        return symbol;
    }

    public int getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public boolean getWalkable() {
        return walkable;
    }

    public boolean getTradeAllowed() {
        return tradeAllowed;
    }

    public boolean getBattleAllowed() {
        return battleAllowed;
    }

    // changes the terrain to common
    public void resetTerrain() {
        this.symbol = " ";
        this.type = 0;
        this.color = new Color();
        this.walkable = true;
        this.tradeAllowed = false;
        this.battleAllowed = true;
    }

    // tostring
    public String toString() {
        return color.getColor() + symbol + color.getBlack();
    }
}
