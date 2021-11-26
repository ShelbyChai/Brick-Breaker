module com.example.cw_fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens brickdestroyer to javafx.fxml;
    exports brickdestroyer;
    exports brickdestroyer.GameBoard;
    opens brickdestroyer.GameBoard to javafx.fxml;
    exports brickdestroyer.HomeMenu;
    opens brickdestroyer.HomeMenu to javafx.fxml;
}