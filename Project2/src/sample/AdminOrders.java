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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminOrders implements Initializable {

    @FXML
    private ImageView logoImage;

    @FXML
    private Button returnButton;

    @FXML
    private Label ordersLabel;

    @FXML
    private Label currentOrderLabel;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<?, ?> orderIDColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TableColumn<?, ?> orderDateColumn;

    @FXML
    private Label orderIdLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label commentLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label shipdateLabel;

    @FXML
    private ChoiceBox<String> shippingChoiceBox;

    @FXML
    private Button confirmButton;

    @FXML
    private TextArea productField;

    @FXML
    private TableColumn<?, ?> userNameColumn1;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> searchBox;

    @FXML
    private Button searchButton;


    private DBSingleton dbc = new DBSingleton();

    private ArrayList<Item> currentItemlist;

    private String itemList;
    private ObservableList<Order> orderData = null;
    private StringBuilder sb = new StringBuilder();


    @FXML
    void onSearchButtonPressed(ActionEvent event) {

        if (!searchField.getText().equals("")) {
            try {

                orderData = FXCollections.observableArrayList(dbc.getAdminOrderSearch(searchField.getText(),searchBox.getValue()));
                orderTable.setItems(orderData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (searchField.getText().equals("")) {
            try {
                orderData = FXCollections.observableArrayList(dbc.getOrderforAdmin());
                orderTable.setItems(orderData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void onConfirmButtonPressed(ActionEvent event) {
        if (!orderIdLabel.getText().equals("")) {
            try {
                dbc.updateOrderStatus(shippingChoiceBox.getValue(),Integer.valueOf(orderIdLabel.getText()));
                try {
                    orderData = FXCollections.observableArrayList(dbc.getOrderforAdmin());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                orderTable.setItems(orderData);
                orderIdLabel.setText("");
                statusLabel.setText("Status: ");
                orderDateLabel.setText("Order date: ");
                commentLabel.setText("Comment: ");
                usernameLabel.setText("Username: ");
                shipdateLabel.setText("Ship date: ");
                productField.setText("Products: ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onReturnButtonPressed(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shippingChoiceBox.setItems(FXCollections.observableArrayList("Pending","Processing","Shipped","Cancelled"));
        shippingChoiceBox.getSelectionModel().selectFirst();
        searchBox.setItems(FXCollections.observableArrayList("OrderID","Status","Order date","Username"));
        searchBox.getSelectionModel().selectFirst();
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        userNameColumn1.setCellValueFactory(new PropertyValueFactory<>("userName"));

        try {
            orderData = FXCollections.observableArrayList(dbc.getOrderforAdmin());
            orderTable.setItems(orderData);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        orderTable.setRowFactory((TableView<Order> tv) -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    itemList = "";
                    sb.setLength(0);
                    Order rowData = row.getItem();
                    orderIdLabel.setText(String.valueOf(rowData.getOrderID()));
                    statusLabel.setText("Status: " + rowData.getStatus());
                    orderDateLabel.setText("Order date: " + rowData.getOrderDate());
                    commentLabel.setText("Comment: " + rowData.getComment());
                    usernameLabel.setText("Username: " + rowData.getUserName());
                    shipdateLabel.setText("Ship date: " + rowData.getShippedDate());
                    try {
                        currentItemlist = dbc.getitemListForOrders(rowData.getOrderID());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    for (Item it : currentItemlist) {
                        sb.append(it.getItemName() + "\n");
                    }
                    productField.setText("Products: " + sb);

                }
            });
            return row;
        });
    }
}
