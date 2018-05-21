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
import javafx.stage.Stage;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    DBSingleton test = DBSingleton.getInstance();
    TestingClass tc = new TestingClass();
    DBConnection dbc = new DBConnection();

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
    private Button addItem;

    @FXML
    private Button removeItem;

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
    private TableColumn<Item, Integer> quantityColumn;

    @FXML
    private TableColumn<Item, Double> priceColumn;

    private ArrayList<Item> itemlist = new ArrayList<>();
    private ArrayList<Item> cartP = new ArrayList<>();
    private ObservableList<Item> cartPreviewData;
    private static DecimalFormat df = new DecimalFormat(".##");

    private double cost;

    @FXML
    private Label costLabelMM;

    @FXML
    private Button adminButton;

    @FXML
    void onAdminPressed(ActionEvent event) throws Exception {
        changeScene("AdminController.fxml", event);
    }


    @FXML
    void logOutButtonPressed(ActionEvent event) throws Exception {
        // close db connection here and clear any variables that might have been used
        changeScene("login.fxml", event);

    }

    @FXML
    private void searchButtonPressed(ActionEvent event) throws Exception {
        // db.Getitems(search)
    }

    @FXML
    private void viewAccountInformationButtonPressed(ActionEvent event) throws Exception {
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
    private void viewCartButtonPressed(ActionEvent event) throws Exception {
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
    private void viewOrderHistoryButtonPressed(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("orderHistory.fxml"));
        Parent root = loader.load();
        OrderHistory oh = loader.getController();
        oh.setData(cartP);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private void changeScene(String newScene, ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void setData(ArrayList<Item> list) throws Exception {
        cartP = list;
        cartPreviewData = FXCollections.observableArrayList(cartP);
        cartPreviewTable.setItems(cartPreviewData);
        for (Item s : cartP) {
            cost = cost + s.getPrice();
        }
        costLabelMM.setText(df.format(cost));
    }

    private void startUp () throws Exception {
        dbc.Connect();
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
            return row ;
        });



        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartPpriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartPnameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        ObservableList<Item> itemData = FXCollections.observableArrayList(dbc.itemList(""));
        itemTableView.setItems(itemData);
        cartPreviewData = FXCollections.observableArrayList(cartP);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
