package brickdestroyer.PauseMenu;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class PauseMenuController {
    private PauseMenuModel pauseMenuModel;

    @FXML
    private Button continueButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    @FXML
    protected void onContinueButtonClick(ActionEvent event) {
        hidePauseMenu(event);
    }

    @FXML
    protected void onRestartButtonClick(ActionEvent event) {
        pauseMenuModel.getGameBoardModel().resetPoint();
        pauseMenuModel.getGameBoardModel().wallReset();
        pauseMenuModel.getGameBoardViewController().repaintMessage("Restarting Game");
        hidePauseMenu(event);

    }

    @FXML
    protected void onExitButtonClick() {
        pauseMenuModel.getPrimaryStage().close();
    }

    @FXML
    protected void onEscapeKeyType(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE)
            hidePauseMenu(event);
    }

    private void hidePauseMenu(Event event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        pauseMenuModel.getPrimaryStage().getScene().getRoot().setEffect(null);
    }

    public void setPauseMenuModel(PauseMenuModel pauseMenuModel) {this.pauseMenuModel = pauseMenuModel;}
}
