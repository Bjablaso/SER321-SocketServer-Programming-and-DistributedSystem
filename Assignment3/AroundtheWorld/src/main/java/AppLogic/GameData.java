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

    /**
     * Retrieves the game data associated with a specific game ID.
     * @param gameId The ID of the game data to retrieve.
     * @return The JSONObject containing the game data, or null if not found.
     */
    public JSONObject getGameDataById(Integer gameId) {
        return gameData.get(gameId);
    }

    /**
     * Loads JSON data into the gameData map.
     * @param jsonData The full JSON object containing all game data.
     */
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
