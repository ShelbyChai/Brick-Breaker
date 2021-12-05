package brickdestroyer.DebugConsole;

import brickdestroyer.GameBoard.GameBoardModel;

public class DebugConsoleModel {
    private final GameBoardModel gameBoardModel;

    public DebugConsoleModel(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
    }

    public GameBoardModel getGameBoardModel() {
        return gameBoardModel;
    }
}
