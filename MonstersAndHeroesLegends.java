public class MonstersAndHeroesLegends {
    private WorldLegends world;
    private Input userInput;

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
        world.generateWorld();
        GridSquareLegend temp = (GridSquareLegend) world.getMap()[2][2];
        temp.setMonster(new Monster());
        System.out.println("Monster created");
    }

    public boolean checkMonsterAdjacent(int i){
        int x = this.world.getPlayerLocationXs(i);
        int y = this.world.getPlayerLocationYs(i);
        if (this.world.getMap()[x][y].getTerrain().getType() == 7){
            return false;
        }
        if (this.world.getMap()[x][y-1].getTerrain().getWalkable()) {
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
            for (int i = 0; i < world.getHeroTeam().getTeamSize(); i++) {
                if (checkWinCondition() != 0) {
                    break;
                }
                this.world.showGlobalView();
                System.out.println("[+] Player " + (i+1) + "'s turn.");
                String move = userInput.userAction().toLowerCase();
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
                    case "m":                                                           // show map            
                        Color.clearScreen();
                        this.world.getHeroTeam(i).recall();
                        // Remove from current cell
                        int x = this.world.getPlayerLocationXs(i);
                        int y = this.world.getPlayerLocationYs(i);
                        this.world.getMap()[x][y].removeHeroTeam();
                        
                        // CHange coordinates of heroTeam
                        int height = this.world.getMap().length;
                        this.world.setPlayerLocationXs(i, height-2);
                        this.world.setPlayerLocationYs(i, (3*i+1));
                        break;
                    case "y":
                        Color.clearScreen();
                        player = this.userInput.queryInt("Which player would you like to teleport to?", 1, this.world.getHeroTeam().getTeamSize())-1;
                        int[] destination = this.world.showTeleportDestination(player);
                        x = this.world.getPlayerLocationXs(i);
                        y = this.world.getPlayerLocationYs(i);
                        this.world.getMap()[destination[0]][destination[1]].moveHeroTeam(this.world.getHeroTeam(i));
                        this.world.getMap()[x][y].removeHeroTeam();
                        this.world.setPlayerLocationXs(i, destination[0]);
                        this.world.setPlayerLocationYs(i, destination[1]);
                        break;
                    case "h":                                                           // show hero team                  
                        Color.clearScreen();
                        this.world.getHeroTeam().showHeroTeam();
                        break;
                    case "i":                                                        // show inventory                  
                        Color.clearScreen();
                        if (this.world.getHeroTeam().getTeamSize() > 1){                // if there are multiple players, ask which player's inventory to view
                            String temp = "";
                            for (int j = 0; j < this.world.getHeroTeam().getTeamSize(); j++) {
                                temp += (j+1) + ". " + this.world.getHeroTeam().getHeroes().get(j).getName() + " ";
                            }
                            player = userInput.queryInt("Which player's inventory would you like to view?" + temp, 1, this.world.getHeroTeam().getTeamSize());
                            world.getHeroTeam().getHeroes().get(player-1).showInventory();
                        }
                        else {
                            world.getHeroTeam().getHeroes().get(0).showInventory();
                        }
                        break;
                    case "t":                                                        // trade with merchant                         
                        if (this.world.checkMarket(i)){
                            Color.clearScreen();
                            GridSquareNexusHero market = (GridSquareNexusHero) this.world.getMap()[this.world.getPlayerLocationXs(i)][this.world.getPlayerLocationYs(i)];
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
                        for (int j = 0; j < this.world.getHeroTeam().getTeamSize(); j++) {  // show all players
                            System.out.println((j+1) + ". " + this.world.getHeroTeam().getHeroes().get(j));
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
                        for (int j = 0; i < this.world.getHeroTeam().getTeamSize(); j++) {
                            System.out.println((j+1) + ". " + this.world.getHeroTeam().getHeroes().get(j));
                        }                                                               // show all players                 
                        player = userInput.queryInt("Which player is drinking?", 1, this.world.getHeroTeam().getTeamSize());
                        this.world.getHeroTeam().getHeroes().get(player-1).drinkPotion(this.world.getHeroTeam().getHeroes().get(player-1), null, userInput);
                        break;
                    default:
                        System.out.println("Invalid input. Please try again.");

                    }
            }
        }
        // while (world.getHeroTeam().heroAlive()) {                                   // While the player is alive  
        //     this.world.showPlayerView(this.world.getHeroTeam().getVision());        // show the player's view
        //     String move = userInput.userAction().toLowerCase();
        //     int player;
        //     switch (move) {
        //         case "w":                                                           // move north
        //             Color.clearScreen();
        //             this.world.moveHeroTeam(-1, 0);
        //             break;
        //         case "a":                                                           // move west
        //             Color.clearScreen();
        //             this.world.moveHeroTeam( 0, -1);
        //             break;
        //         case "s":                                                        // move south                  
        //             Color.clearScreen();
        //             this.world.moveHeroTeam(1, 0);
        //             break;
        //         case "d":                                                        // move east               
        //             Color.clearScreen();        
        //             this.world.moveHeroTeam(0, 1);
        //             break;
        //         case "m":                                                           // show map            
        //             Color.clearScreen();
        //             this.world.showGlobalView();
        //             break;
        //         case "h":                                                           // show hero team                  
        //             Color.clearScreen();
        //             this.world.getHeroTeam().showHeroTeam();
        //             break;
        //         case "i":                                                        // show inventory                  
        //             Color.clearScreen();
        //             if (this.world.getHeroTeam().getTeamSize() > 1){                // if there are multiple players, ask which player's inventory to view
        //                 String temp = "";
        //                 for (int i = 0; i < this.world.getHeroTeam().getTeamSize(); i++) {
        //                     temp += (i+1) + ". " + this.world.getHeroTeam().getHeroes().get(i).getName() + " ";
        //                 }
        //                 player = userInput.queryInt("Which player's inventory would you like to view?" + temp, 1, this.world.getHeroTeam().getTeamSize());
        //                 world.getHeroTeam().getHeroes().get(player-1).showInventory();
        //             }
        //             else {
        //                 world.getHeroTeam().getHeroes().get(0).showInventory();
        //             }
        //             break;
        //         case "t":                                                        // trade with merchant                         
        //             if (this.world.checkMarket()){
        //                 Color.clearScreen();
        //                 GridSquareMarket market = (GridSquareMarket) this.world.getCurrent();
        //                 market.getMarket().enterMarket(this.world.getHeroTeam());
        //             }
        //             else {
        //                 System.out.println("There is no market in this area.");
        //             }
        //             break;
        //         case "g":                                                       // Manipulate terrain (GEB only)                                   
        //             if (this.world.getHeroTeam().canManipulateTerrain()){
        //                 // Color.clearScreen();
        //                 int direction = userInput.queryInt("Which direction would you like to manipulate terrain? (1. North, 2. East, 3. South, 4. West)", 1, 4);
        //                 switch (direction){
        //                     case 1:                                             // replace north square with a common
        //                         this.world.manipulateTerrain(-1, 0);            
        //                         break;
        //                     case 2:                                        // replace east square with a common     
        //                         this.world.manipulateTerrain(0, 1);
        //                         break;
        //                     case 3:                                       // replace south square with a common                 
        //                         this.world.manipulateTerrain(1, 0);
        //                         break;
        //                     case 4:                                     // replace west square with a common            
        //                         this.world.manipulateTerrain(0, -1);
        //                         break;
        //                 }
        //             }
        //             else {
        //                 System.out.println("Only Geb can manipulate terrain.");
        //             }
        //             break;
                
        //         case "c":                                                               // Change equiptment                               
        //             Color.clearScreen();
        //             for (int i = 0; i < this.world.getHeroTeam().getTeamSize(); i++) {  // show all players
        //                 System.out.println((i+1) + ". " + this.world.getHeroTeam().getHeroes().get(i));
        //             }
        //             player = userInput.queryInt("Which player's to change?", 1, this.world.getHeroTeam().getTeamSize());
        //             int temp = userInput.queryInt("Would you like to remove equiptment or add equiptment? (1. Remove, 2. Add)", 1, 2);
        //             if (temp == 1){                                                     // remove equiptment                 
        //                 this.world.getHeroTeam().getHeroes().get(player-1).removeEquiptment(this.world.getHeroTeam().getHeroes().get(player-1), userInput);
        //             }
        //             else {                                                               // add from ruck to body             
        //                 this.world.getHeroTeam().getHeroes().get(player-1).addEquiptment(this.world.getHeroTeam().getHeroes().get(player-1), null, userInput);
        //             }
        //             break;
                
        //         case "p":                                                           // Drink potion            
        //             Color.clearScreen();
        //             for (int i = 0; i < this.world.getHeroTeam().getTeamSize(); i++) {
        //                 System.out.println((i+1) + ". " + this.world.getHeroTeam().getHeroes().get(i));
        //             }                                                               // show all players                 
        //             player = userInput.queryInt("Which player is drinking?", 1, this.world.getHeroTeam().getTeamSize());
        //             this.world.getHeroTeam().getHeroes().get(player-1).drinkPotion(this.world.getHeroTeam().getHeroes().get(player-1), null, userInput);
        //             break;
        //         default:
        //             System.out.println("Invalid input. Please try again.");
        //     }
        // }
        // Color.clearScreen();
        // System.out.println("[+] All heroes have become legends. Game over!");
        // this.world.getHeroTeam().printStats();
        if (checkWinCondition() == 1) {
            System.out.println("The heroes have won!");
        }
<<<<<<< Updated upstream
        else if (checkWinCondition() == 2) {
            System.out.println("The monsters have won!");
=======
        else if (checkWinCondition() % 2 == 0 && checkWinCondition() != 0) {                    // check win condition. If multiple cross, still win not a tie
            System.out.println("[+] The monsters have won!");
>>>>>>> Stashed changes
        }
        else {
            System.out.println("The game has ended in a draw.");
        }
    }

    public int checkWinCondition(){
        int win = 0;
        for (int i = 0; i < this.world.getHeight(); i++) {
            for (int j = 0; j < this.world.getSize(); j++) {
                if (i == 1){
                    if (this.world.getMap()[i][j] instanceof GridSquareNexusMonster) {
                        GridSquareNexusMonster temp = (GridSquareNexusMonster) this.world.getMap()[i][j];
                        win += temp.checkWin();
                    }
                }
                else if (i == this.world.getHeight()-2){
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
