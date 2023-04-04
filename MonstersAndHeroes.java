/*
 * MonstersAndHeroes.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Main game loop. Creates world with player in it
 */

public class MonstersAndHeroes {
    private World world;
    private Input userInput;

    // Constructor
    public MonstersAndHeroes() {
        this.userInput = new Input();
        System.out.println("[+] Welcome to Monsters and Heroes!");
        boolean modifySize = this.userInput.queryBoolean("Would you like to modify the size of the world?");
        if (modifySize) {
            int size = this.userInput.queryInt("Enter the size of the world", 3) + 2;   // +2 to account for walls, minimum size is 3x3
            this.world = new World(size);
        }
        else {
            this.world = new World();
        }
    }

    // Main game loop
    public void start() {
        Color.clearScreen();
        world.showGlobalView();                                                     // show the world
        while (world.getHeroTeam().heroAlive()) {                                   // While the player is alive  
            this.world.showPlayerView(this.world.getHeroTeam().getVision());        // show the player's view
            String move = userInput.userAction().toLowerCase();
            int player;
            switch (move) {
                case "w":                                                           // move north
                    Color.clearScreen();
                    this.world.moveHeroTeam(-1, 0);
                    break;
                case "a":                                                           // move west
                    Color.clearScreen();
                    this.world.moveHeroTeam( 0, -1);
                    break;
                case "s":                                                        // move south                  
                    Color.clearScreen();
                    this.world.moveHeroTeam(1, 0);
                    break;
                case "d":                                                        // move east               
                    Color.clearScreen();        
                    this.world.moveHeroTeam(0, 1);
                    break;
                case "m":                                                           // show map            
                    Color.clearScreen();
                    this.world.showGlobalView();
                    break;
                case "h":                                                           // show hero team                  
                    Color.clearScreen();
                    this.world.getHeroTeam().showHeroTeam();
                    break;
                case "i":                                                        // show inventory                  
                    Color.clearScreen();
                    if (this.world.getHeroTeam().getTeamSize() > 1){                // if there are multiple players, ask which player's inventory to view
                        String temp = "";
                        for (int i = 0; i < this.world.getHeroTeam().getTeamSize(); i++) {
                            temp += (i+1) + ". " + this.world.getHeroTeam().getHeroes().get(i).getName() + " ";
                        }
                        player = userInput.queryInt("Which player's inventory would you like to view?" + temp, 1, this.world.getHeroTeam().getTeamSize());
                        world.getHeroTeam().getHeroes().get(player-1).showInventory();
                    }
                    else {
                        world.getHeroTeam().getHeroes().get(0).showInventory();
                    }
                    break;
                case "t":                                                        // trade with merchant                         
                    if (this.world.checkMarket()){
                        Color.clearScreen();
                        GridSquareMarket market = (GridSquareMarket) this.world.getCurrent();
                        market.getMarket().enterMarket(this.world.getHeroTeam());
                    }
                    else {
                        System.out.println("There is no market in this area.");
                    }
                    break;
                case "g":                                                       // Manipulate terrain (GEB only)                                   
                    if (this.world.getHeroTeam().canManipulateTerrain()){
                        // Color.clearScreen();
                        int direction = userInput.queryInt("Which direction would you like to manipulate terrain? (1. North, 2. East, 3. South, 4. West)", 1, 4);
                        switch (direction){
                            case 1:                                             // replace north square with a common
                                this.world.manipulateTerrain(-1, 0);            
                                break;
                            case 2:                                        // replace east square with a common     
                                this.world.manipulateTerrain(0, 1);
                                break;
                            case 3:                                       // replace south square with a common                 
                                this.world.manipulateTerrain(1, 0);
                                break;
                            case 4:                                     // replace west square with a common            
                                this.world.manipulateTerrain(0, -1);
                                break;
                        }
                    }
                    else {
                        System.out.println("Only Geb can manipulate terrain.");
                    }
                    break;
                
                case "c":                                                               // Change equiptment                               
                    Color.clearScreen();
                    for (int i = 0; i < this.world.getHeroTeam().getTeamSize(); i++) {  // show all players
                        System.out.println((i+1) + ". " + this.world.getHeroTeam().getHeroes().get(i));
                    }
                    player = userInput.queryInt("Which player's to change?", 1, this.world.getHeroTeam().getTeamSize());
                    int temp = userInput.queryInt("Would you like to remove equiptment or add equiptment? (1. Remove, 2. Add)", 1, 2);
                    if (temp == 1){                                                     // remove equiptment                 
                        this.world.getHeroTeam().getHeroes().get(player-1).removeEquiptment(this.world.getHeroTeam().getHeroes().get(player-1), userInput);
                    }
                    else {                                                               // add from ruck to body             
                        this.world.getHeroTeam().getHeroes().get(player-1).addEquiptment(this.world.getHeroTeam().getHeroes().get(player-1), null, userInput);
                    }
                    break;
                
                case "p":                                                           // Drink potion            
                    Color.clearScreen();
                    for (int i = 0; i < this.world.getHeroTeam().getTeamSize(); i++) {
                        System.out.println((i+1) + ". " + this.world.getHeroTeam().getHeroes().get(i));
                    }                                                               // show all players                 
                    player = userInput.queryInt("Which player is drinking?", 1, this.world.getHeroTeam().getTeamSize());
                    this.world.getHeroTeam().getHeroes().get(player-1).drinkPotion(this.world.getHeroTeam().getHeroes().get(player-1), null, userInput);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
        Color.clearScreen();
        System.out.println("[+] All heroes have become legends. Game over!");
        this.world.getHeroTeam().printStats();
    }
}
