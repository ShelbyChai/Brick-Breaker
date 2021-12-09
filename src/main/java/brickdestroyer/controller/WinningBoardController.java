package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import brickdestroyer.model.game.GameLogic;
import brickdestroyer.model.game.ScoreBoardModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class WinningBoardController {

    private AnchorPane winningBoardPane;
    private final SceneManager sceneManager;
    private final GameLogic gameLogic;
    private final ScoreBoardModel scoreBoardModel;

    @FXML
    private Label scoreLabel;
    @FXML
    private Label numLeaderBoardLabel;

    public WinningBoardController(SceneManager sceneManager, ScoreBoardModel scoreBoardModel, GameLogic gameLogic) {
        this.sceneManager = sceneManager;
        this.gameLogic = gameLogic;
        this.scoreBoardModel = scoreBoardModel;
    }

    public void showWinningBoard() {
        Text congratulationText = new Text("Congratulation You Have Completed all Levels!!!");
        congratulationText.setFont(Font.font(40));
        congratulationText.setTextOrigin(VPos.TOP);

        FXMLLoader winningBoardLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/WinningBoard.fxml"));
        winningBoardLoader.setController(this);

        try {
            winningBoardPane = winningBoardLoader.load();

        } catch (IOException e) {
            System.err.println("WinningBoard.fxml could not be loaded");
            e.printStackTrace();
        }

        winningBoardPane.getChildren().add(congratulationText);
        Stage stage = sceneManager.getPrimaryStage();
        stage.setScene(new Scene(winningBoardPane));
        stage.setResizable(false);
        stage.show();

        double sceneWidth = stage.getWidth();
        double msgWidth = congratulationText.getLayoutBounds().getWidth();
        KeyValue initKeyValue = new KeyValue(congratulationText.xProperty(),sceneWidth);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO,initKeyValue);
        KeyValue endKeyValue = new KeyValue(congratulationText.xProperty(),-msgWidth);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(3),initKeyValue,endKeyValue);
        Timeline timeline = new Timeline(initFrame,endFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.setRate(0.5);
        timeline.play();

        initialize();
    }

    public void initialize() {
//        scoreLabel.setText(String.valueOf(scoreBoardModel.getGameLogic().getScore()));
//        numLeaderBoardLabel.setText(String.valueOf(scoreBoardModel));
    }

}
