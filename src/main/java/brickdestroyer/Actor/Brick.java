package brickdestroyer.Actor;


import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

abstract public class Brick {
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    // TODO Think what is the point of the name pass in?
    final private String name;

    // TODO Note to refactor: Brick and Ball class have the same 3 variable declaration
    final private Shape brickFace;
    final private Color borderColor;
    final private Color innerColor;

    final private Point2D pos;
    final private Dimension2D size;

    final private int fullStrength;
    private int strength;
    private boolean broken;

    public Brick(String name, Point2D pos, Dimension2D size, Color borderColor, Color innerColor, int strength){

        broken = false;
        this.name = name;
        this.borderColor = borderColor;
        this.innerColor = innerColor;
        fullStrength = this.strength = strength;
        this.pos = pos;
        this.size = size;

        brickFace = makeBrickFace(pos,size);

    }

    protected abstract Shape makeBrickFace(Point2D pos,Dimension2D size);

    public boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return broken;
    }

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

    // Only used in Cement
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    // Only used in steel
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

    // Newly 4 created getter
    public Shape getBrickFace() {
        return brickFace;
    }

    public Point2D getPos() {
        return pos;
    }

    public Dimension2D getSize() {
        return size;
    }

    public abstract Path getCrackPath();
}




//package brickdestroyer.Actor;
//
//
//        import javafx.geometry.Dimension2D;
//        import javafx.geometry.Point2D;
//        import javafx.scene.paint.Color;
//        import javafx.scene.shape.Path;
//        import javafx.scene.shape.Shape;
//
//abstract public class Brick {
//    public static final int DEF_CRACK_DEPTH = 1;
//    public static final int DEF_STEPS = 35;
//
//    public static final int UP_IMPACT = 100;
//    public static final int DOWN_IMPACT = 200;
//    public static final int LEFT_IMPACT = 300;
//    public static final int RIGHT_IMPACT = 400;
//
//    // TODO Think what is the point of the name pass in?
//    final private String name;
//
//    // TODO Note to refactor: Brick and Ball class have the same 3 variable declaration
//    final private Shape brickFace;
//    final private Color borderColor;
//    final private Color innerColor;
//
//    final private Point2D pos;
//    final private Dimension2D size;
//
//    final private int fullStrength;
//    private int strength;
//    private boolean broken;
//
//    public Brick(String name, Point2D pos, Dimension2D size, Color borderColor, Color innerColor, int strength){
//
//        broken = false;
//        this.name = name;
//        this.borderColor = borderColor;
//        this.innerColor = innerColor;
//        fullStrength = this.strength = strength;
//        this.pos = pos;
//        this.size = size;
//
//        brickFace = makeBrickFace(pos,size);
//
//    }
//
//    protected abstract Shape makeBrickFace(Point2D pos,Dimension2D size);
//
//    public boolean setImpact(Point2D point , int dir){
//        if(broken)
//            return false;
//        impact();
//        return broken;
//    }
//
//    public final int findImpact(Ball ball){
//        if(broken)
//            return 0;
//        int out  = 0;
//        if(getBrickFace().contains(ball.getRight()))
//            out = LEFT_IMPACT;
//        else if(getBrickFace().contains(ball.getLeft()))
//            out = RIGHT_IMPACT;
//        else if(getBrickFace().contains(ball.getUp()))
//            out = DOWN_IMPACT;
//        else if(getBrickFace().contains(ball.getDown()))
//            out = UP_IMPACT;
//        return out;
//    }
//
//    //Refactor: 'In Clean Code' conditionals should be expressed as positives: isBroken() return broken -> !broken
//    public final boolean isBroken(){
//        return !broken;
//    }
//
//    // Only used in Cement
//    public void repair() {
//        broken = false;
//        strength = fullStrength;
//    }
//
//    // Only used in steel
//    public void impact(){
//        strength--;
//        broken = (strength == 0);
//    }
//
//    public abstract Shape getBrick();
//
//    public Color getBorderColor(){
//        return borderColor;
//    }
//
//    public Color getInnerColor(){
//        return innerColor;
//    }
//
//    // Newly 4 created getter
//    public Shape getBrickFace() {
//        return brickFace;
//    }
//
//    public Point2D getPos() {
//        return pos;
//    }
//
//    public Dimension2D getSize() {
//        return size;
//    }
//
//    public abstract Path getCrackPath();
//}

