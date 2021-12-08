package brickdestroyer.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player implements Entity, Movable {

    private static final int DEF_MOVE_AMOUNT = 5;
    private static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    private static final Color INNER_COLOR = Color.GREEN;
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

        double x = ballPoint.getX() + moveAmount;

        if(x < min || x > max)
            return;

        ballPoint = new Point2D(x, ballPoint.getY());
        playerFace.setX(ballPoint.getX()-playerFace.getWidth()/2);
    }

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

    public Rectangle getPlayerFace() {return playerFace;}

    @Override
    public Color getBorderColor() {return BORDER_COLOR;}

    @Override
    public Color getInnerColor() {return INNER_COLOR;}

    @Override
    public double getXPosition() {return playerFace.getX();}

    @Override
    public double getYPosition() {return playerFace.getY();}

    @Override
    public double getWidth() {return playerFace.getWidth();}

    @Override
    public double getHeight() {return playerFace.getHeight();}

}