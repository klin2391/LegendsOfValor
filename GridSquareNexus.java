public abstract class GridSquareNexus extends GridSquareLegend{
    private Monster monster;
    private HeroTeam heroTeam;

    abstract public int checkWin();

    public GridSquareNexus(int terrain){
        super(terrain);
    }

    // Accessor
    public HeroTeam getHeroTeam() {
        return this.heroTeam;
    }

    public boolean moveHeroTeam(HeroTeam ht) {
        this.heroTeam = ht;
        this.isOccupied = true;
        return true;
    }
}
