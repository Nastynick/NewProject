package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class RegisterController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField surNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registerRegisterButton;

    @FXML
    private Button registerCancel;

    @FXML
    private TextField ageField;

    @FXML
    private Label registrationStatusLabel;

    @FXML
    private TextField emailField;

    private String error = "";
    private DBSingleton dbc = new DBSingleton();

    @FXML
    private void onRegisterCancelButtonPressed(ActionEvent event) throws Exception {
        changeScene("login.fxml", event);

    }

    @FXML
    private void onRegisterRegisterButtonPressed(ActionEvent event) throws Exception {
        register(event);
        }


    private void register(ActionEvent event) throws Exception { // registers the user
        if (errorCheck()) {
            try {
                User user = new User(usernameField.getText(),passwordField.getText(),addressField.getText(),emailField.getText(),firstNameField.getText(),surNameField.getText(),Integer.valueOf(ageField.getText()),phoneNumberField.getText());
                dbc.insertUser(user);
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();
                Login log = loader.getController();
                log.setData("Registration Successful!");
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
                registrationStatusLabel.setTextFill(Color.web("#eb0000"));
                registrationStatusLabel.setText(error);
                error = "";
            }
        } else {
            registrationStatusLabel.setTextFill(Color.web("#eb0000"));
            registrationStatusLabel.setText(error);
            error = "";
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeScene (String newScene, ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }


    private boolean errorCheck () throws SQLException { // method for errorhandling
        int usernamecheck = 0;


            if (dbc.registerCheck(usernameField.getText(),emailField.getText()) && !usernameField.getText().equals("") && !emailField.getText().equals("")) {
                usernamecheck = 1;
            }
            if (ageField.getText().equals("")) {
                ageField.setText("0");
            }
            if (usernamecheck == 0) {
                error += "Error: " + "Username/Email already exists!";
            }
            if (passwordField.getLength() < 3) {
                error += "\nError: " + "Password is too short!";
            }
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                error += "\nError: " + "Passwords do not match!";
            }
            if (usernameField.getLength() == 0 || firstNameField.getLength() == 0 || surNameField.getLength() == 0 || addressField.getLength() == 0 || emailField.getLength() == 0 || phoneNumberField.getLength() == 0 || ageField.getLength() == 0) {
                error += "\nError: " + "All fields must be filled in!";
            }
            if (Integer.valueOf(ageField.getText()) < 13) {
                error += "\nError: " + "Minimum age is 13 to register!";
            }
            return passwordField.getLength() >= 3 && passwordField.getText().equals(confirmPasswordField.getText()) && usernameField.getLength() > 0 && usernamecheck != 0 && firstNameField.getLength() > 0 && surNameField.getLength() > 0 && addressField.getLength() > 0 && emailField.getLength() > 0 && phoneNumberField.getLength() > 0 && ageField.getLength() > 0 && Integer.valueOf(ageField.getText()) >= 13;
    }
    private void startUp() throws Exception {
        //dbc.Connect();
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();

            if (newText.matches("-?([0-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        ageField.setTextFormatter(
                new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));

            }
}

