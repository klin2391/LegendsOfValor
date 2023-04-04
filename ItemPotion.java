/*
 * ItemPotion.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Represents potions in the game. Color cyan
 */

import java.util.*;

public class ItemPotion extends Item implements Useable, Repairable {
    private int effectValue;
    private String effect;      // What attribute is affected
    private String[] effects;

    // Constructor
    public ItemPotion(String[] data, String description) {
        super(data, description);
        this.effectValue = Integer.parseInt(data[5]);
        this.effect = data[6];
        this.effects = effect.split("/");
    }

    // Hero drinks potion
    public void use(Hero hero, Useable item, Monster monster) {
        if (uses.getCurrent() == 0) {                       // If no uses left
            Color.clearScreen();
            System.out.println("You do not have any uses left for this item.");
            return;
        }
        if (effect.equals("Weight")){            // If potion affects weight
            AttributeWeight temp = (AttributeWeight) hero.getAttributes().get("Weight");
            temp.increaseMax(effectValue );                 // Increase max weight
        }
        if (effect.equals("Experience")){           // If affects experience
            hero.getLevel().addExperience(effectValue);     // Increase experience
        }
        for (String effect : effects) {                         // If affects attributes
            hero.getAttributes().get(effect).increaseCurrent(effectValue);
        }
        uses.decreaseCurrent(1);
        System.out.println("You used a " + name + " and increased your " + effect + " by " + effectValue + "!");
    }
    
    // Repairs item
    public void repair(Hero hero, Repairable item) {
        uses.setCurrent(uses.getMax());
        hero.setGold(hero.getGold() - valueGold);
    }

    // ToString method
    public String toString() {
        Color color = new Color();
        String output = "";
        color.setColor("cyan");
        output += " Name: " + color.getColor() + name + color.getBlack() + "\n";
        output += "\t[i] Description: " + description + "\n";
        color.setColor("gold");
        output += "\t[i] Value: " + color.getColor() + valueGold +color.getBlack()+ " coins\n";
        output += "\t[i] Weight: " + weight + " lbs\n";
        color.setColor("purple");
        output += "\t[i] Required Level: " +color.getColor() + requiredLevel + color.getBlack() +"\n";
        output += "\t[i] Effect: " + effectValue + "\n";
        output += "\t[i] Target Attribute: " + effect + "\n";
        if ((double) uses.getCurrent() / uses.getMax() < 0.33) {
            color.setColor("red");
        }
        else if ((double) uses.getCurrent() / uses.getMax() < 0.66) {
            color.setColor("yellow");
        }
        else {
            color.setColor("green");
        }
        output += "\t[i] " + uses.getName() + ": " + color.getColor() + uses.getCurrent() + color.getBlack() + " / " + uses.getMax() + "\n";
        return output;
    }
}
