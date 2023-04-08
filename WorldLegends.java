/*
 * WorldLegends.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * The world for Legends. Contains the grid and the heroes.
 * Grid is dependent on number of heroes and are made up of GridSquareLegend objects.
 */

import java.util.ArrayList;

public class WorldLegends extends World{
    private int height;
    private int numHeroes;
    private ArrayList<HeroTeamLegends> heroes;
    private ArrayList<Integer> playerLocationXs;
    private ArrayList<Integer> playerLocationYs;

    // Constructor
    public WorldLegends(int size) {
        super(size, 1);
        numHeroes = this.getHeroTeam().getHeroes().size();
        this.height = size;
        this.setProbabilityMarket(30);          // using this as probability of special tiles
        this.setProbabilityRestricted(5);
        this.heroes = new ArrayList<>();
        // for (int i = 0; i < this.getHeroTeam().getHeroes().size(); i++) {
        //     System.out.println(this.getHeroTeam().getHeroes().get(i));
        // }
        for (int i = 0; i < this.getHeroTeam().getHeroes().size(); i++) {
            this.heroes.add(new HeroTeamLegends(this.getHeroTeam().getHeroes().get(i),i));
        }
        this.playerLocationXs = new ArrayList<>();
        this.playerLocationYs = new ArrayList<>();
    }

    // Accessors
    public int getPlayerLocationXs(int i) {
        return this.playerLocationXs.get(i);
    }

    public int getPlayerLocationYs(int i) {
        return this.playerLocationYs.get(i);
    }

    public int getHeight() {
        return this.height;
    }

    public HeroTeamLegends getHeroTeam(int i){
        return this.heroes.get(i);
    }

    // Mutators
    public int setPlayerLocationXs(int i, int x) {
        return this.playerLocationXs.set(i, x);
    }

    public int setPlayerLocationYs(int i, int y) {
        return this.playerLocationYs.set(i, y);
    }

    // Sees if the player is on a market
    public boolean checkMarket(int player){
        System.out.println(this.getPlayerLocationXs(player));
        System.out.println(this.getPlayerLocationYs(player));
        if (this.getMap()[this.getPlayerLocationXs(player)][this.getPlayerLocationYs(player)].getTerrain().getTradeAllowed()) {
            return true;
        }
        return false;
    }

