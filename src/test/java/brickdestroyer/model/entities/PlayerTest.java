package brickdestroyer.model.entities;

import brickdestroyer.model.game.GameBoardModel;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {
    Point2D initialPosition = new Point2D(300, 430);
    Player player
            = new Player(initialPosition,150,10, new Rectangle(0, 0, GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT));

    /**
     * This method test whether the player can move over the defined min X position of the border.
     */
    @Test
    void move() {
        int moveLoop = 1000;
        for (int i = 0 ; i < moveLoop; i++) {
            player.moveLeft();
            player.move();
        }

        assertEquals(player.getMin(), player.getBallPoint().getX());
    }

    @Test
    void moveTo() {
        player.moveTo(new Point2D(250, 430));
        assertAll(
                () -> assertEquals(250, player.getBallPoint().getX()),
                () -> assertEquals(430, player.getBallPoint().getY())
        );
    }

    @Test
    void stop() {
        player.moveLeft();
        player.stop();
        assertEquals(0, player.getMoveAmount());
    }

    @Test
    void moveLeft() {
        player.moveLeft();
        assertEquals(-5, player.getMoveAmount());
    }

    @Test
    void moveRight() {
        player.moveRight();
        assertEquals(5, player.getMoveAmount());
    }

    @Test
    void getPlayerFace() {
        assertNotNull(player.getPlayerFace());
    }

    @Test
    void getBorderColor() {
        assertEquals(Color.GREEN.darker().darker(), player.getBorderColor());
    }

    @Test
    void getInnerColor() {
        assertEquals(Color.GREEN, player.getInnerColor());
    }

    @Test
    void getXPosition() {
        player.moveLeft();
        player.move();
        assertEquals(220,player.getXPosition());
    }

    @Test
    void getYPosition() {
        assertEquals(430, player.getYPosition());
    }

    @Test
    void getWidth() {
        assertEquals(150, player.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(10, player.getHeight());
    }


}