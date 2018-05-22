package sample;

import java.sql.*;
import java.util.ArrayList;
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
            pst.setString(2, user.getFirstname());
            pst.setString(3, user.getLastname());
            pst.setString(4, user.getPassWord());
            pst.setString(5, user.getEmail());
            pst.setString(6, user.getAddress());
            pst.setString(7, user.getPhoneNumber());
            pst.setInt(8, user.getAge());

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
            default:
                break;
        }

        ArrayList <User> userList = new ArrayList<>();
        try {
            Connection conn = setConnection();
            assert conn != null;
            PreparedStatement pst = conn.prepareStatement(query);
            if (searchmethod == 2) {
                pst.setString(1,search);
            } else if (searchmethod == 3) {
                pst.setString(1,"%" + search + "%");
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
            if (searchmethod == 2) {
                pst.setString(1,search);
            } else if (searchmethod == 3) {
                pst.setString(1,"%" + search + "%");
            }
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Item item = new Item(result.getInt("iditem"),result.getString("itemsname"),result.getDouble("price"),result.getInt("stock"),result.getString("description"),result.getString("imageURL"));
                itemsList.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsList;
    }


    @Override
    public String toString() {
        return "I have no idea if this works";
    }
}
