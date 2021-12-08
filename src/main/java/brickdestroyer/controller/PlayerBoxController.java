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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerBoxController implements Initializable{
    private final SceneManager sceneManager;
    private final Stage playerBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button backButton;
    @FXML
    private Button startGameButton;

    private String playerName;

    public PlayerBoxController(SceneManager sceneManager) {
        this.playerName = "";
        this.sceneManager = sceneManager;
        playerBox = new Stage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startGameButton.setOnAction(actionEvent -> {
            if (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty() || nameTextField.getText().contains(",")) {
                Alert alertBox = new Alert(Alert.AlertType.WARNING);
                alertBox.setTitle("Player name Warning");
                alertBox.setHeaderText(null);
                alertBox.setContentText("Please insert valid a player name!");
                alertBox.showAndWait();

                return;
            }

            playerName = nameTextField.getText();
            sceneManager.getGameBoard();
            playerBox.close();
        });

        backButton.setOnAction(actionEvent -> playerBox.close());
    }

    public void showPlayerBox() {
        FXMLLoader playerBoxLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/PlayerBox.fxml"));
        playerBoxLoader.setController(this);

        try {
            Scene scene = new Scene(playerBoxLoader.load());
            scene.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/PlayerBoxStyle.css").toExternalForm());
            playerBox.setScene(scene);

        } catch (IOException e) {
            System.err.println("PlayerBox.fxml could not be loaded");
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

    public String getPlayerName() {
        return playerName;
    }
}
