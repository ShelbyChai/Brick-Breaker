package brickdestroyer.model.game;


import brickdestroyer.view.GameBoardView;

public class DebugConsoleModel {
    private final GameLogic gameLogic;
    private final GameBoardView gameBoardView;

    public DebugConsoleModel(GameLogic gameLogic, GameBoardView gameBoardView) {
        this.gameLogic = gameLogic;
        this.gameBoardView = gameBoardView;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }

}
