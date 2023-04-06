public abstract class GridSquareNexus extends GridSquare{
    private Monster monster;
    private HeroTeam heroTeam;

    abstract public int checkWin();

    public GridSquareNexus(int terrain){
        super(terrain);
    }
}
