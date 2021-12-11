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


/**
 * Game Board View is responsible to draw and the scene of the game.
 * This class uses a graphic context 2D which is available through
 * the use of canvas to draw the visual of the game.
 */
public class GameBoardView {
    private final GameBoardModel gameBoardModel;
    private final GameLogic gameLogic;
    private final Canvas canvas;
    private final GraphicsContext gc;

    /**
     * @param gameBoardModel Data of the scoreBoardModel.
     */
    public GameBoardView(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
        this.gameLogic = gameBoardModel.getGameLogic();

        canvas = new Canvas(GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT);
        canvas.setFocusTraversable(true);
        gc = canvas.getGraphicsContext2D();
    }

    /**
     * This is the master method of all draw method. It accumulates and fire the
     * behavior of each drawing method in this class.
     */
    public void paint() {
        gc.clearRect(0,0,GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT);

        drawMessage(gameBoardModel.getMessage());
        drawHighScore(gameBoardModel.getScore());
        drawWall();
        drawBall(gameLogic.getBall());
        drawPlayer(gameLogic.getPlayer());
    }

    /**
     * This method is called by paint() method to draw the String message
     * on the canvas.
     * @param message a String that represent the message to be paint on the screen. This
     *                message contains the number of brick and ball available for the player.
     */
    private void drawMessage(String message) {
        gc.setFill(Color.BLUE);
        gc.fillText(message, 250,225);
    }

    /**
     * This method is called by paint() method to draw the String highScore
     * on the canvas.
     * @param highScore a String that represent the score of the player throughout the
     *                  game.
     */
    private void drawHighScore(String highScore) {
        gc.setFill(Color.BLUE);
        gc.fillText(highScore, 270, 200);
    }

    /**
     * This method is called by paint() method to draw the array of brick of the current level
     * on the canvas.
     */
    private void drawWall() {
        for(Brick brick : gameLogic.getBricks())
            if(brick.isBroken()) {
                drawBrick(brick);
                drawCrack(brick.getCrackPath());
            }
    }

    /**
     * This method is called by drawWall() method to draw a brick using its properties.
     * @param brick a Brick type object that represent the brick in the game.
     *              It contains the properties of the current brick to be drawn on the canvas.
     */
    private void drawBrick(Brick brick) {
        gc.setFill(brick.getInnerColor());
        gc.fillRect(brick.getXPosition(), brick.getYPosition(), brick.getWidth(), brick.getHeight());

        gc.setStroke(brick.getBorderColor());
        gc.strokeRect(brick.getXPosition(), brick.getYPosition(), brick.getWidth(), brick.getHeight());
    }

    /**
     * This method is called by drawWall() method to draw the crack path of a crackable
     * brick on the canvas.
     * @param crackPath a Path object that contains the path element of the crack path of
     *                  a brick.
     */
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

    /**
     * This method is called by drawCrack() method to parse the String of the crackPath
     * into each individual path element in the form of string.
     * @param crackPath a Path object that contains the path element to be drawn on the game window.
     * @param index an Integer that represent the index value of the for loop.
     * @return a 1d array String that contains all the path element of a given crack path of a brick.
     */
    private String[] parseStringPath(Path crackPath, int index) {
        String path = crackPath.getElements().get(index).toString();
        return path.replaceAll("[a-zA-Z]","").replaceAll("[=]","").replaceAll("[\\[-\\]]","").split(", ");
    }

    /**
     * This method is called by paint() method to draw the ball using the given ball
     * properties.
     * @param ball a Ball object that represent the travelling ball in the game. It contains the basic
     *             properties of the current ball to be drawn on the canvas.
     */
    private void drawBall(Ball ball) {
        gc.setFill(ball.getInnerColor());
        gc.fillOval(ball.getXPosition(), ball.getYPosition(), ball.getWidth(), ball.getHeight());

        gc.setStroke(ball.getBorderColor());
        gc.strokeOval(ball.getXPosition(), ball.getYPosition(), ball.getWidth(), ball.getHeight());
    }

    /**
     * This method is called by paint() method to draw the player using the given player
     * properties.
     * @param player a Player object that represent the entity to be played and controlled by
     *               the user. It contains the basic properties of a Player Object to be drawn
     *               on the canvas.
     */
    private void drawPlayer(Player player) {
        gc.setFill(player.getInnerColor());
        gc.fillRect(player.getXPosition(), player.getYPosition(), player.getWidth(), player.getHeight());

        gc.setStroke(player.getBorderColor());
        gc.strokeRect(player.getXPosition(), player.getYPosition(), player.getWidth(), player.getHeight());
    }

    /**
     * This method is called to specifically repaint the new message to the canvas.
     * @param message a String that represent the message to be paint on the screen. This
     *               message contains the number of brick and ball available for the player.
     */
    public void repaintMessage(String message) {
        gameBoardModel.setMessage(message);
        paint();
    }

    /**
     * This method is called to specifically repaint the new score of the player to the
     * canvas.
     * @param score a String that represent the score of the player throughout the
     *             game.
     */
    public void repaintScore(String score) {
        gameBoardModel.setScore(score);
        paint();
    }

    public Canvas getCanvas() {return canvas;}

}
