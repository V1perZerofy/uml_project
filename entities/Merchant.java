class Merchant extends NPC {
  private List<Item> inventory;
  private String currncyName;

  public Merchant(){

  }
  
  public List<Item> getInventory() {
    return inventory;
  }

  public int getPrice(Item item) {
    price = item.getBasePrice();
    return price * 1.2;
  }
  
  public int restock(Item item, int quantity) {
    inventory.add(item);
    return quantity;
  }

  public String interact() {
    return "Welcome to my shop! Take a look at my wares.";
  }

  public void removeFromInventory(Item item) {
    inventory.remove(item);
  }

}
