package test;

import java.awt.*;

public class BrickFactory {
    // TODO Refactor: Try to make a get method to access the name of the type of brick
    public Brick getBrick(Point point, Dimension size, String brickType) {
        if (brickType == null) {
            return null;
        }
        // Find a way to not input the name manually
        if (brickType.equalsIgnoreCase("Clay Brick")) {
            return new ClayBrick(point, size);
        }
        if (brickType.equalsIgnoreCase("Cement Brick")) {
            return new CementBrick(point, size);
        }
        if (brickType.equalsIgnoreCase("Steel Brick")) {
            return new SteelBrick(point, size);
        }
        return null;
    }
}
