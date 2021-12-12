package brickdestroyer.model.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * Brick Factory uses the factory design pattern to generate different types of brick in the
 * game of Brick Destroyer.
 */
public class BrickFactory {
    /**
     * Generate a specified brick type with the given center position. This method will return
     * the newly created brick object if the brick type is available, null if the brick type is
     * not in the factory.
     * @param brickType a String that represent the brick type to be return by the factory.
     * @param position a Point2D x and y position that represent the upper-left bound of the brick.
     * @param size a Dimension2D value that represent the width/height of the brick.
     * @return the created Brick types.
     */
    public Brick getBrickType(String brickType, Point2D position, Dimension2D size) {
        if (brickType == null)
            return null;
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
