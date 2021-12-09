package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeMenuController implements Initializable {

    private VBox homePane;
    private final SceneManager sceneManager;

    @FXML
    private Button startButton, infoButton, leaderBoardButton, exitButton;


    public HomeMenuController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void showHomeMenu() {
        FXMLLoader homeLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/HomeMenu.fxml"));
        homeLoader.setController(this);

        try {
            homePane = homeLoader.load();
            homePane.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/HomeMenuStyle.css").toExternalForm());
        } catch (IOException e) {
            System.err.println("HomeMenu.fxml could not be loaded");
            e.printStackTrace();
        }

        Stage stage = sceneManager.getPrimaryStage();
        stage.setScene(new Scene(homePane));
        stage.setTitle("Brick Destroyer");
        stage.getIcons().add(new Image(String.valueOf(BrickDestroyerMain.class.getResource("/brickdestroyer/images/game-icon.png"))));
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnAction(actionEvent -> {
            sceneManager.setPrimaryStage((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
            sceneManager.getPlayerBox();
        });

        infoButton.setOnAction(actionEvent -> {
            sceneManager.setPrimaryStage((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
            sceneManager.getInfoMenu();
        });

        leaderBoardButton.setOnAction(actionEvent -> {
            sceneManager.setPrimaryStage((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
            sceneManager.getScoreBoard();
        });

        exitButton.setOnAction(actionEvent -> {
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        });
    }
}