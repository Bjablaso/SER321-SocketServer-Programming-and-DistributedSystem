package taskone;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class SharedResource {

    static final  StringList sharedProgramData = new StringList();
    static final Performer sharedPerformerData = new Performer(sharedProgramData);
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();



    public  void sendMessage(Socket conn, int id ) {
        try (
                ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream())
        ) {
            byte[] incoming = NetworkUtils.receive(in);

            JSONObject recievedMessage = JsonUtils.fromByteArray(incoming);
            int option = handleIncomingMessage(recievedMessage,conn, id);

            JSONObject response = processOption(option, recievedMessage, conn, id);


            byte[] responseInBytes = JsonUtils.toByteArray(response);
            NetworkUtils.send(out, responseInBytes);

        } catch (IOException e) {
            System.err.println("[Error] at thread " + id + ": " + e.getMessage());

        } finally {
            closeThreadSocket(conn, id);
        }

    }

    private  int handleIncomingMessage(JSONObject incomingMessage, Socket socket, int id) {
        if (!incomingMessage.has("selected")) {
            String message = "[Error] at thread " + id + ": No 'selected' data in the request at port "
                    + socket.getPort();
            System.err.println(message);
            return -1;
        }
        try {
            return incomingMessage.getInt("selected");
        } catch (Exception e) {
            String message = "[Error] at thread " + id + ": 'selected' data must be an integer."
                    + socket.getPort();
            System.err.println(message);
            return -1;
        }
    }

    private  JSONObject processOption(int option, JSONObject incomingMessage, Socket socket, int id) {
        JSONObject response = new JSONObject();
        switch (option) {
            case 0:
                response  = sharedPerformerData.quite();

                break;
            case 1:
                String data = incomingMessage.getString("data");
                lock.writeLock().lock();
                if(!incomingMessage.has("data") && data.isEmpty() || data == null) {
                    System.out.println("data field do not exist || is null or empty");
                    String message = "[Error] at thread " + id + " Port" + socket.getPort() +
                    " : No or empty 'data' request in data" +
                            " field for option 1 " +
                            "which is required";
                    sharedPerformerData.error(message);
                    lock.writeLock().unlock();
                }else {
                    try {
                        response = sharedPerformerData.add(data);
                    } catch (InterruptedException e) {
                        System.err.println("[Error] at thread " + id + " Port" + socket.getPort() +
                                " : Interrupted while adding data.");
                        response = sharedPerformerData.error("Failed to add data.");

                    } finally {
                        lock.writeLock().unlock();
                    }
                }
                break;
            case 2:
                lock.readLock().lock();

                if(!incomingMessage.has("data")){
                    System.out.println("data field do not exist");
                    String message = "[Error] at thread " + id + " Port" + socket.getPort() +
                            " : No 'data' request in data field for option 2 " +
                            "which is required";
                    sharedPerformerData.error(message);
                    lock.readLock().unlock();
                }else{
                    int index = incomingMessage.getInt("data");
                    try{
                        response = sharedPerformerData.display(index);

                    }finally {
                        lock.readLock().unlock();
                    }
                }
                break;
            case 3:
                lock.readLock().lock();
                try{
                    response = sharedPerformerData.count();
                }finally {
                    lock.readLock().unlock();
                }
                break;

            default:
                String message = "[Error] at thread " + id + " Port" + socket.getPort() +
                        " : Invalid option selected.";
                response = sharedPerformerData.error(message);

        }

        return response;
    }




    private  void closeThreadSocket(Socket conn, int id) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (IOException e) {
            System.err.println("[Error] at thread " + id + ": Failed to close the socket on port: " + conn.getPort());
        }
    }
}
