/*
 * ItemWeapon.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Represents weapons in the game color red
 */

public class ItemWeapon extends Item implements Useable, Repairable , Equiptable{
    private int damage;         // damage of weapon
    private int minHands;       // minimum hands required to use
    private boolean twoHanded;  // if weapon requires two hands

    // Constructor
    public ItemWeapon(String[] data, String description) {
        super(data, description);
        this.damage = Integer.parseInt(data[5]);
        this.minHands = Integer.parseInt(data[6]);
    }
    
    // Accessors
    public int getDamage() {
        return damage;
    }

    public int getMinHands() {
        return minHands;
    }

    public int getValue() {
        return damage;
    }

    //Tostring method
    public String toString() {
        Color color = new Color();
        String output = "";
        color.setColor("red");
        output += " Name: " + color.getColor() + name + color.getBlack() + "\n";
        output += "\t[i] Description: " + description + "\n";
        color.setColor("gold");
        output += "\t[i] Value: " + color.getColor() + valueGold +color.getBlack()+ " coins\n";
        output += "\t[i] Weight: " + weight + " lbs\n";
        color.setColor("purple");
        output += "\t[i] Required Level: " +color.getColor() + requiredLevel + color.getBlack() +"\n";
        output += "\t[i] Damage: " + damage + "\n";
        output += "\t[i] Hands Required: " + minHands + "\n";
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

    // Use weapon on monster
    public void use(Hero hero, Useable item, Monster monster) {
        if (uses.getCurrent() == 0) {                                                               // If no uses left
            Color.clearScreen();    
            System.out.println("You do not have any uses left for this item.");
            return;
        }
        int damage = ((ItemWeapon) item).getDamage();
        if (twoHanded){                                                                             // If weapon is two handed, more damage                           
            damage = (int) (damage + hero.getAttributes().get("Strength").getCurrent() * 0.75);
        }
        else {
            damage = (int) (damage + hero.getAttributes().get("Strength").getCurrent() * 0.5);       // If weapon is one handed, less damage
        }
        RandomGen rand = new RandomGen();
        int dodge = rand.getRandomInt(0, 100);
        if (dodge < monster.getAttributes().get("Agility").getCurrent()) {                          // If monster dodges
            Color color = new Color("red");
            System.out.println(monster.getName() +color.getColor()+ " dodged "+color.getBlack()+ "your attack!");
        }
        else {
            double val = damage-monster.getAttributes().get("Defense").getCurrent();                //amount of damage done is damage - defense
            Color color = new Color();
            if (val <= 0) {
                val = 0;
                color.setColor("red");                                                              // If damage is negative, no damage is done
                System.out.println(color.getColor() + "INEFFECTIVE! " + color.getBlack() + hero.getName()+ " hit " + monster.getName() + " for " + (int) val + " damage!");
            }
            else{
                monster.getAttributes().get("Health").decreaseCurrent((int) val);                       // If damage is positive, damage is done
                color.setColor("green");
                System.out.println(color.getColor() + "EFFECTIVE! " + color.getBlack() + hero.getName()+ " hit " + monster.getName() + " for " + (int) val + " damage!");
            }
            
        }
        uses.decreaseCurrent(1);                                                                    // Decrease uses                            
    }

    // Repair weapon
    public void repair(Hero hero, Repairable item) {
        uses.setCurrent(uses.getMax());
        hero.setGold(hero.getGold() - valueGold);
    }

    // Equipt weapon
    public void equipt(Hero hero, Equiptable item, int position) {                  //0 Left Hand, 1 Right Hand, 2 Both
        if (minHands == 2) {                                    // If weapon requires two hands     
            position = 2;
        }
        if (position == 2){                                     // If weapon is two handed, remove items from both hands
            twoHanded = true;
            if (hero.getWeapons()[0] != null) {
                remove(hero, hero.getWeapons()[0], 0);
            }
            if (hero.getWeapons()[1] != null) {
                remove(hero, hero.getWeapons()[1], 1);
            }
        }
        else {                                                  // If weapon is one handed, remove item from hand
            twoHanded = false;
            if (hero.getWeapons()[position] != null) {
                remove(hero, hero.getWeapons()[position], position);
            }
            if (hero.getWeapons()[2] != null) {
                remove(hero, hero.getWeapons()[2], position);
            }
        }
        hero.getInventory().remove(item);                       // Remove item from inventory
        hero.getWeapons()[position] =  (ItemWeapon) item;           // Add item to hand
    }

    // Remove weapon
    public void remove(Hero hero, Equiptable item, int position) {
        hero.getInventory().add((Item) item);                       // Add item to inventory
        hero.getWeapons()[position] =  null;                        // Remove item from hand
    }
}
