/*
 * Item.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Abstract class of item. Has state and behavior for all items.
 */

public abstract class Item {
    protected String name;
    protected String description;
    protected int valueGold;            //COST
    protected int weight;
    protected int requiredLevel;
    protected Attribute uses;
    
    // Constructor
    public Item() {
        this.name = "Item";
        this.description = "This is an item.";
        this.valueGold = 1;
        this.weight = 1;
        this.requiredLevel = 1;
        this.uses = new Attribute(1, "Uses Remaining", false);
    }

    // Constructor that takes in data
    public Item(String[] itemData, String description) {
        this.name = itemData[0];
        this.valueGold = Integer.parseInt(itemData[1]);
        this.weight = Integer.parseInt(itemData[2]);
        this.requiredLevel = Integer.parseInt(itemData[3]);
        this.description = description;
        this.uses = new Attribute(Integer.parseInt(itemData[4]), "Uses Remaining", false);
    }

    // Accessors
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValueGold() {
        return valueGold;
    }

    public int getWeight() {
        return weight;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public Attribute getUses() {
        return uses;
    }

    // Sell item moves item from hero inventory
    public Item sell(Hero hero) {
        Color.clearScreen();
        hero.getInventory().remove(this);
        hero.getAttributes().get("Weight").setCurrent(hero.getAttributes().get("Weight").getCurrent() - weight);    // Adjust weight
        hero.setGold(hero.getGold() + valueGold);                                                                           // Add gold
        valueGold = valueGold * 2;                                                                                          // Double value
        uses.setCurrent(uses.getMax());                                                                                     // Reset uses                        
        System.out.println("You have sold a " + name + " for " + valueGold/2 + " coins.");
        return this;
    }

    // Buy item moves item from shop to hero inventory
    public boolean buy(Hero hero) {
        Color.clearScreen();
        if (hero.getGold() < valueGold) {                                                                                   // Check if hero can buy item
            System.out.println("You do not have enough gold to buy this item.");
            return false;
        }
        else if (hero.getAttributes().get("Weight").getCurrent() + weight > hero.getAttributes().get("Weight").getMax()) {
            System.out.println("You do not have enough space in your inventory to buy this item.");
            return false;
        }
        else if (hero.getLevel().getLevel() < requiredLevel) {
            System.out.println("You are not high enough level to buy this item.");
            return false;
        }
        hero.getInventory().add(this);                                                                                      // Add item to hero inventory                     
        hero.getAttributes().get("Weight").setCurrent(hero.getAttributes().get("Weight").getCurrent() + weight);    // Adjust weight
        hero.setGold(hero.getGold() - valueGold);                                                                           // Remove gold 
        valueGold = valueGold/2;                                                                                            // Half value               
        System.out.println("You have bought a " + name + " for " + valueGold*2 + " coins.");
        return true;
    }

    // Repair item
    public void repair(Hero hero) {
        Color.clearScreen();
        if (hero.getGold() < valueGold) {                                                                            // Check if hero can buy item                      
            System.out.println("You do not have enough gold to repair this item.");
            return;
        }
        else if (uses.getCurrent() == uses.getMax()) {                                                              // Check if item is already fully repaired              
            System.out.println("This item is already fully repaired.");
            return;
        }
        uses.setCurrent(uses.getMax());                                                                             // Reset uses       
        hero.setGold(hero.getGold() - valueGold);                                                                    // Remove gold
        System.out.println("Your "+ name + " has been repaired");
    }

    // Prints item information
    public String toString() {
        Color color = new Color();
        String output = "";
        output += " Name: " + name + "\n";
        output += "\t[i] Description: " + description + "\n";
        output += "\t[i] Value: " + valueGold + " coins\n";
        output += "\t[i] Weight: " + weight + " lbs\n";
        output += "\t[i] Required Level: " + requiredLevel + "\n";
        if ((double) uses.getCurrent() / uses.getMax() < 0.33) {
            color.setColor("red");
        }
        else if ((double) uses.getCurrent() / uses.getMax() < 0.66) {
            color.setColor("yellow");
        }
        else {
            color.setColor("green");
        }
        output += "\t[i] " + uses.getName() + ": " + color.getColor() + uses.getCurrent() + color.getBlack() +  " / " + uses.getMax() + "\n";
        return output;
    }
}
