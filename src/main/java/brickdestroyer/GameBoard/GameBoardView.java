package brickdestroyer.GameBoard;

import brickdestroyer.GameLogic;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class GameBoardView {
    private AnchorPane view;

    public GameBoardView() {
        view = new AnchorPane();
    }

    public Parent asParent() {
        return view;
    }
}
