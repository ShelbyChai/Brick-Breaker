package brickdestroyer;

import brickdestroyer.model.game.GameBoardModel;
import brickdestroyer.model.game.GameLogic;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BrickDestroyerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        GameLogic gameLogic = new GameLogic(new Rectangle(0, 0, GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT), new Point2D(300,430));
        SceneManager sceneManager = new SceneManager(gameLogic);
        sceneManager.setPrimaryStage(primaryStage);
        sceneManager.getHomeMenu();

    }

}