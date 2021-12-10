package brickdestroyer;

import brickdestroyer.controller.*;
import brickdestroyer.model.game.*;
import brickdestroyer.view.GameBoardView;
import javafx.stage.Stage;


/**
 * Scene Manager manage all the screen swapping in the game
 * of Brick Destroyer.
 */
public class SceneManager {

    private GameBoardView gameBoardView;
    private NameInputBoxController nameInputBoxController;
    private final GameLogic gameLogic;
    private Stage primaryStage;
    ScoreBoardModel scoreBoardModel;
    ScoreBoardController scoreBoardController;

    /**
     * @param gameLogic a reference to the game logic of the game.
     */
    public SceneManager(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    /**
     * Swap the stage into Home Menu upon calling this method.
     */
    public void getHomeMenu() {
        HomeMenuController homeMenuController = new HomeMenuController(this);
        homeMenuController.showHomeMenu();
    }

    /**
     * Swap the stage into Info Menu upon calling this method
     */
    public void getInfoMenu() {
        InfoMenuController infoMenuController = new InfoMenuController(this);
        infoMenuController.showInfoMenu();
    }

    /**
     * Generate a pop-up user-name input box upon calling this method.
     */
    public void getPlayerBox() {
        nameInputBoxController = new NameInputBoxController(this);
        nameInputBoxController.showPlayerBox();
    }

    /**
     * Swap the scene to the game board upon calling this method.
     */
    public void getGameBoard() {
        GameBoardModel gameBoardModel = new GameBoardModel(gameLogic);
        gameBoardView = new GameBoardView(gameBoardModel);
        GameBoardController gameBoardController = new GameBoardController(gameBoardModel, gameBoardView, this);
        gameBoardController.showGameBoard();
    }

    /**
     * Generate a pop-up debug console for the game board upon calling this method.
     */
    public void getDebugConsole() {
        DebugConsoleModel debugConsoleModel = new DebugConsoleModel(gameLogic, gameBoardView);
        DebugConsoleController debugConsoleController = new DebugConsoleController(debugConsoleModel, this);
        debugConsoleController.showDebugConsole();
    }

    /**
     * Generate a pop-up pause menu for the game board upon calling this method.
     */
    public void getPauseMenu() {
        PauseMenuModel pauseMenuModel = new PauseMenuModel(gameLogic, gameBoardView);
        PauseMenuController pauseMenuController = new PauseMenuController(pauseMenuModel, this);
        pauseMenuController.showPauseMenu();
    }

    /**
     * Swap the scene to the winning board upon calling this method.
     */
    public void getWinningBoard() {
        WinningBoardController winningBoardController = new WinningBoardController(this, scoreBoardModel, gameLogic);
        winningBoardController.showWinningBoard();
    }

    /**
     * Swap the scene to the score board upon calling this method.
     */
    public void getScoreBoard(){
        scoreBoardModel = new ScoreBoardModel(gameLogic, nameInputBoxController);
        scoreBoardController = new ScoreBoardController(scoreBoardModel, this);
        scoreBoardController.showScoreBoard();
    }

    /**
     * Setter method for the current primary stage of the application.
     * @param primaryStage a Stage that of the current display stage.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Getter method for the current primary stage of the application.
     * This method is called and used for easy navigation around the
     * screen of the game.
     * @return the current Stage of the application.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
