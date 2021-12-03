package brickdestroyer.DebugConsole;

import brickdestroyer.Actor.Levels;
import brickdestroyer.GameBoard.GameBoardModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;


public class DebugConsoleController implements Initializable {
    private GameBoardModel gameBoardModel;

    @FXML
    private Button skipLevel;

    @FXML
    private Button resetBalls;

    @FXML
    private Slider ballXSpeed;

    @FXML
    private Slider ballYSpeed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ballXSpeed.valueProperty().addListener(e -> gameBoardModel.setBallXSpeed((int)ballXSpeed.getValue()));
        ballYSpeed.valueProperty().addListener(e -> gameBoardModel.setBallYSpeed((int)ballYSpeed.getValue()));

    }

    @FXML
    protected void onSkipLevelClick(ActionEvent event) {
        if (this.gameBoardModel.hasLevel())
            this.gameBoardModel.nextLevel();
    }

    @FXML
    protected void onResetButtonClick(ActionEvent event) {
        this.gameBoardModel.resetBallCount();
    }

    public void setGameBoardModel(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
    }

}
