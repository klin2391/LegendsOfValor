/*
 * ItemCharm.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Represents Charm for spell in the game. Color purple
 */

public class ItemCharm extends Item implements Useable, Repairable {
    private int manaCost;
    private String effectType;  // what it does
    private int effectValue;    // how much it does
    private int damage;

    // Constructor
    public ItemCharm(String[] data, String description) {
        super(data, description);
        this.manaCost = Integer.parseInt(data[6]);
        this.effectType = data[5];
        this.effectValue = Integer.parseInt(data[7]);
        this.damage = Integer.parseInt(data[8]);
    }
    
    // Cast spell
    public void use(Hero hero, Useable item, Monster monster) {
        if (uses.getCurrent() == 0) {                                                   // Checks if still useable
            Color.clearScreen();
            System.out.println("You do not have any uses left for this item.");
            return;
        }
        if (hero.getAttributes().get("Mana").getCurrent() < manaCost) {             // Checks if enough mana
            System.out.println("You do not have enough mana to use this item.");
            return;
        }
        double attackVal =  (damage * hero.getAttributes().get("Dexterity").getCurrent() / 100) + damage;   // Calculates attack value
        RandomGen rand = new RandomGen();
        int dodge = rand.getRandomInt(0, 100);
        if (dodge < monster.getAttributes().get("Agility").getCurrent()) {              // Checks if monster dodges
            System.out.println(monster.getName() + " dodged your attack!");
        }
        else {
            double damage = attackVal - monster.getAttributes().get("Defense").getCurrent();    // Calculates damage
            if (damage < 0) {
                damage = 0;
            }
            monster.getAttributes().get("Health").decreaseCurrent((int) damage);        // Decreases monster's health
            monster.getAttributes().get(effectType).decreaseCurrent(effectValue);       // Decreases monster's attribute
            System.out.println(hero.getName() +" hit " + monster.getName() + " for " + damage + " damage!");
            System.out.println(hero.getName() +" also decreased " + effectType  + monster.getName() + " by " + effectValue + "!");
        }
        uses.decreaseCurrent(1);                                                    // Decreases uses
        hero.getAttributes().get("Mana").decreaseCurrent(manaCost);                     // Decreases mana
    }

    // Getters
    public int getManaCost() {
        return manaCost;
    }

    public String getEffectType() {
        return effectType;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public int getDamage() {
        return damage;
    }
    
    // Repair item
    public void repair(Hero hero, Repairable item) {
        uses.setCurrent(uses.getMax());
        hero.setGold(hero.getGold() - valueGold);
    }

    // Returns string representation of item
    public String toString() {
        Color color = new Color();
        String output = "";
        color.setColor("purple");
        output += " Name: " + color.getColor() + name + color.getBlack() + " Charm\n";
        output += "\t[i] Description: " + description + "\n";
        color.setColor("gold");
        output += "\t[i] Value: " + color.getColor() + valueGold +color.getBlack()+ " coins\n";
        output += "\t[i] Weight: " + weight + " lbs\n";
        color.setColor("purple");
        output += "\t[i] Required Level: " +color.getColor() + requiredLevel + color.getBlack() +"\n";
        output += "\t[i] Effect: " + effectValue + "\n";
        output += "\t[i] Target Attribute: " + effectType + "\n";
        output += "\t[i] Damage: " + damage + "\n";
        output += "\t[i] Mana Cost: " + manaCost + "\n";
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