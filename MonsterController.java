/*
 * MonsterController.java
 * by Kevin Lin/Anand Shetler (lin2391@bu.edu/ anashe@bu.edu)
 * 07APR023
 * 
 * MonsterController class that controls the monsters in the game
 * This includes spawning, movement, and attack for all monsters in play
 */

import java.util.ArrayList;

public class MonsterController {
    private ArrayList<Monster> livingMonsters;
    private ArrayList<GridSquareNexusMonster> nexuses;
    private int turnsUntilRespawn;
    private FactoryMonster fm = new FactoryMonster();
    private int turnsLeft;
    private ArrayList<HeroTeamLegends> heroes;

    // Constructor
    public MonsterController(int turnsUntilRespawn, ArrayList<HeroTeamLegends> heroes) {
        this.turnsUntilRespawn = turnsUntilRespawn;
        this.turnsLeft = 0;
        this.livingMonsters = new ArrayList<>();
        this.nexuses = new ArrayList<>();
        this.heroes = heroes;
    }

    //Mutator
    public void addNexus(GridSquareNexusMonster nexus) {
        this.nexuses.add(nexus);
    }

    // Turns for monster
    public void takeMonsterTurns() {
        if (turnsLeft == 0) {
            this.spawnMonsters();
            this.turnsLeft = this.turnsUntilRespawn;
        }
        for (int i = 0; i < this.livingMonsters.size(); i++) {
            // fight or move

        }
    }

    private void spawnMonsters() {
        for (int i = 0; i < this.nexuses.size(); i++) {
            if (!((GridSquareLegend)this.nexuses.get(i)).hasMonster()) {
                Monster m = fm.selectOneMonster(this.heroes.get(i).getCombinedLevel(), 1).get(0);
                this.nexuses.get(i).addMonster(m);
            }
        }
    }
}