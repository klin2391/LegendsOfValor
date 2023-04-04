/*
 * Attribute.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This class is for any attribute of a noun (hero, monster, item)
 * It contains a name, the current and max values of the attribute.
 */

import java.math.*;
public class Attribute {
    protected double current;
    protected double max;
    protected String name;
    protected boolean favored;

    // Default Constructor
    public Attribute(){
        this.max = 100;
        this.current = 100;
        this.name = "Attribute";
        this.favored = false;
    }

    // Constructor with values
    public Attribute(double max, String name, boolean favored) {
        this.max = max;
        this.current = max;
        this.name = name;
        this.favored = favored;
    }

    // Accessor methods
    public double getCurrent() {
        return current;
    }

    public double getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    // Mutator methods
    public void setCurrent(double current) {
        this.current = current;
    }

    public void setMax(double max) {                // set current to max because useful for level up
        this.max = max;
        this.current = max;
    }

    public void increaseCurrent(double amount) {
        if (amount < 0){                            // Increase positive values only
            amount = 0;
        }
        this.current += amount;
    }

    public void decreaseCurrent(double amount) {
        if (amount < 0){                            // Decrease positive values only
            amount = 0;
        }
        if (this.current - amount < 0) {            // Don't go below 0
            this.current = 0;
        }
        else {
            this.current -= amount;
        }
    }

    public void increaseMax(double amount) {        // Increase max by amount
        this.max += amount;
        this.current = this.max;                    // Set current to max
    }

    // Update method for observer
    public void update(double level) {
        switch (name) {
            case "Health":
                setMax(level * 100);
                break;
            case "Mana":
                setMax(max * Math.pow(1.1, level-1));
                break;
            case "Weight":
                break;
            default:
                setMax(max * Math.pow(1.05, level-1));
                if (favored) {
                    setMax(max * Math.pow(1.05, level-1));
                }
                break;
        }
    }
    
    // toString method
    public String toString() {
        String output = "";
        Color color = new Color();
        if ((double) current / max < 0.33) {
            color.setColor("red");
        }
        else if ((double) current / max < 0.66) {
            color.setColor("yellow");
        }
        else {
            color.setColor("green");
        }
        output += "\t[i] Current "+ name +": " + color.getColor() + (int) current + color.getBlack() + " / " +  (int) max + "\n";
        return output;
    }
}
