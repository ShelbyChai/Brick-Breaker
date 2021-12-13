package brickdestroyer.model.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class BallTest {
    Point2D initialPosition = new Point2D(300, 430);
    Ball rubberBall = new RubberBall(initialPosition);


    @Test
    void moveTo() {
        rubberBall.moveTo(new Point2D(400, 430));

        assertEquals(new Point2D(400, 430), rubberBall.getCenter());
    }

    @Test
    void move() {
        rubberBall.setSpeed(3, -3);
        rubberBall.move();

        Point2D centerXY = new Point2D(rubberBall.getBallFace().getCenterX(), rubberBall.getBallFace().getCenterY());
        assertNotEquals(new Point2D(400, 430), centerXY);
    }

    @Test
    void makeBall() {
        assertNotNull(rubberBall.getBallFace());
    }

    @Test
    void reverseX() {
        rubberBall.setSpeedX(3);
        rubberBall.reverseX();
        assertEquals(-3 ,rubberBall.getSpeedX());
    }

    @Test
    void reverseY() {
        rubberBall.setSpeedY(-3);
        rubberBall.reverseY();
        assertEquals(3 ,rubberBall.getSpeedY());
    }

    @Test
    void setSpeed() {
        rubberBall.setSpeed(3,-3);
        Point2D speedXY = new Point2D(rubberBall.getSpeedX(), rubberBall.getSpeedY());
        assertEquals(new Point2D(3,-3), speedXY);
    }

    @Test
    void setSpeedX() {
        rubberBall.setSpeedX(3);
        assertEquals(3, rubberBall.getSpeedX());
    }

    @Test
    void setSpeedY() {
        rubberBall.setSpeedY(-3);
        assertEquals(-3, rubberBall.getSpeedY());
    }

    @Test
    void getCenter() {
        assertEquals(initialPosition, rubberBall.getCenter());
    }

    @Test
    void getUp() {
        rubberBall.move();
        Point2D expectedUp = new Point2D(rubberBall.getCenter().getX(),rubberBall.getCenter().getY()-(rubberBall.getHeight() / 2));
        assertEquals(expectedUp, rubberBall.getUp());
    }

    @Test
    void getDown() {
        rubberBall.move();
        Point2D expectedDown = new Point2D(rubberBall.getCenter().getX(),rubberBall.getCenter().getY()+(rubberBall.getHeight() / 2));
        assertEquals(expectedDown, rubberBall.getDown());
    }

    @Test
    void getLeft() {
        rubberBall.move();
        Point2D expectedLeft = new Point2D(rubberBall.getCenter().getX() - rubberBall.getWidth()/2,rubberBall.getCenter().getY());
        assertEquals(expectedLeft, rubberBall.getLeft());
    }

    @Test
    void getRight() {
        rubberBall.move();
        Point2D expectedRight = new Point2D(rubberBall.getCenter().getX() + rubberBall.getWidth()/2,rubberBall.getCenter().getY());
        assertEquals(expectedRight, rubberBall.getRight());
    }

    @Test
    void getBorderColor() {
        Color expectedBorderColor = Color.rgb(255,219,88).darker().darker();
        assertEquals(expectedBorderColor, rubberBall.getBorderColor());
    }

    @Test
    void getInnerColor() {
        Color expectedInnerColor = Color.rgb(255,219,88);
        assertEquals(expectedInnerColor, rubberBall.getInnerColor());
    }

}