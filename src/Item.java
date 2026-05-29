package dungeonadventure;

public abstract class Item {

    protected String name;
    protected String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void use(Entity target);

    public String getName() {
        return name;
    }
}
