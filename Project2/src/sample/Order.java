package sample;

import java.util.ArrayList;

public class Order {
    private int orderID;
    private  String status;
    private  String shippedDate;
    private  String comment;
    private  String orderDate;
    private String userName;
    private ArrayList<Item> itemList = new ArrayList<>();

    public Order(int orderID, String status, String shippedDate, String comment, String orderDate,String userName, ArrayList<Item> itemList) {
        this.orderID = orderID;
        this.status = status;
        this.shippedDate = shippedDate;
        this.comment = comment;
        this.orderDate = orderDate;
        this.itemList = itemList;
        this.userName = userName;
    }

    public int getOrderID() {
        return orderID;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
