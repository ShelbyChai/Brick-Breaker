package brickdestroyer.model.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Clay Brick is a concrete class used to represent one type of the obstacle in the game of
 * Brick Destroyer. This class extends Brick superclass and contains all
 * the properties of a clay brick such as the name, strength, inner and border color.
 * Clay Brick is the most classic type of brick in the game.
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_BORDER = Color.GRAY;
    private static final Color DEF_INNER = Color.rgb(178, 34, 34).darker();
    private static final int CLAY_STRENGTH = 1;


    /**
     * Creates a new clay brick shape with the given position, size and the behavior defined in its superclass.
     * @param position The x and y coordinate of the upper-left corner of the brick.
     * @param size The width/height dimension of the brick.
     */
    public ClayBrick(Point2D position, Dimension2D size){
        super(NAME,position,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    /**
     * The method returns the newly created clay brick shape along with the
     * specified properties. This method defines the overall properties of the clay
     * brick to be drawn in the game window.
     * @param position a Point2D x and y position of the upper-left corner of the brick to be created.
     * @param size a Dimension2D value width/height dimension of the brick to be created.
     * @return a new black stone brick shape with the specified upper-left corner position and radius.
     */
    @Override
    protected Shape makeBrickFace(Point2D position, Dimension2D size) {
        return new Rectangle(position.getX(),position.getY(),size.getWidth(),size.getHeight());
    }

    /**
     * Getter method for the property of the clay brick shape such as the position, shape and so on.
     * This method is called to check the direction of impact from the ball.
     * @return a Shape that represent and contains the properties of a brick shape.
     */
    @Override
    public Shape getBrickFace() {
        return super.getBrickFace();
    }

    /**
     * It returns the path element of a brick that contains the
     * crack path element position to be drawn in the game window
     * @return a null value as clay doesn't support crack.
     */
    @Override
    public Path getCrackPath() {
        return null;
    }

}
