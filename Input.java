/*
 * Input.java
 * by Kevin Lin (lin2391@bu.edu)
 * 14MAR2023
 * 
 * This class is used centralize user input.
 * Can query ints, strings and booleans and validates each.
 * If user ever types quit as a response, quits program.
 * Adapted from TTT project.
 * 
 * Modified by Kevin
 * 07APR2023
 * Adding unique prompts and to check for certain letters is okay.
 * 
 */

import java.util.Scanner;

public class Input {
    public Scanner userInput;                       // Scanner object

    // Constructor to initialize the Scanner object
    public Input() {
        userInput = new Scanner(System.in);
    }

    // Takes user action. Returns the action
    public String userAction(){
        System.out.println("[+] <w/a/s/d> to move, <i> for inventory, <m> for map, <h> for hero stats, <t> for trade, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit");
        String action = " ";
        while(!isValidString(action)){
            action = userInput.nextLine();    
            checkQuit(action);
        }
        return action.toLowerCase();
    }

    // New prompt for user Action
    public String userActionLegend(){
        System.out.println("[+] <w/a/s/d> to move, <i> for inventory, <m> for recall, <h> for hero stats, <t> for trade, <f> for fight, <y> for teleport, <g> for changing terrain, <c> to change inventory, <p> to drink potion, <q> to quit");
        String action = " ";
        while(!isValidString(action)){
            action = userInput.nextLine();    
            checkQuit(action);
        }
        return action.toLowerCase();
    }

    // Asks user for a string. Takes a prompt as input. Returns string
    public String queryString(String prompt) {
        System.out.println("[+] " + prompt + ": ");
        String value = userInput.nextLine();
        checkQuit(value);
        return value;
    }

    // Asks user for an int. Takes a prompt as input. Ensures is int type. Returns int.
    public int queryInt(String prompt, int lowerBound) {
        String dimension = " ";
        int i_dimension = (int) Double.NEGATIVE_INFINITY;
        while (i_dimension < lowerBound){
            System.out.println("[+] " + prompt + ": ");
            dimension = userInput.nextLine();
            checkQuit(dimension);
            while (!isNumeric(dimension)){
                System.out.println("[+] " + prompt + ": ");
                dimension = userInput.nextLine();
                checkQuit(dimension);
            }
            i_dimension = Integer.parseInt(dimension);
        }
        return i_dimension;
    }

    // Extension of above method but with upper bound
    public int queryInt(String prompt, int lowerbound, int upperbound) {
        int temp = this.queryInt(prompt, lowerbound);
        if (temp > upperbound) {
            System.out.println("[!] Value must be less than " + upperbound);
            temp = this.queryInt(prompt, lowerbound, upperbound);
        }
        return temp;
    }

    // Extension of above method but with special value. Returns -1 if special value is entered
    public int queryInt(String prompt, int lowerBound, int upperBound, String specialVal) {
        String dimension = " ";
        int i_dimension = (int) Double.NEGATIVE_INFINITY;
        while (i_dimension < lowerBound || i_dimension > upperBound){
            System.out.println("[+] " + prompt + ": ");
            dimension = userInput.nextLine();
            checkQuit(dimension);
            if (dimension.equalsIgnoreCase(specialVal)) {
                return -1;
            }
            while (!isNumeric(dimension)){
                System.out.println("[+] " + prompt + ": ");
                dimension = userInput.nextLine();
                checkQuit(dimension);
            }
            i_dimension = Integer.parseInt(dimension);
        }
        return i_dimension;
    }

    // Asks user for a boolean. Takes a prompt as input. Ensures is y or n. Returns boolean equivilent
    public boolean queryBoolean(String prompt) {
        String value = " ";
        while (!value.equals("y") && !value.equals("n")){
            System.out.println("[+] " + prompt + " (y/n): ");
            value = userInput.nextLine();
            checkQuit(value);
        }
        if (value.equals("y")){
            return true;
        }
        return false;
    }

    // Helper function to determine if a user input is numeric
    private boolean isNumeric(String value) {
        try {
            int i = Integer.parseInt(value);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // Method to ensure user input is valid
    private boolean isValidString(String value) {
        String val = value.toLowerCase();
        switch (val) {
            case "w":
                return true;
            case "a":
                return true;
            case "s":
                return true;
            case "d":
                return true;
            case "i":
                return true;
            case "m":
                return true;
            case "h":
                return true;
            case "t":
                return true;
            case "g":
                return true;
            case "c":
                return true;
            case "p":
                return true;
            case "y":
                return true;
            case "f":
                return true;
            default:
                return false;
        }
    }

    // Method to quit at any time
    private void checkQuit(String input){
        if (input.toLowerCase().equals("q")|| input.toLowerCase().equals("Q")){
            System.out.println("[+] Goodbye!");
            System.exit(0);
        }
    }
}
