/*
 * Marketplace.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Marketplace class has a list of items for sale and methods to buy and sell items
 * Players can enter the marketplace and buy, sell, or repair items
 */

import java.util.*;
public class Marketplace {
    ArrayList <Item> itemsForSale;
    Input userInput = new Input();
    
    // Constructor
    public Marketplace(){
        itemsForSale = new ArrayList<Item>();
    }

    // Constructor that takes in data
    public Marketplace(ArrayList <Item> items){
        itemsForSale = items;
    }

    // Players enter 1 by 1. Can sell, buy, or repair items
    public void enterMarket(HeroTeam ht){
        ArrayList <Hero> heroTeam = ht.getHeroes();
        for (Hero hero: heroTeam) {         // For each hero in the team
            boolean complete = false;
            System.out.println("Hello there "+hero.getName()+"!");
            while (!complete){
                int action = userInput.queryInt("What can I do for you? Would you like to buy or sell goods? (Buy: 1, Sell: 2, Repair: 3, Just lookin: 4)", 1, 4);
                if (action == 1){
                    buy(hero);
                }
                else if (action == 2){
                    sell(hero);
                }
                else if (action == 3){
                    repair(hero);
                }
                else {
                    System.out.println("Thats too bad. Just remember, you break it, you buy it!");
                    complete = true;
                }
            }
            Color.clearScreen();
        }
        System.out.println("Pleasure doing buisness with you!");
    }

    // Buy items
    public void buy(Hero hero){
        System.out.println(this);                                   // Prints out all items for sale
        int i = itemsForSale.size();
        System.out.println("["+(i+1)+"] Leave Shop");
        int selection = userInput.queryInt("Which item would you like to purchase?", 1, itemsForSale.size()+1);
        if (selection == itemsForSale.size()+1){
            System.out.println("Very well, come back when you have something to buy!");         // Leaves shop
            return;
        }
        if (itemsForSale.get(selection-1).buy(hero))                // Adds to hero inventory
            itemsForSale.remove(selection-1);
    }

    // Sell items
    public void sell(Hero hero){
        ArrayList <Item> sellable = hero.getInventory();                // Gets all items in inventory
        for (int i = 0; i < sellable.size(); i++) {
            System.out.println("["+(i+1)+"] "+sellable.get(i));
        }
        System.out.println("["+(sellable.size()+1)+"] Leave Shop");
        int selection = userInput.queryInt("Which item would you like to sell?", 1, hero.getInventory().size()+1);  
        if (selection == hero.getInventory().size()+1){                 // Leaves shop
            System.out.println("Very well, come back when you have something to sell!");
            return;
        }
        itemsForSale.add(hero.getInventory().get(selection-1).sell(hero));    // Adds to items for sale
    }

    // Repair items
    public void repair(Hero hero){
        ArrayList <Item> repairable = hero.getInventory();            // Gets all items in inventory
        for (int i = 0; i < repairable.size(); i++) {
            System.out.println("["+(i+1)+"] "+repairable.get(i));
        }
        System.out.println("["+(repairable.size()+1)+"] Leave Shop");
        int selection = userInput.queryInt("Which item would you like to repair?", 1, hero.getInventory().size()+1);
        if (selection == hero.getInventory().size()+1){                         // Leaves shop
            System.out.println("Very well, come back when you have something to repair!");
            return;
        }
        hero.getInventory().get(selection-1).repair(hero);                  // Repairs item
    }

    // prints all items for sale
    public String toString(){
        String output = "";
        for (int i = 0; i < itemsForSale.size(); i++) {
            output += "[" + (i+1) + "]" + itemsForSale.get(i).toString();
        }
        return output;
    }
}
