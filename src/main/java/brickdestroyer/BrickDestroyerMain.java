package brickdestroyer;

import brickdestroyer.model.game.GameBoardModel;
import brickdestroyer.model.game.GameLogic;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class BrickDestroyerMain extends Application {

    private GameLogic gameLogic;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        gameLogic = new GameLogic(new Rectangle(0, 0, GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT), new Point2D(300,430));
        primaryStage.setScene(getScene());
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("BrickDestroyer");
        primaryStage.show();

    }

    private Scene getScene() {
        SceneManager sceneManager = new SceneManager(gameLogic);
        return sceneManager.getScene();
    }
}