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

//    private static final int LEVELS_COUNT = 4;
//    private static final int CLAY = 1;
//    private static final int STEEL = 2;
//    private static final int CEMENT = 3;
//    private Brick[][] levels;
//    private int level;

    Brick[] bricks;
    private int brickCount;


    private Rectangle area;
    private Random rnd;
    Ball ball;
    Player player;
    private Point startPoint;
    private int ballCount;
    private boolean ballLost;

    // Refactor: Create a new class call Levels and change the Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) -> (Rectangle drawArea, Point ballPos)
    // Rectangle, 30, 3, 3, 300, 430
//     public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){
//    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
    public Wall(Rectangle drawArea, Point ballPos){

//        this.levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
//        this.level = 0;


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
//
//    // Rectangle, 30, 3, 3, Type
//    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
//        /*
//          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
//          multiple of lineCount smaller then brickCount
//         */
//        // 30
//        brickCnt -= brickCnt % lineCnt;
//
//        // 10
//        int brickOnLine = brickCnt / lineCnt;
//
//        double brickLen = drawArea.getWidth() / brickOnLine;
//        double brickHgt = brickLen / brickSizeRatio;
//
//        // 31
//        brickCnt += lineCnt / 2;
//
//        Brick[] tmp  = new Brick[brickCnt];
//
//        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
//        Point p = new Point();
//
//        int i;
//        // tmp.length = 31, brickOnLine = 10, lineCnt = 3
//        for(i = 0; i < tmp.length; i++){
//            // The current line of brick
//            int line = i / brickOnLine;
//            // Terminate when the line == lineCount (break when i=30)
//            if(line == lineCnt)
//                break;
//            // x = the current x position
//            double x = (i % brickOnLine) * brickLen;
//            // line 0,2 will proc, Second condition when its the first line
//            // Half the width so the second line's 1st brick will be half width
//            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
//            double y = (line) * brickHgt;
//            p.setLocation(x,y);
//            // makeBrick check what type of Brick to make
//            tmp[i] = makeBrick(p,brickSize,type);
//
//            System.out.println(i);
//        }
//
//        // This for loop add a brick to the end of every even row
//        // y= width
//        // BrickOnLine = 10, BrickHeight = 20
//        // Since i is terminated on 30, thus this for loop only run once since tmp.length = 31
//        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
//            // x always the same = 570
//            double x = (brickOnLine * brickLen) - (brickLen / 2);
//
//            p.setLocation(x,y);
//            tmp[i] = new ClayBrick(p,brickSize);
//        }
//
//        return tmp;
//    }
//
//    // 0 ,0, 3, 3, CLAY, STEEL
//    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
//        /*
//          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
//          multiple of lineCount smaller then brickCount
//         */
//        brickCnt -= brickCnt % lineCnt;
//
//        //brickOnLine = 10
//        int brickOnLine = brickCnt / lineCnt;
//
//        // centerLeft = 4, centerRight = 6
//        int centerLeft = brickOnLine / 2 - 1;
//        int centerRight = brickOnLine / 2 + 1;
//
//        // brickLen = 60, brickHgt = 20
//        double brickLen = drawArea.getWidth() / brickOnLine;
//        double brickHgt = brickLen / brickSizeRatio;
//
//        // brickCnt = 31
//        brickCnt += lineCnt / 2;
//
//        Brick[] tmp  = new Brick[brickCnt];
//
//        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
//        Point p = new Point();
//
//        int i;
//        for(i = 0; i < tmp.length; i++){
//            int line = i / brickOnLine;
//            if(line == lineCnt)
//                break;
//
//            // poxX: value ranging from 0-9
//            int posX = i % brickOnLine;
//
//            double x = posX * brickLen;
//            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
//            double y = (line) * brickHgt;
//            p.setLocation(x,y);
//
//            // first or third row & even bricks || posX = 5,6 & second row
//            // if b = true then typeA else steel typeB
//            // first condition cause the first and third row's even number brick to be typeA (assume we start from 0)
//            // second condition cause the second row's 15 and 16 brick to be type A
//            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
//            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
//        }
//
//        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
//            double x = (brickOnLine * brickLen) - (brickLen / 2);
//            p.setLocation(x,y);
//            tmp[i] = makeBrick(p,brickSize,typeA);
//        }
//        return tmp;
//    }
//
//    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
//        Brick[][] tmp = new Brick[LEVELS_COUNT][];
//        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
//        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
//        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
//        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
//        return tmp;
//    }
//
//    private Brick makeBrick(Point point, Dimension size, int type){
//        Brick out;
//        switch(type){
//            case CLAY:
//                out = new ClayBrick(point,size);
//                break;
//            case STEEL:
//                out = new SteelBrick(point,size);
//                break;
//            case CEMENT:
//                out = new CementBrick(point, size);
//                break;
//            default:
//                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
//        }
//        return out;
//    }
//
//    public void nextLevel(){
//        // Postfix increment
//        bricks = levels[level++];
//        this.brickCount = bricks.length;
//    }
//
//    public boolean hasLevel(){
//        return level < levels.length;
//    }




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

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }


}
