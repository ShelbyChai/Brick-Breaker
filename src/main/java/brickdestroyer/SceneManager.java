package brickdestroyer;

import brickdestroyer.controller.DebugConsoleController;
import brickdestroyer.controller.GameBoardController;
import brickdestroyer.controller.HomeMenuController;
import brickdestroyer.controller.PauseMenuController;
import brickdestroyer.model.game.DebugConsoleModel;
import brickdestroyer.model.game.GameBoardModel;
import brickdestroyer.model.game.GameLogic;
import brickdestroyer.model.game.PauseMenuModel;
import brickdestroyer.view.GameBoardView;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SceneManager {

    private HomeMenuController homeMenuController;
    private GameBoardModel gameBoardModel;
    private GameBoardView gameBoardView;
    private GameBoardController gameBoardController;
    private DebugConsoleModel debugConsoleModel;
    private DebugConsoleController debugConsoleController;
    private PauseMenuModel pauseMenuModel;
    private PauseMenuController pauseMenuController;

    private final GameLogic gameLogic;
    private Stage primaryStage;
    private final VBox mainWindow;

    public SceneManager(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        mainWindow = getHomeMenu();
    }

    private VBox getHomeMenu() {
        homeMenuController = new HomeMenuController(this);
        return homeMenuController.getHomeMenu();
    }

    public Scene getGameBoard() {
        gameBoardModel = new GameBoardModel();
        gameBoardView = new GameBoardView(gameBoardModel, gameLogic);
        gameBoardController = new GameBoardController(gameBoardModel, gameBoardView, gameLogic, this);

        return gameBoardController.getGameBoard();
    }

    public Stage getDebugConsole() {
        debugConsoleModel = new DebugConsoleModel(gameLogic);
        debugConsoleController = new DebugConsoleController(debugConsoleModel, this);

        return debugConsoleController.getDebugConsole();
    }

    public Stage getPauseMenu() {
        pauseMenuModel = new PauseMenuModel(primaryStage,gameLogic, gameBoardView);
        pauseMenuController = new PauseMenuController(pauseMenuModel, this);

        return pauseMenuController.getPauseMenu();
    }

    public Scene getScene() {
        return new Scene(mainWindow);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
