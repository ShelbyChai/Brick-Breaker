package brickdestroyer.HomeMenu;

import brickdestroyer.GameBoard.GameBoardViewController;
import brickdestroyer.GameBoard.GameBoardModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class HomeMenuController {

    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;

    private Stage stage;
    GameBoardViewController gameBoardViewController;

    @FXML
    protected void onStartButtonClick(ActionEvent event) {

        gameBoardViewController = new GameBoardViewController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(gameBoardViewController.getGameScene());
        gameBoardViewController.start();
    }

    @FXML
    protected void onExitButtonClick(ActionEvent event) {
        System.out.println("Goodbye " + System.getProperty("user.name"));
        System.exit(0);
    }
}