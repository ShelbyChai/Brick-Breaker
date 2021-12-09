package brickdestroyer.model.entities_factory;


import brickdestroyer.model.entities.*;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class BrickFactory {
    public Brick getBrickType(String brickType, Point2D position, Dimension2D size) {
        if (brickType == null) {
            return null;
        }
        if (brickType.equalsIgnoreCase("Clay Brick")) {
            return new ClayBrick(position, size);
        }
        if (brickType.equalsIgnoreCase("Cement Brick")) {
            return new CementBrick(position, size);
        }
        if (brickType.equalsIgnoreCase("Steel Brick")) {
            return new SteelBrick(position, size);
        }
        if (brickType.equalsIgnoreCase("Haste Brick")) {
            return new HasteBrick(position, size);
        }
        if (brickType.equalsIgnoreCase("Black Stone Brick")) {
            return new BlackStoneBrick(position, size);
        }
        return null;
    }
}
