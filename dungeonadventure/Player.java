package dungeonadventure;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity implements IFightable {

    private static final int ATTACK_POWER = 10;

    private List<Item> inventory;
    private int gold;

    public Player(String name, int maxHealth, int gold) {
        super(name, maxHealth);
        this.gold = gold;
        this.inventory = new ArrayList<>();
    }

    public void useItem(Item item) {
        if (inventory.contains(item)) {
            item.use(this);
            inventory.remove(item);
        }
    }

    public void pickUp(Item item) {
        inventory.add(item);
    }

    public boolean hasHealingPotion() {
        for (Item item : inventory) {
            if (item instanceof HealingPotion) {
                return true;
            }
        }
        return false;
    }

    public int getGold() {
        return gold;
    }

    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    @Override
    public int attack() {
        return ATTACK_POWER;
    }

    @Override
    public void defend(int dmg) {
        health -= dmg;
        if (health < 0) {
            health = 0;
        }
    }
}
