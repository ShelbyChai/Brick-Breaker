package brickdestroyer;

import brickdestroyer.controller.*;
import brickdestroyer.model.game.*;
import brickdestroyer.view.GameBoardView;
import javafx.stage.Stage;


public class SceneManager {

    private GameBoardView gameBoardView;
    private NameInputBoxController nameInputBoxController;
    private final GameLogic gameLogic;
    private Stage primaryStage;
    ScoreBoardModel scoreBoardModel;
    ScoreBoardController scoreBoardController;

    public SceneManager(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public void getHomeMenu() {
        HomeMenuController homeMenuController = new HomeMenuController(this);
        homeMenuController.showHomeMenu();
    }

    public void getInfoMenu() {
        InfoMenuController infoMenuController = new InfoMenuController(this);
        infoMenuController.showInfoMenu();
    }

    public void getPlayerBox() {
        nameInputBoxController = new NameInputBoxController(this);
        nameInputBoxController.showPlayerBox();
    }

    public void getGameBoard() {
        GameBoardModel gameBoardModel = new GameBoardModel(gameLogic);
        gameBoardView = new GameBoardView(gameBoardModel);
        GameBoardController gameBoardController = new GameBoardController(gameBoardModel, gameBoardView, this);
        gameBoardController.showGameBoard();
    }

    public void getDebugConsole() {
        DebugConsoleModel debugConsoleModel = new DebugConsoleModel(gameLogic, gameBoardView);
        DebugConsoleController debugConsoleController = new DebugConsoleController(debugConsoleModel, this);
        debugConsoleController.showDebugConsole();
    }

    public void getPauseMenu() {
        PauseMenuModel pauseMenuModel = new PauseMenuModel(gameLogic, gameBoardView);
        PauseMenuController pauseMenuController = new PauseMenuController(pauseMenuModel, this);
        pauseMenuController.showPauseMenu();
    }

    public void getWinningBoard() {
        WinningBoardController winningBoardController = new WinningBoardController(this, scoreBoardModel, gameLogic);
        winningBoardController.showWinningBoard();
    }

    public void getScoreBoard(){
        scoreBoardModel = new ScoreBoardModel(gameLogic, nameInputBoxController);
        scoreBoardController = new ScoreBoardController(scoreBoardModel, this);
        scoreBoardController.showScoreBoard();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
