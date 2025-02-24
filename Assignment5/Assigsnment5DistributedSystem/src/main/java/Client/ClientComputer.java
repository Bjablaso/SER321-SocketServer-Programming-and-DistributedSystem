package Client;

import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Stream;

public class ClientComputer {
    public static void main(String[] args) throws Exception {
        Socket serverSocket = null;
        OutputStream writeOutput = null;
        InputStream readInput = null;

        int port = 8080; // configure AWS to use this

        if (args.length != 2){
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }
        String host = args[0];

        try{
            port = Integer.parseInt(args[1]);

        }catch (NullPointerException e){
            System.out.println("[Port must be an Integer]");
            System.exit(1);
        }

      Request op =   initalRequest().build(); // inial request
       Response response;

        try{
            serverSocket = new Socket(host, port);

            System.out.println("CLIENT connect to Server on " + host + ":" + port);

            writeOutput = serverSocket.getOutputStream();
            readInput = serverSocket.getInputStream();

            while(true){

                response = Response.parseDelimitedFrom(readInput);

                System.out.println("Server Responded with " + response.toString());

                Request.Builder req = Request.newBuilder();

                switch (response.getResponseType()){
                    case ACKNOWLEDGE:
                        if(!response.getAccepted()){
                            System.out.println(response.getErrorMessage().toString());
                            req = dataMessanger();

                        }else {
                            req = initalRequest();
                        }

                        break;

                    case RESULT:

                        break;

                    case DISCONNECT:

                        break;


                    case ERROR:
                        break;

                }


            }

        }catch (IOException e){
            System.out.println("[Server connection failed [Server-Offline]");
            System.exit(1);
        }finally {
            exitAndClose(readInput, writeOutput, serverSocket);
        }

    }

    static Request.Builder initalRequest() throws IOException{
        System.out.println("Please provide your name to Server : ");

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String strToSend = stdin.readLine();

        return Request.newBuilder()
                .setOperationType(Request.OperationType.CLIENTNAME)
                .setSenderId(strToSend);
    }

    static Request.Builder dataMessanger() throws IOException{

        System.out.println("Please provide a list of number for the server : ");

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String strToSend = stdin.readLine();

        Request.Builder req = Request.newBuilder()
                .setOperationType(Request.OperationType.DATA)
                .setNumbers(strToSend);

        return  req;
    }

    static Request.Builder  disconnectResponse(Response response){


        return null;
    }


    static void exitAndClose(InputStream in, OutputStream out, Socket serverSock){
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



}
