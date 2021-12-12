module COMP2042.CW.hfyxc3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    exports brickdestroyer;
    exports brickdestroyer.model.game;
    exports brickdestroyer.model.entities;
    exports brickdestroyer.controller;
    exports brickdestroyer.view;


    opens brickdestroyer to javafx.fxml;
    opens brickdestroyer.controller to javafx.fxml;
    opens brickdestroyer.model.game to javafx.fxml;
    opens brickdestroyer.view to javafx.fxml;
    opens brickdestroyer.model.entities to javafx.fxml;

}
