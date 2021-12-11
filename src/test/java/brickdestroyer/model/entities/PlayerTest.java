package brickdestroyer.model.entities;

import brickdestroyer.model.game.GameBoardModel;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import brickdestroyer.model.entities.Player;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player
            = new Player(new Point2D(300,430),150,10, new Rectangle(0, 0, GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT));


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