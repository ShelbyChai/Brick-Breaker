package brickdestroyer.GameBoard;

import brickdestroyer.Actor.Ball;
import brickdestroyer.Actor.Brick;
import brickdestroyer.Actor.Levels;
import brickdestroyer.Actor.Player;
import brickdestroyer.BrickDestroyerApplication;
import brickdestroyer.DebugConsole.DebugConsoleController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Popup;

import java.io.IOException;


public class GameBoardViewController {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    GameBoardModel gameBoardModel;
    private String message;
//    private DebugConsole debugConsole;
//    private PauseMenu pauseMenu;

    private Levels levels;

    private Canvas canvas;
    String userKeyInput = "";
    GraphicsContext gc;
    private volatile boolean isTimerRunning;


    public GameBoardViewController() {
        message = "";
        gameBoardModel = new GameBoardModel(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), new Point2D(300, 430));
        levels = new Levels(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6/2, gameBoardModel);

        levels.nextLevel();

        canvas = new Canvas(DEF_WIDTH, DEF_HEIGHT);
        canvas.setFocusTraversable(true);

        gc = canvas.getGraphicsContext2D();

        keyPressed(canvas);
        keyReleased(canvas);

        animationTimer.start();
    }

    public void setMessage(String message) {this.message = message;}

    public Scene getGameScene() {
        return new Scene(new StackPane(canvas));
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate = 0;
        // Default: 10_000_000
        final private static long TIMER_DELAY = 1_000_000;

        @Override
        public void handle(long currentNanoTimer) {
            if (currentNanoTimer - lastUpdate >= TIMER_DELAY) {
                paint(gc, gameBoardModel);

                setUserKeyInput();
                gameBoardModel.move();
                gameBoardModel.findImpacts();
                message = String.format("Bricks: %d Balls %d", gameBoardModel.getBrickCount(), gameBoardModel.getBallCount());


                if (gameBoardModel.isBallLost()) {
                    if (gameBoardModel.ballEnd()) {
                        gameBoardModel.wallReset();
                        message = "Game over";
                    }
                    gameBoardModel.ballReset();
                    stopAnimationTimer();


                } else if (gameBoardModel.isDone()) {
                    if (levels.hasLevel()) {
                        message = "Go to Next Level";
                        stopAnimationTimer();
                        gameBoardModel.ballReset();
                        gameBoardModel.wallReset();
                        levels.nextLevel();

                    } else {
                        message = "ALL WALLS DESTROYED";
                        stopAnimationTimer();
                    }
                }
                lastUpdate = currentNanoTimer;
            }
        }

        @Override
        public void start(){
            super.start();
            isTimerRunning = true;
        }

        @Override
        public void stop(){
            super.stop();
            isTimerRunning = false;
        }

        // This function is to stop the animationTimer after a certain interval
        public void stopAnimationTimer() {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            animationTimer.stop();
                        }
                    },
                    10
            );
        }
    };



