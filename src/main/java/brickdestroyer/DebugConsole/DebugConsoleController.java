package brickdestroyer.DebugConsole;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import java.net.URL;
import java.util.ResourceBundle;


public class DebugConsoleController implements Initializable {

    private DebugConsoleModel debugConsoleModel;

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

        ballYSpeed.valueProperty().addListener(e ->
                debugConsoleModel.getGameBoardModel().setBallYSpeed((int)ballYSpeed.getValue()));

        ballXSpeed.valueProperty().addListener(e ->
                debugConsoleModel.getGameBoardModel().setBallXSpeed((int)ballXSpeed.getValue()));
    }

    @FXML
    protected void onSkipLevelClick() {
        if (debugConsoleModel.getGameBoardModel().hasLevel())
            debugConsoleModel.getGameBoardModel().nextLevel();
    }

    @FXML
    protected void onResetButtonClick() {
        debugConsoleModel.getGameBoardModel().resetBallCount();
    }

    public void setDebugConsoleModel(DebugConsoleModel debugConsoleModel) {this.debugConsoleModel = debugConsoleModel;}
}
