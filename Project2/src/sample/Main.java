package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Runnable music = () -> { // background music thread
            Media media = new Media(getClass().getClassLoader().getResource("sample/bensound-straight.mp3").toString()); // Royalty free Music from: https://www.bensound.com
                MediaPlayer player = new MediaPlayer(media);
                player.setAutoPlay(true);
                player.setCycleCount(MediaPlayer.INDEFINITE);
                player.setStartTime(Duration.seconds(0));
                player.setStopTime(Duration.seconds(252));
                player.play();
        };
        new Thread(music).start();

        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("The Dragon's Cave.exe");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
