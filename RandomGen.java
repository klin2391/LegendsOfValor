/*
 * RandomGen.java
 * by Kevin Lin (lin2391@bu.edu)
 * 15MAR2023
 * 
 * Generates a random number 1-100. Will allow for probability.
 * 
 */

import java.util.Random;
public class RandomGen {

    private  Random rand;                       // Random object

    // Constructor to initialize the Random object
    public RandomGen() {
        rand = new Random();
    }

    // Generates a random number between 1 and 100
    public int generateRandomNumber() {
        return rand.nextInt(100) + 1;
    }

    // Generates a random number between min and max
    public int getRandomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
