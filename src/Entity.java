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

    //getter and setter methods
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
<<<<<<< HEAD:entities/Entity.java
    
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    

    public boolean isAlive() {
        return health > 0;
    }
=======
>>>>>>> origin/feature/Entities:src/Entity.java
}
