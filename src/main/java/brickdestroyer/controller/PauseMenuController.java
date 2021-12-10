package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import brickdestroyer.model.game.PauseMenuModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Pause Menu Controller is responsible to show and handle all the user interaction
 * with the pause menu in the game of Brick Destroyer. This class provide
 * methods that the Pause Menu View fxml can invoke based on the user's interactions.
 * The player can pause, restart and quit the game. This screen can be display
 * by pressing the ESC key.
 */
public class PauseMenuController implements Initializable {

    private final PauseMenuModel pauseMenuModel;
    private SceneManager sceneManager;

    @FXML
    private Pane pauseMenuPane;

    @FXML
    private Button continueButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    /**
     * @param pauseMenuModel Data of the pauseMenuModel.
     * @param sceneManager For changing the stage and scene of the game.
     */
    public PauseMenuController(PauseMenuModel pauseMenuModel, SceneManager sceneManager) {
        this.pauseMenuModel = pauseMenuModel;
        this.sceneManager = sceneManager;
    }

    /**
     * This method implements the Initializable interface and contain the basic methods
     * that the View can invoke based on the user's interactions.
     * @param url a pointer that represents a Uniform Resource Locator.
     * @param resourceBundle a resource bundles that contain locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        continueButton.setOnAction(this::hidePauseMenu);

        restartButton.setOnAction(actionEvent -> {
            pauseMenuModel.getGameLogic().resetPoint();
            pauseMenuModel.getGameLogic().wallReset();
            pauseMenuModel.getGameBoardView().repaintMessage("Restarting Game");
            pauseMenuModel.getGameBoardView().repaintScore("Score: 0");
            hidePauseMenu(actionEvent);
        });

        exitButton.setOnAction(actionEvent -> sceneManager.getPrimaryStage().close());

        pauseMenuPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
                hidePauseMenu(keyEvent);
        });
    }

    /**
     * This method is called to stage the debug pause menu.
     * It loads the pause menu FXML file from the resource directory and
     * set the stage for pause menu in the game board.
     */
    public void showPauseMenu() {

        FXMLLoader pauseMenuLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/PauseMenu.fxml"));
        pauseMenuLoader.setController(this);
        Stage pauseMenu = new Stage(StageStyle.TRANSPARENT);

        try {
            Scene scene = new Scene(pauseMenuLoader.load());
            scene.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/PauseMenuStyle.css").toExternalForm());
            pauseMenu.setScene(scene);

        } catch (IOException ex) {
            System.err.println("PauseMenu.fxml could not be loaded");
            ex.printStackTrace();
        }

        pauseMenu.initModality(Modality.APPLICATION_MODAL);
        pauseMenu.initOwner(sceneManager.getPrimaryStage());
        pauseMenu.setX(sceneManager.getPrimaryStage().getX() + sceneManager.getPrimaryStage().getWidth()/4);
        pauseMenu.setY(sceneManager.getPrimaryStage().getY() + sceneManager.getPrimaryStage().getHeight()/4);
        pauseMenu.show();
    }

    /**
     * This method is called to hide the pause menu on the game board stage.
     * @param event an action event that will trigger when player press the ESC key.
     */
    private void hidePauseMenu(Event event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        sceneManager.getPrimaryStage().getScene().getRoot().setEffect(null);
    }


}
