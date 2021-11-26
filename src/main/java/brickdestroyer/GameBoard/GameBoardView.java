package brickdestroyer.GameBoard;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;



public class GameBoardView {
    private AnchorPane view;

    public GameBoardView() {
        view = new AnchorPane();
    }

    public Parent asParent() {
        return view;
    }
}
