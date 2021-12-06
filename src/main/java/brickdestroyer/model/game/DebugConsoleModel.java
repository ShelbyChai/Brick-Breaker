package brickdestroyer.model.game;

import brickdestroyer.model.game.GameLogic;

public class DebugConsoleModel {
    private final GameLogic gameLogic;

    public DebugConsoleModel(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameBoardModel() {
        return gameLogic;
    }
}
