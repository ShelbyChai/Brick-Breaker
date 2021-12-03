package brickdestroyer.Actor;


import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ClayBrick extends Brick{
    private static final String NAME = "Clay Brick";
    private static final Color DEF_BORDER = Color.GRAY;
    private static final Color DEF_INNER = Color.rgb(178, 34, 34).darker();
    private static final int CLAY_STRENGTH = 1;


    public ClayBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
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

}




//package brickdestroyer.Actor;
//
//
//        import javafx.geometry.Dimension2D;
//        import javafx.geometry.Point2D;
//        import javafx.scene.paint.Color;
//        import javafx.scene.shape.Path;
//        import javafx.scene.shape.Rectangle;
//        import javafx.scene.shape.Shape;
//
//public class ClayBrick extends Brick{
//    private static final String NAME = "Clay Brick";
//    private static final Color DEF_BORDER = Color.GRAY;
//    private static final Color DEF_INNER = Color.rgb(178, 34, 34).darker();
//    private static final int CLAY_STRENGTH = 1;
//
//
//    public ClayBrick(Point2D point, Dimension2D size){
//        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
//    }
//
//    @Override
//    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
//        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
//    }
//
//    @Override
//    public Shape getBrick() {
//        return super.getBrickFace();
//    }
//
//    @Override
//    public Path getCrackPath() {
//        return null;
//    }
//}
