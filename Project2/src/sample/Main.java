package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.DBConnection;


import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DBConnection testDB = new DBConnection();
        testDB.Connect();
      //  testDB.testRemoteDB("user2");

      /*  ArrayList testItemList;

        testItemList = testDB.itemList();

        System.out.println(testItemList);
        */
      /*  ArrayList testUserList;

        testUserList = testDB.userList();

        System.out.println(testUserList);
        */
      /*  ArrayList testHistoryList;

        testHistoryList = testDB.orderHistory(11);

        System.out.println(testHistoryList);
*/

        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("The Dragon's Den.exe");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
