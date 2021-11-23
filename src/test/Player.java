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


public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    //Refactor: Rectangle, min, max, Point from none -> final
    final private Rectangle playerFace;
    final private Point ballPoint;
    final private int min;
    final private int max;

    private int moveAmount;


    // Container -> x: 600, y: 450
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        // ballPoint-x: 300, ballPoint-y: 430
        this.ballPoint = ballPoint;
        this.moveAmount = 0;
        // width: 150, height: 10
        this.playerFace = makeRectangle(width, height);


        this.min = container.x + (width / 2); // This shows the least x-axis value the board can go which is 75 (because rectangle have midpoint so when moving it doesn't go through walls)
        this.max = min + container.width - width; // This shows the maximum x-axis value the board can go which is 525

    }

    private Rectangle makeRectangle(int width,int height){
        // p.x: 225 , p.y: 430
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());   // This set the initial upper-left corner value of the player
        return new Rectangle(p,new Dimension(width,height));
    }

    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;
    }

    public void move(){
        // ballPoint is the midpoint of the player rectangle
        double x = ballPoint.getX() + moveAmount;
        // Check if the center position of the player (x) go beyond x and y
        if(x < min || x > max)
            return;

        // Move the midpoint of the rectangle
        ballPoint.setLocation(x,ballPoint.getY());
        // Move the upper-left-corner value of the player
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop(){
        moveAmount = 0;
    }

    public Shape getPlayerFace(){
        return  playerFace;
    }

    // Reset the position of the player
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }
}
