package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    DBSingleton dbc = DBSingleton.getInstance();


    @FXML
    private Button viewCart;

    @FXML
    private Button viewOrderHistory;

    @FXML
    private Button viewAccountInformation;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Item> cartPreviewTable;

    @FXML
    private TableColumn<?, ?> cartPnameColumn;

    @FXML
    private TableColumn<?, ?> cartPpriceColumn;

    @FXML
    private Label cartLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, Integer> idColumn;

    @FXML
    private TableColumn<Item, String> nameColumn;

    @FXML
    private TableColumn<Item, Double> priceColumn;

    @FXML
    private ImageView itemImage;

    @FXML
    private Button orderManagementButton;

    private ArrayList<Item> itemlist = new ArrayList<>();
    private ArrayList<Item> cartP = new ArrayList<>();
    private ObservableList<Item> cartPreviewData;
    private static DecimalFormat df = new DecimalFormat(".##");
    private ObservableList<Item> itemData;

    private double cost;

    @FXML
    private Label costLabelMM;

    @FXML
    private Button adminButton;

    @FXML
    private Label productDescLabel;

    private Tooltip tooltip = new Tooltip();

    @FXML
    void onOrderManagementButtonPressed(ActionEvent event) throws Exception { //calls change scene
        changeScene("AdminOrders.fxml",event);
    }

    @FXML
    void onAdminPressed(ActionEvent event) throws Exception { //calls change scene
        changeScene("AdminController.fxml", event);
    }


    @FXML
    void logOutButtonPressed(ActionEvent event) throws Exception { // reset singleton, change back to login
        UserSingleton.getInstance().setAdmin(false);
        UserSingleton.getInstance().setUsername(null);
        changeScene("login.fxml", event);

    }

    @FXML
    private void onEnterKeyPressedMM(KeyEvent event) { // calls search
        if (event.getCode().equals(KeyCode.ENTER)) {
            search();
        }
    }

    @FXML
    private void searchButtonPressed(ActionEvent event) throws Exception { //calls search
        search();
    }

    @FXML
    private void viewAccountInformationButtonPressed(ActionEvent event) throws Exception { //account info page
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountDetails.fxml"));
        Parent root = loader.load();
        AccountDetailsController adc = loader.getController();
        adc.setData(cartP);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void viewCartButtonPressed(ActionEvent event) throws Exception {// cart page
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout.fxml"));
        Parent root = loader.load();
        Checkout ch = loader.getController();
        ch.setData(cartP);
        Scene scene = new Scene(root);
        stage.setScene(scene);


    }

    @FXML
    private void viewOrderHistoryButtonPressed(ActionEvent event) throws Exception { // order history page
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderHistory.fxml"));
        Parent root = loader.load();
        OrderHistory oh = loader.getController();
        oh.setData(cartP);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private void changeScene(String newScene, ActionEvent event) throws Exception { // scene method
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void setData(ArrayList<Item> list) throws Exception { // sets cart data
        cartP = list;
        cartPreviewData = FXCollections.observableArrayList(cartP);
        cartPreviewTable.setItems(cartPreviewData);
        for (Item s : cartP) {
            cost = cost + s.getPrice();
        }
        costLabelMM.setText(df.format(cost));
    }

    private void startUp () throws Exception { // setup for when the scene starts up
        //dbc.Connect();
        df.setRoundingMode(RoundingMode.UP);
        itemTableView.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Item rowData = row.getItem();
                    cost = cost + rowData.getPrice();
                    costLabelMM.setText(df.format(cost));
                    cartPreviewData.add(rowData);
                    cartPreviewTable.setItems(cartPreviewData);
                    cartP.add(rowData);
                }
            });

            row.hoverProperty().addListener((observable) -> {
                Item it = row.getItem();

                if (row.isHover() && it != null) {
                    productDescLabel.setText(it.getDescription());
                    String imagepath = it.getImageURL();
                    Image image = new Image(imagepath);
                    itemImage.setImage(image);
                } else {
                    productDescLabel.setText("");
                    itemImage.setImage(null);
                }
            });
            return row ;
        });



        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartPpriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartPnameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemlist = dbc.getItemList("",1);
        itemData = FXCollections.observableArrayList(itemlist);
        itemTableView.setItems(itemData);
        cartPreviewData = FXCollections.observableArrayList(cartP);

    }

    private void search () { // search method
        if (!searchField.getText().equals("")) {
            itemlist = dbc.getItemList(searchField.getText(),2);
            itemData = FXCollections.observableArrayList(itemlist);
            itemTableView.setItems(itemData);
        } else if (searchField.getText().equals("")) {
            itemlist = dbc.getItemList("",1);
            itemData = FXCollections.observableArrayList(itemlist);
            itemTableView.setItems(itemData);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { // more setup
        tooltip.setText("Double-click on items to add them to your cart.");
        itemTableView.setTooltip(tooltip);
        adminButton.setVisible(false);
        orderManagementButton.setVisible(false);
        if (UserSingleton.getInstance().isAdmin()) {
            adminButton.setVisible(true);
            orderManagementButton.setVisible(true);
        }
        try {
            startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
