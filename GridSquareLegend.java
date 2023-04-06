public class GridSquareLegend extends GridSquare{
    private int amplify;
    private HeroTeam heroTeam;
    private Monster monster;

    public GridSquareLegend(){
        super();
        this.amplify = 0;
    }

    public GridSquareLegend(int amplify){
        super(amplify);
        this.amplify = amplify;
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
        return null;
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
                break;}
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
                break;}
        for (Hero h : this.heroTeam.getHeroes()){
            double effectValue = h.getAttributes().get(effect).getCurrent();
            effectValue -= (effectValue * 1/11);
            h.getAttributes().get(effect).decreaseCurrent(effectValue);
        }
    }
}
