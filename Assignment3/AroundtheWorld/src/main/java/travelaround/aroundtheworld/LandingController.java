package travelaround.aroundtheworld;

import AppLogic.LoadData;
import enitityfolder.Player;
import enitityfolder.PlayerIDGenerator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.json.JSONObject;


public class LandingController {
    @FXML private Button playbutton;

    private static JSONObject cachedGameData = null;
    private static boolean isDataLoaded = false;

    @FXML
    public void initialize() {
        playbutton.setDisable(true);
        new Thread(() -> {
            preloadGameData();
            Platform.runLater(() -> playbutton.setDisable(false));
        }).start();
    }

    private void preloadGameData() {
        System.out.println("Preloading game data...");
        try {
            Client.run("localhost", 8080);
            cachedGameData = Client.getCachedData();
            isDataLoaded = (cachedGameData != null);
        } catch (Exception e) {
            System.err.println("Failed to preload game data: " + e.getMessage());
        }
    }

    @FXML
    public void switchNow() {
        if (!SocketServer.isRunning()) {
            System.out.println("Server is not running");
            return;
        }

        if (!isDataLoaded) {
            System.out.println("Game data is not loaded yet!");
            return;
        }

        Platform.runLater(() -> {
            initializePlayer();
            ViewSwitcher.switchTo(view.GAMEPLAYVIEW);
            initializeGameUI();
        });
    }



    private void initializeGameUI() {
        GameWindowController controller = ViewSwitcher.getGameWindowController();
        if (controller == null) {
            System.out.println("Error: GameWindowController is not available.");
            return;
        }

        if (cachedGameData != null) {
            LoadData loader = LoadData.getInstance();
            loader.setController(controller);
            loader.setFullData(cachedGameData);
        }
    }

    private void initializePlayer() {
        Player playerInstance = Player.getInstance();

        int playerID = Integer.parseInt(PlayerIDGenerator.generateRandomId());
        playerInstance.setId(playerID);
        playerInstance.setPoint(0);
        playerInstance.setTimeComplete(0);

        System.out.println("Player Initialized: " + playerInstance.getId());
    }
}
