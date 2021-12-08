package brickdestroyer.model.entities;


import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

abstract public class Brick implements Entity {
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public enum ImpactDirection {LEFT, RIGHT, UP, DOWN, NONE}

    private final String name;
    private final Color borderColor;
    private final Color innerColor;
    private final Shape brickFace;
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

    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    public abstract Path getCrackPath();

    public String getName() {
        return name;
    }

    public boolean getBroken(){return broken;}

    public final boolean isBroken(){
        return !broken;
    }

    public Shape getBrickFace() {
        return brickFace;
    }

    @Override
    public Color getBorderColor(){
        return borderColor;
    }

    @Override
    public Color getInnerColor(){
        return innerColor;
    }

    @Override
    public double getXPosition() {
        return position.getX();
    }

    @Override
    public double getYPosition() {
        return position.getY();
    }

    @Override
    public double getWidth() {
        return size.getWidth();
    }

    @Override
    public double getHeight() {
        return size.getHeight();
    }

}
