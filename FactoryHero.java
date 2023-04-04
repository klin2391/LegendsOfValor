/*
 * FactoryHero.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This class is used to generate a new ArrayList of heroes
 * It will read from a file and create a new hero based on the file
 * Player will be asked to select a hero
 */

import java.util.*;

public class FactoryHero {
    private ArrayList <String> raw;                 // Raw data from file
    private ArrayList <ArrayList <Hero>> allHeroes; // All heroes from all cultures
    private ArrayList <Hero> finalHeroes;           // Heroes that will be used
    private FileParser fp;
    private Input input;
    private ArrayList <String> availableCulture;

    // Constructor
    public FactoryHero() {
        availableCulture = new ArrayList<String>();
        availableCulture.add("Egyptian");
        availableCulture.add("Norse");
        availableCulture.add("Greek");
        allHeroes = new ArrayList<ArrayList<Hero>>();
        finalHeroes = new ArrayList<Hero>();
        input = new Input();
        for (int i = 0; i < availableCulture.size(); i++) {
            try {
                fp = new FileParser(availableCulture.get(i));
                raw = fp.generateList();
            }
            catch (Exception e) {
                System.out.println("Error: " + e);
            }
            ArrayList <Hero> temp  = new ArrayList<Hero>();                           // Temp arraylist to store heroes from each culture
            for (int j = 1; j < this.raw.size(); j++) {
                String description = this.raw.get(j).split("//")[1];            // Get description
                String[] heroStats = this.raw.get(j).split("\\s+");             // Get stats
                temp.add(new Hero(heroStats, description, availableCulture.get(i)));
            }
            allHeroes.add(temp);

        }
    }

    // Selects heroes from the available cultures
    public ArrayList <Hero> selectHeroes(int maxTeamSize) {
        int numHeroes = 1;
        if (input.queryBoolean("Will additional heroes be joining you?")){
            numHeroes = input.queryInt("How many heroes will embark on this quest? (1-" + maxTeamSize + ")", 1, maxTeamSize);
        }
        for (int i = 0; i < numHeroes; i++) {
            Color.clearScreen();
            for (int j = 0; j < availableCulture.size(); j++) {
                System.out.print("[+] " +(j + 1) + ". " + availableCulture.get(j));
                switch (j) {
                    case 0:
                        System.out.println(" - Favored for their dexterity and agility (magic and dodging)");
                        break;
                    case 1:
                        System.out.println(" - Favored for their strength and dexterity (attack and magic)");
                        break;
                    case 2:
                        System.out.println(" - Favorite for their strength and agility (attack and dodging)");
                        break;
                }
            }
            int cultureChoice = input.queryInt("Player " + (i+1) + ", select a culture to add a hero to your party: (1 - " + availableCulture.size() + ")", 1, availableCulture.size());
            availableCulture.remove(cultureChoice - 1);                                         // Only allow one hero per culture 
            Color.clearScreen();
            for (int j = 0; j < allHeroes.get(cultureChoice - 1).size(); j++) {
                System.out.println((j + 1) + ". " + allHeroes.get(cultureChoice - 1).get(j));
            }
            int heroChoice = input.queryInt("Player " + (i+1) + ", select a hero to add to your party: (1 - " + allHeroes.get(cultureChoice - 1).size() + ")", 1,allHeroes.get(cultureChoice - 1).size());
            finalHeroes.add(allHeroes.get(cultureChoice - 1).get(heroChoice - 1));              // Add hero to final list
            allHeroes.remove(cultureChoice - 1);
        }
        return finalHeroes;
    }
}
