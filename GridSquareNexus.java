public abstract class GridSquareNexus extends GridSquareLegend{
    private Monster monster;
    private HeroTeam heroTeam;
    private Boolean winConditionMet = false;

    abstract public int checkWin();

    public GridSquareNexus(int terrain, int x, int y){
        super(terrain, x ,y );
    }

    // Accessor
    public HeroTeam getHeroTeam() {
        return this.heroTeam;
    }

    public void removeHeroTeam() {
        this.heroTeam = null;
        this.isOccupied = false;
    }

    
    public boolean moveHeroTeam(HeroTeam ht) {
        this.heroTeam = ht;
        this.isOccupied = true;
        System.out.println("MOved");
        return true;
    }

    public Monster getMonster() {
        return this.monster;
    }

}
