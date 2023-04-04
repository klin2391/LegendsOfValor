/*
 * AttributeWeight.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This class is used to represent weight carried by hero.
 * It inherits from Attribute.java but behaves differently.
 */
public class AttributeWeight extends Attribute{

    // Default Constructor
    public AttributeWeight(double max, String name, boolean favored) {
        this.max = max;
        this.current = 0;
        this.name = name;
        this.favored = favored;
    }

    // Mutator methods. Does not make current = max
    public void setMax(double max) {
        this.max = max;
    }

    public void increaseMax(double amount) {
        this.max += amount;
    }

    // Override toString method because less weight is better
    public String toString() {
        String output = "";
        Color color = new Color();
        if ((double) current / max < 0.33) {
            color.setColor("green");
        }
        else if ((double) current / max < 0.66) {
            color.setColor("yellow");
        }
        else {
            color.setColor("red");
        }
        output += "\t[i] Current "+ name +": " + color.getColor() + (int) current + color.getBlack() + " / " + (int) max + "\n";
        return output;
    }
}
