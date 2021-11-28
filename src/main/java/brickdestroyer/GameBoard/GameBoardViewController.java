package brickdestroyer.GameBoard;

import brickdestroyer.Actor.Ball;
import brickdestroyer.Actor.Player;
import brickdestroyer.Actor.RubberBall;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.security.Key;


public class GameBoardViewController {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

//    private StackPane stackPane;
    private Canvas canvas;
    private Timeline t1;

    GameBoardModel gameBoardModel;

    public GameBoardViewController() {
        gameBoardModel = new GameBoardModel(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),new Point2D(300,430));

        canvas = new Canvas(DEF_WIDTH,DEF_HEIGHT);
        canvas.setFocusTraversable(true);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        t1 = new Timeline(new KeyFrame(Duration.millis(10), e-> run(gc)));
        t1.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnKeyTyped(e->{});

        canvas.setOnKeyPressed(e->{
                if(e.getCode()== KeyCode.A){
                    gameBoardModel.player.moveLeft();
                }
                else if (e.getCode()== KeyCode.D){
                    gameBoardModel.player.moveRight();
                }
            gameBoardModel.player.move();
        });
    }

    public Scene getGameScene() {
        return new Scene(new StackPane(canvas));
    }

    public void start() {
        t1.play();
    }

    private void run(GraphicsContext gc) {
        gc.clearRect(0,0,DEF_WIDTH,DEF_HEIGHT);
        drawPlayer(gameBoardModel.player,gc);
//        gameBoardModel.player.move();
//        drawBall(gameBoardModel.ball,gc);
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
//        gc.fillOval(gameBoardModel.ball.);
        gc.setFill(gameBoardModel.ball.getInnerColor());
        gc.fillOval(300,430,10,10);

        gc.setStroke(gameBoardModel.ball.getBorderColor());
        gc.strokeOval(300,430,10,10);
    }
}
