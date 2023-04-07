import java.util.ArrayList;

public class WorldLegends extends World{
    private int height;
    private int numHeroes;
    private ArrayList<HeroTeamLegends> heroes;
    private ArrayList<Integer> playerLocationXs;
    private ArrayList<Integer> playerLocationYs;

    public WorldLegends(int size) {
        super(size, 1);
        numHeroes = this.getHeroTeam().getHeroes().size();
        this.height = size;
        this.setProbabilityMarket(30);
        this.setProbabilityRestricted(5);
        this.heroes = new ArrayList<>();

        for (int i = 0; i < this.getHeroTeam().getHeroes().size(); i++) {
            System.out.println(this.getHeroTeam().getHeroes().get(i));
        }
        for (int i = 0; i < this.getHeroTeam().getHeroes().size(); i++) {
            this.heroes.add(new HeroTeamLegends(this.getHeroTeam().getHeroes().get(i),i));
        }
        this.playerLocationXs = new ArrayList<>();
        this.playerLocationYs = new ArrayList<>();
    }

    public int getPlayerLocationXs(int i) {
        return this.playerLocationXs.get(i);
    }

    public int getPlayerLocationYs(int i) {
        return this.playerLocationYs.get(i);
    }

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

    public int getHeight() {
        return this.height;
    }

    @Override
    public void generateWorld() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (i == 0 || i == height-1 || j % 3 == 0 ||j == 0 || j == getSize()-1)         //Sets the border of the board to be severely restricted.
                    this.getMap()[i][j] = new GridSquareSeverelyRestricted();
                // else if (i == getPlayerLocationX() && j == getPlayerLocationY()) {    //Sets the player's location to be common terrain.
                //     this.getMap()[i][j] = new GridSquareCommon();
                //     this.getMap()[i][j].moveHeroTeam(this.getHeroTeam());
                // }
                else if (i == 1){
                    this.getMap()[i][j] = new GridSquareNexusMonster(i,j);
                }
                else if (i == height-2){
                    this.getMap()[i][j] = new GridSquareNexusHero(this.getFactoryMarket().createMarket(),i,j);    // Creates market
                    int heroNum = (j-1) / 3;
                    if (j>0 && (j-1) % 3 == 0 && heroNum < this.getHeroTeam().getTeamSize()) {
                        this.heroes.get(heroNum).setHomeNexus(this.getMap()[i][j]);
                        this.playerLocationXs.add(i);
                        this.playerLocationYs.add(j);
                        this.getMap()[i][j].moveHeroTeam(this.heroes.get(heroNum));
                    }
                }
                else {
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
        // if (this.map[playerLocationX + dx][playerLocationY + dy].getTerrain().getWalkable()) {          //Checks if the terrain is walkable.
        //     this.map[playerLocationX][playerLocationY].removeHeroTeam();                                //Removes the HeroTeam from the current GridSquare.
        //     this.map[playerLocationX + dx][playerLocationY + dy].moveHeroTeam(ht);                    //Moves the HeroTeam to the new GridSquare.               
        //     playerLocationX += dx;                                                                  //Updates the player's location.                    
        //     playerLocationY += dy;
        //     ht.travel();                                                                        //Updates the HeroTeam's travel distance.                           
        // }
        Color.clearScreen();
        System.out.println(playerLocationXs.get(heroNum));
        System.out.println(playerLocationYs.get(heroNum));
        System.out.println(heroNum);
        if (this.getMap()[playerLocationXs.get(heroNum) + dx][playerLocationYs.get(heroNum) + dy].getTerrain().getWalkable()){
            // System.out.println("WALK");
            this.getMap()[playerLocationXs.get(heroNum)][playerLocationYs.get(heroNum)].removeHeroTeam();
            // System.out.println("Removed");
            this.getMap()[playerLocationXs.get(heroNum) + dx][playerLocationYs.get(heroNum) + dy].moveHeroTeam(this.heroes.get(heroNum));
            // System.out.println("Moved");
            playerLocationXs.set(heroNum, playerLocationXs.get(heroNum) + dx);
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
                String terrainChar = " " + this.getMap()[i][j].getTerrain().getColor().getColor() + this.getMap()[i][j].getTerrain().getSymbol() + this.getMap()[i][j].getTerrain().getColor().getBlack() + " ";
                row13 += terrainChar + "-" + terrainChar + "-" + terrainChar;
                if (this.getMap()[i][j].getIsOccupied())
                    row2 += " | " + this.getMap()[i][j].getHeroTeam().getColor().getColor() + "H" + this.getMap()[i][j].getHeroTeam().getSymbol() + this.getMap()[i][j].getHeroTeam().getColor().getBlack() + " ";
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

    public HeroTeamLegends getHeroTeam(int i){
        return this.heroes.get(i);
    }

    public int[] showTeleportDestination(int heroNum) {
        int x = this.getPlayerLocationXs(heroNum);
        int y = this.getPlayerLocationYs(heroNum);
        int[][] record = new int[2][2];
        String output = "";
        int position = 1;
        for (int i = x; i <= x+1; i++) {
            String row13 = "";
            String row2 = "";
            for (int j = y-1; j <= y+1; j++) {
                String terrainChar = " " + this.getMap()[i][j].getTerrain().getColor().getColor() + this.getMap()[i][j].getTerrain().getSymbol() + this.getMap()[i][j].getTerrain().getColor().getBlack() + " ";
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
        int choice = input.queryInt("Where would you like to teleport to? ", 1, position);
        System.out.println(record[choice-1][0]);
        System.out.println(record[choice-1][1]);
        
        return record[choice-1];
    }
}
