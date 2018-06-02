package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;

import java.io.IOException;

public class PlaceHolder { // isn't used anymore
     private String resource;

    @FXML
    private ImageView logoImage;

    @FXML
    private Button placeHolderReturn;

    @FXML
    private void onPlaceHolderReturnButtonPressed(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
    public void setData(String placeHolderReturn) throws Exception {
        resource = placeHolderReturn;
    }

}

