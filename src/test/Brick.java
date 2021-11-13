package test;

import java.awt.*;
import java.awt.Point;
//import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
//import java.util.Random;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Brick  {


    public static final int MIN_CRACK = 1;
    // These 2 var only used in cementBrick
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    // Move to crack class
//    private static Random rnd;

    // TODO Think what is the point of the name pass in?
    private String name;
    private int fullStrength;
    private int strength;
    private boolean broken;


    // TODO Note to refactor: Brick and Ball class have the same 3 variable declaration
    // Refactor: From protected to private (Shape brickFace)
    // Refactor: Added final attribute to borderColor, innerColor
    private Shape brickFace;
    // Refactor: Rename to borderColor and innerColor
    final private Color borderColor;
    final private Color innerColor;


    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
//        rnd = new Random();
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.borderColor = border;
        this.innerColor = inner;
        this.fullStrength = this.strength = strength;

    }

    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return broken;
    }

    public abstract Shape getBrick();

    public Color getBorderColor(){
        return borderColor;
    }

    public Color getInnerColor(){
        return innerColor;
    }

    // Refactor: Rename b -> ball for meaningful naming convention
    public final int findImpact(Ball ball){
        if(broken)
            return 0;
        int out  = 0;
        if(getBrickFace().contains(ball.getRight()))
            out = LEFT_IMPACT;
        else if(getBrickFace().contains(ball.getLeft()))
            out = RIGHT_IMPACT;
        else if(getBrickFace().contains(ball.getUp()))
            out = DOWN_IMPACT;
        else if(getBrickFace().contains(ball.getDown()))
            out = UP_IMPACT;
        return out;
    }

    //Refactor: 'In Clean Code' conditionals should be expressed as positives: isBroken() return broken -> !broken
    public final boolean isBroken(){
        return !broken;
    }

    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    // TODO Note To Refactor: Split the implementation for impact method, isBroken
    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    // New created getter
    public Shape getBrickFace() {
        return brickFace;
    }


    // Extract this Crack public class out of the abstract Brick class
    // Extract Impact from Crack (Nested Class)
    // ClayBrick didn't use this class but steel and cement did
    // Since its not good to just use Brick class extend Crack class
    // then I think best to split and just use composition
//    public class Crack{
//
//        private static final int CRACK_SECTIONS = 3;
//        private static final double JUMP_PROBABILITY = 0.7;
//
//        public static final int LEFT = 10;
//        public static final int RIGHT = 20;
//        public static final int UP = 30;
//        public static final int DOWN = 40;
//        public static final int VERTICAL = 100;
//        public static final int HORIZONTAL = 200;
//
//
//
//        private GeneralPath crack;
//
//        private int crackDepth;
//        private int steps;
//
//
//        public Crack(int crackDepth, int steps){
//
//            crack = new GeneralPath();
//            this.crackDepth = crackDepth;
//            this.steps = steps;
//
//        }
//
//
//
//        public GeneralPath draw(){
//
//            return crack;
//        }
//
//        public void reset(){
//            crack.reset();
//        }
//
//        protected void makeCrack(Point2D point, int direction){
//            Rectangle bounds = Brick.this.getBrickFace().getBounds();
//
//            Point impact = new Point((int)point.getX(),(int)point.getY());
//            Point start = new Point();
//            Point end = new Point();
//
//
//            // Refactor: Polymorphism (Left extends Directions) or Form Template Method (pull up)
//            switch(direction){
//                case LEFT:
//                    start.setLocation(bounds.x + bounds.width, bounds.y);
//                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
//                    // Inconsistent code as only LEFT have Point tmp
//                    Point tmp = makeRandomPoint(start,end,VERTICAL);
//                    makeCrack(impact,tmp);
//
//                    break;
//                case RIGHT:
//                    start.setLocation(bounds.getLocation());
//                    end.setLocation(bounds.x, bounds.y + bounds.height);
//                    tmp = makeRandomPoint(start,end,VERTICAL);
//                    makeCrack(impact,tmp);
//
//                    break;
//                case UP:
//                    start.setLocation(bounds.x, bounds.y + bounds.height);
//                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
//                    tmp = makeRandomPoint(start,end,HORIZONTAL);
//                    makeCrack(impact,tmp);
//                    break;
//                case DOWN:
//                    start.setLocation(bounds.getLocation());
//                    end.setLocation(bounds.x + bounds.width, bounds.y);
//                    tmp = makeRandomPoint(start,end,HORIZONTAL);
//                    makeCrack(impact,tmp);
//
//                    break;
//
//            }
//        }
//
//        protected void makeCrack(Point start, Point end){
//
//            GeneralPath path = new GeneralPath();
//
//
//            path.moveTo(start.x,start.y);
//
//            // Refactor: Rename to width and height
//            double w = (end.x - start.x) / (double)steps;
//            double h = (end.y - start.y) / (double)steps;
//
//            int bound = crackDepth;
//            int jump  = bound * 5;
//
//            double x,y;
//
//            for(int i = 1; i < steps;i++){
//
//                x = (i * w) + start.x;
//                y = (i * h) + start.y + randomInBounds(bound);
//
//                if(inMiddle(i,CRACK_SECTIONS,steps))
//                    y += jumps(jump,JUMP_PROBABILITY);
//
//                path.lineTo(x,y);
//
//            }
//
//            path.lineTo(end.x,end.y);
//            crack.append(path,true);
//        }
//
//        private int randomInBounds(int bound){
//            int n = (bound * 2) + 1;
//            return rnd.nextInt(n) - bound;
//        }
//
//        private boolean inMiddle(int i,int steps,int divisions){
//            int low = (steps / divisions);
//            int up = low * (divisions - 1);
//
//            return  (i > low) && (i < up);
//        }
//
//        private int jumps(int bound,double probability){
//
//            if(rnd.nextDouble() > probability)
//                return randomInBounds(bound);
//            return  0;
//
//        }
//
//        private Point makeRandomPoint(Point from,Point to, int direction){
//
//            Point out = new Point();
//            int pos;
//
//            switch(direction){
//                case HORIZONTAL:
//                    pos = rnd.nextInt(to.x - from.x) + from.x;
//                    out.setLocation(pos,to.y);
//                    break;
//                case VERTICAL:
//                    pos = rnd.nextInt(to.y - from.y) + from.y;
//                    out.setLocation(to.x,pos);
//                    break;
//            }
//            return out;
//        }
//
//    }

}
