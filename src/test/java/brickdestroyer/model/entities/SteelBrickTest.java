package brickdestroyer.model.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {
    Dimension2D dimBrick = new Dimension2D(60, 20);
    Point2D initialPosition = new Point2D(0,0);
    Brick steelBrick = new SteelBrick(initialPosition, dimBrick);
    Brick cementBrick = new CementBrick(initialPosition, dimBrick);

    /**
     * This test method test the setImpact() for a total of 1000 times to check the
     * impact probability of the steel brick. A single true in the list of array means the test
     * is successful.
     */
    @Test
    void impact() {
        int numOfTest = 1000;
        List<Boolean> testImpact = new ArrayList<Boolean>();
        for (int i =0; i< numOfTest; i++) {
            testImpact.add(steelBrick.setImpact());
        }

        assertTrue(testImpact.contains(true));
    }

    @Test
    void makeBrickFace() {
        assertNotNull(steelBrick.getBrickFace());
    }

    @Test
    void getCrackPath() {
        assertNull(steelBrick.getCrackPath());
    }

    @Test
    void getName() {
        assertEquals("Steel Brick", steelBrick.getName());
    }

    @Test
    void getBrickFace() {
        assertNotNull(steelBrick.getBrickFace());
    }

    @Test
    void getBorderColor() {
        assertEquals(Color.BLACK, steelBrick.getBorderColor());
    }

    @Test
    void getInnerColor() {
        assertEquals(Color.rgb(203, 203, 201), steelBrick.getInnerColor());
    }

    @Test
    void getXPosition() {
        assertEquals(initialPosition.getX() ,steelBrick.getXPosition());
    }

    @Test
    void getYPosition() {
        assertEquals(initialPosition.getY() ,steelBrick.getYPosition());
    }

    @Test
    void getWidth() {
        assertEquals(60 ,steelBrick.getWidth());
    }

    @Test
    void getHeight() {
        double expectedHeightVal = steelBrick.getWidth() / 3;
        assertEquals(expectedHeightVal ,steelBrick.getHeight());
    }

    /* The test below is using clayBrick Instead */

    @Test
    void repair() {
        cementBrick.impact();
        cementBrick.impact();
        cementBrick.repair();
        assertFalse(cementBrick.setImpact());
    }

    @Test
    void isBroken() {
        cementBrick.impact();
        assertTrue(cementBrick.setImpact());
    }



}