package brickdestroyer.model.entities;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class HasteBrick extends Brick {
    private static final String NAME = "Haste Brick";
    private static final Color DEF_BORDER = Color.DARKORANGE;
    private static final Color DEF_INNER = Color.LIGHTYELLOW;
    private static final int HASTE_STRENGTH = 1;


    public HasteBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,HASTE_STRENGTH);
    }

    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
    }

    @Override
    public Shape getBrickFace() {
        return super.getBrickFace();
    }

    @Override
    public Path getCrackPath() {
        return null;
    }
}
