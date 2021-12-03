package brickdestroyer.GameBoard;

import brickdestroyer.Actor.*;
import brickdestroyer.BrickDestroyerApplication;
import brickdestroyer.DebugConsole.DebugConsoleController;
import brickdestroyer.PauseMenu.PauseMenuController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class GameBoardViewController{
    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 450;

    final private GameBoardModel gameBoardModel;
    private String message;
//    private DebugConsole debugConsole;
//    private PauseMenu pauseMenu;

    final private Canvas canvas;
    private String userKeyInput = "";
    final private GraphicsContext gc;
    private volatile boolean isTimerRunning;

    public GameBoardViewController() {
        isTimerRunning = false;
        message = "";
        gameBoardModel = new GameBoardModel(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), new Point2D(300, 430));

        gameBoardModel.nextLevel();

        canvas = new Canvas(DEF_WIDTH, DEF_HEIGHT);
        canvas.setFocusTraversable(true);

        gc = canvas.getGraphicsContext2D();

        keyPressed(canvas);
        keyReleased(canvas);

        paint(gc, gameBoardModel);

        if (isTimerRunning)
            animationTimer.start();
    }

    public void setMessage(String message) {this.message = message;}

    public Scene getGameScene() {
        return new Scene(new StackPane(canvas));
    }

    final private AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate = 0;
        // Default: 10_000_000
        final private static long TIMER_DELAY = 1_000_000;

        @Override
        public void handle(long currentNanoTimer) {
            if (currentNanoTimer - lastUpdate >= TIMER_DELAY) {
                paint(gc, gameBoardModel);

                setUserKeyInput();
                gameBoardModel.move();
                gameBoardModel.findImpacts();
                message = String.format("Bricks: %d Balls %d", gameBoardModel.getBrickCount(), gameBoardModel.getBallCount());


                if (gameBoardModel.isBallLost()) {
                    if (gameBoardModel.ballEnd()) {
                        gameBoardModel.wallReset();
                        message = "Game over";
                    }
                    gameBoardModel.ballReset();
                    animationTimer.stop();

                } else if (gameBoardModel.isDone()) {
                    if (gameBoardModel.hasLevel()) {
                        message = "Go to Next Level";
                        animationTimer.stop();
                        gameBoardModel.ballReset();
                        gameBoardModel.wallReset();
                        gameBoardModel.nextLevel();

                    } else {
                        message = "ALL WALLS DESTROYED";
                        animationTimer.stop();
                    }
                }
                lastUpdate = currentNanoTimer;
            }
        }

        @Override
        public void start(){
            super.start();
            isTimerRunning = true;
        }

        @Override
        public void stop(){
            super.stop();
            isTimerRunning = false;
        }
    };

    // Not private because of PauseMenuController
    public void paint(GraphicsContext gc, GameBoardModel gameBoardModel) {
        gc.clearRect(0,0,DEF_WIDTH,DEF_HEIGHT);
        drawBall(gameBoardModel.getBall(),gc);

        gc.setFill(Color.BLUE);
        gc.fillText(message, 250,225);

        for(Brick b : gameBoardModel.getBrick())
            if(b.isBroken()) {
                drawBrick(b, gc);
//                if (b.getName() == "CementBrick")
                drawCrack(b.getCrackPath(), gc);
            }

        drawPlayer(gameBoardModel.getPlayer(),gc);
    }

    // TODO the crack color is the border color of the brick
    private void drawCrack(Path crackPath, GraphicsContext gc) {
        if (crackPath != null && !crackPath.getElements().isEmpty()) {

            gc.beginPath();
            for (int step = 0; step < Brick.DEF_STEPS; step++) {
                String[] positionXY = parseStringPath(crackPath, step);
                if (step == 0)
                    gc.moveTo(Double.parseDouble(positionXY[0]), Double.parseDouble(positionXY[1]));
                gc.lineTo(Double.parseDouble(positionXY[0]), Double.parseDouble(positionXY[1]));
            }
            gc.fill();
            gc.closePath();
            gc.stroke();
        }
    }

    private String[] parseStringPath(Path crackPath, int index) {
        String path = crackPath.getElements().get(index).toString();
        String value = path.replaceAll("[a-zA-Z]","").replaceAll("[=]","").replaceAll("[\\[-\\]]","");
        return value.split(", ");
    }

    private void drawBrick(Brick brick, GraphicsContext gc) {
        gc.setFill(brick.getInnerColor());
        gc.fillRect(brick.getPos().getX(),brick.getPos().getY(),brick.getSize().getWidth(),brick.getSize().getHeight());

        gc.setFill(brick.getBorderColor());
        gc.strokeRect(brick.getPos().getX(),brick.getPos().getY(),brick.getSize().getWidth(),brick.getSize().getHeight());
    }

    private void drawPlayer(Player player, GraphicsContext gc) {
        gc.setFill(player.getInnerColor());
        gc.fillRect(player.getXUpperLeft(),player.getYUpperLeft(),player.getWidth(), player.getHeight());

        gc.setStroke(player.getBorderColor());
        gc.strokeRect(player.getXUpperLeft(), player.getYUpperLeft(), player.getWidth(), player.getHeight());
    }

    private void drawBall(Ball ball, GraphicsContext gc) {
        gc.setFill(ball.getInnerColor());
        gc.fillOval(ball.getUpperLeftX(),ball.getUpperLeftY(),ball.getRadius(),ball.getRadius());

        gc.setStroke(ball.getBorderColor());
        gc.strokeOval(ball.getUpperLeftX(),ball.getUpperLeftY(),ball.getRadius(),ball.getRadius());
    }

    private void setUserKeyInput(){
        // TODO refactor this string thing
        if (userKeyInput.contains("A")) {
            gameBoardModel.getPlayer().moveLeft();
            gameBoardModel.getPlayer().move();
        }
        else if (userKeyInput.contains("D")) {
            gameBoardModel.getPlayer().moveRight();
            gameBoardModel.getPlayer().move();
        }
        else {
            gameBoardModel.getPlayer().stop();
        }
    }

    Stage stage;
    private void keyPressed(Canvas canvas) {
        canvas.setOnKeyPressed(keyEvent->{
            switch(keyEvent.getCode()){
                case A:
                    userKeyInput = "A";
                    break;

                case D:
                    userKeyInput = "D";
                    break;

                case ESCAPE:
                    animationTimer.stop();

                    stage = (Stage)((Node)keyEvent.getSource()).getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(BrickDestroyerApplication.class.getResource("PauseMenuView.fxml"));
                    try {
                        stage.getScene().getRoot().setEffect(new GaussianBlur());
                        Stage pauseMenu = new Stage(StageStyle.TRANSPARENT);
                        pauseMenu.initOwner(stage);
                        pauseMenu.initModality(Modality.APPLICATION_MODAL);
                        Scene scene = new Scene(fxmlLoader.load());
                        scene.getStylesheets().add(BrickDestroyerApplication.class.getResource("PauseMenuStyle.css").toExternalForm());
//                    pauseMenu.setScene(new Scene(fxmlLoader.load(), Color.TRANSPARENT));
                        pauseMenu.setScene(scene);

                    // TODO Use css to set the background Opacity
//                    pauseMenu.setWidth(DEF_WIDTH);
//                    pauseMenu.setHeight(DEF_HEIGHT);
                        pauseMenu.setX(stage.getX());
                        pauseMenu.setY(stage.getY());
                        pauseMenu.show();

                        PauseMenuController pauseMenuController = fxmlLoader.getController();
                        pauseMenuController.setRootStage(stage);
                        pauseMenuController.setGameBoard(gameBoardModel);
                        pauseMenuController.setGameBoardViewController(this);

                    } catch (IOException ex) {
                    ex.printStackTrace();
                    }
                    break;

                case SPACE:
                    if(isTimerRunning)
                        animationTimer.stop();
                    else
                        animationTimer.start();
                    break;
                case F1:
                    if (keyEvent.isShiftDown() && keyEvent.isAltDown()){
                        animationTimer.stop();
                        getDebugConsole(keyEvent);
                    }
                    break;
                default:
                    break;
            }
        });
    }

    private void keyReleased(Canvas canvas) {
        canvas.setOnKeyReleased(e->{
            userKeyInput = "";
        });
    }

    private void getDebugConsole(KeyEvent event) {
        stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BrickDestroyerApplication.class.getResource("DebugConsoleView.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

            DebugConsoleController debugConsoleController = fxmlLoader.getController();
            debugConsoleController.setGameBoardModel(gameBoardModel);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void repaint() {paint(gc, gameBoardModel);}
}
