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
    private ArrayList<Integer> monsterXs;
    private ArrayList<Integer> monsterYs;
    private ArrayList<GridSquareNexusMonster> nexuses;
    private ArrayList<Integer> nexusXs;
    private ArrayList<Integer> nexusYs;
    private int turnsUntilRespawn;
    private FactoryMonster fm = new FactoryMonster();
    private int turnsLeft;
    private WorldLegends world;

    public MonsterController(int turnsUntilRespawn, WorldLegends world) {
        this.turnsUntilRespawn = turnsUntilRespawn;
        this.turnsLeft = 0;
        this.livingMonsters = new ArrayList<>();
        this.nexuses = new ArrayList<>();
        this.monsterXs = new ArrayList<>();
        this.monsterYs = new ArrayList<>();
        this.nexusXs = new ArrayList<>();
        this.nexusYs = new ArrayList<>();
        this.world = world;
    }

    // Mutator
    public void addNexus(GridSquareNexusMonster nexus, int x, int y) {
        this.nexuses.add(nexus);
        this.nexusXs.add(x);
        this.nexusYs.add(y);
    }

    // Turns for monster
    public void takeMonsterTurns() {
        for (int i = 0; i < this.livingMonsters.size(); i++) {
            ArrayList<HeroTeamLegends> closeHeroes = this.world.getHeroesInRange(this.monsterXs.get(i), this.monsterYs.get(i));
            if (closeHeroes.isEmpty()) {
                // move
                int moved = this.world.moveMonster(this.monsterXs.get(i), this.monsterYs.get(i));
                if (moved == 0)
                    this.monsterXs.set(i,this.monsterXs.get(i)+1);
                else if (moved == 1)
                    this.monsterYs.set(i, this.monsterYs.get(i)-1);
            }
            else {
                // fight
                this.livingMonsters.get(i).attack(closeHeroes.get(0));
                System.out.println(this.livingMonsters.get(i).getName() + " attacks!");
            }
        }
        if (turnsLeft-- == 0) {
            this.spawnMonsters();
            this.turnsLeft = this.turnsUntilRespawn;
        }
    }

    private void spawnMonsters() {
        for (int i = 0; i < this.nexuses.size(); i++) {
            if (!((GridSquareLegend)this.nexuses.get(i)).hasMonster()) {
                Monster m = fm.selectOneMonster(this.world.getHeroes().get(i).getCombinedLevel(), 1).get(0);
                this.nexuses.get(i).setMonster(m);
                this.livingMonsters.add(m);
                this.monsterXs.add(this.nexusXs.get(i));
                this.monsterYs.add(this.nexusYs.get(i));
            }
        }
    }
}