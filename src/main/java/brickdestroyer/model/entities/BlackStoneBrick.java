package brickdestroyer.model.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class BlackStoneBrick extends Brick{
    private static final String NAME = "Black Stone Brick";
    private static final Color DEF_BORDER = Color.rgb(198,198,198);
    private static final Color DEF_INNER = Color.rgb(47,46,51);
    private static final int BLACKSTONE_STRENGTH = 2;
    private static final double BLACKSTONE_PROBABILITY = 0.5;

    final private Crack crack;
    private Shape brickFace;
    private Path path;


    public BlackStoneBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,BLACKSTONE_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.getBrickFace();
    }

    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
    }

    @Override
    public boolean setImpact(Point2D point, Brick.ImpactDirection direction) {
        Random rnd = new Random();
        if(!super.isBroken())
            return false;
        super.impact();
        if (super.isBroken() ) {
            if (rnd.nextDouble() < BLACKSTONE_PROBABILITY) {
                crack.makeCrack(point, direction, brickFace);
                path = crack.draw();
            }
            return false;
        }
        return true;
    }

    @Override
    public Shape getBrickFace() {
        return brickFace;
    }

    @Override
    public Path getCrackPath() {
        return path;
    }

    @Override
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = getBrickFace();
    }
}
