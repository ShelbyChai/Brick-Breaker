package brickdestroyer.model.entities;

import brickdestroyer.model.abstract_entities.Brick;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class SteelBrick extends Brick {

    private static final String NAME = "Steel Brick";
    private static final Color DEF_INNER = Color.rgb(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    public SteelBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
    }

    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
    }

    @Override
    public Shape getBrick() {
        return super.getBrickFace();
    }

    @Override
    public Path getCrackPath() {
        return null;
    }

    @Override
    public boolean setImpact(){
        if(!super.isBroken())
            return false;
        impact();
        return !super.isBroken();
    }

    @Override
    public void impact(){
        Random rnd = new Random();
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }
}
