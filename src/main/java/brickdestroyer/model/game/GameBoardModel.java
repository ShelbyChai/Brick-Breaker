package brickdestroyer.model.game;

/**
 * Game Board Model is responsible to provide all the data
 * needed to run the view of the game.
 */
public class GameBoardModel {
    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 450;

    private final GameLogic gameLogic;
    private String userKeyInput;
    private volatile boolean isTimerRunning;
    private String message;
    private String score;


    /**
     * @param gameLogic Game logic of the Brick Destroyer game.
     */
    public GameBoardModel(GameLogic gameLogic) {
        this.gameLogic = gameLogic;

        userKeyInput = "";
        isTimerRunning = false;
        message = "";
        score = "";
    }

    /**
     * Getter method for the key listener user input on the canvas.
     * @return a String that contains the last user key input.
     */
    public String getUserKeyInput() {
        return userKeyInput;
    }

    /**
     * Setter method for the user input on the canvas.
     * @param userKeyInput a String value of what the user input.
     */
    public void setUserKeyInput(String userKeyInput) {
        this.userKeyInput = userKeyInput;
    }

    /**
     * Getter method for to check if the animation timer class is running.
     * @return a Boolean value that contains of whether the animation timer class is running.
     */
    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    /**
     * Setter method for the running timer.
     * @param timerRunning a Boolean value which represent true if the game is running, false if its paused.
     */
    public void setTimerRunning(boolean timerRunning) {
        isTimerRunning = timerRunning;
    }

    /**
     * Getter method for the message display on the game board view.
     * @return a String value that contains the number of ball and brick left for the player.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for the message display on the game board view.
     * @param message a String value to be display on the game board view.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter method for the current accumulated score by the player.
     * @return a String value that contains the score of the player.
     */
    public String getScore() {
        return score;
    }

    /**
     * Setter method for the current score of the player.
     * @param score a String value that contains the
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * Getter method for the game logic of the Brick Destroyer game.
     * @return the current GameLogic object that contains the behavior and properties of the current game.
     */
    public GameLogic getGameLogic(){
        return gameLogic;
    }

}
