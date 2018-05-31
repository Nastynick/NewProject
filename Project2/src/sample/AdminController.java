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
import java.net.URL;
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
    private TextField address_ImageURL_Field;

    @FXML
    private Label stock_emailLabel;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Label age_desc_Label;

    @FXML
    private TextField stock_emailField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField itemname_firstnameField;

    @FXML
    private Label address_ImageURLLabel;

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

    @FXML
    private TextArea productdescField;

    private DBSingleton dbc = new DBSingleton();


    @FXML
    private void onSearchButtonPressed(ActionEvent event) throws Exception {
        if (usersRadioB.isSelected()) {
            updateUsers(searchField.getText(),2);
        } else if (itemsRadioB.isSelected()) {
            updateItems(searchField.getText(),2);
        }


    }

    @FXML
    private void onAddButtonPressed(ActionEvent event) throws Exception {
        if (itemsRadioB.isSelected()) {
            if (!itemID_UsernameField.getText().equals("")) {
                int i = 0;
                Item item = new Item (Integer.valueOf(itemID_UsernameField.getText()), itemname_firstnameField.getText(),Double.valueOf(price_lastnameField.getText()),Integer.valueOf(stock_emailField.getText()),productdescField.getText(),address_ImageURL_Field.getText());
                for (Item it : dbc.getItemList("",1)) {
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
                User u = new User(itemID_UsernameField.getText(),passwordField.getText(), address_ImageURL_Field.getText(),stock_emailField.getText(),itemname_firstnameField.getText(),price_lastnameField.getText(),Integer.valueOf(ageField.getText()),phoneNumberField.getText());
                for (User s : dbc.getUserList("",1)) {
                   if (s.getUserName().equals(u.getUserName())) {
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
            resetFields();
        }

    }

    @FXML
    private void onRemoveButtonPressed(ActionEvent event) throws Exception {
        if (!itemID_UsernameField.getText().equals("")) {
            if (itemsRadioB.isSelected()) {
                Item item = new Item(Integer.valueOf(itemID_UsernameField.getText()), itemname_firstnameField.getText(), Double.valueOf(price_lastnameField.getText()), Integer.valueOf(stock_emailField.getText()), "Test", address_ImageURL_Field.getText());
                dbc.alterItem(item, 1);
            } else if (usersRadioB.isSelected()) {
                User u = new User(itemID_UsernameField.getText(),passwordField.getText(), address_ImageURL_Field.getText(),stock_emailField.getText(),itemname_firstnameField.getText(),price_lastnameField.getText(),Integer.valueOf(ageField.getText()),phoneNumberField.getText());
                dbc.alterUser(u,1);
            }

        }
        resetFields();
        updateTables();
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
        if (itemsRadioB.isSelected() && !itemID_UsernameField.getText().equals("")) {
            Item item = new Item(Integer.valueOf(itemID_UsernameField.getText()), itemname_firstnameField.getText(), Double.valueOf(price_lastnameField.getText()), Integer.valueOf(stock_emailField.getText()), productdescField.getText(), address_ImageURL_Field.getText());
            dbc.alterItem(item,2);

        } else if (usersRadioB.isSelected() && !itemID_UsernameField.getText().equals("")) {
            User u = new User(itemID_UsernameField.getText(),passwordField.getText(), address_ImageURL_Field.getText(),stock_emailField.getText(),itemname_firstnameField.getText(),price_lastnameField.getText(),Integer.valueOf(ageField.getText()),phoneNumberField.getText());
            dbc.alterUser(u,2);
        }
        resetFields();
        updateTables();
    }

    public void radioSelect(ActionEvent event) throws Exception {
        if (itemsRadioB.isSelected()) {
            selection("ItemID:", "Item name:", "Price:", "Stock:","ImageURL:","Produc Desc:", false);

        } else if (usersRadioB.isSelected()) {
            selection("Username:", "First name:", "Last name:", "Email:","Address:","Age:", true);
        }
    }

    private void selection(String one, String two, String three, String four, String five,String six, boolean b) throws Exception {
        clearAll();
        itemID_usernameLabel.setText(one);
        itemname_firstnamelabel.setText(two);
        price_lastnameLabel.setText(three);
        stock_emailLabel.setText(four);
        address_ImageURLLabel.setText(five);
        age_desc_Label.setText(six);
        ageField.setVisible(b);
        phoneNumberField.setVisible(b);
        phoneNumberLabel.setVisible(b);
        passwordField.setVisible(b);
        passwordLabel.setVisible(b);
        user_table.setVisible(b);
        item_table.setVisible(!b);
        productdescField.setVisible(!b);


    }

    private void firstTimeSetup() throws Exception {
        //dbc.Connect();
        firstColumnitem.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        secondColumnitem.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        firstColumnuser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        secondColumnuser.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        updateTables();

        user_table.setRowFactory((TableView<User> tv) -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    User rowData = row.getItem();
                    itemID_UsernameField.setText(rowData.getUserName());
                    itemname_firstnameField.setText(rowData.getFirstname());
                    price_lastnameField.setText(rowData.getLastname());
                    stock_emailField.setText(rowData.getEmail());
                    ageField.setText(String.valueOf(rowData.getAge()));
                    address_ImageURL_Field.setText(rowData.getAddress());
                    phoneNumberField.setText(rowData.getPhoneNumber());
                    passwordField.setText(rowData.getPassWord());
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
                    productdescField.setText(rowData.getDescription());
                    address_ImageURL_Field.setText(rowData.getImageURL());

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
        address_ImageURL_Field.setText("");
        phoneNumberField.setText("");
    }

    private void updateTables() throws Exception {
        updateItems("",1);
        updateUsers("",1);
    }

    private void updateItems (String search, int searchMethod) throws Exception {
        ObservableList<Item> itemData = FXCollections.observableArrayList(dbc.getItemList(search,searchMethod)
        );
        item_table.setItems(itemData);
    }
    private void updateUsers (String search, int searchMethod) throws Exception {
        ObservableList<User> userData = FXCollections.observableArrayList(dbc.getUserList(search,searchMethod));
        user_table.setItems(userData);
    }
    private void resetFields () {
        itemID_UsernameField.setText("");
        itemname_firstnameField.setText("");
        price_lastnameField.setText("");
        stock_emailField.setText("");
        ageField.setText("");
        address_ImageURL_Field.setText("");
        phoneNumberField.setText("");
        passwordField.setText("");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            selection("ItemID:", "Item name:", "Price:", "Stock:","ImageURL","Product desc:", false);
            firstTimeSetup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
