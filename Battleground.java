/*
 * Battleground.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This class is used to represent the battleground where the hero fights monsters.
 * It contains a probability of monsters appearing, a factory to create monsters, and a random generator.
 */

import java.util.*;
public class Battleground {
    private int probMonsters;
    private FactoryMonster factoryMonster;
    private RandomGen rand;
    private ArrayList <Monster> monsters;

    public Battleground(){
        probMonsters = 50;
        factoryMonster = new FactoryMonster();
        rand = new RandomGen();
    }

    // takes heroes and will see if they encounter a monster. If so, it will create a monster and fight it.
    public void battle(HeroTeam heroes){
        if (heroes.getKmTraveled() > 0){                                                        // No monsters at start
            if (rand.generateRandomNumber() < probMonsters){                                    // If random number is less than probMonsters, then there is a monster
                monsters = factoryMonster.selectMonsters(heroes.getCombinedLevel(), heroes.getHeroes().size());
                System.out.println("You have encountered a monster!");
                for (int i = 0; i < monsters.size(); i++){
                    System.out.println((i + 1) + ":" + monsters.get(i));
                }
                fight(heroes, monsters);
                for (Hero hero: heroes.getHeroes()){                        
                    if (hero.getAttributes().get("Health").getCurrent() == 0)               // Revive heroes if they are dead
                        hero.heal(hero.getAttributes().get("Health").getMax() * 0.5);
                        System.out.println("Healed " + hero.getName() + " for " + hero.getAttributes().get("Health").getMax() * 0.5 + " health!");
                }
            }
        }
    }

    // takes heroes and monsters and will fight until one side is dead
    public void fight(HeroTeam heroTeam, ArrayList <Monster> monsters) {
        while (monsters.size() != 0 && heroTeam.heroAlive()){
            heroTeam.heroAttack(heroTeam, monsters);                                // Hero attacks
            monsterAttack(heroTeam, monsters);                                      // Monster attacks
            for (Hero hero: heroTeam.getHeroes()){                                  // Heal heroes if they are alive
                if (hero.getAttributes().get("Health").getCurrent() > 0)
                    hero.heal(hero.getAttributes().get("Health").getCurrent() * 0.1);
                    System.out.println("Healed " + hero.getName() + " for " + hero.getAttributes().get("Health").getCurrent() * 0.1 + " health!");
            }
        }
    }

    // takes heroes and monsters and will attack the heroes
    public void monsterAttack(HeroTeam heroTeam, ArrayList <Monster> monsters){
        for (Monster monster : monsters){
            System.out.println(monster.getName() + " attacks!");
            monster.attack(heroTeam);
        }
    }
}
