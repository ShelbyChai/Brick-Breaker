package brickdestroyer;

import brickdestroyer.Actor.Ball;
import brickdestroyer.Actor.RubberBall;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class GameLogic {
    private Rectangle area;
    private Random rnd;

    Ball ball;

    private Point2D startPoint;
    private int ballCount;
    private boolean ballLost;
//    private int brickCount;

    public GameLogic(Point2D ballPos){

        // this.startPoint = new Point2D(ballPos);
        this.startPoint = new Point2D(ballPos.getX(),ballPos.getY());


        this.ballCount = 3;
        this.ballLost = false;

        makeBall(ballPos);
//        ball.setSpeed(randomSpeedX(),randomSpeedY());

    }

//    public int randomSpeedX() {
//        rnd = new Random();
//        int speedX;
//        do{
//            speedX = rnd.nextInt(5) - 2;
//        }while(speedX == 0);
//
//        return speedX;
//    }
//
//    public int randomSpeedY() {
//        rnd = new Random();
//        int speedY;
//        do{
//            speedY = -rnd.nextInt(3);
//        }while(speedY == 0);
//
//        return speedY;
//    }

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    public Shape getBall() {return ball.getBallFace();}

//    public void move(){
////        player.move();
//        ball.move();
//    }
//
//    public void findImpacts(){
//        // Check if the ball's midpoint hit the left or right of the border screen
//        if(impactBorder()) {
//            ball.reverseX();
//        }
//        // Check if the ball's midpoint hit the top of the screen
//        else if(ball.getPosition().getY() < area.getY()){
//            ball.reverseY();
//        }
//        // Check if the ball's midpoint hit the bottom of the screen
//        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
//            ballCount--;
//            ballLost = true;
//        }
//    }

//    private boolean impactBorder(){
//        Point2D p = ball.getPosition();
//        // Check if the ball's midpoint hit the left or right of the border screen
//        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
//    }

//    public void ballReset(){
//        rnd = new Random();
//        ball.moveTo(startPoint);
//        int speedX,speedY;
//        do{
//            speedX = rnd.nextInt(5) - 2;
//        } while(speedX == 0);
//        do{
//            speedY = -rnd.nextInt(3);
//        } while(speedY == 0);
//
//        ball.setSpeed(speedX,speedY);
//        ballLost = false;
//    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public void setBallXSpeed(int s){
        ball.setSpeedX(s);
    }

    public void setBallYSpeed(int s){
        ball.setSpeedY(s);
    }

    public void resetBallCount(){
        ballCount = 3;
    }

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

}
