/*
 * GameSelector.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * Chooses which game to play
 */
public class GameSelector {
    private Input input;
    
    // Constructor
    public GameSelector() {
        this.input = new Input();
        boolean play = true;
        while (play) {
            int i = input.queryInt("Which game would you like to play? 1: Monsters and Heroes 2: Legends", 1, 2);   // Choose game
            switch (i) {
                case 1:
                    MonstersAndHeroes mh = new MonstersAndHeroes();                            // Start original game
                    mh.start();
                    break;
                case 2:
                    MonstersAndHeroesLegends mhl = new MonstersAndHeroesLegends();              // Start new game
                    mhl.start();
                    break;
                default:
                    System.out.println("[-] Invalid input");
                    break;
            }
            play = input.queryBoolean("Would you like to play again?");                     // Play again?
        }
        System.out.println("[+] Goodbye!");
    }
}
