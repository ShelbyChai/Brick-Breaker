package brickdestroyer.model.game;

public class GameBoardModel {
    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 450;

    private final GameLogic gameLogic;
    private String userKeyInput;
    private volatile boolean isTimerRunning;
    private String message;
    private String score;


    public GameBoardModel(GameLogic gameLogic) {
        this.gameLogic = gameLogic;

        userKeyInput = "";
        isTimerRunning = false;
        message = "";
        score = "";
    }

    public String getUserKeyInput() {
        return userKeyInput;
    }

    public void setUserKeyInput(String userKeyInput) {
        this.userKeyInput = userKeyInput;
    }

    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        isTimerRunning = timerRunning;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public GameLogic getGameLogic(){
        return gameLogic;
    }

}
