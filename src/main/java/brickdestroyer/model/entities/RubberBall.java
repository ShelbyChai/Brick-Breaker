package brickdestroyer.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Rubber ball is a concrete class used to represent an object for the user to clear
 * different types of brick in the game of Brick Destroyer. This class extends Ball superclass
 * and contains all the properties of a rubber ball such as the radius, inner and border color.
 */
public class RubberBall extends Ball {

    private static final int DEF_RADIUS = 10;
    private static final Color DEF_INNER_COLOR = Color.rgb(255,219,88);
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();


    /**
     * Creates a new circle ball shape with the given position, properties and behavior of the superclass.
     * @param center a Point2D center x and y position of the center of the ball to be created.
     */
    public RubberBall(Point2D center){
        super(center,DEF_RADIUS, DEF_RADIUS ,DEF_INNER_COLOR,DEF_BORDER_COLOR);
    }


    /**
     * The method returns the newly created rubber circle shape along with its
     * specified properties.
     * @param center a Point2D x and y value of the center of the ball to be created.
     * @param radius an Integer value of the radius of the ball to be created.
     * @return a new Circle shape with the specified center position and radius of the rubber ball.
     */
    @Override
    protected Circle makeBall(Point2D center, int radius) {
        return new Circle(super.getXPosition(),super.getYPosition(), radius);
    }

}
