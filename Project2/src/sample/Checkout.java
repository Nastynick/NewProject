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

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Checkout implements Initializable{

    @FXML
    private Button confirmButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button clearCartButton;

    @FXML
    private TableView<Item> itemArea;

    @FXML
    private TableColumn<?, ?> quantityColoumn;

    @FXML
    private TableColumn<?, ?> productColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    private ArrayList<Item> itemlist = new ArrayList<>();
    private ObservableList<Item> cart;
    private double cost;
    private static DecimalFormat df = new DecimalFormat(".##");

    @FXML
    private Label costLabel;

    @FXML
    private void clearCartButtonPressed(ActionEvent event) throws Exception {
        itemlist.clear();
        ObservableList<Item> cart = FXCollections.observableArrayList(itemlist);
        itemArea.setItems(cart);
        cost = 0;
        costLabel.setText(df.format(cost));
    }

    @FXML
    private void confirmButtonPressed(ActionEvent event) throws Exception {
        if (!itemlist.isEmpty()) {
            Order o = new Order(getRandomNr(),"Not Shipped","N/A","B",getTime(),UserSingleton.getInstance().getUsername(),itemlist);

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOutField.fxml"));
            Parent root = loader.load();
            RecieptArea RA = loader.getController();
            RA.setData(o);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            costLabel.setText("Cart empty!");
        }
    }

    @FXML
    private void returnButtonPressed(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        MainMenu MM = loader.getController();
        MM.setData(itemlist);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    private String getTime () throws Exception {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        return date;
    }
    private int getRandomNr() throws Exception {
        SecureRandom random = new SecureRandom();
        return random.nextInt(2147483647);
    }
    private void startUp () throws Exception {
        productColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemArea.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Item rowData = row.getItem();
                    cart.remove(rowData);
                    cost = cost - rowData.getPrice();
                    costLabel.setText(df.format(cost));
                    itemlist.remove(rowData);
                }
            });
            return row ;
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(ArrayList<Item> data) throws Exception {
        itemlist = data;

        for (Item s : itemlist ) {
            cost = cost + s.getPrice();
        }
        cart = FXCollections.observableArrayList(itemlist);
        itemArea.setItems(cart);
        costLabel.setText(df.format(cost));

    }

}
