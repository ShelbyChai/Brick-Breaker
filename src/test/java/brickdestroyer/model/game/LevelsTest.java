package brickdestroyer.model.game;

import brickdestroyer.model.entities.Brick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class LevelsTest {
    Levels levels = new Levels(30, 3, 3);

    @Test
    void getLevels() {
        assertNotNull(levels.getLevels());
    }

    /**
     * This method test the first and second brick type in the first level. The first level
     * contains only Clay Brick and Cement Brick.
     */
    @Test
    void testBrickType() {
        Brick[][] bricks = levels.getLevels();
        Brick[] levelOneBricks = bricks[1];

        assertAll("Test the first and second brick",
                () -> assertEquals("Clay Brick" ,levelOneBricks[0].getName()),
                () -> assertEquals("Cement Brick" ,levelOneBricks[1].getName())
        );
    }

    /**
     * This method test the first, tenth and twentieth y position value.
     * The height of the brick is 20 so the first brick in every row should
     * be incremented by 20.
     */
    @Test
    void testYPositionOfBrick() {
        Brick[][] bricks = levels.getLevels();
        Brick[] brickNum = bricks[0];

        int[] brickYPosition = {0, 20, 40};

        assertAll(
                () -> assertEquals(brickYPosition[0], brickNum[0].getYPosition()),
                () -> assertEquals(brickYPosition[1], brickNum[10].getYPosition()),
                () -> assertEquals(brickYPosition[2], brickNum[20].getYPosition())
        );
    }
}