package brickdestroyer.Actor;


import javafx.animation.PathTransition;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

// TODO CrackableBrick extends Brick CementBrick extends CrackableBrick ClayBrick extends Brick
// TODO so i have a separate abstract class that extends from entities to accomodate movement functions

public class CementBrick extends Brick{


    private static final String NAME = "Cement Brick";
    private static final Color DEF_BORDER = Color.rgb(217, 199, 175);
    private static final Color DEF_INNER = Color.rgb(147, 147, 147);
    private static final int CEMENT_STRENGTH = 2;

    final private Crack crack;
    private Shape brickFace;
    private Path gp;

    public CementBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.getBrickFace();
    }

    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(),pos.getY(),size.getWidth(),size.getHeight());
    }

    @Override
    public boolean setImpact(Point2D point, int direction) {
        if(!super.isBroken())
            return false;
        super.impact();
        if(super.isBroken()){
            //
            System.out.println(super.isBroken());
            crack.makeCrack(point,direction,brickFace);
            updateBrick();
            return false;
        }
        return true;
    }

    @Override
    public Shape getBrick() {
        return brickFace;
    }

    // From void to boolean
    private void updateBrick(){
        // I used a Path element to redraw the brick itself with the crack
        //i passed in the information about the brick to the class path and redrew the brick with cracks

//            return crack.draw();
//            Path gp = crack.draw();
//            gp.append(getBrick(),false);
//            brickFace = gp;

            // TODO this not confirm
            if(super.isBroken()){
                gp = crack.draw();

                gp.setFill(Color.BLACK);
                gp.setStroke(Color.BLACK);
                brickFace = gp;

            }

    }

    public void repair(){
        super.repair();
        crack.reset();
        brickFace = getBrick();
    }

}
