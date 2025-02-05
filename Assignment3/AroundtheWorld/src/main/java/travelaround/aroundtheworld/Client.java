package travelaround.aroundtheworld;

import AppLogic.LoadData;

import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Random;
class Client {
    private static Socket clientSocket;
    private static ObjectOutputStream obj;
    private static DataInputStream in;
    private static JSONObject cachedGameData;

    public static void run(String serverAddress, int port) {
        try {
            clientSocket = new Socket(serverAddress, port);
            obj = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());

            JSONObject request = new JSONObject();
            request.put("type", "player");
            request.put("playersID", "12345");
            request.put("start", false);
            request.put("message", "i want to play");

            obj.writeObject(request.toString());
            obj.flush();

            String response = in.readUTF();
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getJSONObject("header").getBoolean("permission")) {
                cachedGameData = jsonResponse; // Store response in memory
            } else {
                System.out.println("Server denied access.");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getCachedData() {
        return cachedGameData;
    }

    public static void closeClient() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
