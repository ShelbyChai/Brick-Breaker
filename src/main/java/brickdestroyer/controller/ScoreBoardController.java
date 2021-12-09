package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import brickdestroyer.model.game.ScoreBoardModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ScoreBoardController implements Initializable {

    private final ScoreBoardModel scoreBoardModel;
    private final SceneManager sceneManager;
    private AnchorPane scoreBoardPane;

    @FXML
    private Label nameLabel1, nameLabel2, nameLabel3, nameLabel4, nameLabel5;

    @FXML
    private Label scoreLabel1, scoreLabel2, scoreLabel3, scoreLabel4, scoreLabel5;

    @FXML
    private Button homeMenuButton;


    public ScoreBoardController(ScoreBoardModel scoreBoardModel, SceneManager sceneManager){
        this.scoreBoardModel = scoreBoardModel;
        this.sceneManager = sceneManager;
    }

    public void showScoreBoard() {
        FXMLLoader scoreBoardLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/ScoreBoard.fxml"));
        scoreBoardLoader.setController(this);

        try {
            scoreBoardPane = scoreBoardLoader.load();
            scoreBoardPane.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/ScoreBoardStyle.css").toExternalForm());

        } catch (IOException e) {
            System.err.println("ScoreBoard.fxml could not be loaded");
            e.printStackTrace();
        }

        Stage stage = sceneManager.getPrimaryStage();
        stage.setScene(new Scene(scoreBoardPane));
        stage.setResizable(false);
        stage.show();

        if (scoreBoardModel.getPlayerBoxController() != null) {
            scoreBoardModel.updateScoreList();
        }

        setScoreBoardValue();
    }

    private void setScoreBoardValue() {
        HashMap<String, Integer> tempPlayerRecord = scoreBoardModel.getPlayerRecord();
        List<String> playerName = new ArrayList<String>();
        List<Integer> playerScore = new ArrayList<Integer>();

        for (String player : tempPlayerRecord.keySet()) {
            playerName.add(player);
            playerScore.add(tempPlayerRecord.get(player));
        }

        if (tempPlayerRecord.size() < 5)
            for (int i = tempPlayerRecord.size(); i < ScoreBoardModel.DEF_SCORE_BOARD; i++) {
                playerName.add("------");
                playerScore.add(0);
            }

        nameLabel1.setText(playerName.get(0));
        nameLabel2.setText(playerName.get(1));
        nameLabel3.setText(playerName.get(2));
        nameLabel4.setText(playerName.get(3));
        nameLabel5.setText(playerName.get(4));

        scoreLabel1.setText(String.valueOf(playerScore.get(0)));
        scoreLabel2.setText(String.valueOf(playerScore.get(1)));
        scoreLabel3.setText(String.valueOf(playerScore.get(2)));
        scoreLabel4.setText(String.valueOf(playerScore.get(3)));
        scoreLabel5.setText(String.valueOf(playerScore.get(4)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeMenuButton.setOnAction(actionEvent -> {
            sceneManager.setPrimaryStage((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
            sceneManager.getHomeMenu();
        });
    }
}
