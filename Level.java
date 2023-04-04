/*
 * Level.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * Level Attribute has level and experience
 */

import java.util.*;
public class Level {
    private int level;
    private int experience;
    private ArrayList<Attribute> observers;     // list of attributes that are observing this level
    
    // Constructor
    public Level() {
        this.level = 1;
        this.experience = 0;
        this.observers = new ArrayList<Attribute>();
    }

    // Constructor that takes in data
    public Level(int i) {
        this.level = i;
        this.experience = 0;
        this.observers = new ArrayList<Attribute>();
    }

    // Accessors
    public int getLevel() {
        return level;
    }

    // Setters
    public void setLevel(int i) {
        level = i;
        notifyObservers();              // notify observers when level is set
    }

    // Add attribute to update
    public void registerObserver(Attribute observer) {
        observers.add(observer);
    }

    // Remove attribute to update
    public void removeObserver(Attribute observer) {
        observers.remove(observer);
    }

    // Level up
    public void levelUp() {
        level++;
        notifyObservers();
    }

    // Add experience
    public void addExperience(int experience) {
        this.experience += experience;
        if (this.experience >= level * 10) {
            levelUp();
            this.experience = 0;
        }
    }

    // Notify observers
    public void notifyObservers() {
        for (Attribute observer : observers) {
            observer.update(level);
        }
    }

    // toString
    public String toString() {
        String output = "";
        output += "\t[i] Level: " + level + "\n";
        output += "\t[i] Experience to next level: " + (level * 10 - experience) + "\n";
        return output;
    }
}
