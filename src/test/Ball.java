package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 *
 */

// TODO Crazy Idea: Instead of a Ball abstract class make it Ball and BallMove
abstract public class Ball {

    private Shape ballFace;

    final private Point2D center;
    // Refactor: From protected to private
    // Created the getter for 4 of the property
    // Refactor: Added final attribute to center,up,down,left,right,borderColor and innerColor
    final private Point2D up;
    final private Point2D down;
    final private Point2D left;
    final private Point2D right;

    // Refactor: Rename to borderColor and innerColor
    final private Color borderColor;
    final private Color innerColor;

    private int speedX;
    private int speedY;


    // Refactor: changed the parameter name from inner and border to innerColor, borderColor
    public Ball(Point2D center,int radiusA,int radiusB,Color innerColor,Color borderColor){
        this.ballFace = makeBall(center,radiusA,radiusB);
        this.center = center;

        this.up = new Point2D.Double();
        this.down = new Point2D.Double();
        this.left = new Point2D.Double();
        this.right = new Point2D.Double();

        this.borderColor = borderColor;
        this.innerColor = innerColor;
        this.setSpeed(0,0);
        this.setPoints(radiusB, radiusA);

        // Combine the code below with method setPoints
//        getUp().setLocation(center.getX(),center.getY()-(radiusB / 2));
//        getDown().setLocation(center.getX(),center.getY()+(radiusB / 2));
//
//        getLeft().setLocation(center.getX()-(radiusA /2),center.getY());
//        getRight().setLocation(center.getX()+(radiusA /2),center.getY());

        // Use method setSpeed instead of assigning 1 by 1
        //speedX = 0;
        //speedY = 0;
    }

    // Note: Should this really be protected?
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    private void setPoints(double width,double height){
        getUp().setLocation(center.getX(),center.getY()-(height / 2));
        getDown().setLocation(center.getX(),center.getY()+(height / 2));

        getLeft().setLocation(center.getX()-(width / 2),center.getY());
        getRight().setLocation(center.getX()+(width / 2),center.getY());
    }

    // Note to refactor: Name of the method is not final
    // Extract similar code from move() and moveTo method to create generateRectangle
    private RectangularShape generateRectangle(){
        RectangularShape Rectangle = (RectangularShape) ballFace;
        double rectangleWidth = Rectangle.getWidth();
        double rectangleHeight = Rectangle.getHeight();
        Rectangle.setFrame((center.getX() -(rectangleWidth / 2)),(center.getY() - (rectangleHeight / 2)),rectangleWidth,rectangleHeight);

        return Rectangle;
    }

    // move and moveTo method might have Higher Refactor
    public void move(){
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));

//        RectangularShape tmp = (RectangularShape) ballFace;
//        double w = tmp.getWidth();
//        double h = tmp.getHeight();
//        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        RectangularShape temp = generateRectangle();
        ballFace = temp;

        setPoints(temp.getWidth(),temp.getHeight());
    }

    public void moveTo(Point p){
        center.setLocation(p);

//        RectangularShape tmp = (RectangularShape) ballFace;
//        double w = tmp.getWidth();
//        double h = tmp.getHeight();
//        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);

        ballFace = generateRectangle();
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }

    // Section => Setter Method of the class
    public void setSpeed(int speedX,int speedY){
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Refactor: Rename to setSpeedX, Y from setXSpeed
    // TODO Note to refactor: Both of the setter only used in wall class, they are used at the same time so can replace with setSpeed instead
    public void setSpeedX (int s){
        speedX = s;
    }

    public void setSpeedY(int s){
        speedY = s;
    }


    // Section => Getter Method of the class (Ascending Order according to field)
    public Shape getBallFace(){
        return ballFace;
    }

    public Point2D getPosition(){
        return center;
    }


    public Point2D getUp() {
        return up;
    }

    public Point2D getDown() {
        return down;
    }

    public Point2D getLeft() {
        return left;
    }

    public Point2D getRight() {
        return right;
    }

    public Color getBorderColor(){
        return borderColor;
    }

    public Color getInnerColor(){
        return innerColor;
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }
}
