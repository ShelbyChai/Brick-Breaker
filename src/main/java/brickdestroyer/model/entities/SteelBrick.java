package brickdestroyer.model.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

/**
 * Steel Brick is a concrete class used to represent one type of the obstacle in the game of
 * Brick Destroyer. This class extends Brick superclass and contains all
 * the properties of a steel brick. Steel Brick contains a rng factor to determine the
 * successful impact of the ball.
 */
public class SteelBrick extends Brick {

    private static final String NAME = "Steel Brick";
    private static final Color DEF_INNER = Color.rgb(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    /**
     * Creates a new Steel brick shape with the given position, size and the behavior defined in its superclass.
     * @param position The x and y coordinate of the upper-left corner of the brick.
     * @param size The width/height dimension of the brick.
     */
    public SteelBrick(Point2D position, Dimension2D size){
        super(NAME,position,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
    }

    /**
     * The method returns the newly created steel brick shape along with the
     * specified properties. This method defines the overall properties of the steel
     * brick to be drawn in the game window.
     * @param position a Point2D x and y position of the upper-left corner of the brick to be created.
     * @param size a Dimension2D value width/height dimension of the brick to be created.
     * @return a new steel brick shape with the specified upper-left corner position and radius.
     */
    @Override
    protected Shape makeBrickFace(Point2D position, Dimension2D size) {
        return new Rectangle(position.getX(),position.getY(),size.getWidth(),size.getHeight());
    }

    /**
     * Getter method for the property of the steel brick shape such as the position, shape and so on.
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

    /**
     * This method is called when the ball impacted the brick in any direction.
     * @return a boolean value which return true if the brick is destroyed, false if it's not.
     */
    @Override
    public boolean setImpact(){
        if(!super.isBroken())
            return false;
        impact();
        return !super.isBroken();
    }

    /**
     * This method is called by setImpact() to determine if the steel brick has been
     * impacted using a rng factor after impacted by a ball.
     */
    @Override
    public void impact(){
        Random rnd = new Random();
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }
}
