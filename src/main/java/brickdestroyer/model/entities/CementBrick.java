package brickdestroyer.model.entities;


import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Cement Brick is a concrete class used to represent one type of the obstacle in the game of
 * Brick Destroyer. This class extends Brick superclass and contains all
 * the properties of a cement brick such as the name, crack, strength,
 * inner and border color. Cement class contains the ability to crack.
 */
public class CementBrick extends Brick {

    private static final String NAME = "Cement Brick";
    private static final Color DEF_BORDER = Color.rgb(217, 199, 175);
    private static final Color DEF_INNER = Color.rgb(147, 147, 147);
    private static final int CEMENT_STRENGTH = 2;

    final private Crack crack;
    private Shape brickFace;
    private Path path;


    /**
     * Creates a new cement brick shape with the given position, size and the behavior
     * defines in the superclass. This class also instantiate a new crack object as it support crack features.
     * @param position The x and y coordinate of the upper-left corner of the brick.
     * @param size The width/height dimension of the brick.
     */
    public CementBrick(Point2D position, Dimension2D size){
        super(NAME,position,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.getBrickFace();
    }

    /**
     * The method returns the newly created cement brick shape along with the
     * specified properties. This method defines the overall properties of the cement
     * brick to be drawn in the game window.
     * @param position a Point2D x and y position of the upper-left corner of the brick to be created.
     * @param size a Dimension2D value width/height dimension of the brick to be created.
     * @return a new cement brick shape with the specified upper-left corner position and radius.
     */
    @Override
    protected Shape makeBrickFace(Point2D position, Dimension2D size) {
        return new Rectangle(position.getX(),position.getY(),size.getWidth(),size.getHeight());
    }

    /**
     * Sets the impact of the cement brick when it is hit by the ball and set a crack path on the cement brick
     * position if it is not broken.
     * @param point a Point2D x and y position impact by the ball.
     * @param direction an Impact Direction enum value in the Brick Class that specifies the direction
     *                  of the ball when it impacts the brick.
     * @return a boolean value which return true if the cement brick is destroyed, false if it's not.
     */
    @Override
    public boolean setImpact(Point2D point, Brick.ImpactDirection direction) {
        if(!super.isBroken())
            return false;
        super.impact();
        if(super.isBroken()){
            crack.makeCrack(point,direction,brickFace);
            path = crack.draw();
            return false;
        }
        return true;
    }

    /**
     * Getter method for the property of the cement brick shape such as the position, shape and so on.
     * This method is called to check the direction of impact from the ball.
     * @return a Shape that represent and contains the properties of a brick shape.
     */
    @Override
    public Shape getBrickFace() {
        return brickFace;
    }


    /**
     * It returns the path element of a brick that contains the
     * crack path element position to be drawn in the game window
     * @return a Path that contains path elements of the crack path to be drawn in the game window.
     */
    @Override
    public Path getCrackPath() {
        return path;
    }

    /**
     *  Repair the black stone brick and remove all cracks.
     */
    @Override
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = getBrickFace();
    }

}
