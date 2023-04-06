public class WorldLegends extends World{
    private int height;
    private int numHeroes;

    public WorldLegends(int size) {
        super(size, 1);
        numHeroes = this.getHeroTeam().getHeroes().size();
        this.height = size;
        this.setProbabilityMarket(30);
        this.setProbabilityRestricted(5);
    }

    public void generateWorld() {
        boolean hasMarket = false;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (i == 0 || i == height-1 || j % 3 == 0 ||j == 0 || j == getSize()-1)         //Sets the border of the board to be severely restricted.
                    this.getMap()[i][j] = new GridSquareSeverelyRestricted();
                else if (i == getPlayerLocationX() && j == getPlayerLocationY()) {    //Sets the player's location to be common terrain.
                    this.getMap()[i][j] = new GridSquareCommon();
                    this.getMap()[i][j].moveHeroTeam(this.getHeroTeam());
                }
                else if (i == 1){
                    this.getMap()[i][j] = new GridSquareNexusMonster();
                    hasMarket = true;
                }
                else if (i == height-2){
                    this.getMap()[i][j] = new GridSquareNexusHero(this.getFactoryMarket().createMarket());    // Creates market
                    hasMarket = true;
                }
                else {
                    int randNum = this.getRand().generateRandomNumber();
                    if (randNum <= this.getProbabilityMarket()) {
                        randNum = this.getRand().getRandomInt(4, 6);
                        this.getMap()[i][j] = new GridSquareLegend(randNum);                                // Creates legend terrain
                    }
                    else if (randNum <= this.getProbabilityMarket() + this.getProbabilityRestricted()) {      // Creates restricted terrain
                        this.getMap()[i][j] = new GridSquareRestricted();
                    }
                    else {
                        this.getMap()[i][j] = new GridSquareLegend(0);                              // Creates common terrain
                    }
                }
            }
        }
        if (!hasMarket) {
            generateWorld();                                                                  // If there is no market, generate a new world.
        }
        // System.out.println("World generated!");
    }

    // Shows the entire world
    public void showGlobalView() {
        System.out.println(this);
    }

    // tostring shows whole world
    public String toString() {
        String output = "";
        for (int i = 0; i < this.getSize(); i++) {
            output += "+-----";
        }
        output += "+\n";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < this.getSize(); j++) {
                output += "|  " + this.getMap()[i][j].getTerrain().getColor().getColor() + this.getMap()[i][j] + this.getMap()[i][j].getTerrain().getColor().getBlack() + "  ";
                // if (this.getMap()[i][j].getIsOccupied())
                //     output += "|  " + this.getMap()[i][j].getHeroTeam().getColor().getColor() + this.getMap()[i][j].getHeroTeam().getSymbol() + this.getMap()[i][j].getHeroTeam().getColor().getBlack() + "  ";
                // else
                //     output += "|  " + this.getMap()[i][j].getTerrain().getColor().getColor() + this.getMap()[i][j] + this.getMap()[i][j].getTerrain().getColor().getBlack() + "  ";
            }
            output += "|\n";
            for (int j = 0; j < this.getSize(); j++) {
                output += "+-----";
            }
            output += "+\n";
        }
        return output;
    }


}
