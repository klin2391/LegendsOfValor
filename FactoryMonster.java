/*
 * FactoryMonster.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This class is used to create an ArrayList of monsters.
 * First it creates an array of all monsters then it
 * creates a random selection of monsters at the level of heroes.
 * 
 */

import java.util.*;
import java.math.*;

public class FactoryMonster {
    private ArrayList <String> raw;
    private ArrayList <Monster> allMonsters;
    private ArrayList <Monster> finalMonsters;
    private ArrayList <String> monsterType;
    private FileParser fp;
    private int created;
    

    public FactoryMonster(){
        monsterType = new ArrayList<String>();
        monsterType.add("Animal");                      // File names for monsters
        monsterType.add("Hybrid");
        monsterType.add("Beast");
        allMonsters = new ArrayList<Monster>();

        for (int i = 0; i < monsterType.size(); i++) {
            try {
                fp = new FileParser(monsterType.get(i));
                raw = fp.generateList();
            }
            catch (Exception e) {
                System.out.println("Error: " + e);
            }
            for (int j = 1; j < this.raw.size(); j++) {
                String description = this.raw.get(j).split("//")[1];
                String[] MonsterStats = this.raw.get(j).split("\\s+");
                allMonsters.add(new Monster(MonsterStats, description, monsterType.get(i)));
            }
        }
        this.created = 0;
    }

    // Creates a random selection of monsters scaled to the level of the heroes
    public ArrayList <Monster> selectMonsters(int combinedLevel, int numHeroes) {
        RandomGen rand = new RandomGen();
        int choice = rand.getRandomInt(0, 2);
        switch (choice) {
            case 0:
                return selectEqualMonster(combinedLevel, numHeroes);                    // Equal monsters ie 2 players = 2 monsters
            case 1:
                return selectOneMonster(combinedLevel, numHeroes);                      // One monster
            case 2:
                return selectManyLittleMonsters(combinedLevel, numHeroes);              // Many level 1 monsters
            default:
                return selectEqualMonster(combinedLevel, numHeroes);
        }
    }

    // Creates a random selection of monsters scaled to the level of the heroes at average level of hero
    public ArrayList <Monster> selectEqualMonster(int combinedLevel, int numHeroes) {
        int averageLevel =(int) Math.ceil((float)combinedLevel / numHeroes);
        finalMonsters = new ArrayList<Monster>();
        RandomGen rand = new RandomGen();
        for (int i = 0; i < numHeroes; i++) {
            int choice = rand.getRandomInt(0, allMonsters.size() - 1);
            finalMonsters.add(allMonsters.get(choice));
            finalMonsters.get(i).getLevel().setLevel(averageLevel);
            finalMonsters.get(i).setId(this.created++);
        }
        return finalMonsters;
    }

    // Creates a random monster at combined level of heroes
    public ArrayList <Monster> selectOneMonster(int combinedLevel, int numHeroes) {
        finalMonsters = new ArrayList<Monster>();
        RandomGen rand = new RandomGen();
        int choice = rand.getRandomInt(0, allMonsters.size() - 1);
        finalMonsters.add(allMonsters.get(choice));
        finalMonsters.get(0).getLevel().setLevel(combinedLevel);
        finalMonsters.get(0).setId(this.created++);
        return finalMonsters;
    }

    // Creates a random selection of monsters at level 1. There are a lot of them.
    public ArrayList <Monster> selectManyLittleMonsters(int combinedLevel, int numHeroes) {
        finalMonsters = new ArrayList<Monster>();
        RandomGen rand = new RandomGen();
        for (int i = 0; i < combinedLevel ; i++) {
            int choice = rand.getRandomInt(0, allMonsters.size() - 1);
            finalMonsters.add(allMonsters.get(choice));
            finalMonsters.get(i).getLevel().setLevel(1);
            finalMonsters.get(i).setId(this.created++);
        }
        return finalMonsters;
    }

}
