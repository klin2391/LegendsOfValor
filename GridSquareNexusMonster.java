public class GridSquareNexusMonster extends GridSquareNexus{

    public GridSquareNexusMonster() {
        super(8);
    }

    public void removeHeroTeam(){
        super.removeHeroTeam();
    }
    public String toString() {
        Color color = new Color("red");
        return color.getColor() + "N" + color.getBlack();
    }
    
    public int checkWin() {
        if (this.getHeroTeam() != null) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
