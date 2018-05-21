package sample;

public class Item {

    private int itemID;
    private String itemName;
    private double price;
    private int stock;

    public Item(int itemID, String itemName, double price, int stock) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
    }



    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product ID: " + itemID + "\n" + itemName + "\nPrice: " + price;
    }
}

