package brickdestroyer.Actor;


import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class BrickFactory {
    public Brick getBrick(Point2D point, Dimension2D size, String brickType) {
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
