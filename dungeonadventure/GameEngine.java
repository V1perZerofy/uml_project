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
    private final ConsoleUI ui = new ConsoleUI();

    public GameEngine(Player player, Room startRoom, List<Room> rooms) {
        this.player = player;
        this.currentRoom = startRoom;
        this.rooms = rooms;
    }

    public void start() {
        running = true;
        ui.message("You enter the dungeon.");
        ui.render(currentRoom, player);
        while (running && player.isAlive()) {
            ui.prompt();
            String cmd = scanner.nextLine();
            handleInput(cmd);
            if (running && player.isAlive()) {
                ui.render(currentRoom, player);
            }
        }
        if (!player.isAlive()) {
            ui.message("You have been defeated. Game over.");
        }
        ui.render(currentRoom, player);
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
                ui.message("There is nothing to fight here.");
            }
        } else if (input.equals("take")) {
            if (currentRoom.hasItem()) {
                Item item = currentRoom.getItem();
                player.pickUp(item);
                currentRoom.removeItem();
                ui.message("You picked up " + item.getName() + ".");
            } else {
                ui.message("There is nothing to take.");
            }
        } else if (input.equals("talk")) {
            if (currentRoom.hasMerchant()) {
                Merchant merchant = currentRoom.getMerchant();
                ui.message(merchant.interact());
                if (merchant.getInventory().isEmpty()) {
                    ui.message("The merchant has nothing for sale.");
                } else {
                    ui.message("For sale:");
                    for (Item item : merchant.getInventory()) {
                        ui.message("  " + item.getName() + " - " + merchant.getPrice(item) + " gold");
                    }
                }
            } else {
                ui.message("There is no one to talk to.");
            }
        } else if (input.equals("inventory") || input.equals("inv")) {
            if (player.getInventory().isEmpty()) {
                ui.message("Your inventory is empty.");
            } else {
                ui.message("You are carrying (" + player.getGold() + " gold):");
                for (Item item : player.getInventory()) {
                    ui.message("  " + item.getName());
                }
            }
        } else if (input.startsWith("use ")) {
            String name = input.substring(4).trim();
            Item item = findByName(player.getInventory(), name);
            if (item == null) {
                ui.message("You do not have that.");
            } else {
                player.useItem(item);
                ui.message("You used " + item.getName() + ".");
            }
        } else if (input.startsWith("buy ")) {
            if (!currentRoom.hasMerchant()) {
                ui.message("There is no merchant here.");
            } else {
                Merchant merchant = currentRoom.getMerchant();
                String name = input.substring(4).trim();
                Item item = findByName(merchant.getInventory(), name);
                if (item == null) {
                    ui.message("The merchant does not sell that.");
                } else if (merchant.trade(player, item)) {
                    ui.message("You bought " + item.getName() + ".");
                } else {
                    ui.message("You cannot afford that.");
                }
            }
        } else if (input.equals("look")) {
            ui.message("You look around.");
        } else {
            ui.message("Unknown command.");
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
            ui.message("You cannot go that way.");
            return;
        }
        if (currentRoom.hasMerchant()) {
            currentRoom.getMerchant().restock();
        }
        if (currentRoom.hasMonster() && !currentRoom.getMonster().isAlive()) {
            Monster m = currentRoom.getMonster();
            m.heal(m.getMaxHealth());
        }
        currentRoom = next;
        ui.message("You go " + direction + ".");
    }

    public void resolveCombat(Player p, Monster m) {
        while (p.isAlive() && m.isAlive()) {
            int playerDmg = p.attack();
            m.defend(playerDmg);
            ui.message(p.getName() + " hits " + m.getName() + " for " + playerDmg + ".");
            ui.render(currentRoom, p);
            pause();
            if (!m.isAlive()) {
                break;
            }
            int monsterDmg = m.attack();
            p.defend(monsterDmg);
            ui.message(m.getName() + " hits " + p.getName() + " for " + monsterDmg + ".");
            ui.render(currentRoom, p);
            pause();
            if (p.isAlive() && p.getHealth() < 50 && p.hasHealingPotion()) {
                HealingPotion potion = findPotion(p);
                if (potion != null) {
                    p.useItem(potion);
                    ui.message(p.getName() + " drinks " + potion.getName() + " and recovers.");
                    ui.render(currentRoom, p);
                    pause();
                }
            }
        }
        if (!p.isAlive()) {
            running = false;
            return;
        }
        ui.message("You have defeated " + m.getName() + "!");
        Item loot = m.dropLoot();
        if (loot != null) {
            p.pickUp(loot);
            ui.message("You loot " + loot.getName() + ".");
        }
        ui.render(currentRoom, p);
    }

    private HealingPotion findPotion(Player p) {
        for (Item item : p.getInventory()) {
            if (item instanceof HealingPotion) {
                return (HealingPotion) item;
            }
        }
        return null;
    }

    private void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int calculateDamage() {
        return player.attack();
    }

    public boolean checkFlee() {
        return random.nextBoolean();
    }
}