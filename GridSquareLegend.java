public class GridSquareLegend extends GridSquare{
    private int amplify;
    private HeroTeam heroTeam;
    private Monster monster;
    private int xcoord;
    private int ycoord;

    public GridSquareLegend(){
        super();
        this.amplify = 0;
    }

    public GridSquareLegend(int amplify, int x, int y){
        super(amplify);
        this.amplify = amplify;
        this.xcoord = x;
        this.ycoord = y;
    }

    public Monster getMonster(){
        return monster;
    }

    public void setMonster(Monster m){
        this.monster = m;
    }

    public boolean moveHeroTeam(HeroTeam ht){
        this.heroTeam = ht;
        increaseAttributes(amplify);
        this.isOccupied = true;
        return false;
    }
    public void removeHeroTeam(){
        decreaseAttributes(amplify);
        this.heroTeam = null;
        this.isOccupied = false;
    }

    public String toString() {
        if (this.isOccupied) {
            return this.heroTeam.toString();
        }
        else {
            return this.terrain.getSymbol();
        }
    }

    public HeroTeam getHeroTeam(){
        return heroTeam;
    }

    private void increaseAttributes(int amplify){
        String effect = "";
        switch (amplify) {
            case 4:
                effect = "Dexterity";
                break;
            case 5:
                effect = "Agility";
                break;
            case 6:
                effect = "Strength";
                break;
            default:
                return;}
        for (Hero h : this.heroTeam.getHeroes()){
            double effectValue = h.getAttributes().get(effect).getCurrent();
            effectValue += (effectValue * 0.1);
            h.getAttributes().get(effect).increaseCurrent(effectValue);
        }
    }

    private void decreaseAttributes(int amplify){
        String effect = "";
        switch (amplify) {
            case 4:
                effect = "Dexterity";
                break;
            case 5:
                effect = "Agility";
                break;
            case 6:
                effect = "Strength";
                break;
            default:
                return;}
        for (Hero h : this.heroTeam.getHeroes()){
            double effectValue = h.getAttributes().get(effect).getCurrent();
            effectValue -= (effectValue * 1/11);
            h.getAttributes().get(effect).decreaseCurrent(effectValue);
        }
    }
}
