package taskone;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadedServer implements Runnable {
      Socket conn;
    int id;
    static Performer performerdata;
    static StringList programdata;

     final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ThreadedServer(Socket socketconn, int id) {
        this.conn = socketconn;
        this.id = id;
        this.programdata = new StringList();
        performerdata = new Performer(programdata);
    }

    @Override
    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream())
        ) {
            // Read incoming message
            JSONObject incomingMessage = new JSONObject(in.readObject().toString());
            int option = handleIncomingMessage(incomingMessage);

            // Process the request based on the "selected" option
            JSONObject outgoingMessage = processOption(option, incomingMessage);

            // Send the response back to the client
            out.writeObject(outgoingMessage.toString());
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Error] at thread " + id + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (IOException e) {
                System.err.println("[Error] at thread " + id + ": Failed to close the socket.");
            }
        }

    }


    private int handleIncomingMessage(JSONObject incomingMessage) {
        int option = -1; // Default to invalid option
        if (!incomingMessage.has("selected")) {
            String message = "[Error] at thread " + id + ": No 'selected' data in the request.";
            Performer.error(message);
            return option;
        }
        try {
            option = incomingMessage.getInt("selected");
        } catch (Exception e) {
            String message = "[Error] at thread " + id + ": 'selected' data must be an integer.";
            Performer.error(message);
        }
        return option;
    }

    private JSONObject processOption(int option, JSONObject incomingMessage) {
        JSONObject outgoingMessage = new JSONObject();
        switch (option) {
            case 0: // Quit
                outgoingMessage = performerdata.quite();
                break;

            case 1: // Add
                String data = incomingMessage.optString("data", "");
                lock.writeLock().lock();
                try {
                    outgoingMessage = performerdata.add(data);
                } catch (InterruptedException e) {
                    System.err.println("[Error] at thread " + id + ": Interrupted while adding data.");
                    outgoingMessage = Performer.error("Failed to add data.");
                } finally {
                    lock.writeLock().unlock();
                }
                break;

            case 2: // Display
                int index = incomingMessage.optInt("data", -1);
                lock.readLock().lock();
                try {
                    outgoingMessage = performerdata.display(index);
                } finally {
                    lock.readLock().unlock();
                }
                break;

            case 3: // Count
                lock.readLock().lock();
                try {
                    outgoingMessage = performerdata.count();
                } finally {
                    lock.readLock().unlock();
                }
                break;

            default:
                String message = "[Error] at thread " + id + ": Invalid option selected.";
                outgoingMessage = Performer.error(message);
        }
        return outgoingMessage;
    }




    public static void main(String[] args) throws IOException {
        Socket sock = null;
        int id = 0;
        int port = 0;

        if (args.length != 1) {
            System.out.println("Usage: runServer -Pport=<port>");
            System.exit(1);
        }

        try{
            port = Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            System.out.println("Port number must be a Integer" );
            System.exit(1);
        }

        try{
            ServerSocket server = new ServerSocket(port);

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
            if(sock != null){
                sock.close();
            }
        }


    }


}
