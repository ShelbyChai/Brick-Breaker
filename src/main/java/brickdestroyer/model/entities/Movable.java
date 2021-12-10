package brickdestroyer.model.entities;

import javafx.geometry.Point2D;

/**
 * This interface is to ensure all movable entities will
 * have the behavior of moving in the game of Brick Destroyer.
 */
public interface Movable {

    /**
     * Move and update the entity coordinate to the
     * new x and y position in the game window.
     */
    void move();

    /**
     * Move the entity to a specified x and y position.
     * @param position a Point 2D x and y value that move the entity to in the game window.
     */
    void moveTo(Point2D position);
}
