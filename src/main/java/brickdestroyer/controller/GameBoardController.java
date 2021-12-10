package brickdestroyer.controller;


import brickdestroyer.SceneManager;
import brickdestroyer.model.entities.Levels;
import brickdestroyer.model.game.GameBoardModel;
import brickdestroyer.view.GameBoardView;
import brickdestroyer.model.game.GameLogic;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;


/**
 * Game Board Controller handle all the user interaction on the
 * Player object and set the view logic of the main game screen.
 * It uses animation timer that sets the view logic flow of the
 * overall game.
 */
public class GameBoardController{

    private final GameBoardModel gameBoardModel;
    private final GameLogic gameLogic;
    private final GameBoardView gameBoardView;
    private final SceneManager sceneManager;
    private Stage primaryStage;


    /**
     * @param gameBoardModel Data of the gameBoardController.
     * @param gameBoardView Visuals of the game.
     * @param sceneManager For changing the stage and scene of the game.
     */
    public GameBoardController(GameBoardModel gameBoardModel, GameBoardView gameBoardView, SceneManager sceneManager) {
        this.gameBoardModel = gameBoardModel;
        this.gameLogic = gameBoardModel.getGameLogic();
        this.gameBoardView = gameBoardView;
        this.sceneManager = sceneManager;

        this.gameLogic.nextLevel();
        initializeListener();
        this.gameBoardView.paint();
    }

    /**
     * This class add listener to the canvas when the
     * game board controller object is created.
     */
    private void initializeListener() {
        keyPressed(this.gameBoardView.getCanvas());
        keyReleased(this.gameBoardView.getCanvas());
    }

    /**
     * This method is called to set scene of game board.
     */
    public void showGameBoard() {
        primaryStage = sceneManager.getPrimaryStage();
        primaryStage.setScene(new Scene(new StackPane(gameBoardView.getCanvas())));
    }

    /**
     * This is the main timer loop of the game. This class will refresh
     * the view and check the statement and behavior in the handle() method
     * every 10 millisecond. It contains all the necessary statement in
     * order for the main game to function properly.
     */
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

                gameBoardView.repaintScore("Score: " + gameLogic.getScore());
                gameBoardView.repaintMessage(String.format("Bricks: %d Balls %d", gameLogic.getBrickCount(), gameLogic.getBallCount()));

                handleBallLost();

                try {
                    handleNextLevel();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

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

    /**
     * This method is called by the handle() method in animation timer class
     * to define how the information should be displayed after a ball is lost
     * in the game.
     */
    private void handleBallLost() {
        if (gameLogic.isBallLost()) {
            if (gameLogic.ballEnd()) {
                gameLogic.wallReset();
                gameBoardView.repaintMessage("Game Over");
                animationTimer.stop();
                sceneManager.getScoreBoard();
            }
            gameLogic.resetPoint();
            gameBoardView.paint();
            animationTimer.stop();
        }
    }

    /**
     * This method is called by the handle() method in animation timer class
     * to define how the information should be displayed after a level is cleared
     * by the player.
     * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    private void handleNextLevel() throws FileNotFoundException {
        if (gameLogic.isDone()) {
            if (gameLogic.hasLevel()) {

                gameBoardView.repaintMessage("Go to Next Level");
                animationTimer.stop();
                showCurrentLevelComplete();
                gameLogic.resetPoint();
                gameLogic.nextLevel();
                animationTimer.stop();

            } else {
                gameBoardView.repaintMessage("All WALLS DESTROYED");
                animationTimer.stop();

                sceneManager.getScoreBoard();
            }
        }
    }

    /**
     * This method is called to move and update the position of the player if
     * the user entered the specified key.
     */
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

    /**
     * This method act as a key listener for the canvas when a key is pressed. It defines and
     * fire the necessary behavior for the basic key interaction of the game.
     * @param canvas represents a blank rectangular area of the screen onto which the application
     *               can draw or from which the application can trap input events from the user.
     */
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
                    sceneManager.setPrimaryStage(primaryStage);
                    primaryStage.getScene().getRoot().setEffect(new GaussianBlur());
                    sceneManager.getPauseMenu();

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
                        sceneManager.getDebugConsole();
                    }
                    break;

                case F2:
                    sceneManager.getWinningBoard();
                    break;
            }
        });
    }

    /**
     * This method is called to display an information pop-up box of the level summary
     * after the player completed a level.
     */
    private void showCurrentLevelComplete() {
        int remainingLevel = Levels.LEVELS_COUNT - gameLogic.getCurrentLevel();
        Alert summaryLevelBox = new Alert(Alert.AlertType.INFORMATION);
        summaryLevelBox.setTitle("Level Summary");
        summaryLevelBox.setHeaderText("Level " + gameLogic.getCurrentLevel() + " Complete!!!");
        summaryLevelBox.setContentText
                ("Current score: " + gameLogic.getScore() + ", Remaining Ball: " + gameLogic.getBallCount() + ", LevelLeft: " + remainingLevel);
        summaryLevelBox.show();
    }

    /**
     * This method act as a key listener for the canvas when a key is released. It sets the user input
     * value to an empty string to stop the user from continuously moving.
     * @param canvas represents a blank rectangular area of the screen onto which the application
     *              can draw or from which the application can trap input events from the user.
     */
    private void keyReleased(Canvas canvas) {
        canvas.setOnKeyReleased(e-> gameBoardModel.setUserKeyInput(""));
    }

}
