/*
 * FactoryMarket.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This class is used to create a marketplace with items.
 * First it creates an array of all items then it 
 * creates a marketplace with a random selection of items.
 */
import java.util.*;

public class FactoryMarket {
    private ArrayList <String> raw;                         // Raw data from file
    private ArrayList <Item> allItems;
    private FileParser fp;
    private ArrayList <String> itemTypes;
    private RandomGen rand = new RandomGen();
    private int numItemsPerMarket = 6;

    // Constructor
    public FactoryMarket() {
        itemTypes = new ArrayList<String>();
        itemTypes.add("Armor");                             // File names for items
        itemTypes.add("Weapons");
        itemTypes.add("Charms");
        itemTypes.add("Potions");
        allItems = new ArrayList<Item>();
        for (int i = 0; i < itemTypes.size(); i++) {
            try {
                fp = new FileParser(itemTypes.get(i));
                raw = fp.generateList();
            }
            catch (Exception e) {
                System.out.println("Error: " + e);
            }
            for (int j = 1; j < this.raw.size(); j++) {
                String description = this.raw.get(j).split("//")[1];            // Get description
                String[] itemStats = this.raw.get(j).split("\\s+");             // Get stats
                switch (i) {
                    case 0:
                        allItems.add(new ItemArmor(itemStats, description));
                        break;
                    case 1:
                        allItems.add(new ItemWeapon(itemStats, description));
                        break;
                    case 2:
                        allItems.add(new ItemCharm(itemStats, description));
                        break;
                    case 3:
                        allItems.add(new ItemPotion(itemStats, description));
                        break;
                }
            }

        }
    }

    // Creates a marketplace with a random selection of items
    public Marketplace createMarket(){
        ArrayList <Item> temp = new ArrayList<Item>();
        for (int i = 0; i < numItemsPerMarket; i++){
            temp.add(allItems.get(rand.getRandomInt(0,allItems.size()-1)));
        }
        Marketplace m = new Marketplace(temp);
        return m;
    }
    
    // Returns a string of all items
    public String toString(){
        String output = "";
        for (int i = 0; i < allItems.size(); i++) {
            output += allItems.get(i).toString();
        }
        return output;
    }    
}
