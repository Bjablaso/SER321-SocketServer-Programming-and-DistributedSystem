package Server;

import Control.Leader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: java Server <port>");
            System.exit(1);
        }
        int port = 8080;


        Socket socket = null;
        ServerSocket serverSocket = null;

        try{
            port = Integer.parseInt(args[0]);

        }catch(NumberFormatException e){
            System.out.println(" port number must be an integer");
            System.exit(1);
        }

        try{
            while(true){
                serverSocket = new ServerSocket(port);
                socket = serverSocket.accept();

                System.out.println("Client connected to server on port: " + port);

                Leader mainComputer = Leader.getInstance(socket);
                Thread spawnThread = new Thread(mainComputer);

            }

        }catch (IOException e){
            System.out.println("Error in accepting client connection.");
            System.exit(1);
        }
    }
}
