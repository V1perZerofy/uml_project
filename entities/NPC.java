public abstract class NPC extends Entity {
    protected String dialogueText;
    protected boolean isHostile;

    public NPC(String name, int health) {
        super(name, health);
    }

    public abstract String interact();

    public String getDialogueText() { return dialogueText; }
    public void setDialogueText(String dialogueText) { this.dialogueText = dialogueText; }
    public boolean isHostile() { return isHostile; }
    public void setHostile(boolean isHostile) { this.isHostile = Boolean.toString(isHostile); }
}
