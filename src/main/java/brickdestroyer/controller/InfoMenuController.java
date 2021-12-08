package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoMenuController implements Initializable {

    private final SceneManager sceneManager;
    private AnchorPane infoPane;

    @FXML
    private Button backButton;

    public InfoMenuController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void showInfoMenu() {
        FXMLLoader infoLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/InfoMenu.fxml"));
        infoLoader.setController(this);

        try {
            infoPane = infoLoader.load();
            infoPane.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/InfoMenuStyle.css").toExternalForm());
        } catch (IOException e) {
            System.err.println("InfoMenu.fxml could not be loaded");
            e.printStackTrace();
        }

        Stage stage = sceneManager.getPrimaryStage();
        stage.setScene(new Scene(infoPane));
        stage.setResizable(false);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(actionEvent -> {
            sceneManager.setPrimaryStage((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
            sceneManager.getHomeMenu();
        });
    }
}
