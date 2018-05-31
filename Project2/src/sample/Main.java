package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Runnable music = () -> {
            Media media = new Media(getClass().getClassLoader().getResource("sample/bensound-straight.mp3").toString()); // Royalty free Music from: https://www.bensound.com
                MediaPlayer player = new MediaPlayer(media);
                player.setAutoPlay(true);
                player.setCycleCount(MediaPlayer.INDEFINITE);
                player.play();
        };
        new Thread(music).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            
        }));

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
