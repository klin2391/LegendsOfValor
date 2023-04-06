public class GridSquareNexusMonster extends GridSquareNexus{

    public GridSquareNexusMonster() {
        super(7);
    }

    public boolean moveHeroTeam(HeroTeam ht) {
        return false;
    }
    public void removeHeroTeam(){

    }
    public String toString() {
        Color color = new Color("red");
        return color.getColor() + "N" + color.getBlack();
    }
    public HeroTeam getHeroTeam() {
        return null;
    }
    public int checkWin() {
        return 0;
    }
}
