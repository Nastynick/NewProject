package sample;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBSingleton {
    private static String db_url;
    private static String db_port;
    private static String db_name;
    private static String db_user;
    private static String db_password;
    private static Statement connection;

    DBSingleton() {
        db_url = "jdbc:mysql://46.101.143.158";
        db_port = "3306";
        db_name = "Shop";
        db_user = "carl";
        db_password = "dragoncave";
        connection = setConnection();
    }

    private static Statement setConnection() {
        try {
            String url = "" + db_url + ":" + db_port + "/" + db_name + "";
            java.sql.Connection conn = DriverManager.getConnection(url, db_user, db_password);

            java.sql.Statement state = conn.createStatement();
            return (Statement) state;
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

    public static Statement getStatement() {
        return connection;
    }

    @Override
    public String toString() {
        return "I have no idea if this works";
    }
}
