package travelaround.aroundtheworld;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;

import javafx.stage.Stage;

import java.io.IOException;


public class ViewSwitcher {
    private static Scene scene;
    private static Stage stage;
    private static GameWindowController gameWindowController;
    private static WinScreenController controllerScore;

    public static void setScene(Scene scene, Stage stage) {
        ViewSwitcher.scene = scene;
        ViewSwitcher.stage = stage;
    }

    public static void switchTo(view cusView) {
        if (scene == null || stage == null) {
            throw new IllegalStateException("Scene or Stage has not been set yet.");
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewSwitcher.class.getResource(cusView.getFilename()));
            Pane root = fxmlLoader.load();

            if (cusView == view.GAMEPLAYVIEW) {

                gameWindowController = fxmlLoader.getController();
                System.out.println("GameWindowController preloaded successfully.");
            }

            scene.setRoot(root);
            stage.setWidth(root.prefWidth(-1) + 20);
            stage.setHeight(root.prefHeight(-1) + 40);
        } catch (IOException e) {
            System.out.println("Error loading view: " + cusView.getFilename());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static GameWindowController getGameWindowController() {
        if (gameWindowController == null) {
            System.out.println("GameWindowController is not initialized yet.");
        }
        return gameWindowController;
    }

    public static WinScreenController getControllerScore() {
        if (controllerScore == null) {
            System.out.println("ControllerScore is not initialized yet.");
        }
        return controllerScore;
    }
    public static void popUpWindow() {
        Platform.runLater(() -> {
            try {
                FXMLLoader winnerScreenLoader = new FXMLLoader(ViewSwitcher.class.getResource(view.SCOREVIEW.getFilename()));
                Pane root = winnerScreenLoader.load();
                Popup popup = new Popup();
                popup.getContent().add(root);

                controllerScore = winnerScreenLoader.getController();
                controllerScore.setScoreVakue();

                popup.show(stage);

            } catch (IOException e) {
                System.out.println("Error loading view: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
