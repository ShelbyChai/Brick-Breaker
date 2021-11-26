package brickdestroyer;

import brickdestroyer.HomeMenu.HomeMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BrickDestroyerApplication extends Application {

    HomeMenuController homeMenuController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BrickDestroyerApplication.class.getResource("HomeMenuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("BrickDestroyer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}