package brickdestroyer.model.game;

import brickdestroyer.model.entities.Ball;
import brickdestroyer.model.entities_factory.BallFactory;
import brickdestroyer.model.entities.Brick;
import brickdestroyer.model.entities.Levels;
import brickdestroyer.model.entities.Player;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import brickdestroyer.model.entities.Brick.ImpactDirection;


/**
 * Game logic contains all the basic behavior of how entities interact with
 * each other and the overall state of the game. This class instantiate
 * all the available entities and keep track of the resources needed throughout the
 * game of Brick Destroyer.
 */
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

    /**
     * Initialize the resources and properties before starting the game.
     * @param drawArea a Rectangle shape that defines the width and height of game window.
     * @param initialPosition an initial Point2D x and y position of where the player and ball should start.
     */
    public GameLogic(Rectangle drawArea, Point2D initialPosition){

        this.currentLevel = 0;
        this.ballCount = 3;
        this.ballLost = false;
        this.startPoint = new Point2D(initialPosition.getX(),initialPosition.getY());
        this.score = 0;

        BallFactory ballFactory = new BallFactory();
        ball = ballFactory.getBallType("Rubber Ball", initialPosition);
        player = new Player(initialPosition,150,10, drawArea);
        levels = new Levels(30, 3, (double)6/2);

        initialiseSpeed();
        area = drawArea;
    }

    /**
     * Move and update the player and ball position. This method is called
     * to allow continuous movement as it updates the player and ball's
     * position in the game frame.
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * Resets the ball and player to its initial position. This method is called
     * when a level is completed or all ball is lost.
     */
    public void resetPoint(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        initialiseSpeed();
        ballLost = false;
    }

    /**
     * Resets the structure of the wall and resets the ball count to 3.
     * This method is called when all ball is lost the all the ball or
     * when the player is tweaking in the debug console.
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    /**
     * Move and retrieve the brick count for the next level.
     * This method is called when all the brick in the current level is destroyed
     * level or when the player is tweaking in the debug console.
     */
    public void nextLevel(){
        setBricks(levels.getLevels()[currentLevel++]);
        setBrickCount(getBricks().length);

        if(currentLevel!=1){
            score += 200;
            score += ballCount * 100;
        }

    }

    /**
     * This method is called to initialize the starting speed
     * of the ball using a rng factor.
     */
    public void initialiseSpeed() {
        Random rnd = new Random();
        int speedX, speedY;
        do {
            speedX = rnd.nextInt(5) - 2;
        } while (speedX == 0);

        do {
            speedY = -rnd.nextInt(3);
        } while (speedY == 0);

        //TODO
//        speedX = -2;
//        speedY = -3;

        ball.setSpeed(speedX, speedY);
    }

    /**
     * This is the master method for handling the impact between
     * entity and wall of the game window. It changes the property
     * of the class after an impact.
     */
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

            if (score >= 100)
                score -= 100;
            else
                score = 0;
        }
    }


    /**
     * This method is called findImpacts() to detect the impact between player and ball.
     * @param player a Player object that is controlling by the user in the game window.
     * @param ball a Ball object.
     * @return a Boolean that is true if there is an impact between player and ball, false if there isn't.
     */
    public boolean setImpact(Player player,Ball ball){
        return player.getPlayerFace().contains(ball.getCenter()) && getPlayer().getPlayerFace().contains(ball.getDown()) ;
    }

    /**
     * This method is called by findImpacts() to detect the impact between bricks and ball. It defines the impact direction of
     * the ball to change its travelling direction.
     * @return a Boolean that is true if there is an impact between the ball and bricks, false if there isn't.
     */
    private boolean impactWall(){
        for(Brick brick : bricks){
            boolean isSpeedUp = brick.getName().equalsIgnoreCase("Haste Brick");
            boolean isCrackableBrick = brick.getName().equalsIgnoreCase("Cement Brick")
                    || brick.getName().equalsIgnoreCase("Black Stone Brick");

            switch (impactBrick(brick, ball)) {
                case UP:
                    ball.reverseY();
                    speedUp(isSpeedUp);
                    brickScoreCalculation(brick);
                    return isCrackableBrick ? brick.setImpact(ball.getDown(), ImpactDirection.UP) : brick.setImpact();

                case DOWN:
                    ball.reverseY();
                    speedUp(isSpeedUp);
                    brickScoreCalculation(brick);
                    return isCrackableBrick ? brick.setImpact(ball.getUp(), ImpactDirection.DOWN) : brick.setImpact();

                case LEFT:
                    ball.reverseX();
                    speedUp(isSpeedUp);
                    brickScoreCalculation(brick);
                    return isCrackableBrick ? brick.setImpact(ball.getRight(), ImpactDirection.RIGHT) : brick.setImpact();

                case RIGHT:
                    ball.reverseX();
                    speedUp(isSpeedUp);
                    brickScoreCalculation(brick);
                    return isCrackableBrick ? brick.setImpact(ball.getLeft(), ImpactDirection.LEFT) : brick.setImpact();
            }
        }
        return false;
    }


    /**
     * This method is called by impactWall() to allocate a defined score
     * when there is an impact of ball with the specific type of brick.
     * @param brick a Brick object that is impacted by the ball.
     */
    private void brickScoreCalculation(Brick brick) {
        switch (brick.getName()) {
            case "Clay Brick":
            case "Haste Brick":
            case "Steel Brick":
                score += 30;
                break;
            case "Cement Brick":
                score += 60;
                break;
            case "Black Stone Brick":
                score += 90;
                break;
        }
    }


    /**
     * This method is called by impactWall() when the ball impact
     * a Haste Brick. It sets the speed of the ball to the highest possible randomize value of
     * initializeSpeed() method.
     * @param isSpeedUp a Boolean that represent true if the ball impacted the Haste Brick, false if there isn't.
     */
    public void speedUp(boolean isSpeedUp) {
        if (isSpeedUp) {
            ball.setSpeed(3,-3);
        }
    }

    /**
     * This method is called by impactWall() when the ball impacted the horizontal
     * border of the level by checking the x position of the ball.
     * @return a Boolean that represent true if the ball impact the border, false if there isn't
     */
    private boolean impactBorderX(){
        Point2D p = ball.getCenter();

        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * This method is called by impactWall() when the ball impacted the ceiling border
     * of the level by checking the y position of the ball.
     * @return a Boolean that represent true if the ball impact the ceiling, false if there isn't.
     */
    private boolean impactBorderCeiling() {
        return ball.getYPosition() < area.getY();
    }

    /**
     * This method is called by impactWall() when the ball impacted the floor border of the level
     * by checking the y position of the ball.
     * @return a Boolean that represent true if the ball impact the floor, false if there isn't.
     */
    private boolean impactBorderFloor() {
        return ball.getYPosition() > area.getY() + area.getHeight();
    }

    /**
     * This method is called by impactWall() to determine the direction of the ball impact
     * on the bricks. This method return the enum value of the Brick ImpactDirection.
     * @param brick a Brick object that is impacted by the ball.
     * @param ball a Ball object that impacted the brick.
     * @return a ImpactDirection enum value that represent the direction of the ball impact.
     */
    public final ImpactDirection impactBrick(Brick brick, Ball ball){
        if(!brick.isBroken())
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

    /**
     * This method is called to check if there is more playable level.
     * @return a Boolean that represent true if there is more playable level, false if there isn't.
     */
    public boolean hasLevel(){
        return currentLevel < Levels.LEVELS_COUNT;
    }

    /**
     * This method is called to check if the current ball count is 0.
     * @return a Boolean that represent true if the ballCount is 0, false if it isn't.
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * This method is called to check if all brick is destroyed.
     * @return a Boolean that represent true if the brickCount is 0, false if it isn't.
     */
    public boolean isDone(){
        return brickCount == 0;
    }

    /**
     * This method is called to check if the ball is lost
     * @return a Boolean that represent true if the ballLost is 0, false if it isn't;
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * Setter method for the travelling speed of the ball object in the x plane. Speed x is the rate in pixels which the ball
     * travel in every frame of the game window.
     * @param speedX an integer that can be positive and negative value. Negative value traverse the ball to the left of the screen
     *              while positive traverse the ball to the right.
     */
    public void setBallSpeedX(int speedX) {
        ball.setSpeedX(speedX);
    }

    /**
     * Setter method for the travelling speed of the ball object in the y plane. Speed y is the rate in pixels which the ball
     * travel in every frame of the game window.
     * @param speedY an integer that can be positive and negative value. It is usually set to negative from the start the traverse
     *               the ball to the top of the screen. Negative value traverse the ball to the top of the game
     *               window while positive traverse the ball to the bottom.
     */
    public void setBallSpeedY(int speedY){
        ball.setSpeedY(speedY);
    }

    /**
     * Setter method to set the Bricks object.
     * @param bricks a Brick object.
     */
    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }

    /**
     * Setter method to set the number of brick for the level.
     * @param brickCount the number of brick for the level.
     */
    public void setBrickCount(int brickCount){
        this.brickCount = brickCount;
    }

    /**
     * This method is called to reset the ball count of the game.
     */
    public void resetBallCount(){
        ballCount = 3;
    }

    /**
     * Getter method for the number of ball in the game.
     * @return an Integer that represent the number of ball.
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * Getter method for the number of brick available in the level.
     * @return an Integer that represent the number of brick.
     */
    public int getBrickCount(){
        return brickCount;
    }

    /**
     * Getter method for setting the score of the player in the game. Score cannot
     * be negative value.
     * @return an Integer that contains the total score obtain by the user at the moment.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter method for the Player object.
     * @return the Player object in the game board.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter method for the Ball object.
     * @return the Ball object in the game board.
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Getter method for the Bricks in the current level of the game.
     * @return a 1d array that contains the bricks of the level.
     */
    public Brick[] getBricks() {
        return bricks;
    }

    /**
     * Getter method for the current level the player is in.
     * @return an Integer that contains the current level the player is in.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
}
