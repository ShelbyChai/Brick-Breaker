package brickdestroyer.Actor;


import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

abstract public class Brick {
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public enum ImpactDirection {LEFT, RIGHT, UP, DOWN, NONE}

    private final String name;
    // TODO Note to refactor: Brick and Ball class have the same 3 variable declaration
    private final Shape brickFace;
    private final Color borderColor;
    private final Color innerColor;

    private final Point2D position;
    private final Dimension2D size;

    private final int fullStrength;
    private int strength;
    private boolean broken;

    public Brick(String name, Point2D position, Dimension2D size, Color borderColor, Color innerColor, int strength){

        broken = false;
        this.name = name;
        this.borderColor = borderColor;
        this.innerColor = innerColor;
        fullStrength = this.strength = strength;
        this.position = position;
        this.size = size;

        brickFace = makeBrickFace(position,size);

    }

    protected abstract Shape makeBrickFace(Point2D pos,Dimension2D size);

    // Refactor: Polymorphism
    public boolean setImpact(){
        if(broken)
            return false;
        impact();
        return broken;
    }

    public boolean setImpact(Point2D point , ImpactDirection direction){
        if(broken)
            return false;
        impact();
        return broken;
    }

    public final ImpactDirection findImpact(Ball ball){
        if(broken)
            return ImpactDirection.NONE;

        ImpactDirection direction = ImpactDirection.NONE;
        if(getBrickFace().contains(ball.getRight()))
            direction = ImpactDirection.LEFT;

        else if(getBrickFace().contains(ball.getLeft()))
            direction = ImpactDirection.RIGHT;

        else if(getBrickFace().contains(ball.getUp()))
            direction = ImpactDirection.DOWN;

        else if(getBrickFace().contains(ball.getDown()))
            direction = ImpactDirection.UP;

        return direction;
    }

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

    public Shape getBrickFace() {
        return brickFace;
    }

    public Point2D getPosition() {
        return position;
    }

    public Dimension2D getSize() {
        return size;
    }

    public abstract Path getCrackPath();

    public String getName() {
        return name;
    }
}
