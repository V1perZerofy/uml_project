//Java Class Entity

public abstract class Entity {
    protected String name;
    protected int health;
    protected int maxHealth;

    public Entity(String name, int health, int maxHealth) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    //getter and setter methods
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
    
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
}
