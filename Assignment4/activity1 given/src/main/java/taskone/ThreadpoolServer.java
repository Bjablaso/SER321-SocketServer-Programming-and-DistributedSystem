package taskone;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadpoolServer implements Runnable{
    private final Socket conn;
    private final int id;


    public ThreadpoolServer(Socket socketconn, int id) {
        this.conn = socketconn;
        this.id = id;

    }

    @Override
    public void run() {
        SharedResource sharedResource = new SharedResource();
        // sending data
        sharedResource.sendMessage(conn,id);

    }






    public static void main(String[] args) throws IOException {
        Socket sock = null;
        int id = 0;


        if (args.length <= 2) {
            System.out.println("Usage: gradle runThreadedServerPool -Pport <port> <thread amount> ");
            System.exit(1);
        }
        int port = 0;
        int threadAmount = 0;
        try{
            port = Integer.parseInt(args[0]);
            threadAmount = Integer.parseInt(args[1]);

        }catch (NumberFormatException e){
            System.out.println("Usage: gradle runThreadedServerPool -Pport <port>" +
                    " must be integer and thread amount must be greater than 0");
            System.out.println(e.getMessage());
        }

        ServerSocket serverSocket = new ServerSocket(port);
        Thread[] newThreads = new Thread[threadAmount];
        System.out.println("initializing thread pool");
        try {
            System.out.println("Socket Listing on port " +port);

            int counter = 0;

            while (true) {
                sock = serverSocket.accept();
                System.out.println("Client connect accept... ");

                if (counter < threadAmount) {
                    System.out.println("Adding client thread to  Thread pool accepted");
                    newThreads[counter] = new Thread(new ThreadpoolServer(sock, id++));// add things
                    newThreads[counter].start();
                    counter++;

                }else{
                    System.out.println("Thread pool full. Rejecting client and resetting connection...");
                    sock.close();
                }

            }



        }catch (IOException e){
            System.out.println("Error while listening on port " + port + "cound not keep connect alive");
            System.out.println(e.getMessage());
        }finally {
           closeConnection(sock, serverSocket);
        }
    }


    public static void closeConnection(Socket sock, ServerSocket server) {
        try{
            sock.close();
            server.close();

        }catch (Exception e){
            System.out.println("Could not close connection");
            e.printStackTrace();
        }

    }

}
