package AppLogic;

import javafx.application.Platform;
import javafx.stage.Popup;
import org.json.JSONArray;
import org.json.JSONObject;
import travelaround.aroundtheworld.GameWindowController;
import travelaround.aroundtheworld.ViewSwitcher;

public class LoadData {
    private static  LoadData loadadtaInstance;
    private GameWindowController gameWindowController;
    private JSONArray gameDataArray;
    private String correctGuess;
    private int currentIndex = 0;


    public LoadData() {
    }

    public void setController(GameWindowController controller) {
        this.gameWindowController = controller;
    }

    public static LoadData getInstance() {
        if (loadadtaInstance == null) {
            loadadtaInstance = new LoadData();
        }
        return loadadtaInstance;
    }


    public void setFullData(JSONObject fullData) {
        System.out.println("Full data loaded: " + fullData);
        gameDataArray = fullData.optJSONArray("gamedata");


        if (gameDataArray == null || gameDataArray.length() == 0) {
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

        if (currentIndex >= gameDataArray.length()) {
            System.out.println("Game Over. No more questions.");
            return;
        }

        JSONObject currentGameData = gameDataArray.getJSONObject(currentIndex);

            correctGuess = currentGameData.optString("answer", "");


        String url = currentGameData.optString("url", "");
        JSONObject hintData = currentGameData.optJSONObject("hint");
        JSONObject choiceData = currentGameData.optJSONObject("choice");


        if(currentGameData.has("asnswer")){
            System.out.println("has asnswer");
        }else {
            System.out.println("has no asnswer");
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



    public boolean checkAnswer(String selectedAnswer) {
        if (correctGuess == null || correctGuess.isEmpty()) {
            System.out.println("Error: correctGuess is null or empty. Ensure loadData() is called first.");
            return false;
        }

        System.out.println("Checking Answer: Selected = " + selectedAnswer + ", Expected = " + correctGuess);

        if (selectedAnswer.equals(correctGuess)) {
            //currentIndex++;
            return true;
        }

        return false;
    }

    public void nextQuestion() {
        currentIndex++;
        loadData();
    }
}
