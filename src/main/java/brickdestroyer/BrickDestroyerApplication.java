package brickdestroyer;

import brickdestroyer.HomeMenu.HomeMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BrickDestroyerApplication extends Application {
    final static int WIDTH = 450;
    final static int HEIGHT = 300;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader homeFxmlLoader = new FXMLLoader(BrickDestroyerApplication.class.getResource("HomeMenuView.fxml"));
        Parent homePane =  homeFxmlLoader.load();
        Scene scene = new Scene(homePane,WIDTH,HEIGHT);




//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("BrickDestroyer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}