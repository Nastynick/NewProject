package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.DBConnection;
import sample.Model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountDetailsController {

    private TestingClass tc = new TestingClass();

    private Alert error = new Alert(Alert.AlertType.ERROR);
    private Alert inform = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label updateLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField genderChoiceBox;

    @FXML
    private TextField ageField;

    @FXML
    private TextField employeeIDField;

    @FXML
    private Label salaryLabel;

    @FXML
    private TextField adminIDField;


    private ArrayList<Item> temp = new ArrayList<>();
    private DBConnection dbc = new DBConnection();
    private ArrayList<User> localUserList = new ArrayList<>();

    @FXML
    private void cancelButtonPressed(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        MainMenu MM = loader.getController();
        MM.setData(temp);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        }

    @FXML
    private void updateButtonPressed(ActionEvent event) {

        if (oldPasswordField.getText().equals(newPasswordField.getText())) {
            inform.setTitle("Account information");
            inform.setHeaderText("Account information update");
            inform.setContentText("Account information has been updated! Have a nice day!");
            inform.showAndWait();

        } else {
            error.setTitle("Update error!");
            error.setHeaderText("Password mismatch");
            error.setContentText("Failed to update account information! Please make sure so your old password and new password match if you want to change password and user information");
            error.showAndWait();
        }

    }
    public void setData (ArrayList<Item> list) {
        dbc.Connect();
        temp = list;
        for (User e : dbc.userList("")) {
            System.out.println(e.getUsername());
            firstNameField.setText(e.getFirstName());
            lastNameField.setText(e.getSurName());
            ageField.setText(String.valueOf(e.getAge()));
            phoneNumberField.setText(e.getPassword());
            addressField.setText(e.getAddress());
            emailField.setText(e.getEmail());
            genderChoiceBox.setText(e.getGender());
            oldPasswordField.setText(e.getPassword());
        }


    }

}
