package brickdestroyer.PauseMenu;

import brickdestroyer.GameBoard.GameBoardModel;
import brickdestroyer.GameBoard.GameBoardViewController;
import javafx.stage.Stage;

public class PauseMenuModel {
      private final Stage primaryStage;
      private final GameBoardModel gameBoardModel;
      private final GameBoardViewController gameBoardViewController;

    public PauseMenuModel(Stage primaryStage, GameBoardModel gameBoardModel, GameBoardViewController gameBoardViewController) {
        this.primaryStage = primaryStage;
        this.gameBoardModel = gameBoardModel;
        this.gameBoardViewController = gameBoardViewController;
    }

    public Stage getPrimaryStage() {return primaryStage;}

    public GameBoardModel getGameBoardModel() {return gameBoardModel;}

    public GameBoardViewController getGameBoardViewController() {return gameBoardViewController;}

}
