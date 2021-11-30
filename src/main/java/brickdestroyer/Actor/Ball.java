package brickdestroyer.Actor;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

abstract public class Ball {

    private Shape ballFace;
    final private Color borderColor;
    final private Color innerColor;

    private Point2D center;
    private Point2D up;
    private Point2D down;
    private Point2D left;
    private Point2D right;

    private int speedX;
    private int speedY;

    private int radius;

    public Ball(Point2D center,int radiusA,int radiusB,Color innerColor,Color borderColor){
//        double centerUpX = center.getX() + radius;
//        double centerUpY = center.getY() + radius;
//        Point2D newCenter = new Point2D(centerUpX,centerUpY);
//        ballFace = makeBall(newCenter,radiusA,radiusB);
//        this.center = newCenter;

        this.center = center;
        this.radius = radiusA;

        up = new Point2D(0,0);
        down = new Point2D(0,0);
        left = new Point2D(0,0);
        right = new Point2D(0,0);

        this.borderColor = borderColor;
        this.innerColor = innerColor;
        ballFace = makeBall(center,radiusA,radiusB);
    }

    protected abstract Circle makeBall(Point2D center,int radiusA,int radiusB);

    private void setPoints(double width,double height){

        up = new Point2D(center.getX(),center.getY()-(height / 2));
        down = new Point2D(center.getX(),center.getY()+(height / 2));

        left = new Point2D(center.getX()-(width / 2),center.getY());
        right = new Point2D(center.getX()+(width / 2),center.getY());

    }

    private Circle generateCircle() {

        Circle circle = (Circle) ballFace;
        double radius = circle.getRadius();
        circle.setCenterX(getUpperLeftX());
        circle.setCenterY(getUpperLeftY());
        circle.setRadius(radius);

        return circle;
    }

    public void move(){
        center = new Point2D((center.getX() + speedX),(center.getY() + speedY));
        Circle temp = generateCircle();
        ballFace = temp;

        setPoints(temp.getRadius(),temp.getRadius());
    }

    public void moveTo(Point2D p){
        center = p;
        ballFace = generateCircle();
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

    public Double getUpperLeftX() {return center.getX() - (radius/2);}

    public Double getUpperLeftY() {return center.getY() - (radius/2);}

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

    public int getRadius() {
        return radius;
    }
}
