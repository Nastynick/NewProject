package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

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
    private Button forgottenUsernameButton;

    @FXML
    private Button forgottenPasswordButton;

    private DBSingleton dbc = new DBSingleton();



    @FXML
    private void onLoginButtonPressed(ActionEvent event) throws Exception {
        login(event);
    }

    @FXML
    private void onRegisterButtonPressed(ActionEvent event) throws Exception {
        changeScene("registrationPage.fxml",event);
    }

    @FXML
    private void onEnterKeyPressedLogin(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            login(event);
        }
    }



    private void login (Event event) throws Exception {
        if (!userNameField.getText().equals("") && !passwordField.getText().equals("")) {
            if (dbc.login(userNameField.getText(),passwordField.getText())) {
                UserSingleton.getInstance().setAdmin(dbc.adminCheck(userNameField.getText()));
                UserSingleton.getInstance().setUsername(userNameField.getText());
                System.out.println(UserSingleton.getInstance().getUsername());
                System.out.println(UserSingleton.getInstance().isAdmin());
                changeScene("mainMenu.fxml",event);
            } else {
                statusLabel.setTextFill(Color.web("#eb0000"));
                statusLabel.setText("Invalid username/password");
            }
        } else {
            statusLabel.setTextFill(Color.web("#eb0000"));
            statusLabel.setText("Fields empty!");
        }

    }

    public void setData(String registerMsg) throws Exception {
        statusLabel.setTextFill(Color.web("#000000"));
        statusLabel.setText(registerMsg);
    }
    private void changeScene (String newScene, Event event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void onForgottenPasswordPressed(ActionEvent event) throws Exception {
        recoveryChangeScene(event, 2);
    }

    @FXML
    private void onForgottenUsernamePressed(ActionEvent event) throws Exception {
        recoveryChangeScene(event,1);
    }

    private void recoveryChangeScene (ActionEvent event, int selection) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("recoveryPage.fxml"));
        Parent root = loader.load();
        RecoveryController rc = loader.getController();
        rc.setData(selection);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
