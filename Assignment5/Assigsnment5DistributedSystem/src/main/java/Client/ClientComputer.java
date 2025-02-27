package Client;

import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.io.*;
import java.net.Socket;

import java.util.Scanner;


public class ClientComputer {
    public static void main(String[] args) {
        Socket serverSocket = null;
        OutputStream writeOutput = null;
        InputStream readInput = null;
        int port = 8080;

        if (args.length != 2) {
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }

        String host = args[0];

        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("[Port must be an integer]");
            System.exit(1);
        }

        System.out.println("Debug: Host = " + host + ", Port = " + port);

        try {
            serverSocket = new Socket(host, port);
            System.out.println("CLIENT connected to Server on " + host + ":" + port);

            writeOutput = serverSocket.getOutputStream();
            readInput = serverSocket.getInputStream();

            // Send initial name request
            Request op = initialRequest().build();
            op.writeDelimitedTo(writeOutput);
            writeOutput.flush();
            System.out.println("Debug: Sending request: " + op.toString());

            while (true) {
                System.out.println("Waiting for server response...");
                Response response = Response.parseDelimitedFrom(readInput);

                if (response == null) {
                    System.out.println("Server sent an invalid response. Disconnecting...");
                    break;
                }

                System.out.println("Server Responded: " + response.toString());

                Request.Builder request = null; 
                switch (response.getResponseType()) {
                    case ACKNOWLEDGE:
                        if (!response.getAccepted()) {
                            System.out.println(response.getErrorMessage());
                            request = initialRequest();
                        } else {
                            request = dataMessenger();
                        }
                        break;

                    case FINAL_SUM:
                        if (!response.getAccepted()) {
                            System.out.println(response.getErrorMessage());
                            request = dataMessenger();
                        }else {
                            System.out.println( "The Sum of all the number you inputted is : " + response.getFinalSum());
                        }

                        if(!askToContinue()){
                            exitAndClose(readInput, writeOutput, serverSocket);
                        }
                        request = dataMessenger();
                        break;

                    case DISCONNECT:
                        System.out.println("Server has disconnected. Closing connection...");
                        exitAndClose(readInput, writeOutput, serverSocket);
                        return;

                    case ERROR:
                        System.out.println("Error received from server: " + response.getErrorMessage());
                        request = initialRequest(); // Restart properly
                        break;

                    default:
                        System.out.println("Unexpected response from server.");
                        break;
                }

                request.build().writeDelimitedTo(writeOutput);
                writeOutput.flush();
            }
        } catch (IOException e) {
            System.out.println("[Server connection failed - Server might be offline]: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            exitAndClose(readInput, writeOutput, serverSocket);
        }
    }

    static Request.Builder initialRequest() throws IOException {
        String name;

            System.out.print("Please provide your name to Server: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            name = reader.readLine();



        return Request.newBuilder()
                .setOperationType(Request.OperationType.CLIENTNAME)
                .setSenderId(name);
    }


    static Request.Builder dataMessenger() throws IOException {

       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please Enter a list of numeric value");
        String numericValue = reader.readLine();

        System.out.println("Would you like to introduce a fualtynode : ( yes , no)");
        String fualtynode = reader.readLine();

        if(fualtynode.equalsIgnoreCase("yes")){
            return Request.newBuilder()
                    .setOperationType(Request.OperationType.DATA)
                    .setNumbers(numericValue)
                    .setIsfualty(true);

        }

        return Request.newBuilder()
                .setOperationType(Request.OperationType.DATA)
                .setNumbers(numericValue);
    }


    static boolean askToContinue() throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String answer;


            do {
                System.out.print("Do you want to continue? (yes/no): ");
                answer = reader.readLine();
                if (!answer.equals("yes") && !answer.equalsIgnoreCase("no")) {
                    System.out.println("Invalid input. Type 'yes' to continue or 'no' to exit.");
                    Thread.sleep(500);
                }
            } while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no"));


        return answer.equals("yes");
    }

    static void exitAndClose(InputStream in, OutputStream out, Socket serverSock) {
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



