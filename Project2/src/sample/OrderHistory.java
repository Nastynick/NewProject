package sample;

import com.sun.org.apache.xpath.internal.operations.Or;
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

public class OrderHistory implements Initializable{

    TestingClass tc = new TestingClass();
    String test = "";

    @FXML
    private ImageView logoImage;

    @FXML
    private TableView<Order> orderHIstoryTable;

    @FXML
    private TableColumn<?, ?> orderIDColumn;

    @FXML
    private TableColumn<?, ?> dayOfPurchaseColumn;

    @FXML
    private Button returnButton;

    @FXML
    private Label ordersLabel;


    @FXML
    private Label currentOrderLabel;

    @FXML
    private TextArea currentOrderTextArea;

    private ArrayList<Item> temp = new ArrayList<>();
    DBSingleton dbc = new DBSingleton();

    @FXML
    private void onReturnButtonPressed(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        MainMenu MM = loader.getController();
        MM.setData(temp);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void setData (ArrayList<Item> list) throws Exception {
        temp = list;
    }

    private void startUp () throws Exception {
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        dayOfPurchaseColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        ObservableList<Order> oldOrders = FXCollections.observableArrayList(dbc.getOrder(UserSingleton.getInstance().getUsername()));
        orderHIstoryTable.setItems(oldOrders);


        orderHIstoryTable.setRowFactory((TableView<Order> tv) -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Order rowData = row.getItem();
                    String test = "";

                    try {
                        for (Item s : dbc.getitemListForOrders(rowData.getOrderID())) {
                            test += s + "\n";
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    currentOrderTextArea.setText("Order ID: " + rowData.getOrderID() + "\n" + "Shipping Status: "
                            + rowData.getStatus() + "\n" + "Order Date: " + rowData.getOrderDate()
                            + "\n" + "Comment: " + rowData.getComment() + "\n" + "Shipped Date: "
                            + rowData.getShippedDate() + "\n\n" + "Ordered items: " + "\n" + test);
                }
            });
            return row;
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try {
            startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

