/*
 * HeroTeamLegends.java
 * by Kevin Lin (lin2391@bu.edu)
 * 07APR2023
 * 
 * Represents a team of heroes in legends game.
 * Each Hero team is in a lane. Each hero team has 1 hero
 */
import java.util.ArrayList;

public class HeroTeamLegends extends HeroTeam{
    private GridSquare homeNexus;                       // Nexus that the hero team should spawn at

    // Constructors
    public HeroTeamLegends(int heroNum) {
        super();
        this.setSymbol(String.valueOf(heroNum+1));
    }

    // Constructor that takes in a specific hero
    public HeroTeamLegends(Hero hero, int heroNum) {
        super(hero);
        this.setSymbol(String.valueOf(heroNum+1));
    }

    // Sets the nexus that the hero should spawn at
    public void setHomeNexus(GridSquare nexus) {
        this.homeNexus = nexus;
    }

    // Returns the hero team to spawn nexus
    public boolean recall() {
        if (this.homeNexus != null && this.homeNexus.getHeroTeam() == null){        // if exists and not occupied
            this.homeNexus.moveHeroTeam(this);
            return true;
        }
        else {
            System.out.println("You cannot recall to this nexus");
            return false;
        }
        
    }

    // Respawns the hero team. Resets everything except for name and stats. Moves back to nexus
    public void respawn() {
        Hero temp = this.getHeroes().get(0);
        String name = temp.getName();                            // Save name
        FactoryHero factory = new FactoryHero();                
        temp = factory.createHero(name);
        this.setHero(temp, 0);                          // Reset hero
        recall();                                                   // Move back to nexus
    }

    // Actions of the hero team
    public void heroAttack(HeroTeam heroTeam, ArrayList <Monster> monsters){
        if (monsters.size() == 0){                                      // If no monsters, skip turn
            System.out.println("There are no monsters to attack!");
            return;
        }
        Input input = new Input();
        for (Hero hero : heroTeam.getHeroes()){
            if (hero.getAttributes().get("Health").getCurrent() <= 0){           // If hero is dead, skip turn
                respawn();
                continue;
            }
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
