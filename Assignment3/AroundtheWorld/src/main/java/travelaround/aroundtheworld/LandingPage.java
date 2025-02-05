package travelaround.aroundtheworld;

import AppLogic.LoadData;
import AppLogic.countDown;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class LandingPage extends Application {
    private static String serverAddress = "localhost";
    private static int port = 8080;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Pane());
        ViewSwitcher.setScene(scene, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Run the client connection in a separate thread
        new Thread(() -> {
            try {


                Platform.runLater(() -> ViewSwitcher.switchTo(view.GAMEPLAYVIEW));

                // Ensure the controller is fully initialized before proceeding
//                while (ViewSwitcher.getGameWindowController() == null) {
//                    Thread.sleep(1000);
//                }
                Client.run(serverAddress, port);


                LoadData loader = new LoadData();
                countDown countdown = new countDown();
                loader.loadData();
                countdown.startCountdown();

            } catch (Exception e) {
                System.err.println("Failed to start client: " + e.getMessage());
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to connect to the server!");
                    alert.showAndWait();
                });
            }
        }).start();
    }

    @Override
    public void stop() throws Exception {
        Client.closeClient();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
