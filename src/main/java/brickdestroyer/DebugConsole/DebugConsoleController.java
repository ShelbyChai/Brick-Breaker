package brickdestroyer.DebugConsole;

import brickdestroyer.Actor.Levels;
import brickdestroyer.GameBoard.GameBoardModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class DebugConsoleController{

//    private static final Color DEF_BKG = Color.WHITE;

    @FXML
    private Button skipLevel;

    @FXML
    private Button resetBalls;

    @FXML
    private Slider ballXSpeed;

    @FXML
    private Slider ballYSpeed;

    private GameBoardModel gameBoardModel;
    private Levels levels;

    public DebugConsoleController(GameBoardModel gameBoardModel, Levels levels) {
        this.gameBoardModel = gameBoardModel;
        this.levels = levels;
    }

    @FXML
    protected void onSkipLevelClick(ActionEvent event) {
        this.levels.nextLevel();
    }

    @FXML
    protected void onResetButtonClick(ActionEvent event) {
        this.gameBoardModel.resetBallCount();
    }

    @FXML
    protected void onBallXSpeedSliderChange() {
        this.gameBoardModel.setBallXSpeed((int)ballXSpeed.getValue());
    }

    @FXML
    protected void onBallYSpeedSliderChange() {
        this.gameBoardModel.setBallYSpeed((int)ballYSpeed.getValue());
    }

}
