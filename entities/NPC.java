public abstract class NPC extends Entity {
    protected String dialogueText;
    protected String isHostile;

    public NPC(String name, int health) {
        super(name, health);
    }

    public abstract String interact();

}
