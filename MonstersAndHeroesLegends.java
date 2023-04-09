/*
 * MonstersAndHeroesLegends.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * This is the main controlling class of the game. Initializes the world and starts the game loop
 */

public class MonstersAndHeroesLegends {
    private WorldLegends world;
    private Input userInput;
    private MonsterController mc;

    // Constructor
    public MonstersAndHeroesLegends() {
        this.userInput = new Input();
        System.out.println("[+] Welcome to Monsters and Heroes Legends!");
        boolean modifySize = this.userInput.queryBoolean("Would you like to modify the size of the world?");
        if (modifySize) {
            int size = this.userInput.queryInt("Enter the size of the world", 8) + 2;   // +2 to account for walls, minimum size is 3x3
            this.world = new WorldLegends(size);
        }
        else {
            this.world = new WorldLegends(10);
        }
        mc = new MonsterController(5, this.world);
        world.generateWorld(this.mc);
    }

    // Checks to see if monster is next to hero team. If so, the hero cannot move forward
    private boolean checkMonsterAdjacent(int i){
        int x = this.world.getPlayerLocationXs(i);
        int y = this.world.getPlayerLocationYs(i);
        if (this.world.getMap()[x][y].getTerrain().getType() == 7){
            return false;
        }
        if (this.world.getMap()[x][y-1].getTerrain().getWalkable()) {                   // Ensures that tile is not a wall
            GridSquareLegend temp = (GridSquareLegend) this.world.getMap()[x][y-1];
            if (temp.getMonster() != null) {
                return true;
            }
        }
        if (this.world.getMap()[x][y+1].getTerrain().getWalkable()) {
            GridSquareLegend temp = (GridSquareLegend) this.world.getMap()[x][y+1];
            if (temp.getMonster() != null) {
                return true;
            }
        }
        return false;
    }

