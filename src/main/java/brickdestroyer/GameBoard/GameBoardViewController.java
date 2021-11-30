package brickdestroyer.GameBoard;

import brickdestroyer.Actor.Ball;
import brickdestroyer.Actor.Brick;
import brickdestroyer.Actor.Levels;
import brickdestroyer.Actor.Player;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;


//public class GameBoardViewController extends Canvas{
public class GameBoardViewController{
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;
    private static final Color BG_COLOR = Color.WHITE;

    GameBoardModel gameBoardModel;
    private String message;
//    private DebugConsole debugConsole;
//    private Levels levels;
//    private PauseMenu pauseMenu;

    private Levels levels;

    private Canvas canvas;
    String userKeyInput = "";
    GraphicsContext gc;


    public GameBoardViewController(){
        message = "";
        gameBoardModel = new GameBoardModel(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),new Point2D(300,430));
        levels = new Levels(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2, gameBoardModel);

        levels.nextLevel();

        canvas = new Canvas(DEF_WIDTH,DEF_HEIGHT);
        canvas.setFocusTraversable(true);

        gc = canvas.getGraphicsContext2D();

        keyPressed(canvas);
        keyReleased(canvas);


        new AnimationTimer()
        {
            private long lastUpdate = 0;
            // Default: 10_000_000
            final private static long TIMER_DELAY = 1_000_000;
            @Override
            public void handle(long currentNanoTimer) {

                if (currentNanoTimer - lastUpdate >= TIMER_DELAY) {
                    paint(gc,gameBoardModel);

                    // TODO refactor this string thing
                    if (userKeyInput.contains("A")){
                        gameBoardModel.player.moveLeft();
                        gameBoardModel.player.move();
                    }
                    if (userKeyInput.contains("D")){
                        gameBoardModel.player.moveRight();
                        gameBoardModel.player.move();
                    }
                    if (userKeyInput.contains("SPACE")){
                    }

                    gameBoardModel.ball.move();
                    gameBoardModel.findImpacts();
                    message = String.format("Bricks: %d Balls %d", gameBoardModel.getBrickCount(), gameBoardModel.getBallCount());


                    if(gameBoardModel.isBallLost()){
                        if(gameBoardModel.ballEnd()){
                            gameBoardModel.wallReset();
                            message = "Game over";
                        }
                        gameBoardModel.ballReset();
//                        gameTimer.stop();
                    }
//                    else if(gameBoardModel.isDone()){
//                        if(levels.hasLevel()){
//                            message = "Go to Next Level";
//                            gameTimer.stop();
//                            gameBoardModel.ballReset();
//                            gameBoardModel.wallReset();
//                            levels.nextLevel();
//                        }
//                        else{
//                            message = "ALL WALLS DESTROYED";
////                            gameTimer.stop();
//                        }


                    lastUpdate = currentNanoTimer;
                }
            }
        }.start();
    }

    public Scene getGameScene() {
        return new Scene(new StackPane(canvas));
    }

//    private void initialize() {
//        this.setWidth(DEF_WIDTH);
//        this.setHeight(DEF_HEIGHT);
//        this.setFocusTraversable(true);
//        this.requestFocus();
//        this.addEventHandler();
//    }

    private void paint(GraphicsContext gc, GameBoardModel gameBoardModel) {
        gc.clearRect(0,0,DEF_WIDTH,DEF_HEIGHT);
        drawBall(gameBoardModel.ball,gc);

        gc.setFill(Color.BLUE);
        gc.fillText(message, 250,225);

        // Brick
        for(Brick b : gameBoardModel.bricks)
            if(b.isBroken()){
                drawBrick(b,gc);
            }



        drawPlayer(gameBoardModel.player,gc);
    }

    private void drawCrack(Path crack, GraphicsContext gc) {

    }

    private void drawBrick(Brick brick, GraphicsContext gc) {
//        gc.setFill(gameBoardModel.bricks);
        gc.setFill(brick.getInnerColor());
        gc.fillRect(brick.getPos().getX(),brick.getPos().getY(),brick.getSize().getWidth(),brick.getSize().getHeight());

        gc.setFill(brick.getBorderColor());
        gc.strokeRect(brick.getPos().getX(),brick.getPos().getY(),brick.getSize().getWidth(),brick.getSize().getHeight());
    }

    private void drawPlayer(Player player, GraphicsContext gc) {
        // Player fill Color
        gc.setFill(player.getInnerColor());
        gc.fillRect(player.getXUpperLeft(),player.getYUpperLeft(),player.getWidth(), player.getHeight());

        // Player border Color
        gc.setStroke(player.getBorderColor());
        gc.strokeRect(player.getXUpperLeft(), player.getYUpperLeft(), player.getWidth(), player.getHeight());
    }

    private void drawBall(Ball ball, GraphicsContext gc) {
        int ballRadius = ball.getRadius();
        double ballUpperLeftX = ball.getUpperLeftX();
        double ballUpperLeftY = ball.getUpperLeftY();

        // Ball fill Color
        gc.setFill(ball.getInnerColor());
        gc.fillOval(ballUpperLeftX,ballUpperLeftY,ballRadius,ballRadius);

        // Ball border Color
        gc.setStroke(ball.getBorderColor());
        gc.strokeOval(ballUpperLeftX,ballUpperLeftY,ballRadius,ballRadius);
    }


//    public void onLostFocus(){
//        gameTimer.stop();
//        message = "Focus Lost";
//        repaint();
////        pauseMenu.repaint();
//    }

    private void keyPressed(Canvas canvas) {
        canvas.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.A){
                userKeyInput = "A";
            }
            else if (e.getCode() == KeyCode.D){
                userKeyInput = "D";
            }
            else if (e.getCode() == KeyCode.ESCAPE){
                userKeyInput = "ESCAPE";
            }
            else if (e.getCode() == KeyCode.SPACE){
                userKeyInput = "SPACE";
            }
            else if (e.getCode() == KeyCode.F1){
                userKeyInput = "F1";
            }
        });
    }

    private void keyReleased(Canvas canvas) {
        canvas.setOnKeyReleased(e->{
            userKeyInput = "";
        });
    }

    public void setMessage(String message) {this.message = message;}
}
