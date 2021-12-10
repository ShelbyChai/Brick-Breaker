package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import brickdestroyer.model.game.DebugConsoleModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Debug Console Controller is responsible to show and handle all the user interaction
 * with the debug console panel in the game of Brick Destroyer. This class provide
 * methods that the Debug Console View fxml can invoke based on the user's interactions.
 * The player can increment the level count, reset the ball, increase the speed of the ball
 * using debug console. The debug console can be triggered by tapping on ALT + SHIFT + F1 key.
 */
public class DebugConsoleController implements Initializable {

    private final DebugConsoleModel debugConsoleModel;
    private final SceneManager sceneManager;

    @FXML
    private Button skipLevelButton;

    @FXML
    private Button resetBallsButton;

    @FXML
    private Slider ballXSpeed;

    @FXML
    private Slider ballYSpeed;

    /**
     * @param debugConsoleModel Data of the debug console model.
     * @param sceneManager For changing the stage and scene of the game.
     */
    public DebugConsoleController(DebugConsoleModel debugConsoleModel, SceneManager sceneManager) {
        this.debugConsoleModel = debugConsoleModel;
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

        ballXSpeed.valueProperty().addListener(e ->
                debugConsoleModel.getGameLogic().setBallSpeedX((int)ballXSpeed.getValue()));

        ballYSpeed.valueProperty().addListener(e ->
                debugConsoleModel.getGameLogic().setBallSpeedY((int)ballYSpeed.getValue()));


        skipLevelButton.setOnAction(actionEvent -> {
            if (debugConsoleModel.getGameLogic().hasLevel()) {
                debugConsoleModel.getGameLogic().nextLevel();
                debugConsoleModel.getGameBoardView().paint();
            }
        });

        resetBallsButton.setOnAction(actionEvent -> {
            debugConsoleModel.getGameLogic().resetBallCount();
            debugConsoleModel.getGameBoardView().paint();
        });
    }

    /**
     * This method is called to stage the debug console.
     * It loads the debug console FXML file from the resource directory and
     * create the pop-up debug console panel.
     */
    public void showDebugConsole() {
        Stage debugConsole = new Stage();
        FXMLLoader debugConsoleLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/DebugConsole.fxml"));
        debugConsoleLoader.setController(this);

        try {
            debugConsole.setScene(new Scene(debugConsoleLoader.load()));
        } catch (IOException ioException) {
            System.err.println("DebugConsole.fxml could not be loaded");
            ioException.printStackTrace();
        }

        debugConsole.setTitle("Debug Console");
        debugConsole.setResizable(false);
        debugConsole.initModality(Modality.APPLICATION_MODAL);
        debugConsole.getIcons().add(new Image(String.valueOf(BrickDestroyerMain.class.getResource("/brickdestroyer/images/debugconsole-icon.png"))));
        debugConsole.initOwner(sceneManager.getPrimaryStage());
        debugConsole.show();
    }

}
