package brickdestroyer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BrickDestroyerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader homeFxmlLoader = new FXMLLoader(BrickDestroyerApplication.class.getResource("HomeMenuView.fxml"));
        Parent homePane =  homeFxmlLoader.load();
        Scene scene = new Scene(homePane);
        scene.getStylesheets().add(BrickDestroyerApplication.class.getResource("HomeMenuStyle.css").toExternalForm());

//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("BrickDestroyer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}