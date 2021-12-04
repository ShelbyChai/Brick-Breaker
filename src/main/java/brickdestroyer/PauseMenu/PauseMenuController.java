package brickdestroyer.PauseMenu;

import brickdestroyer.BrickDestroyerApplication;
import brickdestroyer.GameBoard.GameBoardModel;
import brickdestroyer.GameBoard.GameBoardViewController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PauseMenuController {
    private Stage primaryStage;
    private GameBoardModel gameBoardModel;
    private GameBoardViewController gameBoardViewController;

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
        // MVC: Model (call from model method)
        this.gameBoardModel.resetPoint();
        this.gameBoardModel.wallReset();
        this.gameBoardViewController.repaintMessage("Restarting Game");
        hidePauseMenu(event);

    }

    @FXML
    protected void onExitButtonClick(ActionEvent event) {
        primaryStage.close();
    }

    @FXML
    protected void onEscapeKeyType(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE)
            hidePauseMenu(event);
    }

    private void hidePauseMenu(Event event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        primaryStage.getScene().getRoot().setEffect(null);
    }

    // MVC: Model
    private void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setGameBoard(GameBoardModel gameBoardModel) {
        this.gameBoardModel = gameBoardModel;
    }

    private void setGameBoardViewController(GameBoardViewController gameBoardViewController) {
        this.gameBoardViewController = gameBoardViewController;
    }

    // TODO Create a scene Manager and add it there
    public void getPauseMenu(Stage primaryStage, GameBoardModel gameBoardModel, GameBoardViewController gameBoardViewController) {
        Scene scene;
        FXMLLoader fxmlLoader = new FXMLLoader(BrickDestroyerApplication.class.getResource("PauseMenuView.fxml"));
        Stage pauseMenu = new Stage(StageStyle.TRANSPARENT);

        primaryStage.getScene().getRoot().setEffect(new GaussianBlur());

        try {
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BrickDestroyerApplication.class.getResource("PauseMenuStyle.css").toExternalForm());
            pauseMenu.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        pauseMenu.initModality(Modality.APPLICATION_MODAL);
        pauseMenu.initOwner(primaryStage);
        pauseMenu.setX(primaryStage.getX());
        pauseMenu.setY(primaryStage.getY());
        pauseMenu.show();

        PauseMenuController pauseMenuController = fxmlLoader.getController();
        pauseMenuController.setPrimaryStage(primaryStage);
        pauseMenuController.setGameBoard(gameBoardModel);
        pauseMenuController.setGameBoardViewController(gameBoardViewController);

    }
}
