package brickdestroyer.model.entities;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.*;

import java.util.Random;

/**
 * Crack class contains the properties and behavior of a crackable
 * brick class such as cement brick in the game of Brick Destroyer.
 */
public class Crack {

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;
    private enum Direction {HORIZONTAL, VERTICAL}
    private Path crackPath;
    private final Random rnd;
    private final int crackDepth;
    private final int steps;


    /**
     * Defines and instantiate the properties of the crack class using the given
     * properties.
     * @param crackDepth The
     * @param steps The number of steps and turns for each created crack path.
     */
    public Crack(int crackDepth, int steps){
        rnd = new Random();
        crackPath = new Path();
        this.crackDepth = crackDepth;
        this.steps = steps;
    }

    /**
     *
     * @return
     */
    public Path draw(){
        return crackPath;
    }

    public void reset(){
        crackPath.getElements().clear();
    }

    public void makeCrack(Point2D point, Brick.ImpactDirection impactDirection, Shape brickBounds){
        Bounds bounds = brickBounds.getBoundsInParent();
        Point2D impact = new Point2D((int)point.getX(),(int)point.getY());
        Point2D start, end, tmp;

        switch (impactDirection) {
            case LEFT -> {
                start = new Point2D(bounds.getMaxX(), bounds.getMinY());
                end = new Point2D(bounds.getMaxX(), bounds.getMaxY());
                tmp = makeRandomPoint(start, end, Direction.VERTICAL);
                crack(impact, tmp);
            }
            case RIGHT -> {
                start = new Point2D(bounds.getMinX(), bounds.getMinY());
                end = new Point2D(bounds.getMinX(), bounds.getMaxY());
                tmp = makeRandomPoint(start, end, Direction.VERTICAL);
                crack(impact, tmp);
            }
            case UP -> {
                start = new Point2D(bounds.getMinX(), bounds.getMaxY());
                end = new Point2D(bounds.getMaxX(), bounds.getMaxY());
                tmp = makeRandomPoint(start, end, Direction.HORIZONTAL);
                crack(impact, tmp);
            }
            case DOWN -> {
                start = new Point2D(bounds.getMinX(), bounds.getMinY());
                end = new Point2D(bounds.getMaxX(), bounds.getMinY());
                tmp = makeRandomPoint(start, end, Direction.HORIZONTAL);
                crack(impact, tmp);
            }
        }
    }

    private void crack(Point2D start, Point2D end){
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

    private boolean inMiddle(int i,int divisions){
        int low = (CRACK_SECTIONS / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    private int jumps(int bound){
        if(rnd.nextDouble() > JUMP_PROBABILITY)
            return randomInBounds(bound);
        return  0;
    }

    private Point2D makeRandomPoint(Point2D from,Point2D to, Direction direction){
        Point2D randomPoint = new Point2D(0,0);
        int randomPosition;

        switch (direction) {
            case HORIZONTAL -> {
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
