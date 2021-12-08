package brickdestroyer.model.entities;

import javafx.scene.paint.Color;

public interface Entity {

    Color getBorderColor();

    Color getInnerColor();

    double getXPosition();

    double getYPosition();

    double getWidth();

    double getHeight();
}
