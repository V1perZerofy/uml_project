package dungeonadventure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public String getDescription() {
        return description;
    }

    public Set<String> getExitDirections() {
        return exits.keySet();
    }

    public String describe() {
        StringBuilder sb = new StringBuilder(description);
        if (hasMonster()) {
            sb.append("\nA ").append(monster.getName()).append(" is here.");
        }
        if (hasMerchant()) {
            sb.append("\nA merchant, ").append(merchant.getName()).append(", is here.");
        }
        if (hasItem()) {
            sb.append("\nYou see a ").append(item.getName()).append(".");
        }
        if (!exits.isEmpty()) {
            sb.append("\nExits: ").append(String.join(", ", exits.keySet()));
        }
        return sb.toString();
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
