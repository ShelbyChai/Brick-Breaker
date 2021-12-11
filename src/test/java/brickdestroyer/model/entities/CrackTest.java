package brickdestroyer.model.entities;


import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Path;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CrackTest {

    /**
     * Creates a Crack object and return its crack path to crackPath variable.
     * This test case test whether the crack.draw() method insert the path element
     * in the crackPath variable. A successful test case will result a non-null
     * value in the crack.
     */
    @Test
    void draw() {
        Brick cementBrick = new CementBrick(new Point2D(300, 430), new Dimension2D(60, 20));
        Crack crack = new Crack(1, 35);
        Path crackPath;

        crack.makeCrack(new Point2D(300, 430), Brick.ImpactDirection.LEFT, cementBrick.getBrickFace());
        crackPath = crack.draw();

        makeCrack();
        assertNotNull(crackPath);
    }

    /**
     * This test case test whether the crack.reset() method works.
     */
    @Test
    void reset() {
        Brick cementBrick = new CementBrick(new Point2D(300, 430), new Dimension2D(60, 20));
        Crack crack = new Crack(1, 35);
        Path crackPath;

        crack.makeCrack(new Point2D(300, 430), Brick.ImpactDirection.LEFT, cementBrick.getBrickFace());
        crackPath = crack.draw();

        makeCrack();
        crack.reset();
        assertTrue(crackPath.getElements().isEmpty());
    }

    /**
     * Since the path is created on the brick this means the path element value should be less than
     * brick's Max X and Max Y position value. This test case retrieve the crackPath and parse the
     * crackPath element to get its x and y value. This test case checks whether the MaxX and MaxY
     * value of the brick is larger than the first value of crackPath.
     */
    @Test
    @Description("Test if the crack element value is within the brick bound")
    void makeCrack() {
        // Brick size x: 300-360, y: 430-450, crack path x and y should be lesser than 360 and 450.
        Brick cementBrick = new CementBrick(new Point2D(300, 430), new Dimension2D(60, 20));
        Bounds cementBound = cementBrick.getBrickFace().getBoundsInParent();
        Crack crack = new Crack(1, 35);
        Path crackPath = crack.draw();
        String path;
        String[] splitValue;
        double value1;
        double value2;

        crack.makeCrack(new Point2D(300, 430), Brick.ImpactDirection.LEFT, cementBrick.getBrickFace());
        path = crackPath.getElements().get(0).toString();
        splitValue = path.replaceAll("[a-zA-Z]", "").replaceAll("[=]", "").replaceAll("[\\[-\\]]", "").split(", ");

        value1 = Double.parseDouble(splitValue[0]);
        value2 = Double.parseDouble(splitValue[1]);

        assertAll("Test the boundary value for the max, min of x,y value",
                () -> assertTrue(value1 <= cementBound.getMaxX()),
                () -> assertTrue(value1 >= cementBound.getMinX()),
                () -> assertTrue(value2 <= cementBound.getMaxY()),
                () -> assertTrue(value2 >= cementBound.getMinY())
        );
    }
}
