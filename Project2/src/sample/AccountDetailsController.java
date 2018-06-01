package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;


public class AccountDetailsController {


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
    private TextField userNameField;


    @FXML
    private TextField ageField;

    @FXML
    private TextField adminIDField;

    private DBSingleton dbc = new DBSingleton();
    private ArrayList<Item> temp = new ArrayList<>();
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

            User user = new User(userNameField.getText(),  newPasswordField.getText(), addressField.getText(),emailField.getText(), firstNameField.getText(), lastNameField.getText(), Integer.valueOf(ageField.getText()), phoneNumberField.getText());
            dbc.alterUser(user, 2);

            inform.showAndWait();



        } else {
            error.setTitle("Update error!");
            error.setHeaderText("Password mismatch");
            error.setContentText("Failed to update account information! Please make sure so you confirm passwords if you want to change and user information");
            error.showAndWait();
        }

    }

    public void setData(ArrayList<Item> list) {
        temp = list;
        adminIDField.setVisible(false);

        if (UserSingleton.getInstance().isAdmin()) {
            try {
                adminIDField.setVisible(true);
                Admin a = dbc.getAdmin(UserSingleton.getInstance().getUsername());
                userNameField.setText(a.getUserName());
                firstNameField.setText(a.getFirstname());
                lastNameField.setText(a.getLastname());
                ageField.setText(String.valueOf(a.getAge()));
                phoneNumberField.setText(a.getPhoneNumber());
                addressField.setText(a.getAddress());
                emailField.setText(a.getEmail());
                oldPasswordField.setText(a.getPassWord());
                adminIDField.setText(a.getAdminID());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (!UserSingleton.getInstance().isAdmin()) {
            ArrayList<User> ul = dbc.getUserList(UserSingleton.getInstance().getUsername(), 3);
            User e = ul.get(0);
            userNameField.setText(e.getUserName());
            firstNameField.setText(e.getFirstname());
            lastNameField.setText(e.getLastname());
            ageField.setText(String.valueOf(e.getAge()));
            phoneNumberField.setText(e.getPhoneNumber());
            addressField.setText(e.getAddress());
            emailField.setText(e.getEmail());
            oldPasswordField.setText(e.getPassWord());
        }



    }
}
