package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class RecoveryController implements Initializable {

    @FXML
    private Label username_email_Label;

    @FXML
    private TextField username_email_Field;

    @FXML
    private Label infoLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label repeatPasswordLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField repeatpasswordField;

    @FXML
    private Label recoveryLabel;

    @FXML
    private TextField recoveryCodeField;

    @FXML
    private Button returnButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Button confirmButton1;

    @FXML
    private Button confirmButton2;

    private int stage = 1;
    private int selectionmode;
    private DBSingleton dbc = new DBSingleton();
    SecureRandom random = new SecureRandom();
    int nr;

    @FXML
    private void onConfirmButtonPressed(ActionEvent event) {
        if (passwordField.getText().equals(repeatpasswordField.getText()) && !passwordField.getText().equals("")) {
            // dbc alter user
            infoLabel.setText("Password reset! You may now go back and login.");
            confirmButton.setDisable(true);
        } else {
            infoLabel.setText("Password field may not be empty and both must match!");
        }
    }

    @FXML
    void onConfirm1ButtonPressed(ActionEvent event) throws Exception {
        System.out.println(selectionmode);

        if (!username_email_Field.getText().equals("") && selectionmode == 2) {
            ArrayList<User> ul = dbc.getUserList(username_email_Field.getText(),3);
            System.out.println(ul);
            if (!ul.isEmpty()) {
                User u = ul.get(0);
                nr = random.nextInt(21475);
                System.out.println(nr);
                recoveryCodeField.setVisible(true);
                recoveryLabel.setVisible(true);
                confirmButton1.setDisable(true);
                confirmButton2.setVisible(true);
                username_email_Field.setEditable(false);
                try {
                    mailSetup(u);
                    infoLabel.setText("Code sent to the registrated email!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                infoLabel.setText("No user found!");
            }

        } else {
            infoLabel.setText("Field empty!");
        }
    }

    @FXML
    void onConfirm2ButtonPressed(ActionEvent event) {
        if (Integer.valueOf(recoveryCodeField.getText()) == nr) {
            passwordLabel.setVisible(true);
            passwordField.setVisible(true);
            repeatPasswordLabel.setVisible(true);
            repeatpasswordField.setVisible(true);
            confirmButton2.setDisable(true);
            recoveryCodeField.setEditable(false);
            confirmButton.setVisible(true);
            infoLabel.setText("Verification confirmed, please set your new password below!");
        }
    }

    @FXML
    private void onReturnButtonPressed(ActionEvent event) throws Exception {
        changeScene("login.fxml",event);
    }
    private void mailSetup (User user) throws Exception {
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
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(user.getEmail()));

            message.setSubject("The Dragon Cave: Forgotten username/password!");

            switch (selectionmode) {
                case 1: message.setText("Here is your username" + user.getUserName());
                case 2: message.setText("Here is your recovery code: " + nr);
            }
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void setData (int mode) {
        selectionmode = mode;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recoveryLabel.setVisible(false);
        recoveryCodeField.setVisible(false);
        passwordField.setVisible(false);
        repeatpasswordField.setVisible(false);
        passwordLabel.setVisible(false);
        repeatPasswordLabel.setVisible(false);
        infoLabel.setText("");
        confirmButton.setVisible(false);
        confirmButton2.setVisible(false);

    }
    private void changeScene(String newScene, ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

}
