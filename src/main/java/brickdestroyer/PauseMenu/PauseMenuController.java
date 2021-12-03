package brickdestroyer.PauseMenu;

import brickdestroyer.GameBoard.GameBoardModel;
import brickdestroyer.GameBoard.GameBoardViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class PauseMenuController {
    private Stage primaryStage;
    private GameBoardModel gameBoardModel;
    private GameBoardViewController gameBoardViewController;


    @FXML
    private Button continueButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    Stage stage;

    @FXML
    protected void onContinueButtonClick(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        this.primaryStage.getScene().getRoot().setEffect(null);
    }

    @FXML
    protected void onRestartButtonClick(ActionEvent actionEvent) {
        this.gameBoardViewController.setMessage("Restarting Game...");
        this.gameBoardModel.ballReset();
        this.gameBoardModel.wallReset();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        this.primaryStage.getScene().getRoot().setEffect(null);
        gameBoardViewController.repaint();
    }

    @FXML
    protected void onExitButtonClick(ActionEvent event) {
        this.primaryStage.close();
    }

    @FXML
    protected void onEscapeKeyType(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.hide();
            this.primaryStage.getScene().getRoot().setEffect(null);
        }
    }

    public void setRootStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setGameBoard(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
    }

    public void setGameBoardViewController(GameBoardViewController gameBoardViewController) {
        this.gameBoardViewController = gameBoardViewController;
    }
}
