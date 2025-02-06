package travelaround.aroundtheworld;

import AppLogic.LoadData;
import AppLogic.countDown;
import enitityfolder.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.util.List;
public class LandingPage extends Application {


    private static String serverAddress = "51.20.144.68";
    private static int port = 8889;
    private static JSONObject cachedGameData = null; // Store game data in memory

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Pane());


        ViewSwitcher.setScene(scene, primaryStage);



        preloadGameData();


        primaryStage.setScene(scene);
        centerStage(primaryStage);

        Platform.runLater(() -> {
            primaryStage.show();
            ViewSwitcher.switchTo(view.GAMEPLAYVIEW);
            initializeGameUI();

         var counter = new countDown();
           counter.startCountdown();
        });
    }

    private void preloadGameData() {
        System.out.println("Preloading game data...");

        try {
            Client.run(serverAddress, port);
            cachedGameData = Client.getCachedData();
        } catch (Exception e) {
            System.err.println("Failed to preload game data: " + e.getMessage());
        }
    }

    private void initializeGameUI() {
        Platform.runLater(() -> {
            GameWindowController controller = ViewSwitcher.getGameWindowController();
            if (controller == null) {
                System.out.println("Error: GameWindowController is not available.");
                return;
            }

            if (cachedGameData != null) {
                LoadData loader = LoadData.getInstance();
                loader.setController(controller);
                controller.setLoadData(loader);
                loader.setFullData(cachedGameData);
            }
        });
    }

    private void centerStage(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double centerX = bounds.getMinX() + (bounds.getWidth() - stage.getWidth()) / 2;
        double centerY = bounds.getMinY() + (bounds.getHeight() - stage.getHeight()) / 2;

        stage.setX(centerX);
        stage.setY(centerY);
    }


    @Override
    public  void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
//        if (args.length < 2) {
//            System.out.println("Usage: LandingPage <serverAddress> <port>");
//            System.exit(1);
//        }
//        serverAddress = args[0];
//        try{
//            port = Integer.parseInt(args[1]);
//        }catch(Exception e){
//            System.out.println("Usage: LandingPage <serverAddress> <port (Must be a number)>");
//            System.exit(1);
//        }
        launch(args);
    }
}
