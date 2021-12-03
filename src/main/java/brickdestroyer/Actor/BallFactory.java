package brickdestroyer.Actor;

import javafx.geometry.Point2D;

public class BallFactory {

    public Ball getBallType(String ballType, Point2D center) {
        if (ballType == null) {
            return null;
        }
        if (ballType.equalsIgnoreCase("Rubber Ball")) {
            return new RubberBall(center);
        }
        return null;
    }
}
