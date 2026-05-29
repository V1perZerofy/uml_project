import dungeonadventure.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

    public static void main(String[] args) {
        Player player = new Player("Hero", 100, 50);

        Monster goblin = new Monster("Goblin", 30, 8,
                new HealingPotion("Potion", "Restores health.", 20));

        Merchant trader = new Merchant("Trader", 100,
                "Welcome, traveler. Type 'buy <item>' to purchase.", "Gold");
        trader.getInventory().add(new HealingPotion("Potion", "Restores health.", 20));
        trader.getInventory().add(new HealingPotion("Elixir", "Restores a lot of health.", 50));

        Room entrance = new Room("A damp stone entrance hall.", null, null, null);
        Room basement = new Room("A dark basement reeking of decay.", goblin, null,
                new HealingPotion("Potion", "Restores health.", 20));
        Room shop = new Room("A small candle-lit alcove.", null, trader, null);

        entrance.addExit("north", basement);
        basement.addExit("south", entrance);
        entrance.addExit("east", shop);
        shop.addExit("west", entrance);

        List<Room> rooms = new ArrayList<>();
        rooms.add(entrance);
        rooms.add(basement);
        rooms.add(shop);

        System.out.println("=== Dungeon Adventure ===");
        System.out.println("Commands: go <dir>, fight, take, talk, buy <item>, use <item>, inventory, look, quit");
        System.out.println();

        new GameEngine(player, entrance, rooms).start();
    }
}