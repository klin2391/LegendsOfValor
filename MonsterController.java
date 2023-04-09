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

    // Takes a turn for all monsters on the board. Monsters can either move or attack.
    public void takeMonsterTurns() {
        for (int i = 0; i < this.livingMonsters.size(); i++) {
            if (this.livingMonsters.get(i).getAttributes().get("Health").getCurrent() == 0) {
                this.livingMonsters.remove(i);
                ((GridSquareLegend) this.world.getMap()[this.monsterXs.get(i)][this.monsterYs.get(i)]).setMonster(null);
                this.monsterXs.remove(i);
                this.monsterYs.remove(i);
                i--;
                continue;
            }
            ArrayList<HeroTeamLegends> closeHeroes = this.world.getHeroesInRange(this.monsterXs.get(i), this.monsterYs.get(i));
            if (closeHeroes.isEmpty()) {
                // move
                int moved = this.world.moveMonster(this.monsterXs.get(i), this.monsterYs.get(i));
                if (moved == 0)
                    this.monsterXs.set(i,this.monsterXs.get(i)+1);
                else if (moved == 1)
                    this.monsterYs.set(i, this.monsterYs.get(i)-1);
                else if (moved == 2) {
                    this.monsterYs.set(i, this.monsterYs.get(i)+1);
                }
            }
            else {
                // fight
                this.livingMonsters.get(i).attack(closeHeroes.get(0));
                // System.out.println(this.livingMonsters.get(i).getName() + " attacks!");
            }
        }
        if (turnsLeft-- == 0) {
            this.spawnMonsters();
            this.turnsLeft = this.turnsUntilRespawn;
        }
    }

    private void spawnMonsters() {
        for (int i = 0; i < this.nexuses.size(); i++) {
            if (!((GridSquareLegend)this.nexuses.get(i)).hasMonster()) {                                            // Nexus is empty of monsters
                Monster m = fm.selectOneMonster(this.world.getHeroes().get(i).getCombinedLevel(), 1).get(0);    // Get a monster of the appropriate level
                this.nexuses.get(i).setMonster(m);                                            // Set the monster in the nexus   
                this.livingMonsters.add(m);                                     // Add the monster to the list of living monsters
                this.monsterXs.add(this.nexusXs.get(i));                // Add the monster's x coordinate to the list of monster x coordinates
                this.monsterYs.add(this.nexusYs.get(i));                // Add the monster's y coordinate to the list of monster y coordinates
            }
        }
    }
}