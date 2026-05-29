package dungeonadventure;

public abstract class Entity {

    protected String name;
    protected int health;
    protected int maxHealth;

    public Entity(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
