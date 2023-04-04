/*
 * HeroTeam.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Represents a team of heroes in game
 */

import java.util.*;
public class HeroTeam {
    private Color color = new Color("blue");
    private String symbol = "H";
    private int kmTraveled = 0;
    private ArrayList <Hero> heroes;
    private FactoryHero factoryHero;
    private int maxTeamSize = 3;
    private int combinedLevel;
    private int teamSize;
    private boolean canManipulateTerrain = false;           // Easter egg
    private boolean canHeal = false;                        // Easter egg
    private int canSee = 3;                                 // Easter egg

    // Constructor
    public HeroTeam() {
        heroes = new ArrayList<Hero>();
        factoryHero = new FactoryHero();
        heroes = factoryHero.selectHeroes(maxTeamSize);
        teamSize = heroes.size();
        for (Hero hero : heroes) {
            if (hero.getName().equals("Isis")) {    // These gods are Easter eggs
                canHeal = true;
            }
            else if (hero.getName().equals("Heimdallr")) {
                canSee = 5;
            }
            else if (hero.getName().equals("Geb")) {
                canManipulateTerrain = true;
            }
        }
    }

    // Accessors
    public Color getColor() {
        return color;
    }
    public ArrayList <Hero> getHeroes() {
        return heroes;
    }
    public int getVision() {
        return canSee;
    }
    public boolean canManipulateTerrain() {
        return canManipulateTerrain;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getTeamSize(){
        return teamSize;
    }

    public int getCombinedLevel() {
        updateCombinedLevel();
        return combinedLevel;
    }

    public int getKmTraveled(){
        return kmTraveled;
    }

    // Returns the symbol of the hero team
    public String toString() {
        return color.getColor() + symbol + color.getBlack();
    }

    // Prints the stats of the hero team
    public void printStats() {
        System.out.println("Combined Level: " + combinedLevel);
        System.out.println("Kilometers Traveled: " + kmTraveled);
        System.out.println("Heroes:");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println("Player " + (i + 1) + ":");
            System.out.println(heroes.get(i));
        }
    }

    // Updates the combined level of the hero team
    public void updateCombinedLevel() {
        combinedLevel = 0;
        for (Hero hero : heroes) {
            combinedLevel += hero.getLevel().getLevel();
        }
    }

    // If heal, heal as travel
    public void travel(){
        kmTraveled++;
        if (canHeal) {
            for (Hero hero : heroes) {
                hero.heal(10);
            }
        }
        // If Isis, heal her
    }

    // Shows the inventory of the hero team
    public void showInventory(){
        for (Hero hero : heroes) {
            System.out.println(hero.getName());
            hero.showInventory();
        }
    }

    // Shows the hero team
    public void showHeroTeam(){
        for (Hero hero : heroes) {
            System.out.println(hero);
        }
    }

    // Returns true if the hero team is alive
    public boolean heroAlive(){
        for (Hero hero : heroes) {
            if (hero.getAttributes().get("Health").getCurrent() > 0) {
                return true;
            }
        }
        return false;
    }

    // Actions of the hero team
    public void heroAttack(HeroTeam heroTeam, ArrayList <Monster> monsters){
        Input input = new Input();
        for (Hero hero : heroTeam.getHeroes()){
            if (hero.getAttributes().get("Health").getCurrent() <= 0)           // If hero is dead, skip turn
                continue;
            System.out.println(hero.getName() + " it is your turn!");
            System.out.println(hero);
            int action = input.queryInt("What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory", 1, 4, "i");
            while (action < 0){                                                     // Print inventory if special character                  
                Color.clearScreen();
                hero.battleInventory(monsters);
                action = input.queryInt("What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory", 1, 4, "i");
            }
            if (action == 1){                                                       // Attack           
                Color.clearScreen();
                hero.attackWPN(hero, monsters, input);
            }
            else if (action == 2){                                                  // Cast spell                     
                Color.clearScreen();
                hero.useCharm(hero, monsters, input);
            }
            else if (action == 3){                                                  // Drink potion                   
                Color.clearScreen();
                hero.drinkPotion(hero, monsters, input);
            }
            else {                                                                  // Equipt from inventory                          
                Color.clearScreen();
                hero.addEquiptment(hero, monsters, input);
            }
            for (int i = 0; i < monsters.size(); i++){
                if (monsters.get(i).getAttributes().get("Health").getCurrent() <= 0){   // If monster is dead, give experience and gold to living heroes
                    for (Hero hero1: heroTeam.getHeroes()){
                        if (hero1.getAttributes().get("Health").getCurrent() > 0)
                            hero1.getLevel().addExperience(2);
                            hero1.setGold(hero1.getGold() + monsters.get(i).getLevel().getLevel()*100);
                    }
                    monsters.remove(i);                                             // Remove monster if dead
                }
            }
        }
    }
    
}
