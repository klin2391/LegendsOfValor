/*
 * World.java
 * by Kevin Lin (lin2391@bu.edu)
 * 18MAR2023
 * 
 * This class represents the type of terrain in a grid
 * It contains the symbol, type, color, and whether or not it is walkable
 */

public class World {
    private GridSquare[][] map;        // The grid of GridSquares
    private int size = 10;                  // The size of the grid
    private int sizeX = 10;
    private int sizeY = 10;
    private RandomGen rand = new RandomGen();            // RandomGen object
    private int probabilityMarket = 15;      // The probability of a market
    private int probabilityRestricted = 20; // The probability of restricted terrain
    private int playerLocationX = 0;       // The x coordinate of the player
    private int playerLocationY = 0;       // The y coordinate of the player
    private FactoryMarket factoryMarket = new FactoryMarket();    // FactoryMarket object
    private HeroTeam ht;                            // HeroTeam object
    
    // Constructor
    public World() {
        this.map = new GridSquare[sizeX][sizeY];
        ht = new HeroTeam();
        this.generateWorld();
    }

    // Constructor that takes in size
    public World(int size) {
        ht = new HeroTeam();
        this.sizeX = size;
        this.sizeY = size;
        this.map = new GridSquare[sizeX][sizeY];
        this.generateWorld();
    }

    // public World(int size, int height){
    //     ht = new HeroTeam();
    //     // this.size = size;
    //     // this.sizeX = size;
    //     this.sizeY = height;
    //     int numHeroes = ht.getTeamSize();
    //     this.size = numHeroes*3+1;
    //     this.map = new GridSquare[height][this.size];
    //     // this.generateWorld();
    // }

    public World(int size, int gameMode){
        ht = new HeroTeam();
        if (gameMode == 0) {
            this.sizeX = size;
            this.sizeY = size;
            this.map = new GridSquare[sizeX][sizeY];
        }
        else if (gameMode == 1){
            this.sizeY = size;
            int numHeroes = ht.getTeamSize();
            this.size = numHeroes*3+1;
            this.map = new GridSquare[size][this.size];
        }
    }

    // Accessors
    public HeroTeam getHeroTeam() {
        return ht;
    }

    public GridSquare getCurrent() {
        return this.map[playerLocationX][playerLocationY];
    }

    public GridSquare[][] getMap() {
        return this.map;
    }

    public int getSize() {
        return this.size;
    }

    public int getPlayerLocationX() {
        return this.playerLocationX;
    }

    public int getPlayerLocationY() {
        return this.playerLocationY;
    }

    public RandomGen getRand() {
        return this.rand;
    }

    public int getProbabilityMarket() {
        return this.probabilityMarket;
    }

    public int getProbabilityRestricted() {
        return this.probabilityRestricted;
    }

    public FactoryMarket getFactoryMarket() {
        return this.factoryMarket;
    }

    public void setProbabilityMarket(int probabilityMarket) {
        this.probabilityMarket = probabilityMarket;
    }

    public void setProbabilityRestricted(int probabilityRestricted) {
        this.probabilityRestricted = probabilityRestricted;
    }


    // Chooses random place to spawn player
    private void spawnPlayer() {
        while(playerLocationX == 0 || playerLocationX == size -1 ){            // Ensure the player is not spawned on the border of the board.
            playerLocationX = rand.generateRandomNumber() % size;
        }
        while(playerLocationY == 0 || playerLocationY == size -1 ){
            playerLocationY = rand.generateRandomNumber() % size;
        }
    }

    // Sees if the player is on a market
    public boolean checkMarket(){
        if (this.map[playerLocationX][playerLocationY].getTerrain().getTradeAllowed()){
            return true;
        }
        System.out.println("DBG trade not allowed");
        System.out.println(this.map[playerLocationX][playerLocationY].getTerrain().getSymbol());
        return false;
    }

