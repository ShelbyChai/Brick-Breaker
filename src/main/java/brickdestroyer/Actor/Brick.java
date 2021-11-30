package brickdestroyer.Actor;


import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

abstract public class Brick {
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

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

    // NEW for fx
    private Point2D pos;
    private Dimension2D size;

    public Brick(String name, Point2D pos, Dimension2D size, Color borderColor, Color innerColor, int strength){

        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.borderColor = borderColor;
        this.innerColor = innerColor;
        fullStrength = this.strength = strength;


        // New for fx
        this.pos = pos;
        this.size = size;

    }

    protected abstract Shape makeBrickFace(Point2D pos,Dimension2D size);

    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return broken;
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

    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    public abstract Shape getBrick();

    public Color getBorderColor(){
        return borderColor;
    }

    public Color getInnerColor(){
        return innerColor;
    }

    // Newly 3 created getter
    public Shape getBrickFace() {
        return brickFace;
    }

    public Point2D getPos() {
        return pos;
    }

    public Dimension2D getSize() {
        return size;
    }
}
