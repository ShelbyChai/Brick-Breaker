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


/**
 * Home Menu Controller is responsible to show and handle all the user interaction
 * with the Home Menu in the game of Brick Destroyer. This class provide
 * methods that the Home Menu View fxml can invoke based on the user's interactions.
 * This is the initial page of the application that consist of 4 buttons. The player
 * can navigate to the info, leaderboard, game or quit the game with home menu.
 */
public class HomeMenuController implements Initializable {

    private VBox homePane;
    private final SceneManager sceneManager;

    @FXML
    private Button startButton, infoButton, leaderBoardButton, exitButton;


    /**
     * @param sceneManager For changing the stage and scene of the game.
     */
    public HomeMenuController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * This method is called to stage the home menu of the game.
     * It loads the home menu FXML file from the resource directory and
     * set the stage to display the home menu page.
     */
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

    /**
     * This method implements the Initializable interface and contain the basic methods
     * that the View can invoke based on the user's interactions.
     * @param url a pointer that represents a Uniform Resource Locator.
     * @param resourceBundle a resource bundles that contain locale-specific objects.
     */
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