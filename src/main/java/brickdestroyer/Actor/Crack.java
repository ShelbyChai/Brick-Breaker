package brickdestroyer.Actor;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.*;

import java.util.Random;

public class Crack {
    public static final int MIN_CRACK = 1;
    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;


    private static Random rnd;
    private Path crack;

    private int crackDepth;
    private int steps;


    public Crack(int crackDepth, int steps){
        rnd = new Random();
        crack = new Path();
        this.crackDepth = crackDepth;
        this.steps = steps;
    }

    // Return the crack path
    public Path draw(){

        return crack;
    }

    public void reset(){
        crack.getElements().clear();
    }

    // Polymorphism method overloading (there is 2 makeCrack method)
    // Refactor: Added a new Shape brickBounds as a parameter for line Rectangle bounds = ...
    // Refactor: The above refactor also changed CementBrick -> setImpact's -> crack.makeCrack(point,dir) to crack.makeCrack(point,dir,brickFace);
    // TODO Note to refactor: In this makeCrack method it call the other makeCrack method to do something. This means they are not doing the same thing thus can't method overloading (Try to rename the method)
    protected void makeCrack(Point2D point, int direction, Shape brickBounds){
        // bounds: java.awt.Rectangle[x=60,y=40,width=60,height=20], each brick is 60 width & 20 height, the x and y represent the position of the crack brick
        Bounds bounds = brickBounds.getBoundsInParent();

        Point2D impact = new Point2D((int)point.getX(),(int)point.getY());
        Point2D start;
        Point2D end;


        // TODO Note to refactor: Polymorphism (Left extends Directions) or Form Template Method (pull up)
        // Define the direction of the brick but from upside down (left means right)
        switch(direction){
            case LEFT:
                // Start: x-> upper-right corner, y-> top y
                start = new Point2D(bounds.getMaxX(), bounds.getMinY());
                end = new Point2D(bounds.getMaxX(), bounds.getMaxY());

                Point2D tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case RIGHT:
                start = new Point2D(bounds.getMinX(),bounds.getMinY());
                end = new Point2D(bounds.getMinX(),bounds.getMaxY());
                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case UP:
                start = new Point2D(bounds.getMinX(), bounds.getMaxY());
                end = new Point2D(bounds.getMaxX(),bounds.getMinY());
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);

                break;
            case DOWN:
                start = new Point2D(bounds.getMinX(),bounds.getMinY());
                end = new Point2D(bounds.getMaxX(),bounds.getMinY());
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);

                break;
        }
    }

    protected void makeCrack(Point2D start, Point2D end){

        Path path = new Path();

        path.getElements().add(new MoveTo(start.getX(),start.getY()));
//        path.moveTo(start.x,start.y);

        // Refactor: Rename to width and height
        double width = (end.getX() - start.getX()) / (double)steps;
        double height = (end.getY() - start.getY()) / (double)steps;

        // steps = 35
        // crackDepth = 1
        int bound = crackDepth;
        // jump = 5
        int jump  = bound * 5;

        double x,y;

        for(int i = 1; i < steps;i++){

            x = (i * width) + start.getX();
            y = (i * height) + start.getY() + randomInBounds(bound);

            // Refactor: Remove CRACK_SECTIONS and JUMP_PROBABILITY, as they are both constant value
            if(inMiddle(i,steps))
                y += jumps(jump);

            path.getElements().add(new LineTo(x,y));

        }
        path.getElements().add(new LineTo(end.getX(), end.getY()));
        crack = path;
    }

    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    // Refactor: Remove int steps out of the parameter
    private boolean inMiddle(int i,int divisions){
        int low = (CRACK_SECTIONS / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    // Refactor: Remove double probability out of the parameter
    private int jumps(int bound){

        if(rnd.nextDouble() > JUMP_PROBABILITY)
            return randomInBounds(bound);
        return  0;

    }

    // TODO Refactor: Use Enum and make a method
    private Point2D makeRandomPoint(Point2D from,Point2D to, int direction){

        Point2D out = new Point2D(0,0);
        int pos;

        switch (direction) {
            case HORIZONTAL -> {
                // Generate a random int between the start to the end of the crack brick
                pos = rnd.nextInt((int)to.getX() - (int)from.getX()) + (int)from.getX();
                out = new Point2D(pos, to.getY());
            }
            case VERTICAL -> {
                pos = rnd.nextInt((int)to.getY() - (int)from.getY())+ (int)from.getY();
                out = new Point2D(to.getX(),pos);
            }
        }
        return out;
    }
}
