/*
 * Color.java
 * by Kevin Lin (lin2391@bu.edu)
 * 11FEB2023/15MAR2023
 * 
 * This class is a helper class.
 * It contains the ANSI escape codes for the colors.
 * Defaults to black.
 * Taken from TTT project
 */

 public class Color {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_ITALIC = "\u001B[3m";
    public static final String ANSI_ITALIC_RESET = "\u001B[23m";
    public static final String ANSI_GOLD = "\u001B[38;5;220m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private String color;

    // Default constructor
    public Color() {
        color = ANSI_RESET;
    }

    // Constructor that takes string input
    public Color(String color) {
        setColor(color);
    }

    // Returns the colors
    public String getColor() {
        return color;
    }

    // Resets the color to black
    public String resetColor() {
        color = ANSI_RESET;
        return color;
    }

    // Returns the ANSI escape code for black
    public String getBlack() {
        return ANSI_RESET;
    }

    public String italicsReset(){
        return ANSI_ITALIC_RESET;
    }

    // Sets the color based on user input
    public void setColor(String color) {
        if (color.equalsIgnoreCase("black")){
            this.color = ANSI_BLACK;
        }
        else if (color.equalsIgnoreCase("red")){
            this.color = ANSI_RED;
        }
        else if (color.equalsIgnoreCase("green")){
            this.color = ANSI_GREEN;
        }
        else if (color.equalsIgnoreCase("yellow")){
            this.color = ANSI_YELLOW;
        }
        else if (color.equalsIgnoreCase("blue")){
            this.color = ANSI_BLUE;
        }
        else if (color.equalsIgnoreCase("purple")){
            this.color = ANSI_PURPLE;
        }
        else if (color.equalsIgnoreCase("cyan")){
            this.color = ANSI_CYAN;
        }
        else if (color.equalsIgnoreCase("white")){
            this.color = ANSI_WHITE;
        }
        else if (color.equalsIgnoreCase("gold")){
            this.color = ANSI_GOLD;
        }
        else if (color.equalsIgnoreCase("italics")){
            this.color = ANSI_ITALIC;
        }
        else {
            this.color = ANSI_RESET;
        }
    }

    // Clears the screen
    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
