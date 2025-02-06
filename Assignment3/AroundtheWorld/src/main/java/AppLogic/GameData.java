package AppLogic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class GameData {
    private HashMap<Integer, JSONObject> gameData;

    public GameData() {
        gameData = new HashMap<>();
    }

    public void putGameData(Integer messageId, JSONObject data) {
        this.gameData.put(messageId, data);
    }

    public HashMap<Integer, JSONObject> getGameData() {
        return gameData;
    }


    public JSONObject getGameDataById(Integer gameId) {
        return gameData.get(gameId);
    }


    public void loadFromJson(JSONObject jsonData) {
        if (jsonData.has("gamedata")) {
            JSONArray gameArray = jsonData.getJSONArray("gamedata");

            for (int i = 0; i < gameArray.length(); i++) {
                JSONObject gameObject = gameArray.getJSONObject(i);
                this.gameData.put(i, gameObject); // Use index as key
            }
        }
    }
}
