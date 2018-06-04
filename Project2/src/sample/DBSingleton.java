package sample;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBSingleton {
    private static String db_url;
    private static String db_port;
    private static String db_name;
    private static String db_user;
    private static String db_password;

    DBSingleton() {
        db_url = "jdbc:mysql://den1.mysql6.gear.host";
        db_port = "3306";
        db_name = "dragoncave";
        db_user = "dragoncave";
        db_password = "Xt7q~d!0cPTi";
        //connection = setConnection();
    }

    private static Connection setConnection() {
        try {
            String url = "" + db_url + ":" + db_port + "/" + db_name + "";

            //java.sql.Statement state = conn.createStatement();
            return DriverManager.getConnection(url, db_user, db_password);
        } catch (SQLException ex) {
            Logger.getLogger(DBSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static class DBSingletonManagerHolder {
        private final static DBSingleton instance = new DBSingleton();
    }

    public static DBSingleton getInstance() {
        try {
            return DBSingletonManagerHolder.instance;
        } catch (ExceptionInInitializerError ex) {
            ex.printStackTrace();
        }
        return null;
    }

    void insertItem(Item item) {
        String query = "INSERT INTO item" +"\n"+ "VALUES(?,?,?,?,?,?,?)";
        try {
            Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, item.getItemID());
            pst.setString(2,item.getItemName());
            pst.setDouble(3, item.getPrice());
            pst.setInt(4,item.getStock());
            pst.setString(5,item.getDescription());
            pst.setString(6, item.getImageURL());
            pst.setInt(7, 1);

            pst.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void insertUser(User user) {
        String query = "INSERT INTO user" + "\n" + "VALUES (?,?,?,?,?,?,?,?);";
        try {
            Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, user.getUserName());
            pst.setString(3, user.getFirstname());
            pst.setString(4, user.getLastname());
            pst.setString(2, user.getPassWord());
            pst.setString(8, user.getEmail());
            pst.setString(7, user.getAddress());
            pst.setString(6, user.getPhoneNumber());
            pst.setInt(5, user.getAge());

            pst.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    void alterItem (Item it, int operationMethod) {
        String query = null;
        switch (operationMethod) {
            case 1:
                query = "DELETE FROM item WHERE iditem = ?";
                break;
            case 2:
                query = "UPDATE item SET itemsname = ?, price = ?, stock = ?, description = ?, imageURL = ?, manufacturer_idmanufacturer = ? WHERE iditem = ?";
        }
        try {
            Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            if (operationMethod == 1) {
                pst.setInt(1,it.getItemID());
            } else if (operationMethod == 2) {
                pst.setString(1,it.getItemName());
                pst.setDouble(2,it.getPrice());
                pst.setInt(3,it.getStock());
                pst.setString(4,it.getDescription());
                pst.setString(5,it.getImageURL());
                pst.setInt(6,1);
                pst.setInt(7,it.getItemID());
            }
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void alterUser (User u, int operationMethod) {
        String query = null;
        switch (operationMethod) {
            case 1:
                query = "DELETE FROM user WHERE username = ?";
                break;
            case 2:
                query = "UPDATE user SET password = ?, firstname = ?, lastname = ?, age = ?, phonenumber = ?, address = ?, email = ? WHERE username = ?";
        }
        try {
            Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            if (operationMethod == 1) {
                pst.setString(1,u.getUserName());
            } else if (operationMethod == 2) {
                pst.setString(1,u.getPassWord());
                pst.setString(2,u.getFirstname());
                pst.setString(3,u.getLastname());
                pst.setInt(4,u.getAge());
                pst.setString(5,u.getPhoneNumber());
                pst.setString(6,u.getAddress());
                pst.setString(7,u.getEmail());
                pst.setString(8,u.getUserName());
            }
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ArrayList<User> getUserList(String search, int searchmethod) {
            String query = null;
        switch (searchmethod) {
            case 1:
                query = "SELECT * FROM user";
                break;
            case 2:
                query = "SELECT * FROM user WHERE username LIKE ?";
                break;
            case 3:
                query = "SELECT * FROM user WHERE username = ?";
                break;
            case 4:
                query = "SELECT * FROM user WHERE email = ?";
                break;
            default:
                break;
        }

        ArrayList <User> userList = new ArrayList<>();
        try {
            Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            if (searchmethod == 2) {
                pst.setString(1,"%"+ search + "%");
            } else if (searchmethod == 3 || searchmethod == 4) {
                pst.setString(1,search);
            }
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                User user = new User(result.getString("username"),result.getString("password"),
                        result.getString("address"), result.getString("email"),
                        result.getString("firstname"), result.getString("lastname"),
                        result.getInt("age"), result.getString("phoneNumber"));
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    ArrayList<Item> getItemList(String search, int searchmethod) {
        String query = null;
        switch (searchmethod) {
            case 1:
                query = "SELECT * FROM item";
                break;
            case 2:
                query = "SELECT * FROM item WHERE itemsname LIKE ?";
                break;
            case 3:
                query = "SELECT * FROM item WHERE itemsname = ?";
                break;
            default:
                break;
        }

        ArrayList <Item> itemsList = new ArrayList<>();
        try {
            Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            if (searchmethod == 3) {
                pst.setString(1,search);
            } else if (searchmethod == 2) {
                pst.setString(1,"%" + search + "%");
            }
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Item item = new Item(result.getInt("iditem"),result.getString("itemsname"),
                        result.getDouble("price"),result.getInt("stock"),
                        result.getString("description"),result.getString("imageURL"));
                itemsList.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsList;
    }

    public boolean login (String username, String password) throws SQLException {
        String password_from_DB = null;
        Connection conn = setConnection();
        assert conn != null;
        String query = "SELECT password FROM user WHERE username = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, username);
        ResultSet result = pst.executeQuery();
        while (result.next()) {
            password_from_DB = result.getString("password");
        }
        return password_from_DB != null && password_from_DB.equals(password);    }

         boolean adminCheck (String username) throws SQLException {
            String admin = null;
            Connection conn = setConnection();
            assert conn != null;
            String query = "SELECT adminID FROM admin WHERE user_username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet result = pst.executeQuery();
            while (result.next()){
                admin = result.getString("adminID");
            }
            return !(admin == null);
        }

         void insertOrder (Order o) {
            String query = "INSERT INTO dragoncave.order" + "\n" + "VALUES (?,?,?,?,?,?);";
            try {

                Connection conn = setConnection();
                assert conn != null;
                conn.setAutoCommit(false);
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, o.getOrderID());
                pst.setString(2, o.getStatus());
                pst.setString(3, o.getOrderDate());
                pst.setString(4, o.getComment());
                pst.setString(5, UserSingleton.getInstance().getUsername());
                pst.setString(6, o.getShippedDate());
                pst.execute();

                query = "INSERT INTO order_has_item" +"\n" + "VALUES (?,?,?)";
                pst = conn.prepareStatement(query);
                ArrayList<Item> test = o.getItemList();
                Set<Item> uniqueSet = new HashSet<>();
                List<Item> dupesList = new ArrayList<>();

                for (Item a : test) {
                    if (uniqueSet.contains(a)) {
                        dupesList.add(a);
                    }else {
                        uniqueSet.add(a);
                    }
                }

                for (Item b : uniqueSet) {
                    pst.setInt(1,o.getOrderID());
                    pst.setInt(2,b.getItemID());
                    pst.setInt(3,1);
                    pst.addBatch();
                }
                pst.executeBatch();
                conn.commit();

                conn.setAutoCommit(true);
                query = "UPDATE order_has_item SET quantity = ? WHERE item_iditem = ? AND order_idorder = ?";
                pst = conn.prepareStatement(query);
                for (Item c : dupesList) {

                pst.setInt(1,searchOrder(o.getOrderID(),c.getItemID())+1);
                pst.setInt(2,c.getItemID());
                pst.setInt(3,o.getOrderID());
                pst.execute();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        private int searchOrder (int orderID, int itemID) throws SQLException {
        String query = "SELECT quantity FROM order_has_item WHERE order_idorder = ? AND item_iditem = ?";
        int amount = 0;
        Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,orderID);
            pst.setInt(2,itemID);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                amount = result.getInt("quantity");
            }
            return amount;
        }

    ArrayList<Order> getOrder (String username) throws SQLException {
            ArrayList<Order> orderList = new ArrayList<>();
            ArrayList<Item> itemlist = new ArrayList<>();

            String query = "SELECT * FROM dragoncave.order WHERE user_username = ?";
            Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1,username);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Order o = new Order(result.getInt("idorder"),result.getString("status"),result.getString("shippeddate"),result.getString("comment"),result.getString("orderdate"),result.getString("user_username"),itemlist);
                orderList.add(o);
            }
        return orderList;
    }

   ArrayList <Item> getitemListForOrders(int orderid) throws SQLException {
        ArrayList<Item> itemlist = new ArrayList<>();
        String query = "SELECT * FROM order_has_item JOIN item ON item_iditem = iditem WHERE order_idorder = ?";
        Connection conn = setConnection();
        assert conn != null;
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setInt(1,orderid);
        ResultSet result = pst.executeQuery();
        while (result.next()) {
            int quantity = result.getInt("quantity");
            Item a = new Item(result.getInt("iditem"),result.getString("itemsname"),result.getDouble("price"),result.getInt("stock"),result.getString("description"),result.getString("imageURL"));
            itemlist.add(a);
            for (int i = 1; i < quantity; i++) {
                itemlist.add(a);
            }
        }
        return itemlist;
    }

    ArrayList<Order> getOrderforAdmin () throws SQLException {
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<Item> itemlist = new ArrayList<>();

        String query = "SELECT * FROM dragoncave.order ";
        Connection conn = setConnection();
        assert conn != null;
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet result = pst.executeQuery();
        while (result.next()) {
            Order o = new Order(result.getInt("idorder"),result.getString("status"),result.getString("shippeddate"),result.getString("comment"),result.getString("orderdate"),result.getString("user_username"),itemlist);
            orderList.add(o);
        }
        return orderList;
    }

    void updateOrderStatus (String status, int orderID) throws SQLException {
        Connection conn = setConnection();
        String shippeddate;
        if (status.equals("Shipped")) {
            shippeddate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        } else {
            shippeddate = "N/A";
        }

        String query = "UPDATE dragoncave.order SET status = ?, shippeddate = ? WHERE idorder = ?";
        assert conn != null;
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, status);
        pst.setString(2,shippeddate);
        pst.setInt(3,orderID);
        pst.execute();
    }

    Admin getAdmin (String username) throws SQLException {
        Connection conn = setConnection();
        Admin admin = null;
        String query = "SELECT * from user JOIN admin ON username = user_username WHERE username = ?";
        assert conn != null;
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, username);
        ResultSet result = pst.executeQuery();
        while (result.next()) {
             admin = new Admin (result.getString("username"),result.getString("password"),
                    result.getString("address"), result.getString("email"),
                    result.getString("firstname"), result.getString("lastname"),
                    result.getInt("age"), result.getString("phoneNumber"),String.valueOf(result.getInt("adminID")));
        }
        return admin;
    }

    boolean registerCheck (String username, String email) throws SQLException {
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        boolean check = true;
        Connection conn = setConnection();
        String query = "SELECT username, email FROM user";
        assert conn != null;
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet result = pst.executeQuery();
        while (result.next()) {
            usernames.add(result.getString("username"));
            emailList.add( result.getString("email"));
        }
        for (String userN : usernames) {
            if (userN.equals(username)) {
                check = false;
            }
        }
        for (String useremail : emailList){
            if (useremail.equals(email)) {
                check = false;
            }
        }
        return check;
    }

    public ArrayList<Order> getAdminOrderSearch (String search, String searchmethod) throws SQLException {
        ArrayList<Order> orderlist = new ArrayList<>();
        ArrayList<Item> itemlist = new ArrayList<>();
        String query = null;

        switch (searchmethod) {
            case "OrderID":
                query = "SELECT * from dragoncave.order WHERE idorder LIKE ?";
                break;
            case "Status":
                query = "SELECT * from dragoncave.order WHERE status LIKE ?";
                break;
            case "Order date":
                query = "SELECT * from dragoncave.order WHERE orderdate LIKE ?";
                break;
            case "Username":
                query = "SELECT * from dragoncave.order WHERE user_username LIKE ?";
                break;

        }
        Connection conn = setConnection();
        assert conn != null;
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1,"%" + search + "%");
        ResultSet result = pst.executeQuery();
        while (result.next())  {
            Order o = new Order(result.getInt("idorder"),result.getString("status"),result.getString("shippeddate"),result.getString("comment"),result.getString("orderdate"),result.getString("user_username"),itemlist);
            orderlist.add(o);
        }
        return orderlist;
    }


    @Override
    public String toString() {
        return "I have no idea if this works";
    }
}
