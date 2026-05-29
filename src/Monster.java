package dungeonadventure;

public class Monster extends Entity implements IFightable {

    private Item lootDrop;
    private int difficulty;

    public Monster(String name, int maxHealth, int difficulty, Item lootDrop) {
        super(name, maxHealth);
        this.difficulty = difficulty;
        this.lootDrop = lootDrop;
    }

    public Item dropLoot() {
        return lootDrop;
    }

    @Override
    public int attack() {
        return difficulty;
    }

    @Override
    public void defend(int dmg) {
        health -= dmg;
        if (health < 0) {
            health = 0;
        }
    }
}
