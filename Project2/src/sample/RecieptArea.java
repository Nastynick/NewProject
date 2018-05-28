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
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class RecieptArea implements Initializable {



    @FXML
    private Button returnButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextArea boughtItemField;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private TableColumn<?, ?> productColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    private static DecimalFormat df = new DecimalFormat(".##");

    private Order o;

    @FXML
    private void exitButtonPressed(ActionEvent event) throws Exception {
        UserSingleton.getInstance().setAdmin(false);
        UserSingleton.getInstance().setUsername(null);
        changeScene("login.fxml", event);
    }

    @FXML
    private void returnButtonPressed(ActionEvent event) throws Exception {
        changeScene("MainMenu.fxml", event);
    }

    private void changeScene(String newScene, ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
    public void setData (Order order) throws Exception {
        o = order;
        String boughtList ="";
        double price = 0;


        for (Item s : o.getItemList()) {
            boughtList += s + "\n\n";
            price = price + s.getPrice();
        }
        boughtItemField.setText("Thank you for your order " + o.getUserName()+ "!" + "\nOrderID: " + o.getOrderID() + "\nStatus: "
                + o.getStatus() + "\nOrder date: " + o.getOrderDate()+ "\nShipped Date:" + o.getShippedDate() +"\nAdditional Comment:"
                + o.getComment() + "\n\nItems purchased: \n" + boughtList + "\nTOTAL COST: " + df.format(price));
        DBSingleton dbc = new DBSingleton();
        ArrayList<User> ul = dbc.getUserList(UserSingleton.getInstance().getUsername(),5);
        User u = ul.get(0);
        mailSetup(u.getEmail());
    }
    private void mailSetup (String email) {
        final String username = "dragoncaveproject2";
        final String password = "GlassGlassIcecream";
        Properties props = new Properties();
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username,password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dragoncaveproject2@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
            message.setSubject("Thank you for the purchase! Order ID: " + o.getOrderID());
            message.setText(boughtItemField.getText());
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
}
