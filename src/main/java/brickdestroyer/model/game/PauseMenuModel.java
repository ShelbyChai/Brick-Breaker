package brickdestroyer.model.game;


import brickdestroyer.view.GameBoardView;
import javafx.stage.Stage;

public class PauseMenuModel {
      private final Stage primaryStage;
      private final GameLogic gameLogic;
      private final GameBoardView gameBoardView;

    public PauseMenuModel(Stage primaryStage, GameLogic gameLogic, GameBoardView gameBoardView) {
        this.primaryStage = primaryStage;
        this.gameLogic = gameLogic;
        this.gameBoardView = gameBoardView;
    }

    public Stage getPrimaryStage() {return primaryStage;}

    public GameLogic getGameBoardModel() {return gameLogic;}

    public GameBoardView getGameBoardView() {return gameBoardView;}

}