    @Override
    public void generateWorld() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (i == 0 || i == height-1 || j % 3 == 0 ||j == 0 || j == getSize()-1)         //Sets the border of the board to be severely restricted.
                    this.getMap()[i][j] = new GridSquareSeverelyRestricted();
                else if (i == 1){                                                               // Top is monster nexus
                    this.getMap()[i][j] = new GridSquareNexusMonster(i,j);
                }
                else if (i == height-2){                                                        // Bottom is hero nexus                 
                    this.getMap()[i][j] = new GridSquareNexusHero(this.getFactoryMarket().createMarket(),i,j);    // Creates market
                    int heroNum = (j-1) / 3;
                    if (j>0 && (j-1) % 3 == 0 && heroNum < this.getHeroTeam().getTeamSize()) {      // Spawns heroes to nexus
                        this.heroes.get(heroNum).setHomeNexus(this.getMap()[i][j]);
                        this.playerLocationXs.add(i);
                        this.playerLocationYs.add(j);
                        this.getMap()[i][j].moveHeroTeam(this.heroes.get(heroNum));
                    }
                }
                else {                                                                          // Creates random terrain
                    int randNum = this.getRand().generateRandomNumber();
                    if (randNum <= this.getProbabilityMarket()) {
                        randNum = this.getRand().getRandomInt(4, 6);
                        this.getMap()[i][j] = new GridSquareLegend(randNum, i, j);                                // Creates legend terrain
                    }
                    else if (randNum <= this.getProbabilityMarket() + this.getProbabilityRestricted()) {      // Creates restricted terrain
                        this.getMap()[i][j] = new GridSquareRestricted();
                    }
                    else {
                        this.getMap()[i][j] = new GridSquareLegend(0, i ,j);                              // Creates common terrain
                    }
                }
            }
        }
    }

    // Checks to see which monsters are within a 1 block range of hero
    public ArrayList<Monster> getMonstersInRange(int x, int y) {
        ArrayList<Monster> monsters = new ArrayList<>();
        int[][] indices = {{-1,-1},{-1,0},{0,-1},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
        for (int i = 0; i < indices.length; i++) {
            GridSquare gs = this.getMap()[x+indices[i][0]][y+indices[i][1]];
            if (gs instanceof GridSquareLegend && ((GridSquareLegend) gs).hasMonster()) {
                monsters.add(((GridSquareLegend) gs).getMonster());
            }
        }
        return monsters;
    }

    // Checks to see which heroes are within a 1 block range of monster
    public ArrayList<HeroTeamLegends> getHeroesInRange(int x, int y) {
        ArrayList<HeroTeamLegends> heroes = new ArrayList<>();
        int[][] indices = {{-1,-1},{-1,0},{0,-1},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
        for (int i = 0; i < indices.length; i++) {
            GridSquare gs = this.getMap()[x+indices[i][0]][y+indices[i][1]];
            if (gs instanceof GridSquareLegend && ((GridSquareLegend) gs).getHeroTeam() != null) {
                heroes.add((HeroTeamLegends) ((GridSquareLegend) gs).getHeroTeam());
            }
        }
        return heroes;
    }

    // Shows the entire world
    public void showGlobalView() {
        System.out.println(this);
    }

    // Heroes move
    public void moveHeroTeam(int dx, int dy, int heroNum) {
        Color.clearScreen();
        if (this.getMap()[playerLocationXs.get(heroNum) + dx][playerLocationYs.get(heroNum) + dy].getTerrain().getWalkable()){      // Checks to see if the terrain is walkable
            this.getMap()[playerLocationXs.get(heroNum)][playerLocationYs.get(heroNum)].removeHeroTeam();                           // Removes hero from old location
            this.getMap()[playerLocationXs.get(heroNum) + dx][playerLocationYs.get(heroNum) + dy].moveHeroTeam(this.heroes.get(heroNum));   // Moves hero to new location
            playerLocationXs.set(heroNum, playerLocationXs.get(heroNum) + dx);                                          // Updates hero location            
            playerLocationYs.set(heroNum, playerLocationYs.get(heroNum) + dy);
            this.heroes.get(heroNum).travel();
        }

    }

    // toString returns a stringified version of the entire world
    public String toString() {
        String output = "";
        for (int i = 0; i < this.height; i++) {
            String row13 = "";
            String row2 = "";
            for (int j = 0; j < this.getSize(); j++) {
                String terrainChar = " " + this.getMap()[i][j].getTerrain().getColor().getColor() + this.getMap()[i][j].getTerrain().getSymbol()
                                                                            + this.getMap()[i][j].getTerrain().getColor().getBlack() + " ";
                row13 += terrainChar + "-" + terrainChar + "-" + terrainChar;
                if (this.getMap()[i][j].getIsOccupied())
                    row2 += " | " + this.getMap()[i][j].getHeroTeam().getColor().getColor() + "H" + this.getMap()[i][j].getHeroTeam().getSymbol()
                                                                            + this.getMap()[i][j].getHeroTeam().getColor().getBlack() + " ";
                else
                    row2 += " |    ";
                if (this.getMap()[i][j].getIsOccupied()) // TODO: CHANGE TO CHECK FOR MONSTER
                    row2 += "M1 | ";
                else
                    row2 += "   | ";
            }
            output += row13+"\n"+row2+"\n"+row13+"\n";
        }
        return output;
    }

    // Shows the world around the destination hero and returns the coordinates of the destination
    public int[] showTeleportDestination(int heroNum) {
        int x = this.getPlayerLocationXs(heroNum);
        int y = this.getPlayerLocationYs(heroNum);
        int[][] record = new int[2][2];             // Records the coordinates of the destination
        String output = "";
        int position = 1;
        for (int i = x; i <= x+1; i++) {            // prints the world around the hero
            String row13 = "";
            String row2 = "";
            for (int j = y-1; j <= y+1; j++) {
                String terrainChar = " " + this.getMap()[i][j].getTerrain().getColor().getColor() + this.getMap()[i][j].getTerrain().getSymbol()
                                                                            + this.getMap()[i][j].getTerrain().getColor().getBlack() + " ";
                row13 += terrainChar + "-" + terrainChar + "-" + terrainChar;
                Color temp = new Color("red");
                if (this.getMap()[i][j].getIsOccupied())
                    row2 += " | " + temp.getColor() + "X  " + temp.getBlack() + " ";
                else{
                    if (this.getMap()[i][j].getTerrain().getWalkable() && !((i == x+1 && j == y-1)||(i == x+1 && j == y+1))){
                        row2 += " | " + position + "  ";
                        record[position-1][0] = i;
                        record[position-1][1] = j;
                        position++;
                    } 
                    else
                        row2 += " |    ";
                }
                if (this.getMap()[i][j].getIsOccupied()) // TODO: CHANGE TO CHECK FOR MONSTER
                    row2 += "X | ";
                else
                    row2 += "   | ";
            }
            output += row13+"\n"+row2+"\n"+row13+"\n";
        }
        System.out.println(output);
        
        Input input = new Input();
        int choice = input.queryInt("Where would you like to teleport to? ", 1, position);          // Asks the user where they want to teleport to
        return record[choice-1];                                                                                        // Returns the coordinates of the destination
    }

    // changes specified terrain to common terrain
    public void manipulateTerrain(int x, int y, int i) {
        if (this.getMap()[playerLocationXs.get(i)+x][playerLocationYs.get(i)+y].getTerrain().getType() != 3         // makes sure not severly restricted
        && this.getMap()[playerLocationXs.get(i)+x][playerLocationYs.get(i)+y].getTerrain().getType() != 7          // makes sure not nexus
        && this.getMap()[playerLocationXs.get(i)+x][playerLocationYs.get(i)+y].getTerrain().getType() != 8) {
            this.getMap()[playerLocationXs.get(i)+x][playerLocationYs.get(i)+y] = new GridSquareLegend();
        }
        else {
            System.out.println("Geb cannot manipulate this terrain.");
        }
    }
}
