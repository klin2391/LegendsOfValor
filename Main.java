public class Main {
    public static void main(String[] args){
        // MonstersAndHeroes game = new MonstersAndHeroes();
        // game.start();
        int effectValue = 100;
        effectValue += (effectValue * 0.1);
        System.out.println(effectValue);
        effectValue -= (effectValue * 1/11);
        System.out.println(effectValue);
        WorldLegends game = new WorldLegends(12);
        // HeroTeam ht = new HeroTeam();
        game.generateWorld();
        game.showGlobalView();
    }
}
