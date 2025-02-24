package Control;

import Entity.Node;
import buffers.RequestProtos.*;
import buffers.ResponseProtos. *;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

import static buffers.ResponseProtos.Response.ResponseType.FINAL_SUM;

public class Leader implements Runnable{


    private String leaderId;                     // Unique identifier for the leader
    private List<NodeComputer> activeNodes;              // List of active nodes
    private Map<NodeComputer, int[]> assignedTasks;      // Tracks which node gets which data
    private Map<NodeComputer, Integer> pendingResults;   // Stores partial sums from nodes
    private Map<NodeComputer, Boolean> consensusResults; // Stores consensus verification results
    private long heartbeatInterval = 5000;  // Check every 5 seconds
    private long timeoutThreshold = 10000; // Mark as lost if no response in 10 sec
    private boolean faultToleranceEnabled;       // Whether fault handling is enabled
    private long nodeDelay = 8000;
    private String currentUserid = null;
    private  int currentState = 0;
    private int divisiblePart = 3;

    private ExecutorService threadPool;


    Socket clientSocket = null;
    InputStream in = null;
    OutputStream out = null;


    private static Leader instance;

    // Constructor
    public Leader( Socket clientSocket ) {
        this.clientSocket = clientSocket;
        this.assignedTasks = new HashMap<>();
        this.pendingResults = new HashMap<>();
        this.consensusResults = new HashMap<>();
        this.activeNodes = new ArrayList<>();
        this.threadPool = Executors.newFixedThreadPool(3); // Pool of 3 worker threads
        this.clientSocket = clientSocket;


        try {
            this.in = clientSocket.getInputStream();
            this.out = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println("Failed to initialize I/O streams: " + e.getMessage());
        }
    }



    @Override
    public void run() {
        try {
            spawnInstance();
        } catch (IOException e) {
            System.out.println("Client " + currentUserid + " could not connect to server: " + "\n\n"+ e.getMessage());
            exitAndClose(in,out,clientSocket);
        }
    }

    public void spawnInstance() throws IOException {
        try{
           while(true){
                 Response response;
//               String prompt;
//                do{
//                    System.out.println("Would you like to stay connected ? (yes/no)");
//                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//                    prompt = in.readLine();
//
//                }while (prompt.equalsIgnoreCase("yes") || prompt.equalsIgnoreCase("no"));
//
//                if(prompt.equalsIgnoreCase("no")){
//                    // disconnect use while continuing the brogram
//                    // call disconnect sefely method
//                    response = disconnectMe("Server Terminating Your connection..");
//                    response.writeDelimitedTo(out);
//                    break;
//                }

                // communication with clean
               Request op = Request.parseDelimitedFrom(in);
               System.out.println("Got request: " + op.toString());


               switch (op.getOperationType()){
                   case CLIENTNAME :
                           response = nameRequest(op);
                       break;
                   case DATA:
                       if(!op.hasNumbers()){
                           response = error(0, "Try again");
                       }
                       startThread(op);
                      response = processData();

                       break;
               }

            }

        }catch (SocketException se){
            System.out.println("Error Early  disconnection");

        }catch(Exception e){
           Response error = error(0, "Unexpected server error: " + e.getMessage());
            error.writeDelimitedTo(out);
        }finally {
            System.out.println("Client ID " + currentUserid + " disconnected.");
        }
    }

    private Response error(int err, String field) throws IOException {
        String message = field;
        int type = err;

        Response.Builder resp = Response.newBuilder();

        switch (type){
            case 1:
                message = message + "\nError: required field missing or empty";
                break;
            case 2:
                message = message + "\nError: request not supported";
                break;
            default:
                message = message + "\nError: cannot process your request";
                type = 0;
                break;

        }
        resp
                .setResponseType(Response.ResponseType.ERROR)
                .setErrorType(type)
                .setErrorMessage(message);


        return  resp.build();
    }

//    private Response disconnectMe(String message){
//        Response.Builder resp = Response.newBuilder();
//        resp.setResponseType(Response.ResponseType.DISCONNECT)
//                .setDisconnectSafely(message);
//
//        return resp.build();
//    }

    private Response nameRequest(Request req){
        Response.Builder resp = Response.newBuilder();

        if(req.getSenderId().isBlank()){
            resp.setResponseType(Response.ResponseType.ACKNOWLEDGE)
                    .setAccepted(false)
                    .setErrorMessage("Client name missing in fields");
        }else {
            this.currentUserid = req.getSenderId();
            resp.setResponseType(Response.ResponseType.ACKNOWLEDGE)
                    .setAccepted(true);
        }

       return resp.build();
    }

    private void startThread(Request req){


        String data = req.getNumbers();
        int number = Integer.parseInt(data);
        int[] dataArray = toDigitArray(number);

        int len = dataArray.length;
        int part1Size = len / divisiblePart;
        int part2Size = len / divisiblePart;
        int part3Size = len - (part1Size + part2Size);

        int[] firstPart = Arrays.copyOfRange(dataArray, 0, part1Size);
        int[] secondPart = Arrays.copyOfRange(dataArray, part1Size, part1Size + part2Size);
        int[] thirdPart = Arrays.copyOfRange(dataArray, part1Size + part2Size, len);

        int delay = (int) nodeDelay;

        NodeComputer node1 = new NodeComputer(new Node("firstThread"), this);
        NodeComputer node2 = new NodeComputer(new Node("secondThread"), this);
        NodeComputer node3 = new NodeComputer(new Node("thirdThread"), this);

        activeNodes.add(node1);
        activeNodes.add(node2);
        activeNodes.add(node3);

        threadPool.submit(node1);
        threadPool.submit(node2);
        threadPool.submit(node3);
    }

    public Response processData(){
        //start thread
        // recieve thread data
        // response to thread after consensus to stop
        // strop the thread
        // respond response to be send to client
        return  null;
    }

    public synchronized void receiveNodeResponse(Response.Builder response){

        switch(response.getResponseType()){
            case PARTIAL_SUM:
                break;

            case VERIFY_RESULT:
                break;
        }


    }


    public int[] toDigitArray(int num) {
        String numStr = Integer.toString(num);
        return numStr.chars().map(c -> c - '0').toArray();
    }


    void exitAndClose(InputStream in, OutputStream out, Socket serverSock) {
        try {
            if (in != null) in.close();
        } catch (IOException e) {
            System.out.println("Failed to close input stream: " + e.getMessage());
        }
        try {
            if (out != null) out.close();
        } catch (IOException e) {
            System.out.println("Failed to close output stream: " + e.getMessage());
        }
        try {
            if (serverSock != null) serverSock.close();
        } catch (IOException e) {
            System.out.println("Failed to close socket: " + e.getMessage());
        }
        System.exit(0);
    }

    public static synchronized Leader getInstance(Socket clientSocket) {
        if (instance == null) {
            instance = new Leader(clientSocket);
        }
        return instance;
    }


}
