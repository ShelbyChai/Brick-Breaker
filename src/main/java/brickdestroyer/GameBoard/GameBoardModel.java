package brickdestroyer.GameBoard;

import brickdestroyer.Actor.*;
import brickdestroyer.Actor.Brick;
import brickdestroyer.Actor.Player;
import brickdestroyer.Actor.Ball;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static brickdestroyer.GameBoard.GameBoardViewController.DEF_HEIGHT;
import static brickdestroyer.GameBoard.GameBoardViewController.DEF_WIDTH;

public class GameBoardModel {
    private Rectangle area;
    private Random rnd;

    private Brick[] bricks;
    private Player player;
    private Ball ball;
    private Levels levels;

    private Point2D startPoint;
    private int ballCount;
    private boolean ballLost;
    private int brickCount;
    private int currentLevel;

    public GameBoardModel(Rectangle drawArea,Point2D ballPos){

        this.currentLevel = 0;
        this.ballCount = 3;
        this.ballLost = false;
        this.startPoint = new Point2D(ballPos.getX(),ballPos.getY());

        makeBall("Rubber Ball", ballPos);
        makePlayer(ballPos,150,10, drawArea);

        levels = new Levels(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6/2);
        ball.setSpeed(randomSpeedX(),randomSpeedY());
        // Anything uses this are related to border
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

    // MVC: Controller
    public void ballReset(){
        rnd = new Random();
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        ball.setSpeed(randomSpeedX(),randomSpeedY());
        ballLost = false;
    }

    // MVC: Controller
    // Refactor: Created a new method to decrement the brickCount
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    // MVC: Model call in controller
    private void makePlayer(Point2D pos, int width, int height, Rectangle drawArea) {
        player = new Player(pos, width, height,drawArea);
    }

    // MVC: Model
    private void makeBall(String ballType, Point2D center){
        BallFactory ballFactory = new BallFactory();
        ball = ballFactory.getBallType(ballType, center);
    }

    // Lazy method can remove
    public void move(){
        player.move();
        ball.move();
    }

    // MVC: Controller
    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            brickCount--;
        }
        // Check if the ball's midpoint hit the left or right of the border screen
        else if(impactBorder()) {
            ball.reverseX();
        }
        // TODO refactor else if
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
                    return b.setImpact(ball.getUp(), Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getRight(), Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getLeft(), Crack.LEFT);
            }
        }
        return false;
    }

    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        // Check if the ball's midpoint hit the left or right of the border screen
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public void nextLevel(){
        setBricks(levels.getLevels()[currentLevel++]);
        setBrickCount(getBrick().length);
    }

    public boolean hasLevel(){
        return currentLevel < levels.getLevelsCount();
    }
    public boolean ballEnd(){
        return ballCount == 0;
    }
    public boolean isDone(){
        return brickCount == 0;
    }
    public boolean isBallLost(){
        return ballLost;
    }

    public void setBallXSpeed(int s){
        ball.setSpeedX(s);
    }
    public void setBallYSpeed(int s){
        ball.setSpeedY(s);
    }
    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }
    public void setBrickCount(int brickCount){
        this.brickCount = brickCount;
    }

    public void resetBallCount(){
        ballCount = 3;
    }

    public int getBallCount(){
        return ballCount;
    }
    public int getBrickCount(){
        return brickCount;
    }

    public Player getPlayer() {return player;}
    public Ball getBall() {return ball;}
    public Brick[] getBrick() {return bricks;}
    public Levels getLevels(){return levels;}

}
