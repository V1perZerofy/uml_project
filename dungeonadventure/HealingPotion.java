package dungeonadventure;

public class HealingPotion extends Item {

    private int healAmount;

    public HealingPotion(String name, String description, int healAmount) {
        super(name, description);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Entity target) {
        target.heal(healAmount);
    }

    public int getHealAmount() {
        return healAmount;
    }
}
