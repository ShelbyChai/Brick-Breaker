package brickdestroyer.GameBoard;

import brickdestroyer.Actor.Ball;
import brickdestroyer.Actor.Player;
import brickdestroyer.Actor.RubberBall;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class GameBoardModel {
    private Rectangle area;
    private Random rnd;

    Ball ball;
    Player player;

    private Point2D startPoint;
    private int ballCount;
    private boolean ballLost;
//    private int brickCount;

    public GameBoardModel(Rectangle drawArea,Point2D ballPos){

        this.startPoint = new Point2D(ballPos.getX(),ballPos.getY());

        this.ballCount = 3;
        this.ballLost = false;

        makeBall(ballPos);
        ball.setSpeed(randomSpeedX(),randomSpeedY());
        makePlayer(ballPos,150,10, drawArea);

        area = drawArea;
    }

    public int randomSpeedX() {
        rnd = new Random();
        int speedX;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);

        return speedX;
    }

    public int randomSpeedY() {
        rnd = new Random();
        int speedY;
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        return speedY;
    }

    private void makePlayer(Point2D pos, int width, int height, Rectangle drawArea) {
        player = new Player(pos, width, height,drawArea);
    }

    public Shape getPlayer() {return player.getPlayerFace();}

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    public Shape getBall() {return ball.getBallFace();}

    public void move(){
        player.move();
        ball.move();
    }

    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
//        else if(impactWall()){
//            /*for efficiency reverse is done into method impactWall
//             * because for every brick program checks for horizontal and vertical impacts
//             */
//            brickCount--;
//        }
        // Check if the ball's midpoint hit the left or right of the border screen
        else if(impactBorder()) {
            ball.reverseX();
        }
        // Check if the ball's midpoint hit the top of the screen
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        // Check if the ball's midpoint hit the bottom of the screen
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }

    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        // Check if the ball's midpoint hit the left or right of the border screen
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public void ballReset(){
        rnd = new Random();
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        } while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        } while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

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
