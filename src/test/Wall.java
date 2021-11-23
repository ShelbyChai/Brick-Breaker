/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

// TODO Refactor: Crazy idea -> From the wall class make a level, brick logic, player logic, ball logic
public class Wall {
    private Rectangle area;
    private Random rnd;

    Brick[] bricks;
    Ball ball;
    Player player;

    private Point startPoint;
    private int ballCount;
    private boolean ballLost;
    private int brickCount;

    // Refactor: Create a new class call Levels and change the Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) -> (Rectangle drawArea, Point ballPos)
    public Wall(Rectangle drawArea, Point ballPos){

        rnd = new Random();
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


        // drawArea -> screen width and height
        area = drawArea;
    }

    // Refactor: Created 4 new method
    public Brick[] getBricks() {return bricks;}
    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }
    public void setBrickCount(int brickCount){
        this.brickCount = brickCount;
    }
    public void decrementBrickCount() {brickCount--;}



    public int getBrickCount(){
        return brickCount;
    }


    // Refactor: Created a new method to decrement the brickCount
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    public boolean isDone(){
        return brickCount == 0;
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

    // Refactor: Since crack is extracted : Brick.Crack.Up -> Crack.Up
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
        player.moveTo(startPoint);
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
