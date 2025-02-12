package taskone;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadedServer implements Runnable {
    private final Socket conn;
    private final int id;




    public ThreadedServer(Socket socketconn, int id) {
        this.conn = socketconn;
        this.id = id;

    }

    @Override
    public void run() {
        SharedResource sharedResource = new SharedResource();
       // handle shared resurces
        sharedResource.sendMessage(conn,id);

    }


    public static void main(String[] args) throws IOException {
        Socket sock = null;

        int id = 0;
        int port = 0;

        if (args.length != 1) {
            System.out.println("Usage: gradle runThreadedServer -Pport=<port>");
            System.exit(1);
        }

        try{
            port = Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            System.out.println("Port number must be a Integer" );
            System.exit(1);
        }
        ServerSocket server = new ServerSocket(port);
        try{


            while(true){
                System.out.println("Threaded server waiting for connects on port " + port);
                 sock = server.accept();
                System.out.println("Threaded server connected to client-" + id);

                Thread newThread = new Thread(new ThreadedServer(sock, id));
                newThread.start();

            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
          closeConnection(sock,server);
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
