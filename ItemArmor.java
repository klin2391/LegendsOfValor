/*
 * ItemArmor.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Represents armor in the game. Color blue
 */
public class ItemArmor extends Item implements Equiptable, Repairable{
    private int protection;
    private int bodyPart;           // 0 = head, 1 = chest, 2 = legs, 3 = feet
    private String[] bodyParts = {"Head", "Chest", "Legs", "Feet", "Hand"};

    // Constructor
    public ItemArmor() {
        super();
        this.protection = 1;
        this.bodyPart = 0;
    }

    // Constructor that takes in data
    public ItemArmor(String[] data, String description) {
        super(data, description);
        this.protection = Integer.parseInt(data[5]);
        this.bodyPart = Integer.parseInt(data[6]);
    }

    // Prints inportatnt information
    public String toString() {
        Color color = new Color();
        String output = "";
        color.setColor("blue");
        output += " Name: " + color.getColor() + name + color.getBlack() + "\n";
        output += "\t[i] Description: " + description + "\n";
        color.setColor("gold");
        output += "\t[i] Value: " + color.getColor() + valueGold +color.getBlack()+ " coins\n";
        output += "\t[i] Weight: " + weight + " lbs\n";
        color.setColor("purple");
        output += "\t[i] Required Level: " +color.getColor() + requiredLevel + color.getBlack() +"\n";
        output += "\t[i] Protection: " + protection + "\n";
        output += "\t[i] Body Part: " + bodyParts[bodyPart] + "\n";
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

    //Accessors
    public int getValue() {
        return protection;
    }

    public int getBodyPart() {
        return bodyPart;
    }

    // Equiptable methods. Adds to hero inventory
    public void equipt(Hero hero, Equiptable item, int position) {
        if (hero.getArmor()[position] != null) {            // If there is already armor in that position
            remove(hero, hero.getArmor()[position], position);
        }
        if (position == 4){                                 // If the hero is holding something in hands
            if (hero.getWeapons()[2] != null) {
                hero.getInventory().add((Item) hero.getWeapons()[2]);
                hero.getWeapons()[2] = null;
            }
            if (hero.getWeapons()[0] != null && hero.getWeapons()[1] != null) {
                hero.getInventory().add((Item) hero.getWeapons()[0]);
                hero.getWeapons()[0] = null;
            }
        }
        hero.getInventory().remove(item);                   // Remove from inventory       
        hero.getArmor()[position] =  (ItemArmor) item;      // Add to armor
        hero.getAttributes().get("Defense").increaseCurrent(item.getValue());   // Increase defense
    }

    // Repairable methods
    public void repair(Hero hero, Repairable item) {
        uses.setCurrent(uses.getMax());                    // Set uses to max
        hero.setGold(hero.getGold() - valueGold);           // Remove gold
    }

    // Removes armor from hero
    public void remove(Hero hero, Equiptable item, int position) {
        hero.getInventory().add((Item) item);       // Add to inventory
        hero.getArmor()[position] =  null;          // Remove from armor
        hero.getAttributes().get("Defense").decreaseCurrent(item.getValue());   // Decrease defense
    }
}
