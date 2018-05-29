package sample;

public class Item {

    private int itemID;
    private String itemName;
    private double price;
    private int stock;
    private String description;
    private String imageURL;

    public Item(int itemID, String itemName, double price, int stock, String description, String imageURL) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public boolean equals (Object obj) {
        boolean result = false;
        if (obj instanceof Item) {
            result = this.itemName.equals(((Item)obj).getItemName());

        }
        return result;
    }

    @Override
    public String toString() {
        return "Product ID: " + itemID + "\n" + itemName + "\nPrice: " + price;
    }
}

