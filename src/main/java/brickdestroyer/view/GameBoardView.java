package brickdestroyer.view;

import brickdestroyer.model.game.GameBoardModel;
import brickdestroyer.model.entities.Ball;
import brickdestroyer.model.entities.Brick;
import brickdestroyer.model.entities.Player;
import brickdestroyer.model.game.GameLogic;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;


public class GameBoardView {
    private final GameBoardModel gameBoardModel;
    private final GameLogic gameLogic;
    private final Canvas canvas;
    private final GraphicsContext gc;

    public GameBoardView(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
        this.gameLogic = gameBoardModel.getGameLogic();

        canvas = new Canvas(GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT);
        canvas.setFocusTraversable(true);
        gc = canvas.getGraphicsContext2D();
    }

    public void paint() {
        gc.clearRect(0,0,GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT);

        drawMessage(gameBoardModel.getMessage());
        drawHighScore(gameBoardModel.getScore());
        drawWall();
        drawBall(gameLogic.getBall());
        drawPlayer(gameLogic.getPlayer());
    }

    private void drawMessage(String message) {
        gc.setFill(Color.BLUE);
        gc.fillText(message, 250,225);
    }

    private void drawHighScore(String highScore) {
        gc.setFill(Color.BLUE);
        gc.fillText(highScore, 270, 200);
    }

    private void drawWall() {
        for(Brick brick : gameLogic.getBricks())
            if(brick.isBroken()) {
                drawBrick(brick);
                drawCrack(brick.getCrackPath());
            }
    }

    private void drawBrick(Brick brick) {
        gc.setFill(brick.getInnerColor());
        gc.fillRect(brick.getXPosition(), brick.getYPosition(), brick.getWidth(), brick.getHeight());

        gc.setStroke(brick.getBorderColor());
        gc.strokeRect(brick.getXPosition(), brick.getYPosition(), brick.getWidth(), brick.getHeight());
    }

    private void drawCrack(Path crackPath) {
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

    private String[] parseStringPath(Path crackPath, int index) {
        String path = crackPath.getElements().get(index).toString();
        return path.replaceAll("[a-zA-Z]","").replaceAll("[=]","").replaceAll("[\\[-\\]]","").split(", ");
    }

    private void drawBall(Ball ball) {
        gc.setFill(ball.getInnerColor());
        gc.fillOval(ball.getXPosition(), ball.getYPosition(), ball.getWidth(), ball.getHeight());

        gc.setStroke(ball.getBorderColor());
        gc.strokeOval(ball.getXPosition(), ball.getYPosition(), ball.getWidth(), ball.getHeight());
    }

    private void drawPlayer(Player player) {
        gc.setFill(player.getInnerColor());
        gc.fillRect(player.getXPosition(), player.getYPosition(), player.getWidth(), player.getHeight());

        gc.setStroke(player.getBorderColor());
        gc.strokeRect(player.getXPosition(), player.getYPosition(), player.getWidth(), player.getHeight());
    }

    public void repaintMessage(String message) {
        gameBoardModel.setMessage(message);
        paint();
    }

    public void repaintScore(String score) {
        gameBoardModel.setScore(score);
        paint();
    }

    public Canvas getCanvas() {return canvas;}

}
