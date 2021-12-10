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

/**
 * Info Menu Controller is responsible to show and handle all the user interaction
 * with the Info Menu in the game of Brick Destroyer. This class provide
 * methods that the Info Menu View fxml can invoke based on the user's interactions.
 * This menu shows the key player should interact with to play the game.
 */
public class InfoMenuController implements Initializable {

    private final SceneManager sceneManager;
    private AnchorPane infoPane;

    @FXML
    private Button backButton;

    /**
     * @param sceneManager For changing the stage and scene of the game.
     */
    public InfoMenuController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * This method is called to stage the info menu of the game.
     * It loads the info menu FXML file from the resource directory and
     * set the stage to display the info menu page.
     */
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


    /**
     * This method implements the Initializable interface and contain the basic methods
     * that the View can invoke based on the user's interactions.
     * @param url a pointer that represents a Uniform Resource Locator.
     * @param resourceBundle a resource bundles that contain locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(actionEvent -> {
            sceneManager.setPrimaryStage((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
            sceneManager.getHomeMenu();
        });
    }
}
