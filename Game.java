import dungeonadventure.*;
import java.util.ArrayList;
import java.util.List;

public class Game {

    public static void main(String[] args) {
        Player player = new Player("Hero", 100, 80);

        Monster goblin = new Monster("Goblin", 30, 8,
                new HealingPotion("Potion", "Restores health.", 20));
        Monster orc = new Monster("Orc Brute", 55, 12,
                new HealingPotion("Liquid Courage", "Tastes like regret. Restores 40 health.", 40));
        Monster troll = new Monster("Cave Troll", 90, 16,
                new HealingPotion("Troll Tonic", "Thick, green and warm. Restores 70 health.", 70));

        Merchant trader = new Merchant("Trader", 100,
                "Welcome, traveler. Type 'buy <item>' to purchase.", "Gold");
        trader.addStock(new HealingPotion("Potion", "Restores health.", 20));
        trader.addStock(new HealingPotion("Elixir", "Restores a lot of health.", 50));

        Merchant alchemist = new Merchant("Mad Alchemist", 100,
                "Heh heh... fresh brews! Type 'buy <item>' if you dare.", "Gold");
        alchemist.addStock(new HealingPotion("Questionable Brew", "Smells faintly of feet. Restores 15 health.", 15));
        alchemist.addStock(new HealingPotion("Definitely Not Poison", "Just trust the label. Restores 30 health.", 30));
        alchemist.addStock(new HealingPotion("Grandma's Mystery Juice", "Nobody asks what is in it. Restores 60 health.", 60));

        Room entrance = new Room("A damp stone entrance hall.", null, null, null);
        Room lair = new Room("A dark lair reeking of decay.", goblin, null,
                new HealingPotion("Potion", "Restores health.", 20));
        Room shop = new Room("A small candle-lit alcove.", null, trader, null);
        Room chamber = new Room("A pillared chamber, the air thick with dust.", orc, null,
                new HealingPotion("Elixir", "Restores a lot of health.", 50));
        Room apothecary = new Room("A cluttered apothecary, shelves crammed with bubbling vials.",
                null, alchemist, null);
        Room crypt = new Room("A vast crypt. Something enormous stirs in the shadows.", troll, null,
                new HealingPotion("Elixir", "Restores a lot of health.", 50));

        entrance.addExit("north", lair);
        lair.addExit("south", entrance);
        entrance.addExit("east", shop);
        shop.addExit("west", entrance);

        lair.addExit("north", chamber);
        chamber.addExit("south", lair);
        chamber.addExit("east", apothecary);
        apothecary.addExit("west", chamber);
        chamber.addExit("north", crypt);
        crypt.addExit("south", chamber);

        List<Room> rooms = new ArrayList<>();
        rooms.add(entrance);
        rooms.add(lair);
        rooms.add(shop);
        rooms.add(chamber);
        rooms.add(apothecary);
        rooms.add(crypt);

        new GameEngine(player, entrance, rooms).start();
    }
}
