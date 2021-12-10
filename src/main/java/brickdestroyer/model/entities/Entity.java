package brickdestroyer.model.entities;

import javafx.scene.paint.Color;

/**
 * This interface is used to ensure all entities will have
 * the behavior to retrieve the basic properties of each entity's
 * class in the game of Brick Destroyer.
 */
public interface Entity {

    /**
     * Getter method for the border color of the brick. It defines the stroke color of the entity in the game window.
     * @return a Color that contains the sRGB value of the border color of the entity.
     */
    Color getBorderColor();

    /**
     * Getter method for the inner color of the brick. It defines the fill color of the entity in the game window.
     * @return a Color that contains the sRGB value of the inner color of the entity.
     */
    Color getInnerColor();

    /**
     * Getter method for the upper-left corner x bound of the entity. It defines the x position starting
     * point of the brick to be generated and drawn in the game window. The value of it can only be positive.
     * @return a Double that contains the x position of the upper-left corner of the entity.
     */
    double getXPosition();

    /**
     * Getter method for the upper-left corner y bound of the entity. It defines the y position starting
     * point of the brick to be generated and drawn in the game window. The value of it can only be positive.
     * @return a Double that contains the y position of the upper-left bound of the entity.
     */
    double getYPosition();

    /**
     * Getter method for the width of the entity. It defines the brick length in pixels
     * to be drawn on the x plane of the game window. The value of width can only be positive.
     * @return a Double that contains the width of the entity.
     */
    double getWidth();

    /**
     * Getter method for the height at the of the entity. It defines the entity length in pixels
     * to be drawn on the y plane of the game window. The value of height can only be positive.
     * @return a Double that contains the height of the entity.
     */
    double getHeight();
}
