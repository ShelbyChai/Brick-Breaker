package brickdestroyer.Actor;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

abstract public class Ball {

    private Shape ballFace;
    final private Color borderColor;
    final private Color innerColor;

    final private Point2D center;
    final private Point2D up;
    final private Point2D down;
    final private Point2D left;
    final private Point2D right;

    private int speedX;
    private int speedY;

    public Ball(Point2D center,int radiusA,int radiusB,Color innerColor,Color borderColor){
        ballFace = makeBall(center,radiusA,radiusB);
        this.center = center;

        up = new Point2D(0,0);
        down = new Point2D(0,0);
        left = new Point2D(0,0);
        right = new Point2D(0,0);

        this.borderColor = borderColor;
        this.innerColor = innerColor;

        ballFace.setFill(innerColor);
        ballFace.setStroke(borderColor);

        setSpeed(0,0);
        setPoints(radiusB, radiusA);
    }

    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    private void setPoints(double width,double height){
        getUp().add(center.getX(),center.getY()-(height / 2));
        getDown().add(center.getX(),center.getY()+(height / 2));

        getLeft().add(center.getX()-(width / 2),center.getY());
        getRight().add(center.getX()+(width / 2),center.getY());

    }

    // To Rectangle2D
    private Rectangle generateRectangle() {

        Rectangle rectangle = (Rectangle) ballFace;
        double rectangleWidth = rectangle.getWidth();
        double rectangleHeight = rectangle.getHeight();
        rectangle.setX(center.getX() -(rectangleWidth / 2));
        rectangle.setY(center.getY() - (rectangleHeight / 2));
        rectangle.setWidth(rectangleWidth);
        rectangle.setHeight(rectangleHeight);

        return rectangle;
    }

    public void move(){
        center.add((center.getX() + speedX),(center.getY() + speedY));

        Rectangle temp = generateRectangle();
        ballFace = temp;

        setPoints(temp.getWidth(),temp.getHeight());
    }

    public void moveTo(Point2D p){
        center.add(p);
        ballFace = generateRectangle();
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

    // TODO Note to refactor: Both of the setter only used in wall class, they are used at the same time so can replace with setSpeed instead
    public void setSpeedX (int s){
        speedX = s;
    }

    public void setSpeedY(int s){
        speedY = s;
    }

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
