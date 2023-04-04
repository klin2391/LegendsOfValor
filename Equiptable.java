/*
 * Equiptable.java
 * by Kevin Lin (lin2391@bu.edu)
 * 24MAR2023
 * 
 * This interface defines equiptable behavior for items
 * Used armor and weapons
 */
public interface Equiptable {
    public void equipt(Hero hero, Equiptable item, int position);
    public void remove(Hero hero, Equiptable item, int position);
    public int getValue();
}
