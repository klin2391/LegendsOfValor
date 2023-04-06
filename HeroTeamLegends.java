import java.util.ArrayList;

public class HeroTeamLegends extends HeroTeam{
    public HeroTeamLegends(int heroNum) {
        super();
        this.setSymbol(String.valueOf(heroNum));
    }
    

    // Actions of the hero team
    public void heroAttack(HeroTeam heroTeam, ArrayList <Monster> monsters){
        Input input = new Input();
        for (Hero hero : heroTeam.getHeroes()){
            if (hero.getAttributes().get("Health").getCurrent() <= 0)           // If hero is dead, skip turn
                continue;
                // TODO move to respawn
            System.out.println(hero.getName() + " it is your turn!");
            System.out.println(hero);
            int action = input.queryInt("What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory", 1, 4, "i");
            while (action < 0){                                                     // Print inventory if special character                  
                Color.clearScreen();
                hero.battleInventory(monsters);
                action = input.queryInt("What do you want to do? 1: Attack using equipted weapon, 2: Cast a spell using charm, 3: Drink a potion, 4: Equipt from inventory", 1, 4, "i");
            }
            if (action == 1){                                                       // Attack           
                Color.clearScreen();
                hero.attackWPN(hero, monsters, input);
            }
            else if (action == 2){                                                  // Cast spell                     
                Color.clearScreen();
                hero.useCharm(hero, monsters, input);
            }
            else if (action == 3){                                                  // Drink potion                   
                Color.clearScreen();
                hero.drinkPotion(hero, monsters, input);
            }
            else {                                                                  // Equipt from inventory                          
                Color.clearScreen();
                hero.addEquiptment(hero, monsters, input);
            }
            for (int i = 0; i < monsters.size(); i++){
                if (monsters.get(i).getAttributes().get("Health").getCurrent() <= 0){   // If monster is dead, give experience and gold to living heroes
                    for (Hero hero1: heroTeam.getHeroes()){
                        if (hero1.getAttributes().get("Health").getCurrent() > 0)
                            hero1.getLevel().addExperience(2);
                            hero1.setGold(hero1.getGold() + monsters.get(i).getLevel().getLevel()*100);
                    }
                    monsters.remove(i);                                             // Remove monster if dead
                }
            }
        }
    }
}
