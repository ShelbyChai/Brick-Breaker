package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import brickdestroyer.model.game.PauseMenuModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;


public class PauseMenuController {

    private final Stage pauseMenu;
    private final PauseMenuModel pauseMenuModel;
    private SceneManager sceneManager;

    @FXML
    private AnchorPane pauseMenuPane;

    @FXML
    private Button continueButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    public PauseMenuController(PauseMenuModel pauseMenuModel, SceneManager sceneManager) {
        this.pauseMenuModel = pauseMenuModel;
        this.sceneManager = sceneManager;

        FXMLLoader pauseMenuLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/PauseMenu.fxml"));
        pauseMenuLoader.setController(this);
        pauseMenu = new Stage(StageStyle.TRANSPARENT);

        try {
            Scene scene = new Scene(pauseMenuLoader.load());
            scene.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/PauseMenuStyle.css").toExternalForm());
            pauseMenu.setScene(scene);

        } catch (IOException ex) {
            System.err.println("PauseMenu.fxml could not be loaded");
            ex.printStackTrace();
        }

        pauseMenu.initModality(Modality.APPLICATION_MODAL);

        initializeListener();
    }

    private void initializeListener() {
        continueButton.setOnAction(this::hidePauseMenu);

        restartButton.setOnAction(actionEvent -> {
            pauseMenuModel.getGameBoardModel().resetPoint();
            pauseMenuModel.getGameBoardModel().wallReset();
            pauseMenuModel.getGameBoardView().repaintMessage("Restarting Game");
            hidePauseMenu(actionEvent);
        });

        exitButton.setOnAction(actionEvent -> pauseMenuModel.getPrimaryStage().close());

        pauseMenuPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
                hidePauseMenu(keyEvent);
        });
    }

    private void hidePauseMenu(Event event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        pauseMenuModel.getPrimaryStage().getScene().getRoot().setEffect(null);
    }

    public final Stage getPauseMenu() {return pauseMenu;}

}
