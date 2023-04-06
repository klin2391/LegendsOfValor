import java.util.ArrayList;

public class MonsterController {
    private ArrayList<Monster> livingMonsters;
    private ArrayList<GridSquareNexusMonster> nexuses;
    private int turnsUntilRespawn;
    private FactoryMonster fm = new FactoryMonster();
    private int turnsLeft;
    private ArrayList<HeroTeamLegends> heroes;

    public MonsterController(int turnsUntilRespawn, ArrayList<HeroTeamLegends> heroes) {
        this.turnsUntilRespawn = turnsUntilRespawn;
        this.turnsLeft = 0;
        this.livingMonsters = new ArrayList<>();
        this.nexuses = new ArrayList<>();
        this.heroes = heroes;
    }

    public void addNexus(GridSquareNexusMonster nexus) {
        this.nexuses.add(nexus);
    }

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