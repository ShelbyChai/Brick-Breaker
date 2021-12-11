package brickdestroyer.model.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {
    Brick steelBrick = new SteelBrick(new Point2D(0, 0), new Dimension2D(60, 20));

    @Test
    void makeBrickFace() {
        assertNotNull(steelBrick.getBrickFace());
    }

    @Test
    void getCrackPath() {
        assertNotNull(steelBrick.getCrackPath());
    }

    /**
     * This method repeat the test of setImpact() for a total of 100 times.
     * It checks the probability of impact on the steel brick. If a portion of test
     * out of the 100 test succeed then this test is successful.
     */
    @RepeatedTest(100)
    void impact() {
        assertTrue(steelBrick.setImpact());
    }
}