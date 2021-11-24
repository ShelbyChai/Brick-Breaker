package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class LogicBoard {
    private Random rnd;
    private Rectangle area;
    Ball ball;
    Player player;
    private Point startPoint;
    private int ballCount;
    private boolean ballLost;

    GameLogic gameLogic;

    public LogicBoard(Rectangle drawArea, Point ballPos, GameLogic gameLogic) {
        this.gameLogic = gameLogic;

        int speedX,speedY;
        // if speedX is negative then to left else to right (velocity)
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);

        // speedY is negative because move from down to up
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

        this.startPoint = new Point(ballPos);
        this.ballCount = 3;
        this.ballLost = false;
                makeBall(ballPos);
        ball.setSpeed(speedX,speedY);
        player = new Player((Point) ballPos.clone(),150,10, drawArea);
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
            gameLogic.decrementBrickCount();
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

    // Refactor: Since crack is extracted : Brick.Crack.Up -> Crack.Up
    private boolean impactWall(){
        // Find the impact for each bricks
        for(Brick b : gameLogic.getBricks()){
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

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);
        }while(speedY == 0);

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


    // Newly created
    public void setBallCount(int ballCount) {this.ballCount = ballCount;}
}
