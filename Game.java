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
        Room lair = new Room("A dark lair reeking of decay.", goblin, null,
                new HealingPotion("Potion", "Restores health.", 20));
        Room shop = new Room("A small candle-lit alcove.", null, trader, null);

        entrance.addExit("north", lair);
        lair.addExit("south", entrance);
        entrance.addExit("east", shop);
        shop.addExit("west", entrance);

        List<Room> rooms = new ArrayList<>();
        rooms.add(entrance);
        rooms.add(lair);
        rooms.add(shop);

        new GameEngine(player, entrance, rooms).start();
    }
}