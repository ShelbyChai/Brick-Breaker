package brickdestroyer.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Player is a concrete class used to represent the entities control by the player
 * in the game of Brick Destroyer. This class contains all the properties and behavior
 * of a player such as the color, move amount, constraint of a player. The player class
 * can only move on horizontal axis of the game window.
 */
public class Player implements Entity, Movable {

    private static final int DEF_MOVE_AMOUNT = 5;
    private static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    private static final Color INNER_COLOR = Color.GREEN;
    private final Rectangle playerFace;
    private Point2D ballPoint;

    private final int min;
    private final int max;

    private int moveAmount;


    /**
     * Creates a new player rectangle shape with the given initial position, size,
     * color and the movement behavior depending on the size of the game window.
     * @param ballPoint The initial position of the player.
     * @param width The width for the X-axis of the player.
     * @param height The height for the Y-axis of the player.
     * @param container The size of the game window.
     */
    public Player(Point2D ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        this.moveAmount = 0;
        this.min = (int)container.getX() + (width / 2);
        this.max = min + (int)container.getWidth() - width;

        this.playerFace = makePlayerFace(width, height);
    }

    /**
     * The method returns the player rectangle shape along with the
     * specified properties. This method defines the overall player shape
     * to be drawn in the game window.
     * @param width a double x plane value that defines the width of the player shape to be created.
     * @param height a double y plane value that defines the height of the player shape to be created.
     * @return the generated shape of player.
     */
    private Rectangle makePlayerFace(int width, int height){
        Point2D position = new Point2D((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return new Rectangle(position.getX(),position.getY(),width,height);
    }

    /**
     * Move and update the player coordinate to the new x and y position
     * in the game window with the constraint of not going out of the defined
     * min/max value in the game window. This method is called to update the new position of
     * the player in the game window.
     */
    @Override
    public void move(){

        double x = ballPoint.getX() + moveAmount;

        if(x < min || x > max)
            return;

        ballPoint = new Point2D(x, ballPoint.getY());
        playerFace.setX(ballPoint.getX()-playerFace.getWidth()/2);
    }

    /**
     * Move the player to a specified X and Y coordinate. This method is called in the game
     * to move the player to the specified initial position.
     * @param position a Point2D x and y position that can only be positive value. It defines the
     *                new position location of the ball in the game window.
     */
    @Override
    public void moveTo(Point2D position){
        ballPoint = position;
        playerFace.setX(ballPoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(ballPoint.getY());
    }

    /**
     * Stop the movement of the player.
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * Moves the player to the left of the screen. This method allows
     * the horizontal left movement of the player in the game windows.
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * Moves the player to the right of the screen. This method allows
     * the horizontal right movement of the player in the game windows.
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * Getter method for the property of the shape player such as the position, shape and so on.
     * This method is called to check if the ball collide with the player.
     * @return the shape of the player.
     */
    public Rectangle getPlayerFace() {return playerFace;}

    /**
     * Getter method for the border color of the player. It defines the stroke color of the player in the game window.
     * @return a Color that contains the sRGB value of the border color of the player.
     */
    @Override
    public Color getBorderColor() {return BORDER_COLOR;}

    /**
     * Getter method for the inner color of the player. It defines the fill color of the player in the game window.
     * @return a Color that contains the sRGB value of the inner color of the player.
     */
    @Override
    public Color getInnerColor() {return INNER_COLOR;}

    /**
     * Getter method for the upper-left corner x bound of the player. It defines the x position starting
     * point of the player to be generated and drawn in the game window. The value of it can only be positive.
     * @return a Double that contains the upper-left x bound of the player.
     */
    @Override
    public double getXPosition() {return playerFace.getX();}

    /**
     * Getter method for the upper-left corner y bound of the player. It defines the y position starting
     * point of the player to be generated and drawn in the game window. The value of it can only be positive.
     * @return a Double that contains the upper-left y bound of the player.
     */
    @Override
    public double getYPosition() {return playerFace.getY();}

    /**
     * Getter method for the width of the player. It defines the player length in pixels
     * to be drawn on the x plane of the game window. The value of width can only be positive.
     * @return a Double that contains the width of the player.
     */
    @Override
    public double getWidth() {return playerFace.getWidth();}

    /**
     * Getter method for the height at the of the player. It defines the player length in pixels
     * to be drawn on the y plane of the game window. The value of height can only be positive.
     * @return a Double that contains the height of the player.
     */
    @Override
    public double getHeight() {return playerFace.getHeight();}

    /**
     * This Getter method is created for the purpose of testing PlayerTest.java.
     * Getter method for the move amount of the player. A negative value represent a left movement
     * of the player and right represent the right in pixel.
     * @return an Integer value that is the current moveAmount of the player.
     */
    public int getMoveAmount() {
        return moveAmount;
    }

    /**
     * This Getter method is created for the purpose of testing PlayerTest.java.
     * Getter method for receiving the minimum amount of border X position value.
     * @return an Integer value that represent the minimum X position value of
     * how far the player can travel to the left.
     */
    public int getMin() {
        return min;
    }

    /**
     * This Getter method is created for the purpose of testing GameLogicTest.java.
     * Getter method for receiving the minimum amount of border X position value.
     * @return a Point2D value that represent the center x and y position of the player.
     */
    public Point2D getBallPoint() {
        return ballPoint;
    }
}