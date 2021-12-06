package brickdestroyer.model.entities;

import brickdestroyer.model.abstract_entities.Ball;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RubberBall extends Ball {

    private static final int DEF_RADIUS = 10;
    private static final Color DEF_INNER_COLOR = Color.rgb(255,219,88);
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();


    public RubberBall(Point2D center){
        super(center,DEF_RADIUS, DEF_INNER_COLOR,DEF_BORDER_COLOR);
    }

    @Override
    protected Circle makeBall(Point2D center, int radius) {
        return new Circle(super.getUpperLeftX(),super.getUpperLeftY(), radius);
    }

}
