/*
 * Hero.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Represents a hero in the game
 */
import java.util.*;
public class Hero {
    private String name;
    private String description;
    private String culture;
    private Level level;
    private HashMap<String, Attribute> attributes;
    private String[] attributeFields = {"Health", "Mana", "Strength", "Agility", "Dexterity", "Weight"};
    private ArrayList<String> strengths;
    private int gold;
    private ArrayList <Item> inventory;                         // Rucksack
    private ItemArmor[] armor = new ItemArmor[5];               // 0: Head, 1: Chest, 2: Legs, 3: Feet, 4: Shield
    private ItemWeapon[] weapons = new ItemWeapon[3];           //0 Left, 1 Right, 2 Two-Handed

    // Constructor
    public Hero(String name) {
        this.name = name;
        this.description = "A new hero";
        this.level = new Level();
        this.attributes = new HashMap<String, Attribute>();
        for (String field : attributeFields) {
            Attribute attribute = new Attribute(100, field, false);
            level.registerObserver(attribute);
            attributes.put(field, attribute);
        }
    }

    // Constructor that takes in parsed data
    public Hero(String[] data, String description, String culture) {
        this.name = data[0];
        this.description = description;
        this.culture = culture;
        this.level = new Level();
        this.attributes = new HashMap<String, Attribute>();
        for (int i = 0; i < attributeFields.length; i++) {
            ArrayList <String> strengths = findStrengths(culture);
            Attribute attribute;                                             // Create attribute
            if (attributeFields[i].equals("Weight")) {              // If weight, use AttributeWeight
                attribute = new AttributeWeight(Double.parseDouble(data[i + 1]), attributeFields[i], false);
            }
            else if (strengths.contains(attributeFields[i])) {              // If a strength based on culture, true
                attribute = new Attribute(Double.parseDouble(data[i + 1]), attributeFields[i], true);
            }
            else {                                                          // Otherwise, false                   
                attribute = new Attribute(Double.parseDouble(data[i + 1]), attributeFields[i], false);
            }
            level.registerObserver(attribute);                              // Register attribute as observer
            attributes.put(attributeFields[i], attribute);
        }
        this.inventory = new ArrayList<Item>();
        String[] temp = {"Shiny_Rock", "5", "1", "1", "0" , "0", "0", "0", "0"};    // Create a shiny rock
        ItemCharm shinyRock = new ItemCharm(temp, "Why do I have this???");
        inventory.add(shinyRock);
        temp = new String[]{"Wooden_Stick", "0", "1", "1", "100", "5", "1"};        // Create a wooden stick
        ItemWeapon stick = new ItemWeapon(temp, "Good for hitting things");
        inventory.add(stick);
        stick.equipt(this, stick, 2);
        this.gold = Integer.parseInt(data[7]);
        attributes.put("Defense", new Attribute(0, "Defense", false));
    }

