package brickdestroyer.view;

import brickdestroyer.model.game.GameBoardModel;
import brickdestroyer.model.abstract_entities.Ball;
import brickdestroyer.model.abstract_entities.Brick;
import brickdestroyer.model.entities.Player;
import brickdestroyer.model.game.GameLogic;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

import java.util.Objects;


public class GameBoardView {

    private final GameLogic gameLogic;
    private final GameBoardModel gameBoardModel;

    private final Canvas canvas;
    private final GraphicsContext gc;

    public GameBoardView(GameBoardModel gameBoardModel, GameLogic gameLogic) {
        this.gameBoardModel = gameBoardModel;
        this.gameLogic = gameLogic;

        canvas = new Canvas(GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT);
        canvas.setFocusTraversable(true);
        gc = canvas.getGraphicsContext2D();
    }

    public void paint() {
        gc.clearRect(0,0,GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT);
        drawBall(gameLogic.getBall(),gc);

        drawMessage(gameBoardModel.getMessage(), gc);

        for(Brick brick : gameLogic.getBricks())
            if(brick.isBroken()) {
                drawBrick(brick, gc);
                if(Objects.equals(brick.getName(), "Cement Brick"))
                    drawCrack(brick.getCrackPath(), gc);
            }

        drawPlayer(gameLogic.getPlayer(),gc);
    }

    private void drawMessage(String message, GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillText(message, 250,225);
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

    private String[] parseStringPath(Path crackPath, int index) {
        String path = crackPath.getElements().get(index).toString();
        return path.replaceAll("[a-zA-Z]","").replaceAll("[=]","").replaceAll("[\\[-\\]]","").split(", ");
    }

    private void drawBrick(Brick brick, GraphicsContext gc) {
        gc.setFill(brick.getInnerColor());
        gc.fillRect(brick.getPosition().getX(),brick.getPosition().getY(),brick.getSize().getWidth(),brick.getSize().getHeight());

        gc.setFill(brick.getBorderColor());
        gc.strokeRect(brick.getPosition().getX(),brick.getPosition().getY(),brick.getSize().getWidth(),brick.getSize().getHeight());
    }

    private void drawPlayer(Player player, GraphicsContext gc) {
        gc.setFill(player.getInnerColor());
        gc.fillRect(player.getXUpperLeft(),player.getYUpperLeft(),player.getWidth(), player.getHeight());

        gc.setStroke(player.getBorderColor());
        gc.strokeRect(player.getXUpperLeft(), player.getYUpperLeft(), player.getWidth(), player.getHeight());
    }

    private void drawBall(Ball ball, GraphicsContext gc) {
        gc.setFill(ball.getInnerColor());
        gc.fillOval(ball.getUpperLeftX(),ball.getUpperLeftY(),ball.getRadius(),ball.getRadius());

        gc.setStroke(ball.getBorderColor());
        gc.strokeOval(ball.getUpperLeftX(),ball.getUpperLeftY(),ball.getRadius(),ball.getRadius());
    }

    public void repaintMessage(String message) {
        gameBoardModel.setMessage(message);
        paint();
    }

    public Canvas getCanvas() {return canvas;}

}
