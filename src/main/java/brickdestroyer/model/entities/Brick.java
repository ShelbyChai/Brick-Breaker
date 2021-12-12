package brickdestroyer.model.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

/**
 * Abstract Brick class is the superclass of all concrete brick class
 * in the game. This class contains all the basic behavior and
 * the getter/setter of brick properties.
 */
abstract public class Brick implements Entity {
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public enum ImpactDirection {LEFT, RIGHT, UP, DOWN, NONE}

    private final String name;
    private final Color borderColor;
    private final Color innerColor;
    private final Shape brickFace;
    private final Point2D position;
    private final Dimension2D size;
    private final int fullStrength;
    private int strength;
    private boolean broken;

    /**
     * Creates a new brick shape with the given name, size, color, position and strength properties.
     * @param name The name of the child brick class.
     * @param position The X and Y coordinate of the upper-left corner of the child brick class.
     * @param size The width/height dimension of child brick class.
     * @param borderColor The outline color of the child brick class.
     * @param innerColor The interior color of the child brick class.
     * @param strength The number of impact the child brick class can receive.
     */
    public Brick(String name, Point2D position, Dimension2D size, Color borderColor, Color innerColor, int strength){

        broken = false;
        this.name = name;
        this.borderColor = borderColor;
        this.innerColor = innerColor;
        fullStrength = this.strength = strength;
        this.position = position;
        this.size = size;

        brickFace = makeBrickFace(position,size);

    }

    /**
     * An abstract makeBall method for the derived brick class to be implemented.
     * The method returns the newly created derived brick shape along with the
     * specified properties. This method defines the overall brick shape
     * to be drawn in the game window.
     * @param position a Point2D x and y position of the upper-left corner of the brick to be created.
     * @param size a Dimension2D value width/height dimension of the brick to be created.
     * @return a new brick shape with the specified upper-left corner position and radius.
     */
    protected abstract Shape makeBrickFace(Point2D position,Dimension2D size);

    /**
     * Sets the impact of a non-crackable brick. This method is called when the ball impacted the brick
     * in any direction.
     * @return a boolean value which return true if the brick is destroyed, false if it's not.
     */
    public boolean setImpact(){
        if(broken)
            return false;
        impact();
        return broken;
    }

    /**
     * Sets the impact of crackable brick when it is hit by the ball. This method is an overloaded
     * method of the setImpact() method and created specifically for crackable brick.
     * @param point a Point2D x and y position impact by the ball.
     * @param direction an Impact Direction enum value in the Brick class that specifies the direction
     *                  of the ball when it impacts the brick.
     * @return a boolean value which return true if the brick is destroyed, false if it's not.
     */
    public boolean setImpact(Point2D point , ImpactDirection direction){
        if(broken)
            return false;
        impact();
        return broken;
    }

    /**
     * This method is called by setImpact() method to decrement the strength of the brick
     * and determine if the brick is broken.
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }


    /**
     *  Repair the condition of the brick.
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * An abstract getCrackPath method for the derived ball class to be implemented.
     * It returns the path element of a brick that contains the
     * crack path element position to be drawn in the game window.
     * The value could be null if the class doesn't support crack features.
     * @return a Path that contains path elements of the crack path to be drawn in the game window.
     */
    public abstract Path getCrackPath();

    /**
     * Getter method for the name specified for the brick. This method is called to
     * generate different brick types using the brick factory and specifies the
     * use of setImpact() overloading method for crackable brick and default brick.
     * @return the name of the brick.
     */
    public String getName() {
        return name;
    }


    /**
     * Getter method for the condition of whether the brick. This method is used to
     * determine if a crack path is needed to be drawn on a brick.
     * @return a boolean value which return true when the brick is broken, false if it's not.
     */
    public boolean isBroken(){
        return !broken;
    }

    /**
     * Getter method for the property of the shape brick such as the position, shape and so on.
     * This method is called to check the direction of impact from the ball.
     * @return a Shape that represent and contains the properties of a brick shape.
     */
    public Shape getBrickFace() {
        return brickFace;
    }

    /**
     * Getter method for the border color of the brick. It defines the stroke color of the brick in the game window.
     * @return a Color that contains the sRGB value of the border color of the brick.
     */
    @Override
    public Color getBorderColor(){
        return borderColor;
    }

    /**
     * Getter method for the inner color of the brick. It defines the fill color of the brick in the game window.
     * @return a Color that contains the sRGB value of the inner color of the brick.
     */
    @Override
    public Color getInnerColor(){
        return innerColor;
    }

    /**
     * Getter method for the upper-left corner x bound of the brick. It defines the x position starting
     * point of the brick to be generated and drawn in the game window. The value of it can only be positive.
     * @return a Double that contains the upper-left x bound of the brick.
     */
    @Override
    public double getXPosition() {
        return position.getX();
    }

    /**
     * Getter method for the upper-left corner y bound of the brick. It defines the y position starting
     * point of the brick to be generated and drawn in the game window. The value of it can only be positive.
     * @return a Double that contains the upper-left y bound of the brick.
     */
    @Override
    public double getYPosition() {
        return position.getY();
    }

    /**
     * Getter method for the width of the brick. It defines the brick length in pixels
     * to be drawn on the x plane of the game window. The value of width can only be positive.
     * @return a Double that contains the width of the brick.
     */
    @Override
    public double getWidth() {
        return size.getWidth();
    }

    /**
     * Getter method for the height at the of the brick. It defines the brick length in pixels
     * to be drawn on the y plane of the game window. The value of height can only be positive.
     * @return a Double that contains the height of the brick.
     */
    @Override
    public double getHeight() {
        return size.getHeight();
    }

}
