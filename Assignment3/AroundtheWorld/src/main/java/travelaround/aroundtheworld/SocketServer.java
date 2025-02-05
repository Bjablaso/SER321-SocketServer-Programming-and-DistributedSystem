package travelaround.aroundtheworld;


import AppLogic.MessageManager;
import AppLogic.filePath;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    static ServerSocket serverSocket;
    static Socket socket;
    static  boolean isRunning = true;
    static  int currentport;
    static String errMessage = "";
    static String serverRunning = "";
    static String clientConnected = "";

    static MessageManager recieving;
    static MessageManager sending;
    static MessageManager errorMessages;

    static ObjectInputStream in;
    static DataOutputStream os;



    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java SocketServer <port>");
            isRunning =  false;
            System.exit(1);
        }

        int port;
        try{
            port = Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            System.out.println("Invalid port: " + args[0]);
            System.exit(1);
            return;
        }


        try{
            currentport = port;
             connect();




            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (serverSocket != null) {
                        serverSocket.close();
                        System.out.println("Server shut down.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

           // JSONObject response = new JSONObject();

            while (isRunning) {
                try {
                    socket = serverSocket.accept();
                    if (!isRunning) break; // Exit loop if server is shutting down

                    System.out.println("Client connected: " + socket.getInetAddress());

                    in = new ObjectInputStream(socket.getInputStream());
                    OutputStream out = socket.getOutputStream();
                    os = new DataOutputStream(out);

                    String s;
                    try {
                        s = (String) in.readObject(); // Read client message
                        recieving.put(new JSONObject(s)); // Log received message
                        System.out.println("Client Message received: " + s);
                    } catch (Exception e) {
                        System.out.println("Client disconnect detected.");
                        break;
                    }

                    if (!isValid(s)) {
                        JSONObject response = fieldResponse();
                        writeOut(response);
                        System.out.println("Sending Invalid Data Response");
                    } else if (sending.isFirstMessage()) {
                        JSONObject response = writeResponse();
                        writeOut(response);
                        System.out.println("Sending response to client: " + response.toString());
                    }

                } catch (IOException e) {
                    System.out.println("I/O Exception: " + e.getMessage());
                    break;
                }
            }


        }catch (IOException e){
            isRunning = false;
            errMessage = e.getMessage();
            System.exit(1);
        }finally {
            shutdown();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (isRunning) {
                shutdown();
            }
        }));

    }

    public static void connect() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            System.out.println("Server is already running on port " + currentport);
            return;
        }

        serverSocket = new ServerSocket(currentport);
        serverRunning = "Server running";
        System.out.println("Server running on port " + currentport);

        recieving = new MessageManager();
        sending = new MessageManager();
        errorMessages = new MessageManager();
    }


    public static boolean isRunning() {
        return isRunning;
    }

    public static int getCurrentport() {
        return currentport;
    }

    public static String getErrMessage() {
        return errMessage;
    }

    public static String getServerRunning() {
        return serverRunning;
    }



    public static boolean isValid(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            // Ensure all required keys are present
            if (!jsonObject.has("type") || !jsonObject.has("playersID") ||
                    !jsonObject.has("start") || !jsonObject.has("message")) {
                return false;
            }

            // Validate `type` and `start`
            if (!jsonObject.getString("type").equals("player") || jsonObject.getBoolean("start")) {
                return false;
            }

        } catch (JSONException e) {
            System.out.println("Invalid JSON: " + json);
            return false;
        }

        return true;
    }

    public static void writeOut(JSONObject res) {
        try {
            os.writeUTF(res.toString());
            os.flush();
            System.out.println("✅ Sent data to client: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static JSONObject fieldResponse() {
       JSONObject response = new JSONObject();
       response.put("type", "response");
       response.put("request","invalid");
       response.put("ispremetted", false);
       errorMessages.put(response);
        //{
//  "type" : "response",
//  "request" : "invalid",
//  "ispremited" : false
//}
        return response;
    }

    public static JSONObject successfulResponse() {

        JSONObject response = new JSONObject();
        response.put("type", "response");
        response.put("request","valid");
        response.put("permission", true);
        response.put("id",sending.getMessageID() );

        return response;
    }


    public static JSONObject writeResponse() {

        String filename = filePath.GAMEDATAJSON.getFilePath();
        JSONObject message = successfulResponse();

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("im here writing message");

        try {
            JsonNode jsonNode = objectMapper.readTree(new File(filename));

            if (jsonNode != null) {
                JSONObject object = new JSONObject(jsonNode.toString());


                System.out.println("im here sending message");

                sending.put(object);
                object.put("header", message);
                System.out.println("Sending: " + object.toString());
                return object;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not send message. File might be null.");
        }

        return new JSONObject();
    }

    public static void shutdown() {
        System.out.println("Shutting down server...");

        isRunning = false;

        try {
            if (os != null) os.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Client socket closed.");
            }
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server socket closed.");
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
    }



}
