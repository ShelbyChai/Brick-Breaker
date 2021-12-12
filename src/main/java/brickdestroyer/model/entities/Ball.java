package brickdestroyer.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Abstract Ball class is the superclass of all concrete ball class
 * in the game. This class contains all the basic behavior and
 * the getter/setter of ball properties.
 */
abstract public class Ball implements Entity, Movable {

    private final Color borderColor;
    private final Color innerColor;
    private Circle ballFace;
    private final int width;
    private final int height;
    private Point2D center;
    private Point2D up;
    private Point2D down;
    private Point2D left;
    private Point2D right;


    private int speedX;
    private int speedY;

    /**
     * Assign the properties of the given value and creates a new derived circle ball shape
     * with the given size and color.
     * @param center The center X and Y value of the child ball class.
     * @param width The width for the X-axis of the child ball class.
     * @param height The height for the Y-axis of the child ball class.
     * @param innerColor The interior color of the ball child ball class.
     * @param borderColor The outline color of the ball child ball class.
     */
    public Ball(Point2D center, int width, int height, Color innerColor, Color borderColor){

        this.center = center;
        this.width = width;
        this.height = height;

        up = new Point2D(0,0);
        down = new Point2D(0,0);
        left = new Point2D(0,0);
        right = new Point2D(0,0);

        this.borderColor = borderColor;
        this.innerColor = innerColor;
        ballFace = makeBall(center, this.width);
    }

    /**
     * This method is helper method for move() and moveTo() method to return generated circle.
     * The method returns a newly generated Circle shape with the specified parameter
     * to define the new position and shape of the ball.
     * @return a new Circle shape with the specified center position and radius.
     */
    private Circle generateCircle() {
        Circle tempCircle = ballFace;

        tempCircle.setCenterX(getXPosition());
        tempCircle.setCenterY(getYPosition());
        tempCircle.setRadius(width);

        return tempCircle;
    }

    /**
     * This method is called by move() to sets the location value of the ball in the game window.
     * @param width a Double value that defines the x diameter width of the ball to set the location of up, down, left and right.
     * @param height a Double value that defines the y diameter of the ball to set the location of up, down, left and right.
     */
    private void setPoints(double width,double height){

        up = new Point2D(center.getX(),center.getY()-(height / 2));
        down = new Point2D(center.getX(),center.getY()+(height / 2));

        left = new Point2D(center.getX()-(width / 2),center.getY());
        right = new Point2D(center.getX()+(width / 2),center.getY());
    }

    /**
     * Move the ball to the x and y position in the game window.
     * This method is called in the game to move the ball to the initial position.
     * @param position a Point2D x and y position that can only be positive value. It defines the
     *                 new position location of the ball in the game window.
     */
    @Override
    public void moveTo(Point2D position){
        center = position;
        ballFace = generateCircle();
    }

    /**
     * Move and update the ball position to the new x and y position in the game window.
     * This method is called to update the new position of the ball in the game window.
     */
    @Override
    public void move(){
        center = new Point2D((center.getX() + speedX), (center.getY() + speedY));
        Circle tempCircle = generateCircle();
        ballFace = tempCircle;
        setPoints(tempCircle.getRadius(),tempCircle.getRadius());
    }



    /**
     * An abstract makeBall method for the derived ball class to be implemented.
     * This method returns the Circle shape of the ball.
     * @param center a Point2D center x and y position of the center of the ball to be created.
     * @param radius an Integer value of the radius of the ball to be created.
     * @return a new Circle shape with the specified center position and radius.
     */
    protected abstract Circle makeBall(Point2D center,int radius);

    /**
     * Reverse the direction of the ball in the X plane
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * Reverse the direction of the ball in the Y plane.
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * Sets the x and y travelling speed of the ball. Speed x and y is the
     * rate in pixels which the ball travel in every frame of the game window.
     *
     * @param speedX an integer that can be positive and negative value. Negative value traverse the ball to the left of the screen
     *              while positive traverse the ball to the right.
     * @param speedY an integer can be positive and negative value. It is usually set to negative from the start the traverse
     *               the ball to the top of the screen. Negative value traverse the ball to the top of the game
     *               window while positive traverse the ball to the bottom.
     */
    public void setSpeed(int speedX,int speedY){
        this.speedX = speedX;
        this.speedY = speedY;
    }

