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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// TODO: Remove the pause method and create a new class for it
// TODO: Adapt MVC pattern for all

public class GameBoard extends JComponent implements KeyListener {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;
    private static final Color BG_COLOR = Color.WHITE;
    private Timer gameTimer;
    private GameLogic gameLogic;
    private String message;
    private DebugConsole debugConsole;
    // Refactor:
    private Levels levels;
    private PauseMenu pauseMenu;


    public GameBoard(JFrame owner){
        super();
        message = "";
        this.initialize();

        gameLogic = new GameLogic(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),new Point(300,430));
        levels = new Levels(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2, gameLogic);

        pauseMenu = new PauseMenu(gameLogic, this,this);

        debugConsole = new DebugConsole(owner, gameLogic,levels,this);
        //initialize the first level
        levels.nextLevel();

        // Refresh frame per 10millisecond
        gameTimer = new Timer(10,e ->{
            gameLogic.move();
            gameLogic.findImpacts();
            message = String.format("Bricks: %d Balls %d", gameLogic.getBrickCount(), gameLogic.getBallCount());

            if(gameLogic.isBallLost()){
                if(gameLogic.ballEnd()){
                    gameLogic.wallReset();
                    message = "Game over";
                }
                gameLogic.ballReset();
                gameTimer.stop();
            }
            else if(gameLogic.isDone()){
                if(levels.hasLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    gameLogic.ballReset();
                    gameLogic.wallReset();
                    levels.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                }
            }
            // Refresh the pane
            repaint();
        });
    }

    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        // Set the color of number of brick and number of balls left
        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);

        drawBall(gameLogic.ball,g2d);

        for(Brick b : gameLogic.bricks)
            if(b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(gameLogic.player,g2d);

        pauseMenu.displayMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    // Background color
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }


    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
//        pauseMenu.repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                gameLogic.player.moveLeft();
                break;
            case KeyEvent.VK_D:
                gameLogic.player.movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                pauseMenu.setShowPauseMenu(pauseMenu.isShowPauseMenu());
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
//                if(!showPauseMenu)
                if(pauseMenu.isShowPauseMenu())
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                gameLogic.player.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameLogic.player.stop();
    }

    public void setMessage(String message) {this.message = message;}
}
