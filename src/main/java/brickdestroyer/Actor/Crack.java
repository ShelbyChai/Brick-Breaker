package brickdestroyer.Actor;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.*;

import java.util.Random;

public class Crack {
    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    private static final int VERTICAL = 100;
    private static final int HORIZONTAL = 200;

    private Path crackPath;
    private final Random rnd;

    private final int crackDepth;
    private final int steps;


    public Crack(int crackDepth, int steps){
        rnd = new Random();
        crackPath = new Path();
        this.crackDepth = crackDepth;
        this.steps = steps;
    }

    public Path draw(){
        return crackPath;
    }

    public void reset(){
        crackPath.getElements().clear();
    }

    // Polymorphism method overloading (there is 2 makeCrack method)
    // Refactor: The above refactor also changed CementBrick -> setImpact's -> crack.makeCrack(point,dir) to crack.makeCrack(point,dir,brickFace);
    // TODO Note to refactor: In this makeCrack method it call the other makeCrack method to do something. This means they are not doing the same thing thus can't method overloading (Try to rename the method)
    protected void makeCrack(Point2D point, Brick.ImpactDirection impactDirection, Shape brickBounds){
        // bounds: java.awt.Rectangle[x=60,y=40,width=60,height=20], each brick is 60 width & 20 height, the x and y represent the position of the crack brick
        Bounds bounds = brickBounds.getBoundsInParent();

        Point2D impact = new Point2D((int)point.getX(),(int)point.getY());
        Point2D start, end, tmp;

        switch(impactDirection){
            case LEFT:
                // Start: x-> upper-right corner, y-> top y
                start = new Point2D(bounds.getMaxX(), bounds.getMinY());
                end = new Point2D(bounds.getMaxX(), bounds.getMaxY());
                tmp = makeRandomPoint(start,end,VERTICAL);

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

    private void makeCrack(Point2D start, Point2D end){
        Path path = new Path();

        path.getElements().add(new MoveTo(start.getX(),start.getY()));

        double width = (end.getX() - start.getX()) / (double)steps;
        double height = (end.getY() - start.getY()) / (double)steps;

        int jump  = crackDepth * 5;

        for(int i = 1; i < steps;i++){
            double x = (i * width) + start.getX();
            double y = (i * height) + start.getY() + randomInBounds(crackDepth);

            if(inMiddle(i,steps))
                y += jumps(jump);

            path.getElements().add(new LineTo(x,y));

        }
        path.getElements().add(new LineTo(end.getX(), end.getY()));
        crackPath = path;
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
        Point2D randomPoint = new Point2D(0,0);
        int randomPosition;

        switch (direction) {
            case HORIZONTAL -> {
                // Generate a random int between the start to the end of the crack brick
                randomPosition = rnd.nextInt((int)to.getX() - (int)from.getX()) + (int)from.getX();
                randomPoint = new Point2D(randomPosition, to.getY());
            }
            case VERTICAL -> {
                randomPosition = rnd.nextInt((int)to.getY() - (int)from.getY())+ (int)from.getY();
                randomPoint = new Point2D(to.getX(),randomPosition);
            }
        }
        return randomPoint;
    }
}
