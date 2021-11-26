package brickdestroyer.HomeMenu;

import brickdestroyer.Actor.Ball;
import brickdestroyer.Actor.RubberBall;
import brickdestroyer.GameBoard.GameBoardView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class HomeMenuController {

    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;

    private Stage stage;
    private Scene scene;
    GameBoardView gameView;
    private Ball ball;

    @FXML
    protected void clickedStartButton(ActionEvent event) {
        ball = new RubberBall(new Point2D(300,430));

        Group root = new Group(ball.getBallFace());

        gameView = new GameBoardView();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(gameView.asParent(),600,450);
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void clickedExitButton(ActionEvent event) {
        System.out.println("Goodbye " + System.getProperty("user.name"));
        System.exit(0);
    }
}