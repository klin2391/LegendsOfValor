public class GridSquareNexusHero extends GridSquareNexus{
    private Marketplace marketplace;

    public GridSquareNexusHero(Marketplace marketplace) {
        super(7);
        this.marketplace = marketplace;
    }
    
    public void removeHeroTeam(){
        super.removeHeroTeam();
    }
    public String toString() {
        Color color = new Color("cyan");
        return color.getColor() + "N" + color.getBlack();
    }
    public int checkWin() {
        if (this.getMonster() != null) {
            return 2;
        }
        else {
            return 0;
        }
    }

    public Marketplace getMarket() {
        return marketplace;
    }
}
