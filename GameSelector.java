/*
 * GameSelector.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * Chooses which game to play
 */
public class GameSelector {
    private Input input;
    

    public GameSelector() {
        this.input = new Input();
        int i = input.queryInt("Which game would you like to play? 1: Monsters and Heroes 2: Legends", 1, 2);
        switch (i) {
            case 1:
                MonstersAndHeroes mh = new MonstersAndHeroes();
                mh.start();
                break;
            case 2:
                MonstersAndHeroesLegends mhl = new MonstersAndHeroesLegends();
                mhl.start();
                break;
            default:
                System.out.println("[-] Invalid input");
                break;
        }
    }
}
