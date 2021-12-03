package brickdestroyer.Actor;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {


    private static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    private static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    //Refactor: Rectangle, min, max, Point from none -> final
    final private Rectangle playerFace;
    private Point2D ballPoint;

    final private int min;
    final private int max;

    private int moveAmount;


    // Container -> x: 600, y: 450
    public Player(Point2D ballPoint,int width,int height,Rectangle container) {
        // ballPoint-x: 300, ballPoint-y: 430
        this.ballPoint = ballPoint;
        this.moveAmount = 0;
        // width: 150, height: 10
        this.playerFace = makeRectangle(width, height);

        this.min = (int)container.getX() + (width / 2); // This shows the least x-axis value the board can go which is 75 (because rectangle have midpoint so when moving it doesn't go through walls)
        this.max = min + (int)container.getWidth() - width; // This shows the maximum x-axis value the board can go which is 525

    }

    private Rectangle makeRectangle(int width,int height){
        // p.x: 225 , p.y: 430
        Point2D p = new Point2D((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());   // This set the initial upper-left corner value of the player

        // TODO later try to use Dimension2D instead
        return new Rectangle(p.getX(),p.getY(),width,height);
//        return new Rectangle(p,new Dimension2D(width,height));
    }

    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;
    }

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

//        stop();
    }

    // Reset the position of the player
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

}