//    private void initialize() {
//        this.setWidth(DEF_WIDTH);
//        this.setHeight(DEF_HEIGHT);
//        this.setFocusTraversable(true);
//        this.requestFocus();
//        this.addEventHandler();
//    }



    private void paint(GraphicsContext gc, GameBoardModel gameBoardModel) {
        gc.clearRect(0,0,DEF_WIDTH,DEF_HEIGHT);
        drawBall(gameBoardModel.getBall(),gc);

        gc.setFill(Color.BLUE);
        gc.fillText(message, 250,225);

        for(Brick b : gameBoardModel.getBrick())
            if(b.isBroken()){
                drawBrick(b,gc);
                drawCrack(b.getCrackPath(),gc);
            }

        drawPlayer(gameBoardModel.getPlayer(),gc);
    }

    private void drawCrack(Path crackPath, GraphicsContext gc) {
        if (crackPath != null && !crackPath.getElements().isEmpty()) {

            gc.beginPath();
            for (int step = 0; step < Brick.DEF_STEPS; step++) {
                String[] positionXY = parseStringPath(crackPath, step);
                if (step == 0)
                    gc.moveTo(Double.parseDouble(positionXY[0]), Double.parseDouble(positionXY[1]));
                gc.lineTo(Double.parseDouble(positionXY[0]), Double.parseDouble(positionXY[1]));
            }
            gc.fill();
            gc.closePath();
            gc.stroke();
        }

    }

    public String[] parseStringPath(Path crackPath, int index) {
        String path = crackPath.getElements().get(index).toString();
        String value = path.replaceAll("[a-zA-Z]","").replaceAll("[=]","").replaceAll("[\\[-\\]]","");
        return value.split(", ");
    }

    private void drawBrick(Brick brick, GraphicsContext gc) {
        gc.setFill(brick.getInnerColor());
        gc.fillRect(brick.getPos().getX(),brick.getPos().getY(),brick.getSize().getWidth(),brick.getSize().getHeight());

        gc.setFill(brick.getBorderColor());
        gc.strokeRect(brick.getPos().getX(),brick.getPos().getY(),brick.getSize().getWidth(),brick.getSize().getHeight());
    }

    private void drawPlayer(Player player, GraphicsContext gc) {
        gc.setFill(player.getInnerColor());
        gc.fillRect(player.getXUpperLeft(),player.getYUpperLeft(),player.getWidth(), player.getHeight());

        gc.setStroke(player.getBorderColor());
        gc.strokeRect(player.getXUpperLeft(), player.getYUpperLeft(), player.getWidth(), player.getHeight());
    }

    private void drawBall(Ball ball, GraphicsContext gc) {
        int ballRadius = ball.getRadius();
        double ballUpperLeftX = ball.getUpperLeftX();
        double ballUpperLeftY = ball.getUpperLeftY();

        gc.setFill(ball.getInnerColor());
        gc.fillOval(ballUpperLeftX,ballUpperLeftY,ballRadius,ballRadius);

        gc.setStroke(ball.getBorderColor());
        gc.strokeOval(ballUpperLeftX,ballUpperLeftY,ballRadius,ballRadius);
    }

    private void setUserKeyInput(){
        // TODO refactor this string thing
        if (userKeyInput.contains("A")) {
            gameBoardModel.getPlayer().moveLeft();
            gameBoardModel.getPlayer().move();
        }
        else if (userKeyInput.contains("D")) {
            gameBoardModel.getPlayer().moveRight();
            gameBoardModel.getPlayer().move();
        }
        else {
            gameBoardModel.getPlayer().stop();
        }
    }

    private void keyPressed(Canvas canvas) {
        canvas.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.A){
                userKeyInput = "A";
            }
            else if (e.getCode() == KeyCode.D){
                userKeyInput = "D";
            }
            else if (e.getCode() == KeyCode.ESCAPE){
                animationTimer.stop();
            }
            else if (e.getCode() == KeyCode.SPACE){
                if(isTimerRunning) {
                    animationTimer.stop();
                }
                else
                    animationTimer.start();
            }
            else if (e.getCode() == KeyCode.F1) {
//                getDebugConsole();
            }
        });
    }

    private void keyReleased(Canvas canvas) {
        canvas.setOnKeyReleased(e->{
            userKeyInput = "";
        });
    }

//    Popup popup;
//    DebugConsoleController debugConsoleController;
//    FXMLLoader fxmlLoader;
//
//    public void getDebugConsole() {
//        popup = new Popup();
//        debugConsoleController = new DebugConsoleController(gameBoardModel,levels);
//        fxmlLoader = new FXMLLoader(BrickDestroyerApplication.class.getResource("/DebugConsoleView.fxml"));
//        fxmlLoader.setController(debugConsoleController);
//        try {
//            popup.getContent().add((Node)fxmlLoader.load());
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
}
