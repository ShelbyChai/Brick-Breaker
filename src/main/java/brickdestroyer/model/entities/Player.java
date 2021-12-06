package brickdestroyer.model.entities;

import brickdestroyer.model.Movable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player implements Movable {

    private static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    private static final Color INNER_COLOR = Color.GREEN;
    private static final int DEF_MOVE_AMOUNT = 5;
    private final Rectangle playerFace;
    private Point2D ballPoint;
    private final int min;
    private final int max;
    private int moveAmount;


    public Player(Point2D ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        this.moveAmount = 0;
        this.min = (int)container.getX() + (width / 2);
        this.max = min + (int)container.getWidth() - width;

        this.playerFace = makeRectangle(width, height);
    }

    private Rectangle makeRectangle(int width,int height){
        Point2D position = new Point2D((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return new Rectangle(position.getX(),position.getY(),width,height);
    }

    @Override
    public void move(){
        // ballPoint is the midpoint of the player rectangle
        double x = ballPoint.getX() + moveAmount;
        // Check if the center position of the player (x) go beyond x and y
        if(x < min || x > max)
            return;

        // Move the midpoint of the rectangle
        ballPoint = new Point2D(x, ballPoint.getY());

        // Move the upper-left-corner value of the player
        playerFace.setX(ballPoint.getX()-playerFace.getWidth()/2);
    }

    // Reset the position of the player
    @Override
    public void moveTo(Point2D p){
        ballPoint = p;
        playerFace.setX(ballPoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(ballPoint.getY());
    }

    public void stop(){
        moveAmount = 0;
    }

    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    // REDUNDANT CODE
    public double getXUpperLeft() {return playerFace.getX();}

    public double getYUpperLeft() {return playerFace.getY();}

    public double getWidth() {return playerFace.getWidth();}

    public double getHeight() {return playerFace.getHeight();}

    public Color getInnerColor() {return INNER_COLOR;}

    public Color getBorderColor() {return BORDER_COLOR;}

    public Rectangle getPlayerFace() {return playerFace;}

}