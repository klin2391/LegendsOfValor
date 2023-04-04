/*
 * FileParser.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This class is a utility class that reads a file and returns an ArrayList of Strings.
 * From: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
 */

import java.io.*;
import java.util.*;
public class FileParser {
    private BufferedReader br;
    private File file;

    // Constructor that assumes the file is in the default folder
    public FileParser(String fileName) throws Exception{
        file = new File(".\\default\\" + fileName + ".txt");
        br = new BufferedReader(new FileReader(file));

    }

    // Constructor that allows the user to specify the folder
    public ArrayList <String> generateList() throws Exception{
        ArrayList <String> list = new ArrayList<String>();
        String st;
        while ((st = br.readLine()) != null) {
            list.add(st);
        }
        return list;
    }
}