    /**
     * Setter method for the travelling speed of the ball in the x plane. Speed x is the rate in pixels which the ball
     * travel in every frame of the game window.
     * @param speedX an integer that can be positive and negative value. Negative value traverse the ball to the left of the screen
     *      *       while positive traverse the ball to the right.
     */
    public void setSpeedX (int speedX){
        this.speedX = speedX;
    }

    /**
     * Setter method for the travelling speed of the ball in the y plane. Speed y is the rate in pixels which the ball
     * travel in every frame of the game window.
     * @param speedY an integer that can be positive and negative value. It is usually set to negative from the start the traverse
     *      *       the ball to the top of the screen. Negative value traverse the ball to the top of the game
     *      *       window while positive traverse the ball to the bottom.
     */
    public void setSpeedY(int speedY){
        this.speedY = speedY;
    }

    /**
     * Getter method for the center x and y position of the ball. It defines the horizontal and vertical position
     * of the center of the ball in pixels in the game window. The value of center can only be positive.
     * @return a Point2D that contains the x and y position of the center of the ball which has a default value of 0.0.
     */
    public Point2D getCenter(){
        return center;
    }

    /**
     * Getter method for the up x and y position of the ball. It defines the horizontal and vertical position of
     * the up of the ball in pixels in the game window. The value of up can only be positive.
     * @return a Point2D that contains the x and y position of the up point of the ball which has a default value of 0.0.
     */
    public Point2D getUp() {
        return up;
    }

    /**
     * Getter method for the down x and y position of the ball. It defines the horizontal and vertical position of
     * the down of the ball in pixels in the game window. The value of up can only be positive.
     * @return a Point2D that contains the x and y position of the down point of the ball which has a default value of 0.0.
     */
    public Point2D getDown() {
        return down;
    }

    /**
     * Getter method for the left x and y position of the ball. It defines the horizontal and vertical position of
     * the left of the ball in pixels in the game window. The value of up can only be positive.
     * @return a Point2D that contains the x and y position of the left point of the ball which has a default value of 0.0.
     */
    public Point2D getLeft() {
        return left;
    }

    /**
     * Getter method for the right x and y position of the ball. It defines the horizontal and vertical position of
     * the right of the ball in pixels in the game window. The value of up can only be positive.
     * @return a Point2D that contains the x and y position of the right point of the ball which has a default value of 0.0.
     */
    public Point2D getRight() {
        return right;
    }

    /**
     * Getter method for the border color of the ball. It defines the stroke color of the ball in the game window.
     * @return a Color that contains the sRGB value of the border color of the ball.
     */
    @Override
    public Color getBorderColor(){
        return borderColor;
    }

    /**
     * Getter method for the inner color of the ball. It defines the fill color of the ball in the game window.
     * @return a Color that contains the default sRGB value of the inner color of the ball.
     */
    @Override
    public Color getInnerColor(){
        return innerColor;
    }

    /**
     * Getter method for the upper-left corner x bound of the ball. It defines the x position starting
     * point of the ball to be generated and drawn in the game window. The value of it can only be positive.
     * @return a Double that contains the upper-left x bound of the ball.
     */
    @Override
    public double getXPosition() {
        return center.getX() - (double)(width /2);
    }

    /**
     * Getter method for the upper-left corner y bound of the ball. It defines the y position of the starting
     * point of the ball to be generated and drawn in the game window. The value of it can only be positive.
     * @return a Double that contains the upper-left y bound of the ball.
     */
    @Override
    public double getYPosition() {
        return center.getY() - (double)(height /2);
    }

    /**
     * Getter method for the width at the center of the ball. It defines the ball length in pixels
     * to be drawn on the x plane of the game window. The value of width can only be positive.
     * @return a Double that contains the width at the center of the ball.
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * Getter method for the height at the center of the ball. It defines the ball length in pixels
     * to be drawn on the y plane of the game window. The value of height can only be positive.
     * @return a Double that contains the height at the center of the ball.
     */
    @Override
    public double getHeight() {
        return height;
    }

    /**
     * This Getter method is created for the purpose of testing BallTest.java.
     * @return a Circle object that represent the circle shape of the ball.
     */
    public Circle getBallFace() {
        return ballFace;
    }

    /**
     * This Getter method is created for the purpose of testing BallTest.java.
     * @return an Integer value that represent the current x speed of the ball.
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * This Getter method is created for the purpose of testing BallTest.java.
     * @return an Integer value that represent the current y speed of the ball.
     */
    public int getSpeedY() {
        return speedY;
    }

}
