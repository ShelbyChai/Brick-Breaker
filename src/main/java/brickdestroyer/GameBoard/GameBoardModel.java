package brickdestroyer.GameBoard;

import brickdestroyer.Actor.Ball;
import brickdestroyer.Actor.BallFactory;
import brickdestroyer.Actor.Brick;
import brickdestroyer.Actor.Levels;
import brickdestroyer.Actor.Player;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import java.util.Random;

import static brickdestroyer.GameBoard.GameBoardViewController.DEF_HEIGHT;
import static brickdestroyer.GameBoard.GameBoardViewController.DEF_WIDTH;

public class GameBoardModel {
    private Random rnd;
    private Brick[] bricks;
    private Player player;
    private Ball ball;

    private final Levels levels;
    private final Rectangle area;
    private final Point2D startPoint;

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

        levels = new Levels(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, (double)6/2);
        ball.setSpeed(randomSpeedX(),randomSpeedY());

        area = drawArea;
    }

    // MVC: Controller
    public void resetPoint(){
        rnd = new Random();
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        ball.setSpeed(randomSpeedX(),randomSpeedY());
        ballLost = false;
    }

    // MVC: Controller call but in wall class
    // Refactor: Created a new method to decrement the brickCount
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    // MVC: Controller
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
        else if(impactBorderX()) {
            ball.reverseX();
        }
        // TODO refactor else if
        // Check if the ball's midpoint hit the top of the screen
        else if(impactBorderCeiling()){
            ball.reverseY();
        }
        // Check if the ball's midpoint hit the bottom of the screen
        else if(impactBorderFloor()){
            ballCount--;
            ballLost = true;
        }
    }

    // MVC: Controller
    public void nextLevel(){
        setBricks(levels.getLevels()[currentLevel++]);
        setBrickCount(getBrick().length);
    }

    // MVC: Controller call -> in wall class
    private boolean impactWall(){
        // Find the impact for each bricks
        for(Brick brick : bricks){
            // Refactor: Polymorphism
            boolean isCrackableBrick = brick.getName().equalsIgnoreCase("Cement Brick");
            switch (brick.findImpact(ball)) {
                //Vertical Impact
                case UP -> {
                    ball.reverseY();
                    return isCrackableBrick ? brick.setImpact(ball.getDown(), Brick.ImpactDirection.UP) : brick.setImpact();
                }
                case DOWN -> {
                    ball.reverseY();
                    return isCrackableBrick ? brick.setImpact(ball.getUp(), Brick.ImpactDirection.DOWN) : brick.setImpact();
                }
                //Horizontal Impact
                case LEFT -> {
                    ball.reverseX();
                    return isCrackableBrick ? brick.setImpact(ball.getRight(), Brick.ImpactDirection.RIGHT) : brick.setImpact();
                }
                case RIGHT -> {
                    ball.reverseX();
                    return isCrackableBrick ? brick.setImpact(ball.getLeft(), Brick.ImpactDirection.LEFT) : brick.setImpact();
                }
            }
        }
        return false;
    }

    private boolean impactBorderX(){
        Point2D p = ball.getPosition();

        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    private boolean impactBorderCeiling() {
        return ball.getPosition().getY() < area.getY();
    }

    private boolean impactBorderFloor() {
        return ball.getPosition().getY() > area.getY() + area.getHeight();
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

    private int randomSpeedX() {
        rnd = new Random();
        int speedX;
        do{
            speedX = rnd.nextInt(5) - 2;
        }while(speedX == 0);

        return speedX;
    }

    private int randomSpeedY() {
        rnd = new Random();
        int speedY;
        do{
            speedY = -rnd.nextInt(3);

        }while(speedY == 0);

        return speedY;
    }

    public boolean hasLevel(){
        return currentLevel < Levels.LEVELS_COUNT;
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
