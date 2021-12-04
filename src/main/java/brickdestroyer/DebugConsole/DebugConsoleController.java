package brickdestroyer.DebugConsole;

import brickdestroyer.BrickDestroyerApplication;
import brickdestroyer.GameBoard.GameBoardModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    private void setGameBoardModel(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
    }


    // TODO Create a scene Manager and add it there
    public void getDebugConsole(Stage primaryStage, GameBoardModel gameBoardModel) {
        Stage debugConsole = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(BrickDestroyerApplication.class.getResource("DebugConsoleView.fxml"));
        try {
            debugConsole.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
            debugConsole.initModality(Modality.APPLICATION_MODAL);
            debugConsole.initOwner(primaryStage);
            debugConsole.show();

            DebugConsoleController debugConsoleController = fxmlLoader.getController();
            debugConsoleController.setGameBoardModel(gameBoardModel);
    }

}
