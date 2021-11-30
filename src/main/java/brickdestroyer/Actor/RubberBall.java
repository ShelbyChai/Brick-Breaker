package brickdestroyer.Actor;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RubberBall extends Ball{

    private static final int DEF_RADIUS = 10;
    private static final Color DEF_INNER_COLOR = Color.rgb(255,219,88);
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();


    public RubberBall(Point2D center){
        super(center,DEF_RADIUS,DEF_RADIUS,DEF_INNER_COLOR,DEF_BORDER_COLOR);
    }

    @Override
    protected Circle makeBall(Point2D center, int radiusA, int radiusB) {

        double x = center.getX() - (radiusA / 2);
        double y = center.getY() - (radiusB / 2);

        return new Circle(x,y,radiusA);
    }

}
