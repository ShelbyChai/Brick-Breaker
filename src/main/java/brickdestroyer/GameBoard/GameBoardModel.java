package brickdestroyer.GameBoard;

import brickdestroyer.Actor.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class GameBoardModel {
    private Rectangle area;
    private Random rnd;

    Brick[] bricks;
    Ball ball;
    Player player;

    private Point2D startPoint;
    private int ballCount;
    private boolean ballLost;
    private int brickCount;

    public GameBoardModel(Rectangle drawArea,Point2D ballPos){

        this.startPoint = new Point2D(ballPos.getX(),ballPos.getY());
        this.ballCount = 3;
        this.ballLost = false;

        makeBall(ballPos);
//        ball.setSpeed(randomSpeedX(),randomSpeedY());
        ballReset();
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

    public int getBrickCount(){
        return brickCount;
    }
    public boolean isDone(){
        return brickCount == 0;
    }

    // Refactor: Created a new method to decrement the brickCount
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }


    private void makePlayer(Point2D pos, int width, int height, Rectangle drawArea) {
        player = new Player(pos, width, height,drawArea);
    }

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    public void move(){
        player.move();
        ball.move();
    }

    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
             */
            brickCount--;
        }
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

    private boolean impactWall(){
        // Find the impact for each bricks
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.getDown(), Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.getUp(),Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getRight(),Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getLeft(),Crack.LEFT);
            }
        }
        return false;
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

    public Shape getPlayer() {return player.getPlayerFace();}

    public Shape getBall() {return ball.getBallFace();}


    // Refactor: Created method
    public Brick[] getBricks() {return bricks;}
    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }
    public void setBrickCount(int brickCount){
        this.brickCount = brickCount;
    }
    public void decrementBrickCount() {brickCount--;}

}
