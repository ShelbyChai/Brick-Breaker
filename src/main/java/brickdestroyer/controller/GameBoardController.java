package brickdestroyer.controller;


import brickdestroyer.SceneManager;
import brickdestroyer.model.game.GameBoardModel;
import brickdestroyer.view.GameBoardView;
import brickdestroyer.model.game.GameLogic;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GameBoardController {

    private final GameBoardModel gameBoardModel;
    private final GameLogic gameLogic;
    private final GameBoardView gameBoardView;
    private final SceneManager sceneManager;
    private Stage primaryStage;


    public GameBoardController(GameBoardModel gameBoardModel, GameBoardView gameBoardView, GameLogic gameLogic, SceneManager sceneManager) {
        this.gameBoardModel = gameBoardModel;
        this.gameLogic = gameLogic;
        this.gameBoardView = gameBoardView;
        this.sceneManager = sceneManager;

        this.gameLogic.nextLevel();

        initializeListener();

        this.gameBoardView.paint();
    }

    private void initializeListener() {
        keyPressed(this.gameBoardView.getCanvas());
        keyReleased(this.gameBoardView.getCanvas());
    }

    final private AnimationTimer animationTimer = new AnimationTimer() {

        private long lastUpdate = 0;
        // Default: 10_000_000
        final private static long TIMER_DELAY = 1_000_000;

        @Override
        public void handle(long currentNanoTimer) {
            if (currentNanoTimer - lastUpdate >= TIMER_DELAY) {
                gameBoardView.paint();

                setUserKeyInput();
                gameLogic.move();
                gameLogic.findImpacts();

                gameBoardView.repaintMessage(String.format("Bricks: %d Balls %d", gameLogic.getBrickCount(), gameLogic.getBallCount()));

                handleBallLost();
                handleNextLevel();

                lastUpdate = currentNanoTimer;
            }
        }

        @Override
        public void start(){
            super.start();
            gameBoardModel.setTimerRunning(true);
        }

        @Override
        public void stop(){
            super.stop();
            gameBoardModel.setTimerRunning(false);
        }
    };

    private void handleBallLost() {
        if (gameLogic.isBallLost()) {
            if (gameLogic.ballEnd()) {
                gameLogic.wallReset();
                gameBoardView.repaintMessage("Game Over");
                animationTimer.stop();
            }
            gameLogic.resetPoint();
            gameBoardView.paint();
            animationTimer.stop();
        }
    }

    private void handleNextLevel() {
        if (gameLogic.isDone()) {
            if (gameLogic.hasLevel()) {
                gameBoardView.repaintMessage("Go to Next Level");
                animationTimer.stop();
                gameLogic.resetPoint();
                gameLogic.wallReset();
                gameLogic.nextLevel();
            } else {
                gameBoardView.repaintMessage("All WALLS DESTROYED");
                animationTimer.stop();
            }
        }
    }

    private void setUserKeyInput(){
        if (gameBoardModel.getUserKeyInput().contains("A")) {
            gameLogic.getPlayer().moveLeft();
            gameLogic.getPlayer().move();
        }
        else if (gameBoardModel.getUserKeyInput().contains("D")) {
            gameLogic.getPlayer().moveRight();
            gameLogic.getPlayer().move();
        }
        else {
            gameLogic.getPlayer().stop();
        }
    }

    private void keyPressed(Canvas canvas) {
        canvas.setOnKeyPressed(keyEvent->{
            switch(keyEvent.getCode()){
                case A:
                    gameBoardModel.setUserKeyInput("A");
                    break;

                case D:
                    gameBoardModel.setUserKeyInput("D");
                    break;

                case ESCAPE:
                    animationTimer.stop();

                    primaryStage = (Stage)((Node)keyEvent.getSource()).getScene().getWindow();
                    primaryStage.getScene().getRoot().setEffect(new GaussianBlur());

                    sceneManager.setPrimaryStage(primaryStage);

                    Stage pauseMenu = sceneManager.getPauseMenu();
                    pauseMenu.initOwner(primaryStage);
                    pauseMenu.setX(primaryStage.getX());
                    pauseMenu.setY(primaryStage.getY());
                    pauseMenu.show();

                    break;

                case SPACE:
                    if(gameBoardModel.isTimerRunning())
                        animationTimer.stop();
                    else
                        animationTimer.start();
                    break;

                case F1:
                    if (keyEvent.isShiftDown() && keyEvent.isAltDown()){
                        animationTimer.stop();
                        Stage debugConsole = sceneManager.getDebugConsole();
                        debugConsole.initOwner(((Node)keyEvent.getSource()).getScene().getWindow());
                        debugConsole.show();
                    }
                    break;
            }
        });
    }

    private void keyReleased(Canvas canvas) {
        canvas.setOnKeyReleased(e-> gameBoardModel.setUserKeyInput(""));
    }

    public final Scene getGameBoard() {return new Scene(new StackPane(gameBoardView.getCanvas()));}

}
