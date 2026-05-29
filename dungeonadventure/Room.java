package dungeonadventure;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private String description;
    private Monster monster;
    private Merchant merchant;
    private Item item;
    private Map<String, Room> exits;

    public Room(String description, Monster monster, Merchant merchant, Item item) {
        this.description = description;
        this.monster = monster;
        this.merchant = merchant;
        this.item = item;
        this.exits = new HashMap<>();
    }

    public void describe() {
        System.out.println(description);
        if (hasMonster()) {
            System.out.println("A " + monster.getName() + " is here.");
        }
        if (hasMerchant()) {
            System.out.println("A merchant, " + merchant.getName() + ", is here.");
        }
        if (hasItem()) {
            System.out.println("You see a " + item.getName() + ".");
        }
        if (!exits.isEmpty()) {
            System.out.println("Exits: " + String.join(", ", exits.keySet()));
        }
    }

    public Monster getMonster() {
        return monster;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Item getItem() {
        return item;
    }

    public boolean hasMonster() {
        return monster != null;
    }

    public boolean hasMerchant() {
        return merchant != null;
    }

    public boolean hasItem() {
        return item != null;
    }

    public void addExit(String dir, Room room) {
        exits.put(dir, room);
    }

    public Room getExit(String dir) {
        return exits.get(dir);
    }

    public void removeItem() {
        this.item = null;
    }
}
