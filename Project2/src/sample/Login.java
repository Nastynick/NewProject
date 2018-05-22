package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login {

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label statusLabel;



    @FXML
    private void onLoginButtonPressed(ActionEvent event) throws Exception {
        login(event);
    }

    @FXML
    private void onRegisterButtonPressed(ActionEvent event) throws Exception {
        changeScene("registrationPage.fxml",event);
    }


    private void login (ActionEvent event) throws Exception {
        boolean flag = true;
/*        dbConnection = new DBConnection();
        flag = dbConnection.login(userNameField.getText(),passwordField.getText());*/

        // System.out.println("flag: "+flag);

        if (flag) {  //Demo code, will not be present in the final product and will be handled by DBconnection instead, along with any other database related stuff.
            changeScene("mainMenu.fxml",event);
        } else {
            statusLabel.setTextFill(Color.web("#eb0000"));
            statusLabel.setText("Invalid username/password");
        }
    }

    public void setData(String registerMsg) throws Exception {
        statusLabel.setTextFill(Color.web("#000000"));
        statusLabel.setText(registerMsg);
    }
    private void changeScene (String newScene, ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
