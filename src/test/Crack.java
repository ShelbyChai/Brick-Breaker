package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

public class Crack{

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;


    private static Random rnd;
    private GeneralPath crack;

    private int crackDepth;
    private int steps;


    public Crack(int crackDepth, int steps){
        rnd = new Random();
        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
    }


    // Return the crack path
    public GeneralPath draw(){

        return crack;
    }

    public void reset(){
        crack.reset();
    }

    // Polymorphism method overloading (there is 2 makeCrack method)
    // Refactor: Added a new Shape brickBounds as a parameter for line Rectangle bounds = ...
    // Refactor: The above refactor also changed CementBrick -> setImpact's -> crack.makeCrack(point,dir) to crack.makeCrack(point,dir,brickFace);
    // TODO Note to refactor: In this makeCrack method it call the other makeCrack method to do something. This means they are not doing the same thing thus can't method overloading (Try to rename the method)
    protected void makeCrack(Point2D point, int direction, Shape brickBounds){
        // bounds: java.awt.Rectangle[x=60,y=40,width=60,height=20], each brick is 60 width & 20 height, the x and y represent the position of the crack brick
        Rectangle bounds = brickBounds.getBounds();
//        System.out.println(bounds);

        Point impact = new Point((int)point.getX(),(int)point.getY());
        Point start = new Point();
        Point end = new Point();


        // TODO Note to refactor: Polymorphism (Left extends Directions) or Form Template Method (pull up)
        // Define the direction of the brick but from upside down (left means right)
        switch(direction){
            case LEFT:
                // Start: x-> upper-right corner, y-> top y
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                // Inconsistent code as only LEFT have Point tmp
                Point tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case RIGHT:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case UP:
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);

                break;
            case DOWN:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);

                break;
        }
    }

    protected void makeCrack(Point start, Point end){

        GeneralPath path = new GeneralPath();


        path.moveTo(start.x,start.y);

        // Refactor: Rename to width and height
        double width = (end.x - start.x) / (double)steps;
        double height = (end.y - start.y) / (double)steps;

        // steps = 35
        // crackDepth = 1
        int bound = crackDepth;
        // jump = 5
        int jump  = bound * 5;

        double x,y;

        for(int i = 1; i < steps;i++){

            x = (i * width) + start.x;
            y = (i * height) + start.y + randomInBounds(bound);

            // Refactor: Remove CRACK_SECTIONS and JUMP_PROBABILITY, as they are both constant value
            if(inMiddle(i,steps))
                y += jumps(jump);

            path.lineTo(x,y);

        }

        path.lineTo(end.x,end.y);
        crack.append(path,true);
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

    private Point makeRandomPoint(Point from,Point to, int direction){

        Point out = new Point();
        int pos;

        switch(direction){
            case HORIZONTAL:
                // Generate a random int between the start to the end of the crack brick
                pos = rnd.nextInt(to.x - from.x) + from.x;
                out.setLocation(pos,to.y);
                break;
            case VERTICAL:
                pos = rnd.nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x,pos);
                break;
        }
        return out;
    }

}
