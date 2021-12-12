package brickdestroyer.model.game;

import brickdestroyer.model.entities.Ball;
import brickdestroyer.model.entities.Brick;
import brickdestroyer.model.entities.Player;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import javafx.geometry.Point2D;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


class GameLogicTest {
    Rectangle area = new Rectangle(0,0, GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT);
    Point2D initialPosition = new Point2D(300, 430);
    GameLogic gameLogic = new GameLogic(area, initialPosition);

    Player player = gameLogic.getPlayer();
    Ball ball = gameLogic.getBall();

    /**
     * This test method start the movement of the player and ball and fire the move() method in
     * gameLogic to update their position, then check if the player and ball is still remaining at the
     * initial position. This test is successful if the center point of player and ball is not the same
     * as the initial position.
     */
    @Test
    void move() {
        player.moveLeft();
        gameLogic.initialiseBallSpeed();

        gameLogic.move();

        assertAll(
                () -> assertNotEquals(initialPosition, player.getBallPoint()),
                () -> assertNotEquals(initialPosition, ball.getCenter())
        );

    }

    /**
     * This test method start the movement of the player and ball,
     * then it resets it to its initial position. The test is successful
     * if the center point of the ball and player is equal to the initial
     * position.
     */
    @Test
    void resetPoint() {
        int playerMoveLeftCount= 100;
        for (int i = 0; i < playerMoveLeftCount; i++)
            player.moveLeft();

        gameLogic.initialiseBallSpeed();
        gameLogic.move();

        player.stop();
        ball.setSpeed(0,0);
        gameLogic.resetPoint();

        assertAll(
                () -> assertEquals(initialPosition, player.getBallPoint()),
                () -> assertEquals(initialPosition, ball.getCenter())
        );;
    }

    /**
     * This test method spawn the ball in a brick and use findImpacts() method to
     * detect the collision between ball and brick to reduce the brick count by 1.
     * This method then use wallReset() method to reset the brickCount.
     */
    @Test
    void wallReset() {
        gameLogic.nextLevel();
        int initialNumBrick = gameLogic.getBrickCount();
        ball.moveTo(new Point2D(20,20));
        gameLogic.move();
        gameLogic.findImpacts();

        Brick[] brick = gameLogic.getBricks();
        System.out.println("BallCount before wallReset: " + gameLogic.getBrickCount());

        gameLogic.wallReset();
        assertEquals(initialNumBrick, gameLogic.getBrickCount());
        System.out.println("BallCount after wallReset: " + gameLogic.getBrickCount());

    }

    /**
     * This test method test whether the nextLevel() method increment the currentLevel
     * variable count.
     */
    @Test
    void nextLevel() {
        System.out.println("Level before using nextLevel() method: " + gameLogic.getCurrentLevel());
        gameLogic.nextLevel();
        assertEquals(1, gameLogic.getCurrentLevel());
        System.out.println("Level after using nextLevel() method: " + gameLogic.getCurrentLevel());
    }

    /**
     * This test method use test the ball speed using border value testing approach.
     */
    @Test
    void initialiseBallSpeed() {
        gameLogic.initialiseBallSpeed();

        System.out.println("Speed X of the ball: " + ball.getSpeedX());
        System.out.println("Speed Y of the ball: " + ball.getSpeedY());
        assertAll(
                () -> assertTrue(-4 < ball.getSpeedY()),
                () -> assertTrue(1 > ball.getSpeedY()),
                () -> assertTrue(-4 < ball.getSpeedX()),
                () -> assertTrue(4 > ball.getSpeedX())

        );
    }

    @Test
    void findImpacts() {

    }

    /**
     * This test method test whether the speedUp() method set
     * the speed of the x and y to the highest randomize speed value.
     */
    @Test
    void speedUp() {
        gameLogic.speedUp(true);
        assertAll(
                () -> assertEquals(3, ball.getSpeedX()),
                () -> assertEquals(-3, ball.getSpeedY())
        );
    }

    /**
     * This test method test the hasLevel() method by incrementing the level
     * count to the highest.
     */
    @Test
    void hasLevel() {
        for (int i = 0; i< Levels.LEVELS_COUNT; i++) {
            gameLogic.nextLevel();
        }

        assertFalse(gameLogic.hasLevel());
    }


    /**
     * This test method test the setter of setBallSpeedX.
     */
    @Test
    void setBallSpeedX() {
        gameLogic.setBallSpeedX(3);
        assertEquals(3, ball.getSpeedX());
    }

    /**
     * This test method test the setter of setBallSpeedY.
     */
    @Test
    void setBallSpeedY() {
        gameLogic.setBallSpeedY(-3);
        assertEquals(-3, ball.getSpeedY());
    }

    @Test
    void setBricks() {
    }

    /**
     * This test method test the setter method of setBrickCount()
     */
    @Test
    void setBrickCount() {
        gameLogic.setBrickCount(30);
        assertEquals(30, gameLogic.getBrickCount());
    }

    /**
     * This test method test the getter method if getBallCount(0
     */
    @Test
    void getBallCount() {
        assertEquals(3, gameLogic.getBallCount());
    }

    /**
     * This test method test the getter method of brickCount
     * by calling a nextLevel() method to instantiate the
     * bricks object in the current level.
     */
    @Test
    void getBrickCount() {
        gameLogic.nextLevel();
        assertEquals(31, gameLogic.getBrickCount());
    }

    /**
     * This test method test the getter method of getScore().
     * The method call nextLevel() twice since the first called
     * of nextLevel() method didn't increase the player score as
     * it is the start of the game.
     */
    @Test
    void getScore() {

        gameLogic.nextLevel();
        gameLogic.nextLevel();
        assertEquals(500, gameLogic.getScore());
    }

    /**
     * This test method test the getter for Player object.
     */
    @Test
    void getPlayer() {
        assertNotNull(player);
    }

    /**
     * This test method test the getter for Ball object.
     */
    @Test
    void getBall() {
        assertNotNull(ball);
    }

    /**
     * This test method test the getter for Bricks object.
     */
    @Test
    void getBricks() {
        gameLogic.nextLevel();
        assertNotNull(gameLogic.getBricks());
    }

    /**
     * This test method test the getter CurrentLevel() by using nextLevel() method.
     */
    @Test
    void getCurrentLevel() {
        gameLogic.nextLevel();
        assertEquals(1, gameLogic.getCurrentLevel());
    }
}