package brickdestroyer.DebugConsole;

import brickdestroyer.Actor.Levels;
import brickdestroyer.GameBoard.GameBoardModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class DebugConsoleController {
    GameBoardModel gameBoardModel;
    Levels levels;

    @FXML
    private Button skipLevel;

    @FXML
    private Button resetBalls;

    @FXML
    private Slider ballXSpeed;

    @FXML
    private Slider ballYSpeed;

    @FXML
    protected void onSkipLevelClick(ActionEvent event) {
    }

    @FXML
    protected void onResetButtonClick(ActionEvent event) {
    }

    @FXML
    protected void onBallXSpeedSliderChange(ActionEvent event) {
    }

    @FXML
    protected void onBallYSpeedSliderChange(ActionEvent event) {
    }

    public void setGameBoardModel(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
    }

    public void setLevels(Levels levels) {
        this.levels = levels;
    }
}
