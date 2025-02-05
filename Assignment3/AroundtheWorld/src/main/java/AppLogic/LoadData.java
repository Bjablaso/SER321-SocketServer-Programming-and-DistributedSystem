package AppLogic;

import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import travelaround.aroundtheworld.GameWindowController;
import  travelaround.aroundtheworld.ViewSwitcher;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public  class LoadData {
    private static GameWindowController gameWindowController;
    private JSONArray gameDataArray;
    private JSONObject currentGameData;

    public LoadData() {
        gameWindowController = ViewSwitcher.getGameWindowController();
    }

    public void setFullData(JSONObject fullDatax) {
        System.out.println("Full data loaded: " + fullDatax);
        gameDataArray = fullDatax.optJSONArray("gamedata");
        if (gameDataArray == null) {
            System.out.println("Error: gamedata is missing or not an array.");
            return;
        }
        loadData();
    }

    public void loadData() {
        if (gameDataArray == null || gameDataArray.length() == 0) {
            System.out.println("No game data available.");
            return;
        }

        currentGameData = gameDataArray.getJSONObject(new Random().nextInt(gameDataArray.length()));

        String url = currentGameData.optString("url", "");
        JSONObject hintData = currentGameData.optJSONObject("hint");
        JSONObject choiceData = currentGameData.optJSONObject("choice");

        // Ensure GameWindowController is initialized
        while (gameWindowController == null) {
            gameWindowController = ViewSwitcher.getGameWindowController();
        }

        Platform.runLater(() -> {
            gameWindowController.setImageDisplay(url);
            gameWindowController.setHint1(hintData.optString("hint1", "No hint available"));
            gameWindowController.setHint2(hintData.optString("hint2", "No hint available"));
            gameWindowController.setHint3(hintData.optString("hint3", "No hint available"));
            gameWindowController.setAnswerKey1(choiceData.optString("first", ""));
            gameWindowController.setAnswerKey2(choiceData.optString("second", ""));
            gameWindowController.setAnswerKey3(choiceData.optString("third", ""));
        });
    }
}
