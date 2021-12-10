package brickdestroyer.model.entities_factory;

import brickdestroyer.model.entities.Ball;
import brickdestroyer.model.entities.RubberBall;
import javafx.geometry.Point2D;

/**
 * Ball Factory uses the factory design pattern to generate different types of ball in the
 * game of Brick Destroyer.
 */
public class BallFactory {

    /**
     * Generate a specified ball type with the given center position. This method will return
     * the newly created ball object if the ball type is available, null if the ball type is
     * not in the factory.
     * @param ballType a String that represent the type of ball to be return by the factory.
     * @param center a Point2D x and y position that represent the upper-left bound of the ball.
     * @return the created Ball type.
     */
    public Ball getBallType(String ballType, Point2D center) {
        if (ballType == null) {
            return null;
        }
        if (ballType.equalsIgnoreCase("Rubber Ball")) {
            return new RubberBall(center);
        }
        return null;
    }
}
