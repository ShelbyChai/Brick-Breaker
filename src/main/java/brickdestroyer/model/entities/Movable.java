package brickdestroyer.model.entities;

import javafx.geometry.Point2D;

public interface Movable {

    void move();
    void moveTo(Point2D position);
}
