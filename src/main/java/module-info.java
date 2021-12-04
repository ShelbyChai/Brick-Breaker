module COMP2042.CW.hfyxc3 {
    requires javafx.controls;
    requires javafx.fxml;


    exports brickdestroyer;
    exports brickdestroyer.PauseMenu;
    exports brickdestroyer.Actor;
    exports brickdestroyer.GameBoard;
    exports brickdestroyer.HomeMenu;
    exports brickdestroyer.DebugConsole;

    opens brickdestroyer.GameBoard to javafx.fxml;
    opens brickdestroyer to javafx.fxml;
    opens brickdestroyer.HomeMenu to javafx.fxml;
    opens brickdestroyer.DebugConsole to javafx.fxml;
    opens brickdestroyer.PauseMenu to javafx.fxml;

}
