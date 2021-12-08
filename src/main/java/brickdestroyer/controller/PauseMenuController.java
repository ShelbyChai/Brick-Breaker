package brickdestroyer.controller;

import brickdestroyer.BrickDestroyerMain;
import brickdestroyer.SceneManager;
import brickdestroyer.model.game.PauseMenuModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PauseMenuController implements Initializable {

    private final PauseMenuModel pauseMenuModel;
    private SceneManager sceneManager;

    @FXML
    private Pane pauseMenuPane;

    @FXML
    private Button continueButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    public PauseMenuController(PauseMenuModel pauseMenuModel, SceneManager sceneManager) {
        this.pauseMenuModel = pauseMenuModel;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        continueButton.setOnAction(this::hidePauseMenu);

        restartButton.setOnAction(actionEvent -> {
            pauseMenuModel.getGameLogic().resetPoint();
            pauseMenuModel.getGameLogic().wallReset();
            pauseMenuModel.getGameBoardView().repaintMessage("Restarting Game");
            pauseMenuModel.getGameBoardView().repaintScore("Score: 0");
            hidePauseMenu(actionEvent);
        });

        exitButton.setOnAction(actionEvent -> sceneManager.getPrimaryStage().close());

        pauseMenuPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
                hidePauseMenu(keyEvent);
        });
    }

    public void showPauseMenu() {

        FXMLLoader pauseMenuLoader = new FXMLLoader(BrickDestroyerMain.class.getResource("/brickdestroyer/fxml/PauseMenu.fxml"));
        pauseMenuLoader.setController(this);
        Stage pauseMenu = new Stage(StageStyle.TRANSPARENT);

        try {
            Scene scene = new Scene(pauseMenuLoader.load());
            scene.getStylesheets().add(BrickDestroyerMain.class.getResource("/brickdestroyer/css/PauseMenuStyle.css").toExternalForm());
            pauseMenu.setScene(scene);

        } catch (IOException ex) {
            System.err.println("PauseMenu.fxml could not be loaded");
            ex.printStackTrace();
        }

        pauseMenu.initModality(Modality.APPLICATION_MODAL);
        pauseMenu.initOwner(sceneManager.getPrimaryStage());
        pauseMenu.setWidth(sceneManager.getPrimaryStage().getWidth()/2);
        pauseMenu.setHeight(sceneManager.getPrimaryStage().getHeight()/2);
        pauseMenu.setX(sceneManager.getPrimaryStage().getX() + sceneManager.getPrimaryStage().getWidth()/4);
        pauseMenu.setY(sceneManager.getPrimaryStage().getY() + sceneManager.getPrimaryStage().getHeight()/4);
        pauseMenu.show();
    }

    private void hidePauseMenu(Event event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        sceneManager.getPrimaryStage().getScene().getRoot().setEffect(null);
    }


}
