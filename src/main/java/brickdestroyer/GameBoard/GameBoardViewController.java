package brickdestroyer.GameBoard;

import brickdestroyer.Actor.Ball;
import brickdestroyer.Actor.Player;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class GameBoardViewController {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private Canvas canvas;
    String input = "";
    GraphicsContext gc;

    GameBoardModel gameBoardModel;

    public GameBoardViewController(){

        gameBoardModel = new GameBoardModel(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),new Point2D(300,430));

        canvas = new Canvas(DEF_WIDTH,DEF_HEIGHT);
        canvas.setFocusTraversable(true);

        gc = canvas.getGraphicsContext2D();

        keyPressed(canvas);
        keyReleased(canvas);

        new AnimationTimer()
        {
            public void handle(long currentNanoTimer) {
                paint(gc,gameBoardModel);

                // TODO refactor this string thing
                if (input.contains("A")){
                    gameBoardModel.player.moveLeft();
                    gameBoardModel.player.move();
                }
                if (input.contains("D")){
                    gameBoardModel.player.moveRight();
                    gameBoardModel.player.move();
                }
                if (input.contains("SPACE")){
                }

                gameBoardModel.findImpacts();
                gameBoardModel.ball.move();

            }
        }.start();
    }

    public Scene getGameScene() {
        return new Scene(new StackPane(canvas));
    }

    private void paint(GraphicsContext gc, GameBoardModel gameBoardModel) {
        gc.clearRect(0,0,DEF_WIDTH,DEF_HEIGHT);
        drawBall(gameBoardModel.ball,gc);
        drawPlayer(gameBoardModel.player,gc);
    }



    private void keyPressed(Canvas canvas) {
        canvas.setOnKeyPressed(e->{
            if(e.getCode()== KeyCode.A){
                input = "A";
            }
            else if (e.getCode()== KeyCode.D){
                input = "D";
            }
            else if (e.getCode()== KeyCode.SPACE){
            }
        });
    }

    private void keyReleased(Canvas canvas) {
        canvas.setOnKeyReleased(e->{
            input="";
        });
    }


    private void drawPlayer(Player player, GraphicsContext gc) {
        // Create inner
        gc.setFill(gameBoardModel.player.getInnerColor());
        gc.fillRect(player.getXUpperLeft(),player.getYUpperLeft(),player.getWidth(), player.getHeight());

        // Create border
        gc.setStroke(gameBoardModel.player.getBorderColor());
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

}
