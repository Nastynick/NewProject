package sample;

import sample.Model.User;

import java.security.SecureRandom;
import java.util.ArrayList;

public class TestingClass {


    public TestingClass() {
    }

    public ArrayList<User> getUsers() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        User user = new User ("Sazzhole","123",12,"Lund","ivan@gmail.com","Ivan","Marsipan","Other","98765432");
        User user2 = new User ("Nastynick","123",13,"Kristianstad","Niclas@gmail.com","Niclas","Everlönn","Female","123456789");
        User user3 = new User ("Croft","123",5,"Lund","croft@gmail.com","Carl","Greinsmark","Male","00087655");
        users.add(user);
        users.add(user2);
        users.add(user3);
        return users;
    }

    public ArrayList<Item> getItems() throws Exception {
        ArrayList<Item> items = new ArrayList<>();
        Item item1 = new Item (1, "Ticket to Ride: Europe", 19.99, 23);
        Item item2 = new Item (2, "Red Dragon Inn 5", 49.99, 5);
        Item item3 = new Item (3, "Settlers of Catan", 24.99, 17);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        return items;
    }
    public ArrayList<Item> someItems() throws Exception {
        ArrayList<Item> items = new ArrayList<>();
        Item item2 = new Item (5, "Tokaido", 27.99, 1);
        items.add(item2);
        return items;
    }

    public ArrayList<Item> moreItems() throws Exception {
        ArrayList<Item> items = new ArrayList<>();
        Item item1 = new Item(6, "Call of Cthulu", 9.99, 1);
        Item item2 = new Item(1, "Ticket to Ride: Europe", 19.99, 1);
        Item item3 = new Item(7, "WoW TCG booster", 3.50, 5);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        return items;
    }


    public ArrayList<Order> oldOrder() throws Exception {
        ArrayList<Order> olderOrder = new ArrayList<>();
        Order order1 = new Order(1, "Shipped", "2018-05-14", "", "2018-05-11", "Placeholder", moreItems());
        Order order2 = new Order(2, "Pending", "N/A", "", "2018-05-11", "PlaceHolder", moreItems());
        Order order3 = new Order(3, "Shipped", "2018-05-11", "Priority shipping", "Sometime this afternoon eyy", "Placeholder", moreItems());
        olderOrder.add(order1);
        olderOrder.add(order2);
        olderOrder.add(order3);
        return olderOrder;
    }

    public User testingAccountInformation () {
        User aUser = new User("Demo", "Demo", 22, "Kämpargränden 12A", "123@Niclas.com","Adolf",
                "Gustavsson", "Male", "123456789");
        return aUser;

    }

}