    // Generates the world
    public void generateWorld() {
        spawnPlayer();
        boolean hasMarket = false;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (i == 0 || i == size-1 || j == 0 || j == size-1)         //Sets the border of the board to be severely restricted.
                    this.map[i][j] = new GridSquareSeverelyRestricted();
                else if (i == playerLocationX && j == playerLocationY) {    //Sets the player's location to be common terrain.
                    this.map[i][j] = new GridSquareCommon();
                    this.map[i][j].moveHeroTeam(ht);
                }
                else {
                    int randNum = this.rand.generateRandomNumber();
                    if (randNum <= this.probabilityMarket) {
                        this.map[i][j] = new GridSquareMarket(factoryMarket.createMarket());    // Creates market
                        hasMarket = true;
                    }
                    else if (randNum <= this.probabilityMarket + this.probabilityRestricted) {      // Creates restricted terrain
                        this.map[i][j] = new GridSquareRestricted();
                    }
                    else {
                        this.map[i][j] = new GridSquareCommon();                                // Creates common terrain
                    }
                }
            }
        }
        if (!hasMarket) {
            generateWorld();                                                                  // If there is no market, generate a new world.
        }
    }

    // Heroes move
    public void moveHeroTeam(int dx, int dy) {
        if (this.map[playerLocationX + dx][playerLocationY + dy].getTerrain().getWalkable()) {          //Checks if the terrain is walkable.
            this.map[playerLocationX][playerLocationY].removeHeroTeam();                                //Removes the HeroTeam from the current GridSquare.
            this.map[playerLocationX + dx][playerLocationY + dy].moveHeroTeam(ht);                    //Moves the HeroTeam to the new GridSquare.               
            playerLocationX += dx;                                                                  //Updates the player's location.                    
            playerLocationY += dy;
            ht.travel();                                                                        //Updates the HeroTeam's travel distance.                           
        }

    }

    // Shows only a smaller view of the world
    public void showPlayerView(int size) {
        int tempSize = (size - 1) / 2;
        int tempSizeXP = tempSize;
        int tempSizeXN = tempSize;
        int tempSizeYP = tempSize;
        int tempSizeYN = tempSize;
        if (playerLocationX - tempSize < 0) {         //If the player is too close to the left border, the board will be shifted to the right.
            tempSizeXN = playerLocationX;
        }
        if (playerLocationX + tempSize > this.size - 1) {      //If the player is too close to the right border, the board will be shifted to the left.
            tempSizeXP = (this.size - 1 - playerLocationX);
        }
        if (playerLocationY - tempSize < 0) {         //If the player is too close to the top border, the board will be shifted down.
            tempSizeYN = playerLocationY;
        }
        if (playerLocationY + tempSize > this.size - 1) {      //If the player is too close to the bottom border, the board will be shifted up.
            tempSizeYP = (this.size - 1 - playerLocationY);
        }
        String output = "";
        for (int i = playerLocationY-tempSizeYN; i < playerLocationY+tempSizeYP+1; i++) {                              //Prints the top row of the board is dependent on the size of the board.
            output += "+-----";
        }
        output += "+\n";
        for (int i = playerLocationX-tempSizeXN; i < playerLocationX+tempSizeXP+1; i++) {   
            for (int j = playerLocationY-tempSizeYN; j < playerLocationY+tempSizeYP+1; j++) {
                output += "|  " + this.map[i][j].getTerrain().getColor().getColor() + this.map[i][j] + this.map[i][j].getTerrain().getColor().getBlack() + "  ";
                // if (this.map[i][j].getIsOccupied())
                //     output += "|  " + this.map[i][j].getHeroTeam().getColor().getColor() + this.map[i][j].getHeroTeam().getSymbol() + this.map[i][j].getHeroTeam().getColor().getBlack() + "  ";
                // else
                //     output += "|  " + this.map[i][j].getTerrain().getColor().getColor() + this.map[i][j].getTerrain().getSymbol() + this.map[i][j].getTerrain().getColor().getBlack() + "  ";
            }
            output += "|\n";
            for (int k = playerLocationY-tempSizeYN; k < playerLocationY+tempSizeYP+1; k++) {                          //Prints the bottom row of the board is dependent on the size of the board.
                output += "+-----";
            }
            output += "+\n";
        }
        System.out.println(output);
    }

    // Shows the entire world
    public void showGlobalView() {
        System.out.println(this);
    }

    // tostring shows whole world
    public String toString() {
        String output = "";
        for (int i = 0; i < this.size; i++) {
            output += "+-----";
        }
        output += "+\n";
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                output += "|  " + this.map[i][j].getTerrain().getColor().getColor() + this.map[i][j] + this.map[i][j].getTerrain().getColor().getBlack() + "  ";
                // if (this.map[i][j].getIsOccupied())
                //     output += "|  " + this.map[i][j].getHeroTeam().getColor().getColor() + this.map[i][j].getHeroTeam().getSymbol() + this.map[i][j].getHeroTeam().getColor().getBlack() + "  ";
                // else
                //     output += "|  " + this.map[i][j].getTerrain().getColor().getColor() + this.map[i][j] + this.map[i][j].getTerrain().getColor().getBlack() + "  ";
            }
            output += "|\n";
            for (int j = 0; j < this.size; j++) {
                output += "+-----";
            }
            output += "+\n";
        }
        return output;
    }

    // changes specified terrain to common terrain
    public void manipulateTerrain(int x, int y) {
        if (this.map[playerLocationX+x][playerLocationY+y].getTerrain().getType() != 3) {
            this.map[playerLocationX+x][playerLocationY+y] = new GridSquareCommon();
        }
        else {
            System.out.println("Geb cannot manipulate this terrain.");
        }
    }
}
