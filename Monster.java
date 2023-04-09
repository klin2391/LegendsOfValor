/*
 * Monster.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Monsters are enemies that the player can fight. They have a level, attributes, and a category.
 * They can attack the player's team.
 */
 

import java.util.*;
public class Monster {
    private String name;
    private String description;
    private String category;
    private Level level;
    private HashMap<String, Attribute> attributes;
    private String[] attributeFields = {"Health", "Damage", "Agility", "Defense"};
    private int id;

    public Monster(){
        this.name = "M";
        this.description = "Template";
        this.category = " ";
        this.level = new Level();
        this.attributes = new HashMap<String, Attribute>();
        for (int i = 0; i < attributeFields.length; i++) {
            Attribute attribute;
            attribute = new Attribute(0, attributeFields[i], false);
            level.registerObserver(attribute);
            attributes.put(attributeFields[i], attribute);
        }
        this.id = 0;
    }

    // Create deep copy
    public Monster(Monster m) {
        this.name = m.getName();
        this.description = m.getDescription();
        this.category = m.getCategory();
        this.level = new Level();
        this.attributes = new HashMap<String, Attribute>();
        Iterator attrIterator = m.getAttributes().entrySet().iterator();
        while (attrIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)attrIterator.next();
            this.attributes.put(mapElement.getKey().toString(), new Attribute((Attribute)mapElement.getValue()));
        }
    }

    // Constructor that takes in data
    public Monster(String[] data, String description, String category) {
        this.name = data[0];
        this.description = description;
        this.category = category;
        this.level = new Level();
        this.attributes = new HashMap<String, Attribute>();
        for (int i = 0; i < attributeFields.length; i++) {
            Attribute attribute;
            attribute = new Attribute(Double.parseDouble(data[i + 2]), attributeFields[i], false);
            level.registerObserver(attribute);
            attributes.put(attributeFields[i], attribute);
        }
    }

    // Accessors
    public Level getLevel(){
        return level;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }

    public int getId() {
        return this.id;
    }

    // Mutators
    public void setId(int id) {
        this.id = id % 9 + 1;
    }

    // To string. Red is bad
    public String toString() {
        Color color = new Color("red");
        String output = "";
        output += "[i] Name: " + color.getColor() + name +color.getBlack()+ "\n";
        output += "\t[i] Description: " + description + "\n";
        output += "\t[i] Level: " + level.getLevel() + "\n";
        for (int i = 0; i < attributeFields.length; i++) {
            output += attributes.get(attributeFields[i]);
        }
        return output;
    }

    // Attack a hero team
    public void attack(HeroTeam heroTeam) {
        RandomGen randomGen = new RandomGen();
        int heroIndex = randomGen.getRandomInt(1, heroTeam.getHeroes().size()) - 1;                 // get random hero  
        int damage = (int) (attributes.get("Damage").getCurrent() - heroTeam.getHeroes().get(heroIndex).getAttributes().get("Defense").getCurrent());   // calculate damage
        int dodge = randomGen.generateRandomNumber();
        if (dodge < heroTeam.getHeroes().get(heroIndex).getAttributes().get("Agility").getCurrent()) {  // check if dodge
            Color color = new Color("green");
            System.out.println(name +" " + color.getColor() +"missed!" + color.getBlack());                 // dodge
        }
        else {
            if (damage < 0){                                                                                // protection works!                      
                damage = 0;
                Color color = new Color("green");
                heroTeam.getHeroes().get(heroIndex).getAttributes().get("Health").decreaseCurrent(damage);
                System.out.println(name +" attacked " + heroTeam.getHeroes().get(heroIndex).getName() + " for " + color.getColor() + damage +color.getBlack()+ " damage!");
            }
            else{                                                                                               //Hero takes damage      
                Color color = new Color("red");
                heroTeam.getHeroes().get(heroIndex).getAttributes().get("Health").decreaseCurrent(damage);
                System.out.println(name +" attacked " + heroTeam.getHeroes().get(heroIndex).getName() + " for " + color.getColor() + damage +color.getBlack()+ " damage!");
            }   
        }
    }
}
