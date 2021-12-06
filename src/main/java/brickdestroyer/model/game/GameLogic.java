package brickdestroyer.model.game;

import brickdestroyer.model.abstract_entities.Ball;
import brickdestroyer.model.entities_factory.BallFactory;
import brickdestroyer.model.abstract_entities.Brick;
import brickdestroyer.model.Levels;
import brickdestroyer.model.entities.Player;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import brickdestroyer.model.abstract_entities.Brick.ImpactDirection;

import static brickdestroyer.model.game.GameBoardModel.DEF_HEIGHT;
import static brickdestroyer.model.game.GameBoardModel.DEF_WIDTH;

public class GameLogic {
    private Random rnd;
    private Brick[] bricks;
    private Player player;
    private Ball ball;

    private Levels levels;

    private final Rectangle area;
    private final Point2D startPoint;

    private int ballCount;
    private boolean ballLost;
    private int brickCount;
    private int currentLevel;

    public GameLogic(Rectangle drawArea, Point2D ballPos){

        this.currentLevel = 0;
        this.ballCount = 3;
        this.ballLost = false;
        this.startPoint = new Point2D(ballPos.getX(),ballPos.getY());

        BallFactory ballFactory = new BallFactory();
        ball = ballFactory.getBallType("Rubber Ball", ballPos);

        player = new Player(ballPos,150,10, drawArea);

        levels = new Levels(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, (double)6/2);

        ball.setSpeed(randomSpeedX(),randomSpeedY());

        area = drawArea;
    }

    public void move(){
        player.move();
        ball.move();
    }

    public void resetPoint(){
        rnd = new Random();
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        ball.setSpeed(randomSpeedX(),randomSpeedY());
        ballLost = false;
    }

    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    public void nextLevel(){
        setBricks(levels.getLevels()[currentLevel++]);
        setBrickCount(getBricks().length);
    }

    public void findImpacts(){
        if(setImpact(player, ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            brickCount--;
        }
        else if(impactBorderX()) {
            ball.reverseX();
        }
        else if(impactBorderCeiling()){
            ball.reverseY();
        }
        else if(impactBorderFloor()){
            ballCount--;
            ballLost = true;
        }
    }

    public boolean setImpact(Player player,Ball ball){
        return player.getPlayerFace().contains(ball.getPosition()) && getPlayer().getPlayerFace().contains(ball.getDown()) ;
    }

    private boolean impactWall(){
        for(Brick brick : bricks){
            boolean isCrackableBrick = brick.getName().equalsIgnoreCase("Cement Brick");
            switch (impactBrick(brick,ball)) {
                case UP -> {
                    ball.reverseY();
                    return isCrackableBrick ? brick.setImpact(ball.getDown(), ImpactDirection.UP) : brick.setImpact();
                }
                case DOWN -> {
                    ball.reverseY();
                    return isCrackableBrick ? brick.setImpact(ball.getUp(), ImpactDirection.DOWN) : brick.setImpact();
                }
                case LEFT -> {
                    ball.reverseX();
                    return isCrackableBrick ? brick.setImpact(ball.getRight(), ImpactDirection.RIGHT) : brick.setImpact();
                }
                case RIGHT -> {
                    ball.reverseX();
                    return isCrackableBrick ? brick.setImpact(ball.getLeft(), ImpactDirection.LEFT) : brick.setImpact();
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

    public final ImpactDirection impactBrick(Brick brick, Ball ball){
        if(brick.getBroken())
            return ImpactDirection.NONE;

        ImpactDirection direction = ImpactDirection.NONE;
        if(brick.getBrickFace().contains(ball.getRight()))
            direction = ImpactDirection.LEFT;

        else if(brick.getBrickFace().contains(ball.getLeft()))
            direction = ImpactDirection.RIGHT;

        else if(brick.getBrickFace().contains(ball.getUp()))
            direction = ImpactDirection.DOWN;

        else if(brick.getBrickFace().contains(ball.getDown()))
            direction = ImpactDirection.UP;

        return direction;
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

        } while(speedY == 0);

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

    public void setBallSpeedX(int speedX){
        ball.setSpeedX(speedX);
    }
    public void setBallSpeedY(int speedY){
        ball.setSpeedY(speedY);
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
    public Brick[] getBricks() {
        return bricks;
    }

}
