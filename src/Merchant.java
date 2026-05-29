package dungeonadventure;

import java.util.ArrayList;
import java.util.List;

public class Merchant extends NPC {

    private final int DEFAULT_PRICE = 10;
    private int merchant_multiplier;

    private List<Item> inventory;
    private String currencyName;

    public Merchant(String name, int maxHealth, String dialogText, String currencyName, int merchant_multiplier) {
        super(name, maxHealth, dialogText, false);
        this.currencyName = currencyName;
        this.merchant_multiplier = merchant_multiplier;
        this.inventory = new ArrayList<>();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getPrice(Item item) {
        return (int) (DEFAULT_PRICE * merchant_multiplier);
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
