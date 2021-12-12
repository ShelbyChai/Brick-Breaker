package brickdestroyer.model.entities;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.*;

import java.util.Random;

/**
 * Crack class contains the properties and behavior of a crackable
 * brick class such as cement brick in the game of Brick Destroyer.
 * This class defines the overall visual appearance of a crackable brick.
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
     * @param crackDepth line to random jump
     * @param steps The number of steps and turns for each created crack path. (number of line) crackpath element
     */
    public Crack(int crackDepth, int steps){
        rnd = new Random();
        crackPath = new Path();
        this.crackDepth = crackDepth;
        this.steps = steps;
    }

    /**
     * This method is called to return the crack path.
     * @return a Path that contains the generated crack path.
     */
    public Path draw(){
        return crackPath;
    }

    /**
     * This method is called to remove all the crack path element in the crackPath.
     */
    public void reset(){
        crackPath.getElements().clear();
    }

    /**
     * This method locate the bounds and the impact position of the brick, then it calls the helper methods
     * in the crack class to draw the cracks based on the direction of the impact from the ball.
     * @param point a Point2D value that represent the impact position of the ball and the brick.
     * @param impactDirection a ImpactDirection enum value that represent the direction the crack should be generated to.
     * @param brickBounds a Shape object that represent the brickFace of the brick that got impact by the ball.
     */
    public void makeCrack(Point2D point, Brick.ImpactDirection impactDirection, Shape brickBounds) {
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

    /**
     * This is a helper method for makeCrack() method. (Renamed to crack from makeCrack() to avoid method overloading)
     * It draws the crack lines using the start and end parameter and assign into a path object.
     * @param start a Point2D value that represent the position where the ball impact the brick.
     * @param end a Point2D value that represent the random position between the bounds of the bricks.
     */
    private void crack(Point2D start, Point2D end){
        Path path = new Path();

        path.getElements().add(new MoveTo(start.getX(), start.getY()));

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

    /**
     * This is a helper method for crack() method.
     * It checks if the crack steps are within the range of the first and the last generated crack steps.
     * @param i an Integer value that represent the current value of crack steps.
     * @param divisions an Integer value that represent the total value of crack steps.
     * @return a Boolean value that is true if the crack depths are not the first or the last step, false if it is.
     */
    private boolean inMiddle(int i,int divisions){
        int low = (CRACK_SECTIONS / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    /**
     * This method is a helper method for crack() method. It randomizes a double
     * position and return a random generated point if it is more than the predefined
     * probability.
     *
     * @param bound an Integer value that represent the multiplied crack depth.
     * @return an Integer value that contains the random generated point within the bound
     *        if it's greater than the predefined probability, 0 otherwise.
     */
    private int jumps(int bound){
        if(rnd.nextDouble() > JUMP_PROBABILITY)
            return randomInBounds(bound);
        return  0;
    }

    /**
     * This method is a helper method for jumps() method.
     * It obtains and return the random position within the crack depth
     * variable which is required by the jumps() method.
     * @param bound an Integer value that represent the crack depth of the crack.
     * @return an Integer value that contains the random point within the crack depth.
     */
    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    /**
     * This method is a help method for makeCrack() method. It makes a random x and y position using the
     * from parameter as the starting position and to parameter as the end point.
     * @param from a Point2D value that represent the starting value of a point the bound.
     * @param to a Point2D value that represent the end value of a point the bound.
     * @param direction a Direction enum that represent the horizontal or the vertical direction of the crack.
     * @return a Point2D value that represent the generated random point value.
     */
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
