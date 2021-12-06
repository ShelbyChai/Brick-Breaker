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


public class DebugConsoleController implements Initializable {

    private final Stage debugConsole;
    private final DebugConsoleModel debugConsoleModel;
    private SceneManager sceneManager;

    @FXML
    private Button skipLevelButton;

    @FXML
    private Button resetBallsButton;

    @FXML
    private Slider ballXSpeed;

    @FXML
    private Slider ballYSpeed;

    public DebugConsoleController(DebugConsoleModel debugConsoleModel, SceneManager sceneManager) {

        this.debugConsoleModel = debugConsoleModel;

        debugConsole = new Stage();
        FXMLLoader debugConsoleLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/DebugConsole.fxml"));
        debugConsoleLoader.setController(this);
        this.sceneManager = sceneManager;

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

        initializeListener();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ballXSpeed.valueProperty().addListener(e ->
                debugConsoleModel.getGameBoardModel().setBallSpeedX((int)ballXSpeed.getValue()));

        ballYSpeed.valueProperty().addListener(e ->
                debugConsoleModel.getGameBoardModel().setBallSpeedY((int)ballYSpeed.getValue()));
    }

    private void initializeListener() {
        skipLevelButton.setOnAction(actionEvent -> {
            if (debugConsoleModel.getGameBoardModel().hasLevel())
                debugConsoleModel.getGameBoardModel().nextLevel();
        });

        resetBallsButton.setOnAction(actionEvent -> debugConsoleModel.getGameBoardModel().resetBallCount());
    }

    public final Stage getDebugConsole() {return debugConsole;}

}
