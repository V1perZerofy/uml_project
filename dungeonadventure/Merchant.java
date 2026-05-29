package dungeonadventure;

import java.util.ArrayList;
import java.util.List;

public class Merchant extends NPC {

    private static final int DEFAULT_PRICE = 10;

    private List<Item> inventory;
    private String currencyName;

    public Merchant(String name, int maxHealth, String dialogText, String currencyName) {
        super(name, maxHealth, dialogText, false);
        this.currencyName = currencyName;
        this.inventory = new ArrayList<>();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getPrice(Item item) {
        return DEFAULT_PRICE;
    }

    public void restock() {
        // TODO: needs a stock source/template to define what gets restocked
    }

    @Override
    public String interact() {
        return dialogText;
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public boolean trade(Player player, Item item) {
        if (!inventory.contains(item)) {
            return false;
        }
        int price = getPrice(item);
        if (!player.spendGold(price)) {
            return false;
        }
        removeFromInventory(item);
        player.pickUp(item);
        return true;
    }
}
