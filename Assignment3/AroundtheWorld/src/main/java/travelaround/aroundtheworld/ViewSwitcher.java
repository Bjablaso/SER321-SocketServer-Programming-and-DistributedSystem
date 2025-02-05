package travelaround.aroundtheworld;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewSwitcher {
    private static Scene scene;
    private static Stage stage;
    private static GameWindowController gameWindowController;



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
            scene.setRoot(root);

            if (cusView == view.GAMEPLAYVIEW) {
                gameWindowController = fxmlLoader.getController();

                if (gameWindowController == null) {
                    System.out.println(" Controller is still null after switching views!");
                } else {
                    System.out.println(" Controller successfully initialized.");
                }
            }


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
            System.out.println("GameWindowController has not been initialized yet.");
        }
        return gameWindowController;
    }

}
