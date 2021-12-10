package brickdestroyer.model.game;


import brickdestroyer.view.GameBoardView;

/**
 * Debug Console Model is responsible to provide all the data
 * needed to run the debug console panel.
 */
public class DebugConsoleModel {
    private final GameLogic gameLogic;
    private final GameBoardView gameBoardView;

    /**
     * @param gameLogic Game logic of the Brick Destroyer game.
     * @param gameBoardView View of the game.
     */
    public DebugConsoleModel(GameLogic gameLogic, GameBoardView gameBoardView) {
        this.gameLogic = gameLogic;
        this.gameBoardView = gameBoardView;
    }

    /**
     * Getter method to return the game logic object.
     * @return the game logic object.
     */
    public GameLogic getGameLogic() {
        return gameLogic;
    }

    /**
     * Getter method to return the view of the game.
     * @return the game board view object.
     */
    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }

}
