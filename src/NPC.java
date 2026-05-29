package dungeonadventure;

public abstract class NPC extends Entity {

    protected String dialogText;
    protected boolean isHostile;

    public NPC(String name, int maxHealth, String dialogText, boolean isHostile) {
        super(name, maxHealth);
        this.dialogText = dialogText;
        this.isHostile = isHostile;
    }

    public abstract String interact();
}
