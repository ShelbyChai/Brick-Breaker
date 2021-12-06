package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class HomeMenuController {

    private VBox homePane;
    private final SceneManager sceneManager;

    @FXML
    private Button startButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button exitButton;

    public HomeMenuController(SceneManager sceneManager) {
        FXMLLoader homeLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/HomeMenu.fxml"));
        homeLoader.setController(this);
        this.sceneManager = sceneManager;

        try {
            homePane = homeLoader.load();
            homePane.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/HomeMenuStyle.css").toExternalForm());
        } catch (IOException e) {
            System.err.println("HomeMenu.fxml could not be loaded");
            e.printStackTrace();
        }

        initializeListener();
    }

    private void initializeListener() {
        startButton.setOnAction(actionEvent -> {
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(sceneManager.getGameBoard());
        });

        infoButton.setOnAction(actionEvent -> {

        });

        exitButton.setOnAction(actionEvent -> {
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        });
    }

    public final VBox getHomeMenu() {return homePane;}
}