    // Main game loop
    public void start() {
        Color.clearScreen();
        while (checkWinCondition() == 0) {                                   
            for (int i = 0; i< world.getHeroTeam().getTeamSize(); i++) {                // Check if hero is dead, if so, respawn
                if (!this.world.getHeroTeam(i).heroAlive()){
                    this.world.getHeroTeam(i).respawn();
                    int x = this.world.getPlayerLocationXs(i);
                    int y = this.world.getPlayerLocationYs(i);
                    this.world.getMap()[x][y].removeHeroTeam();
                    // CHange coordinates of heroTeam
                    int height = this.world.getMap().length;
                    this.world.setPlayerLocationXs(i, height-2);
                    this.world.setPlayerLocationYs(i, (3*i+1));
                }
            }
            for (int i = 0; i < world.getHeroTeam().getTeamSize(); i++) {               // Hero team's turn. Loops through for each hero
                if (checkWinCondition() != 0) {                                         // Check if win condition is met
                    break;
                }
                this.world.showGlobalView();
                System.out.println("[+] Player " + (i+1) + "'s turn.");
                String move = userInput.userActionLegend().toLowerCase();
                int player;
                switch (move) {
                    case "w":                                                           // move north
                        Color.clearScreen();
                        if (checkMonsterAdjacent(i)) {
                            System.out.println("[!] There is a monster adjacent to you! You cannot move.");
                            break;
                        }
                        this.world.moveHeroTeam(-1, 0, i);
                        break;
                    
                    case "a":                                                           // move west
                        Color.clearScreen();
                        this.world.moveHeroTeam( 0, -1, i);
                        break;
                    
                    case "s":                                                        // move south                  
                        Color.clearScreen();
                        this.world.moveHeroTeam(1, 0, i);
                        break;
                    
                    case "d":                                                        // move east               
                        Color.clearScreen();        
                        this.world.moveHeroTeam(0, 1, i);
                        break;
                    
                    case "m":                                                           // recall to spawning nexus
                        Color.clearScreen();
                        boolean b = this.world.getHeroTeam(i).recall();                 // recall returns true if moved
                        int x = this.world.getPlayerLocationXs(i);
                        int y = this.world.getPlayerLocationYs(i);
                        if (b){                                                          // if moved, remove hero team from old location
                            this.world.getMap()[x][y].removeHeroTeam();
                            int height = this.world.getMap().length;
                            this.world.setPlayerLocationXs(i, height-2);
                            this.world.setPlayerLocationYs(i, (3*i+1));
                        }
                        break;
                    
                    case "y":                                                           // TP to player
                        Color.clearScreen();
                        player = this.userInput.queryInt("Which player would you like to teleport to?", 1, this.world.getHeroTeam().getTeamSize())-1;
                        int[] destination = this.world.showTeleportDestination(player);     // Shows available teleport destinations
                        x = this.world.getPlayerLocationXs(i);
                        y = this.world.getPlayerLocationYs(i);
                        this.world.getMap()[destination[0]][destination[1]].moveHeroTeam(this.world.getHeroTeam(i));
                        this.world.getMap()[x][y].removeHeroTeam();                             // Remove hero team from old location
                        this.world.setPlayerLocationXs(i, destination[0]);
                        this.world.setPlayerLocationYs(i, destination[1]);
                        break;
                    
                    case "h":                                                           // show hero team. Doesnt count as a turn
                        Color.clearScreen();
                        this.world.getHeroTeam(i).showHeroTeam();
                        i--;
                        break;
                    
                    case "i":                                                        // show inventory of player whos turn it is. Doesnt count as a turn          
                        Color.clearScreen();
                        if (this.world.getHeroTeam(i).getTeamSize() > 1){               
                            String temp = "";
                            for (int j = 0; j < this.world.getHeroTeam(i).getTeamSize(); j++) {
                                temp += (j+1) + ". " + this.world.getHeroTeam(i).getHeroes().get(j).getName() + " ";
                            }
                            player = userInput.queryInt("Which player's inventory would you like to view?" + temp, 1, this.world.getHeroTeam(i).getTeamSize());
                            world.getHeroTeam(i).getHeroes().get(player-1).showInventory();
                        }
                        else {
                            world.getHeroTeam(i).getHeroes().get(0).showInventory();
                        }
                        i--;
                        break;
                    
                    case "t":                                                        // trade with merchant. Doesnt count as a turn                    
                        if (this.world.checkMarket(i)){
                            Color.clearScreen();
                            GridSquareNexusHero market = (GridSquareNexusHero) this.world.getMap()[this.world.getPlayerLocationXs(i)][this.world.getPlayerLocationYs(i)];
                            market.getMarket().enterMarket(this.world.getHeroTeam(i));
                        }
                        else {
                            System.out.println("[!] There is no market in this area.");
                        }
                        i--;
                        break;
                    
                    case "g":                                                       // Manipulate terrain (GEB only)                                   
                        if (this.world.getHeroTeam(i).canManipulateTerrain()){
                            // Color.clearScreen();
                            int direction = userInput.queryInt("Which direction would you like to manipulate terrain? (1. North, 2. East, 3. South, 4. West)", 1, 4);
                            switch (direction){
                                case 1:                                             // replace north square with a common
                                    this.world.manipulateTerrain(-1, 0, i);            
                                    break;
                                case 2:                                        // replace east square with a common     
                                    this.world.manipulateTerrain(0, 1, i);
                                    break;
                                case 3:                                       // replace south square with a common                 
                                    this.world.manipulateTerrain(1, 0, i);
                                    break;
                                case 4:                                     // replace west square with a common            
                                    this.world.manipulateTerrain(0, -1, i);
                                    break;
                            }
                        }
                        else {
                            System.out.println("[!] Only Geb can manipulate terrain.");
                        }
                        break;
                    
                    case "c":                                                               // Change equiptment                               
                        Color.clearScreen();
                        for (int j = 0; j < this.world.getHeroTeam(i).getTeamSize(); j++) {  // show all players
                            System.out.println((j+1) + ". " + this.world.getHeroTeam(i).getHeroes().get(j));
                        }
                        player = userInput.queryInt("Which player's to change?", 1, this.world.getHeroTeam(i).getTeamSize());
                        int temp = userInput.queryInt("Would you like to remove equiptment or add equiptment? (1. Remove, 2. Add)", 1, 2);
                        if (temp == 1){                                                     // remove equiptment                 
                            this.world.getHeroTeam(i).getHeroes().get(player-1).removeEquiptment(this.world.getHeroTeam(i).getHeroes().get(player-1), userInput);
                        }
                        else {                                                               // add from ruck to body             
                            this.world.getHeroTeam(i).getHeroes().get(player-1).addEquiptment(this.world.getHeroTeam(i).getHeroes().get(player-1), null, userInput);
                        }
                        break;
                    
                    case "p":                                                           // Drink potion            
                        Color.clearScreen();
                        for (int j = 0; i < this.world.getHeroTeam(i).getTeamSize(); j++) {
                            System.out.println((j+1) + ". " + this.world.getHeroTeam(i).getHeroes().get(j));
                        }                                                               // show all players                 
                        player = userInput.queryInt("Which player is drinking?", 1, this.world.getHeroTeam(i).getTeamSize());
                        this.world.getHeroTeam(i).getHeroes().get(player-1).drinkPotion(this.world.getHeroTeam(i).getHeroes().get(player-1), null, userInput);
                        break;
                    
                    case "f":
                        Color.clearScreen();
                        x = this.world.getPlayerLocationXs(i);
                        y = this.world.getPlayerLocationYs(i);
                        this.world.getHeroTeam(i).heroAttack(this.world.getHeroTeam(i), world.getMonstersInRange(x, y));
                        break;
                    
                    default:
                        System.out.println("[!] Invalid input. Please try again.");

                    }
            }
            this.mc.takeMonsterTurns();
        }
        if (checkWinCondition() == 1) {                         // check win condition
            System.out.println("[+] The heroes have won!");
        }
        else if (checkWinCondition() == 2) {                    // check win condition
            System.out.println("[+] The monsters have won!");
        }
        else {
            System.out.println("[+] The game has ended in a draw.");
        }
    }

    // Helper function to see who won. Returns 1 if heroes won, 2 if monsters won, 0 if draw
    private int checkWinCondition(){
        int win = 0;
        for (int i = 0; i < this.world.getHeight(); i++) {
            for (int j = 0; j < this.world.getSize(); j++) {
                if (i == 1){
                    if (this.world.getMap()[i][j] instanceof GridSquareNexusMonster) {      // check if monster nexus is occupied
                        GridSquareNexusMonster temp = (GridSquareNexusMonster) this.world.getMap()[i][j];
                        win += temp.checkWin();
                    }
                }
                else if (i == this.world.getHeight()-2){                                    // check if hero nexus is occupied             
                    if (this.world.getMap()[i][j] instanceof GridSquareNexusHero) {
                        GridSquareNexusHero temp = (GridSquareNexusHero) this.world.getMap()[i][j];
                        win += temp.checkWin();
                    }
                }
            }
        }
        return win;
    }
}
