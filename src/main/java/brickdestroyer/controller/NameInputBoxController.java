package brickdestroyer.controller;


import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Name Input Box Controller is responsible to show and handle all the user interaction
 * with the name input box in the game of Brick Destroyer. This class provide
 * methods that the Name Input Box View fxml can invoke based on the user's interactions.
 * It lets the user input a name before playing the game their high-score can be
 * recorded.
 */
public class NameInputBoxController implements Initializable{
    private final SceneManager sceneManager;
    private final Stage playerBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button backButton;
    @FXML
    private Button startGameButton;

    private String playerName;

    /**
     * @param sceneManager For changing the stage and scene of the game.
     */
    public NameInputBoxController(SceneManager sceneManager) {
        this.playerName = "";
        this.sceneManager = sceneManager;
        playerBox = new Stage();
    }

    /**
     * This method implements the Initializable interface and contain the basic methods
     * that the View can invoke based on the user's interactions.
     * @param url a pointer that represents a Uniform Resource Locator.
     * @param resourceBundle a resource bundles that contain locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startGameButton.setOnAction(actionEvent -> {
            if (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty() || nameTextField.getText().contains(",")) {
                showWarningMessage();
                return;
            }

            playerName = nameTextField.getText();
            sceneManager.getGameBoard();
            playerBox.close();
        });

        backButton.setOnAction(actionEvent -> playerBox.close());
    }

    /**
     * This method is called to stage the name input box.
     * It loads the name input box FXML file from the resource directory and
     * create the pop-up name input box.
     */
    public void showPlayerBox() {
        FXMLLoader playerBoxLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/NameInputBox.fxml"));
        playerBoxLoader.setController(this);

        try {
            Scene scene = new Scene(playerBoxLoader.load());
            scene.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/NameInputBoxStyle.css").toExternalForm());
            playerBox.setScene(scene);

        } catch (IOException e) {
            System.err.println("NameInputBox.fxml could not be loaded");
            e.printStackTrace();

        }

        playerBox.setTitle("Insert Player Name");
        playerBox.setResizable(false);
        playerBox.setX(sceneManager.getPrimaryStage().getX());
        playerBox.setY(sceneManager.getPrimaryStage().getY());
        playerBox.initModality(Modality.APPLICATION_MODAL);
        playerBox.initOwner(sceneManager.getPrimaryStage());
        playerBox.show();
    }

    /**
     * This method creates an alert box when the user didn't enter
     * their name in the text field of name input box.
     */
    private void showWarningMessage() {
        Alert alertBox = new Alert(Alert.AlertType.WARNING);
        alertBox.setTitle("Player name Warning");
        alertBox.setHeaderText(null);
        alertBox.setContentText("Please insert valid a player name!");
        alertBox.showAndWait();
    }

    /**
     * Getter method for the player name entered by the user.
     * @return a String that represent the player name of the user.
     */
    public String getPlayerName() {
        return playerName;
    }
}
