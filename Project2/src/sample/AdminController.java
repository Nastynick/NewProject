package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Model.DBConnection;
import sample.Model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private ImageView logoImage;

    @FXML
    private Button returnButton;

    @FXML
    private TableView<User> user_table;

    @FXML
    private TableColumn<?, ?> firstColumnuser;

    @FXML
    private TableColumn<?, ?> secondColumnuser;

    @FXML
    private TableView<Item> item_table;

    @FXML
    private TableColumn<?, ?> firstColumnitem;

    @FXML
    private TableColumn<?, ?> secondColumnitem;

    @FXML
    private RadioButton usersRadioB;

    @FXML
    private ToggleGroup adminGroup;

    @FXML
    private RadioButton itemsRadioB;

    @FXML
    private TextField itemID_UsernameField;

    @FXML
    private Label itemID_usernameLabel;

    @FXML
    private TextField price_lastnameField;

    @FXML
    private Label itemname_firstnamelabel;

    @FXML
    private Label price_lastnameLabel;

    @FXML
    private TextField addressField;

    @FXML
    private Label stock_emailLabel;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Label ageLabel;

    @FXML
    private TextField stock_emailField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField itemname_firstnameField;

    @FXML
    private Label addressLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TextField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label statusLabel;

    private DBConnection dbc = new DBConnection();

    @FXML
    private void onSearchButtonPressed(ActionEvent event) throws Exception {
        if (usersRadioB.isSelected()) {
            updateUsers(searchField.getText());
        } else if (itemsRadioB.isSelected()) {
            updateItems(searchField.getText());
        }


    }

    @FXML
    private void onAddButtonPressed(ActionEvent event) throws Exception {
        if (itemsRadioB.isSelected()) {
            if (!itemID_UsernameField.getText().equals("")) {
                int i = 0;
                Item item = new Item (Integer.valueOf(itemID_UsernameField.getText()), itemname_firstnameField.getText(),Double.valueOf(price_lastnameField.getText()),Integer.valueOf(stock_emailField.getText()));
                for (Item it : dbc.itemList("")) {
                    if (it.getItemID()==(item.getItemID())) {
                        i = 1;
                    }
                }
                if (i == 1) {

                    statusLabel.setTextFill(Color.web("#eb0000"));
                    statusLabel.setText("ERROR: DUPLICATE ITEM: Check user input!");
                } else {
                    try {
                        dbc.insertItem(item);
                        statusLabel.setTextFill(Color.web("#000000"));
                        statusLabel.setText("Command successful!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
                statusLabel.setTextFill(Color.web("#eb0000"));
                statusLabel.setText("Error: First field empty!");
            }
            updateTables();

        } else if (usersRadioB.isSelected()) {

            if (!itemID_UsernameField.getText().equals("")){
                int i = 0;
                int age = Integer.valueOf(ageField.getText());
                User u = new User(itemID_UsernameField.getText(), passwordField.getText(), age, addressField.getText(), stock_emailField.getText(), itemname_firstnameField.getText(), price_lastnameField.getText(), genderField.getText(), phoneNumberField.getText());
                for (User s : dbc.userList("")) {
                    if (s.getUsername().equals(u.getUsername())) {
                        i = 1;
                    }
                }
                if (i == 1) {

                    statusLabel.setTextFill(Color.web("#eb0000"));
                    statusLabel.setText("ERROR: DUPLICATE USER: Check user input!");
                } else {
                    try {
                        dbc.insertUser(u);
                        statusLabel.setTextFill(Color.web("#000000"));
                        statusLabel.setText("Command successful!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
                statusLabel.setTextFill(Color.web("#eb0000"));
                statusLabel.setText("Error: First field empty!");
            }
            updateTables();
        }

    }

    @FXML
    private void onRemoveButtonPressed(ActionEvent event) {
        // db.removeUser(itemId_usernameField.GetText();

    }

    @FXML
    private void onReturnButtonPressed(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void onUpdateButtonpressed(ActionEvent event) throws Exception {
        if (itemsRadioB.isSelected()) {
            //Item item = new Item("")
            //dbc.updateItem(item)

        } else if (usersRadioB.isSelected()) {
            //User user = new User("")
            //dbc.updateUser(user)
        }
    }

    public void radioSelect(ActionEvent event) throws Exception {
        if (itemsRadioB.isSelected()) {
            selection("ItemID:", "Item name:", "Price:", "Stock:", false);

        } else if (usersRadioB.isSelected()) {
            selection("Username:", "First name:", "Last name:", "Email:", true);
        }
    }

    private void selection(String one, String two, String three, String four, boolean b) throws Exception {
        clearAll();
        itemID_usernameLabel.setText(one);
        itemname_firstnamelabel.setText(two);
        price_lastnameLabel.setText(three);
        stock_emailLabel.setText(four);
        ageField.setVisible(b);
        ageLabel.setVisible(b);
        addressField.setVisible(b);
        addressLabel.setVisible(b);
        phoneNumberField.setVisible(b);
        phoneNumberLabel.setVisible(b);
        genderField.setVisible(b);
        genderLabel.setVisible(b);
        passwordField.setVisible(b);
        passwordLabel.setVisible(b);
        user_table.setVisible(b);
        item_table.setVisible(!b);

    }

    private void firstTimeSetup() throws Exception {
        dbc.Connect();
        firstColumnitem.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        secondColumnitem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        firstColumnuser.setCellValueFactory(new PropertyValueFactory<>("username"));
        secondColumnuser.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        updateTables();

        user_table.setRowFactory((TableView<User> tv) -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    User rowData = row.getItem();
                    itemID_UsernameField.setText(rowData.getUsername());
                    itemname_firstnameField.setText(rowData.getFirstName());
                    price_lastnameField.setText(rowData.getSurName());
                    stock_emailField.setText(rowData.getEmail());
                    ageField.setText(String.valueOf(rowData.getAge()));
                    addressField.setText(rowData.getAddress());
                    phoneNumberField.setText(rowData.getPhoneNumber());
                    genderField.setText(rowData.getGender());
                    passwordField.setText(rowData.getPassword());
                }
            });
            return row;
        });

        item_table.setRowFactory((TableView<Item> tv) -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Item rowData = row.getItem();
                    itemID_UsernameField.setText(String.valueOf(rowData.getItemID()));
                    itemname_firstnameField.setText(rowData.getItemName());
                    price_lastnameField.setText(String.valueOf(rowData.getPrice()));
                    stock_emailField.setText(String.valueOf(rowData.getStock()));

                }
            });
            return row;
        });
    }

    private void clearAll() throws Exception {
        itemID_UsernameField.setText("");
        itemname_firstnameField.setText("");
        price_lastnameField.setText("");
        stock_emailField.setText("");
        ageField.setText("");
        addressField.setText("");
        phoneNumberField.setText("");
        genderField.setText("");
    }

    private void updateTables() throws Exception {
        updateItems("");
        updateUsers("");
    }

    private void updateItems (String search) throws Exception {
        ObservableList<Item> itemData = FXCollections.observableArrayList(dbc.itemList(search)
        );
        item_table.setItems(itemData);
    }
    private void updateUsers (String search) throws Exception {
        ObservableList<User> userData = FXCollections.observableArrayList(dbc.userList(search));
        user_table.setItems(userData);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            selection("ItemID:", "Item name:", "Price:", "Stock:", false);
            firstTimeSetup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
