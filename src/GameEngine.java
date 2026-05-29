package dungeonadventure;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameEngine {

    private Player player;
    private Room currentRoom;
    private List<Room> rooms;
    private boolean running;

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public GameEngine(Player player, Room startRoom, List<Room> rooms) {
        this.player = player;
        this.currentRoom = startRoom;
        this.rooms = rooms;
    }

    public void start() {
        running = true;
        currentRoom.describe();
        while (running && player.isAlive()) {
            System.out.print("> ");
            String cmd = scanner.nextLine();
            handleInput(cmd);
        }
        if (!player.isAlive()) {
            System.out.println("You have been defeated. Game over.");
        }
    }

    public void handleInput(String cmd) {
        if (cmd == null) {
            return;
        }
        String input = cmd.trim().toLowerCase();
        if (input.equals("quit") || input.equals("exit")) {
            running = false;
        } else if (input.startsWith("go ")) {
            movePlayer(input.substring(3).trim());
        } else if (input.equals("fight")) {
            if (currentRoom.hasMonster() && currentRoom.getMonster().isAlive()) {
                resolveCombat(player, currentRoom.getMonster());
            } else {
                System.out.println("There is nothing to fight here.");
            }
        } else if (input.equals("take")) {
            if (currentRoom.hasItem()) {
                Item item = currentRoom.getItem();
                player.pickUp(item);
                currentRoom.removeItem();
                System.out.println("You picked up " + item.getName() + ".");
            } else {
                System.out.println("There is nothing to take.");
            }
        } else if (input.equals("talk")) {
            if (currentRoom.hasMerchant()) {
                Merchant merchant = currentRoom.getMerchant();
                System.out.println(merchant.interact());
                if (merchant.getInventory().isEmpty()) {
                    System.out.println("The merchant has nothing for sale.");
                } else {
                    System.out.println("For sale:");
                    for (Item item : merchant.getInventory()) {
                        System.out.println("  " + item.getName() + " - " + merchant.getPrice(item) + " gold");
                    }
                }
            } else {
                System.out.println("There is no one to talk to.");
            }
        } else if (input.equals("inventory") || input.equals("inv")) {
            if (player.getInventory().isEmpty()) {
                System.out.println("Your inventory is empty.");
            } else {
                System.out.println("You are carrying (" + player.getGold() + " gold):");
                for (Item item : player.getInventory()) {
                    System.out.println("  " + item.getName());
                }
            }
        } else if (input.startsWith("use ")) {
            String name = input.substring(4).trim();
            Item item = findByName(player.getInventory(), name);
            if (item == null) {
                System.out.println("You do not have that.");
            } else {
                player.useItem(item);
                System.out.println("You used " + item.getName() + ".");
            }
        } else if (input.startsWith("buy ")) {
            if (!currentRoom.hasMerchant()) {
                System.out.println("There is no merchant here.");
            } else {
                Merchant merchant = currentRoom.getMerchant();
                String name = input.substring(4).trim();
                Item item = findByName(merchant.getInventory(), name);
                if (item == null) {
                    System.out.println("The merchant does not sell that.");
                } else if (merchant.trade(player, item)) {
                    System.out.println("You bought " + item.getName() + ".");
                } else {
                    System.out.println("You cannot afford that.");
                }
            }
        } else if (input.equals("look")) {
            currentRoom.describe();
        } else {
            System.out.println("Unknown command.");
        }
    }

    private Item findByName(List<Item> items, String name) {
        for (Item item : items) {
            if (item.getName().toLowerCase().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public void movePlayer(String direction) {
        Room next = currentRoom.getExit(direction);
        if (next == null) {
            System.out.println("You cannot go that way.");
            return;
        }
        currentRoom = next;
        currentRoom.describe();
    }

    public void resolveCombat(Player p, Monster m) {
        while (p.isAlive() && m.isAlive()) {
            int playerDmg = p.attack();
            m.defend(playerDmg);
            System.out.println(p.getName() + " hits " + m.getName() + " for " + playerDmg + ".");
            if (!m.isAlive()) {
                break;
            }
            int monsterDmg = m.attack();
            p.defend(monsterDmg);
            System.out.println(m.getName() + " hits " + p.getName() + " for " + monsterDmg + ".");
        }
        if (!p.isAlive()) {
            running = false;
            return;
        }
        System.out.println(m.getName() + " is defeated.");
        Item loot = m.dropLoot();
        if (loot != null) {
            p.pickUp(loot);
            System.out.println("You loot " + loot.getName() + ".");
        }
    }

    public int calculateDamage() {
        return player.attack();
    }

    public boolean checkFlee() {
        return random.nextBoolean();
    }
}