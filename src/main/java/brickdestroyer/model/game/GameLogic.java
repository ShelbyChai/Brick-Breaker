package brickdestroyer.model.game;

import brickdestroyer.model.entities.Ball;
import brickdestroyer.model.entities_factory.BallFactory;
import brickdestroyer.model.entities.Brick;
import brickdestroyer.model.Levels;
import brickdestroyer.model.entities.Player;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import brickdestroyer.model.entities.Brick.ImpactDirection;

import static brickdestroyer.model.game.GameBoardModel.DEF_HEIGHT;
import static brickdestroyer.model.game.GameBoardModel.DEF_WIDTH;

public class GameLogic {
    private Brick[] bricks;
    private final Player player;
    private final Ball ball;
    private final Levels levels;

    private final Rectangle area;
    private final Point2D startPoint;

    private int ballCount;
    private boolean ballLost;
    private int brickCount;
    private int currentLevel;
    private int score;

    public GameLogic(Rectangle drawArea, Point2D ballPos){

        this.currentLevel = 0;
        this.ballCount = 3;
        this.ballLost = false;
        this.startPoint = new Point2D(ballPos.getX(),ballPos.getY());
        this.score = 0;

        BallFactory ballFactory = new BallFactory();
        ball = ballFactory.getBallType("Rubber Ball", ballPos);
        player = new Player(ballPos,150,10, drawArea);
        levels = new Levels(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, (double)6/2);

        initialiseSpeed();
        area = drawArea;
    }

    // TODO Create a score logic
    public void move(){
        player.move();
        ball.move();
    }

    public void resetPoint(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        initialiseSpeed();
        ballLost = false;
    }

    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;

        score = 0;
    }

    public void nextLevel(){
        setBricks(levels.getLevels()[currentLevel++]);
        setBrickCount(getBricks().length);

        if(currentLevel!=1)
            score += 200;
    }

    public void initialiseSpeed() {
        Random rnd = new Random();
        int speedX, speedY;
        do {
            speedX = rnd.nextInt(5) - 2;
        } while (speedX == 0);

        do {
            speedY = -rnd.nextInt(3);
        } while (speedY == 0);

        ball.setSpeed(speedX, speedY);
    }

    public void findImpacts(){
        if(setImpact(player, ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            brickCount--;

            score += 10;
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

            if (score >= 50)
                score -= 50;
            else
                score = 0;
        }
    }

    public boolean setImpact(Player player,Ball ball){
        return player.getPlayerFace().contains(ball.getCenter()) && getPlayer().getPlayerFace().contains(ball.getDown()) ;
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
        Point2D p = ball.getCenter();

        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    private boolean impactBorderCeiling() {
        return ball.getYPosition() < area.getY();
    }

    private boolean impactBorderFloor() {
        return ball.getYPosition() > area.getY() + area.getHeight();
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
    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }
    public Ball getBall() {
        return ball;
    }
    public Brick[] getBricks() {
        return bricks;
    }
    public Levels getLevels() {
        return levels;
    }

}
