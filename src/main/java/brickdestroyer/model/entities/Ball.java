package brickdestroyer.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


abstract public class Ball implements Entity,Movable {

    private final Color borderColor;
    private final Color innerColor;
    private Circle ballFace;
    private final int radiusA;
    private final int radiusB;
    private Point2D center;
    private Point2D up;
    private Point2D down;
    private Point2D left;
    private Point2D right;
    private int speedX;
    private int speedY;

    public Ball(Point2D center, int width, int height, Color innerColor, Color borderColor){

        this.center = center;
        this.radiusA = width;
        this.radiusB = height;

        up = new Point2D(0,0);
        down = new Point2D(0,0);
        left = new Point2D(0,0);
        right = new Point2D(0,0);

        this.borderColor = borderColor;
        this.innerColor = innerColor;
        ballFace = makeBall(center,radiusA);
    }

    @Override
    public void move(){
        center = new Point2D((center.getX() + speedX),(center.getY() + speedY));
        Circle tempCircle = generateCircle();
        ballFace = tempCircle;
        setPoints(tempCircle.getRadius(),tempCircle.getRadius());
    }

    @Override
    public void moveTo(Point2D position){
        center = position;
        ballFace = generateCircle();
    }

    protected abstract Circle makeBall(Point2D center,int radius);

    private Circle generateCircle() {
        Circle tempCircle = ballFace;

        tempCircle.setCenterX(getXPosition());
        tempCircle.setCenterY(getYPosition());
        tempCircle.setRadius(radiusA);

        return tempCircle;
    }

    private void setPoints(double width,double height){

        up = new Point2D(center.getX(),center.getY()-(height / 2));
        down = new Point2D(center.getX(),center.getY()+(height / 2));

        left = new Point2D(center.getX()-(width / 2),center.getY());
        right = new Point2D(center.getX()+(width / 2),center.getY());
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }

    public void setSpeed(int speedX,int speedY){
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void setSpeedX (int speedX){
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY){
        this.speedY = speedY;
    }

    public int getSpeedX () {return speedX;}

    public int getSpeedY () {return speedY;}

    public Point2D getCenter(){
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

    @Override
    public Color getBorderColor(){
        return borderColor;
    }

    @Override
    public Color getInnerColor(){
        return innerColor;
    }

    @Override
    public double getXPosition() {
        return center.getX() - (double)(radiusA/2);
    }

    @Override
    public double getYPosition() {
        return center.getY() - (double)(radiusB/2);
    }

    @Override
    public double getWidth() {
        return radiusA;
    }

    @Override
    public double getHeight() {
        return radiusB;
    }

}
