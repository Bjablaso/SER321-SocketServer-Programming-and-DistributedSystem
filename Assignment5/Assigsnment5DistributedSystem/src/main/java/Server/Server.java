package Server;

import Control.Leader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            System.exit(1);
        }

        int port = 8080;
        Socket socket = null;
        ServerSocket serverSocket = null;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Port number must be an integer");
            System.exit(1);
            return; // Required to prevent compilation error
        }

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started..");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(2);
        }



            while (true) {
                try{
                    System.out.println("Server started on port: " + port);
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected on port: " + port);

                    // Spawn a new thread for each client connection
                    Thread spawnThread = new Thread(new Leader(clientSocket));
                    spawnThread.start(); // Start the Leader thread to handle the client

                }catch (Exception e) {
                    System.out.println("Error in accepting client connection.");
                }

            }

    }
}