    //Accessors
    public Level getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCulture() {
        return culture;
    }

    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }

    public int getGold() {
        return gold;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ItemArmor[] getArmor() {
        return armor;
    }

    public ItemWeapon[] getWeapons() {
        return weapons;
    }

    //Mutators
    public void setGold(int gold) {
        this.gold = gold;
    }

    // Heals by amount. If amount is greater than max health, sets health to max health
    public void heal(double amount) {
        if (attributes.get("Health").getCurrent() + amount > attributes.get("Health").getMax()) {
            attributes.get("Health").setCurrent(attributes.get("Health").getMax());
        }
        else {
            attributes.get("Health").increaseCurrent(amount);
        }
    }

    // Heals to max health
    public void fullHeal() {
        attributes.get("Health").setCurrent(attributes.get("Health").getMax());
    }

    // Prints hero and stats
    public String toString() {
        String output = "";
        output += "[i] Name: " + name + "\n";
        output += "\t[i] Description: " + description + "\n";
        output += level;
        for (int i = 0; i < attributeFields.length; i++) {
            output += attributes.get(attributeFields[i]);
        }
        Color color = new Color("gold");
        output += "\t[i] Gold: " + color.getColor() + gold + color.getBlack() + " coins\n";
        return output;
    }

    // Helper method to see what strengths are based on culture
    private ArrayList <String> findStrengths(String culture) {
        strengths = new ArrayList<String>();
        strengths.add("Strength");
        strengths.add("Dexterity");
        strengths.add("Agility");
        if (culture.equals("Egyptian")) {
            strengths.remove(strengths.indexOf("Strength"));
        }
        else if (culture.equals("Greek")) {
            strengths.remove(strengths.indexOf("Dexterity"));
        }
        else if (culture.equals("Norse")) {
            strengths.remove(strengths.indexOf("Agility"));
        }
        return strengths;
    }

    // Shows inventory (armor, weapons, and rucksack)
    public void showInventory(){
        System.out.println("Armor:");
        for (Item item : armor) {
            if (item != null) {
                System.out.println(item);
            }
        }
        System.out.println("Weapons:");
        for (Item item : weapons) {
            if (item != null) {
                System.out.println(item);
            }
        }
        System.out.println("Rucksack:");
        for (Item item : inventory) {
            if (item != null) {
                System.out.println(item);
            }
        }
    }

    // Attacks a monster with a weapon
    public void attackWPN(Hero hero, ArrayList <Monster> monsters, Input input) {
        for (int i = 0; i < monsters.size(); i++) {                                                                                             // Print monsters
            System.out.println((i + 1) + ": " + monsters.get(i));
        }
        int monsterToAttack = input.queryInt("Which monster do you want to attack?", 1, monsters.size(), "i");     // Get monster to attack
        while (monsterToAttack < 0){                                                                                                            // If is special value, will return -1 and do inventory                                        
            Color.clearScreen();    
            battleInventory(monsters);
            for (int i = 0; i < monsters.size(); i++) {                                                                                         // Print monsters
                System.out.println((i + 1) + ": " + monsters.get(i));
            }
            monsterToAttack = input.queryInt("Which monster do you want to attack?", 1, monsters.size(), "i");     //Get input
        }
        Color.clearScreen();
        if (hero.getWeapons()[2] != null){                                                                                                      // If 2 handed weapon, use it
            hero.getWeapons()[2].use(hero, hero.getWeapons()[2], monsters.get(monsterToAttack - 1));
        }
        else{                                                                                                                                   // Else, use both weapons
            if (hero.getWeapons()[1] != null){
                hero.getWeapons()[1].use(hero, hero.getWeapons()[1], monsters.get(monsterToAttack - 1));
            }
            if (hero.getWeapons()[0] != null){
                hero.getWeapons()[0].use(hero, hero.getWeapons()[0], monsters.get(monsterToAttack - 1));
            }
            else{
                System.out.println("You have no weapons to attack with!");
            }
        }
    }

    // Attacks a monster with a spell
    public void useCharm(Hero hero, ArrayList <Monster> monsters, Input input){
        for (int i = 0; i < monsters.size(); i++) {                                                                                             // Print monsters                            
            System.out.println((i + 1) + ": " + monsters.get(i));
        }
        ArrayList <ItemCharm> charms = new ArrayList<ItemCharm>();
        for (Item item : hero.getInventory()){                                                                                                  // Get all charms in inventory                                 
            if (item instanceof ItemCharm){
                charms.add((ItemCharm) item);
            }
        }
        Color.clearScreen();
        if (charms.size() > 0){                                                                                                                 // If there are charms, print them
            for (int i = 0; i < charms.size(); i++){
                System.out.println((i + 1) + ":" + charms.get(i));
            }
            int charmToUse = input.queryInt("Which charm do you want to use?", 1, charms.size(), "i");                  // Get charm to use
            while (charmToUse < 0){                                                                                                                  // If is special value, will return -1 and do inventory
                Color.clearScreen();
                battleInventory(monsters);
                for (int i = 0; i < charms.size(); i++){
                    System.out.println((i + 1) + ":" + charms.get(i));
                }
                charmToUse = input.queryInt("Which charm do you want to use?", 1, charms.size(), "i");
            }
            Color.clearScreen();
            for (int i = 0; i < monsters.size(); i++){                                                                                              // Print monsters                                         
                System.out.println((i + 1) + ":" + monsters.get(i));
            }
            int monsterToAttack = input.queryInt("Which monster do you want to attack?", 1, monsters.size(), "i");      // Get monster to attack
            while (monsterToAttack < 0){                                                                                                             // If is special value, will return -1 and do inventory                             
                Color.clearScreen();    
                battleInventory(monsters);
                monsterToAttack = input.queryInt("Which monster do you want to attack?", 1, monsters.size(), "i");
            }
            charms.get(charmToUse - 1).use(hero, charms.get(charmToUse - 1), monsters.get(monsterToAttack - 1));                                        // Use charm
        }
        else{
            System.out.println("You have no charms to use!");
        }
    }

    // Drink a potion
    public void drinkPotion(Hero hero, ArrayList <Monster> monsters, Input input){
        ArrayList <ItemPotion> potions = new ArrayList<ItemPotion>();
        for (Item item : hero.getInventory()){                                                                                              // Get all potions in inventory                                   
            if (item instanceof ItemPotion){
                potions.add((ItemPotion) item);
            }
        }
        if (potions.size() > 0){                                                                                                                // If there are potions, print them                     
            for (int i = 0; i < potions.size(); i++){
                System.out.println((i + 1) + ":" + potions.get(i));
            }
            int potionToUse = input.queryInt("Which potion do you want to use?", 1, potions.size(), "i");           // Get potion to use
            while (potionToUse < 0){                                                                                                            // If is special value, will return -1 and do inventory
                Color.clearScreen();
                battleInventory(monsters);
                for (int i = 0; i < potions.size(); i++){
                    System.out.println((i + 1) + ":" + potions.get(i));
                }
                potionToUse = input.queryInt("Which potion do you want to use?", 1, potions.size(), "i");
            }
            Color.clearScreen();
            potions.get(potionToUse - 1).use(hero, potions.get(potionToUse - 1), null);                                                 // Use potion                         
            System.out.println(hero);
        }
        else{
            System.out.println("You have no potions to use!");
        }
    }

    // Equipt an equiptable
    public void addEquiptment(Hero hero, ArrayList <Monster> monsters, Input input) {
        ArrayList <Equiptable> equiptables = new ArrayList<Equiptable>();                                                                               // Get all equiptables in inventory                          
        for (Item item : hero.getInventory()){
            if (item instanceof Equiptable){
                equiptables.add((Equiptable) item);
            }
        }
        if (equiptables.size() > 0){                                                                                                                    // If there are equiptables, print them
            for (int i = 0; i < equiptables.size(); i++){
                System.out.println((i + 1) + ":" + equiptables.get(i));
            }
            int equiptableToUse = input.queryInt("Which equiptable do you want to use?", 1, equiptables.size(), "i");       // Get equiptable to use
            while (equiptableToUse < 0){                                                                                                                // If is special value, will return -1 and do inventory
                Color.clearScreen();
                battleInventory(monsters);
                for (int i = 0; i < equiptables.size(); i++){
                    System.out.println((i + 1) + ":" + equiptables.get(i));
                }
                equiptableToUse = input.queryInt("Which equiptable do you want to use?", 1, equiptables.size(), "i");
            }
            Color.clearScreen();
            if (equiptables.get(equiptableToUse - 1) instanceof ItemWeapon){                                                                                // If equiptable is a weapon
                ItemWeapon weapon = (ItemWeapon) equiptables.get(equiptableToUse - 1);
                if (weapon.getMinHands() == 2){                                                                                                            // Add to choice hands                                
                    equiptables.get(equiptableToUse - 1).equipt(hero, weapon, 2);
                }
                else {
                    int weaponSlot = input.queryInt("Which weapon slot do you want to use? 0: Left Hand, 1: Right Hand, 2: Double", 0, 2, "i");
                    while (weaponSlot < 0){
                        Color.clearScreen();
                        battleInventory(monsters);
                        weaponSlot = input.queryInt("Which weapon slot do you want to use? 0: Left Hand, 1: Right Hand, 2: Double", 0, 2, "i");
                    }
                    equiptables.get(equiptableToUse - 1).equipt(hero, weapon, weaponSlot);
                }
            }
            else{                                                                                                                                           // If equiptable is armor                                      
                ItemArmor temp = (ItemArmor) equiptables.get(equiptableToUse - 1);
                int position =  temp.getBodyPart();                                                                                                         // Add to proper body part                                        
                equiptables.get(equiptableToUse - 1).equipt(hero, equiptables.get(equiptableToUse - 1), position);
            }
        }
        else{
            System.out.println("You have no equiptables to equipt!");
        }
    }

    // Remove an equiptable
    public void removeEquiptment(Hero hero, Input input) {
        ArrayList <Equiptable> equiptables = new ArrayList<Equiptable>();
        for (int i = 0; i < hero.getArmor().length; i++){                                                                                              // Get all equiptables in armor                                  
            if (hero.getArmor()[i] != null){
                equiptables.add(hero.getArmor()[i]);
            }
        }
        for (int i = 0; i < hero.getWeapons().length; i++){                                                                                            // Get all equiptables in weapons                                
            if (hero.getWeapons()[i] != null){
                equiptables.add(hero.getWeapons()[i]);
            }
        }
        if (equiptables.size() > 0){                                                                                                                    // If there are equiptables, print them                                 
            for (int i = 0; i < equiptables.size(); i++){
                System.out.println((i + 1) + ":" + equiptables.get(i));
            }
            int equiptableToUse = input.queryInt("Which equiptable do you want to remove?", 1, equiptables.size(), "i");    // Get equiptable to remove
            while (equiptableToUse < 0){
                equiptableToUse = input.queryInt("Which equiptable do you want to remove?", 1, equiptables.size(), "i");
            }
            Color.clearScreen();
            hero.getInventory().add((Item) equiptables.get(equiptableToUse - 1));                                                                           // Add to inventory
            for (int i = 0; i < hero.getArmor().length; i++){                                                                                               // Remove from armor                   
                if (hero.getArmor()[i] == equiptables.get(equiptableToUse - 1)){
                    hero.getArmor()[i] = null;
                }
            }
            for (int i = 0; i < hero.getWeapons().length; i++){                                                                                            // Remove from weapons                                   
                if (hero.getWeapons()[i] == equiptables.get(equiptableToUse - 1)){
                    hero.getWeapons()[i] = null;
                }
            }
        }
        else{
            System.out.println("You have no equiptables to equipt!");
        }

    }

    // Print key attributes and inventory
    public void battleInventory(ArrayList <Monster> monsters) {
        String output = "";
        output += "[i] Name: " + name + "\n";                                                                               // Print name
        output += level;                                                                                                    // Print level              
        output += attributes.get("Health");                                                                              // Print health                  
        output += attributes.get("Mana");                                                                                // Print mana   
        output += "[i] Weapons:\n";                                                                                          // Print weapons         
        for (int i = 0; i < weapons.length; i++){
            if (weapons[i] != null){
                output += weapons[i];
            }
        }
        output += "[i] Armor: \n";                                                                                           // Print armor     
        for (int i = 0; i < armor.length; i++){
            if (armor[i] != null){
                output += armor[i];
            }
        }
        output += "[i] Monsters: \n";                                                                                       // Print monsters              
        if (monsters != null){
            for (Monster monster : monsters){
                output += monster;
            }
        }
        System.out.println(output);
    }
}