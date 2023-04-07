public class GridSquareNexusMonster extends GridSquareNexus{

    public GridSquareNexusMonster(int x, int y) {
        super(8,x,y);
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
