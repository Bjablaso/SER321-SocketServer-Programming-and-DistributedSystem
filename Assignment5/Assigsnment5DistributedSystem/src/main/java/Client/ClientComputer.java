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

            while (true) {

                Request op = initialRequest().build();
                System.out.println("Debug: Sending request: " + op.toString());


                op.writeDelimitedTo(writeOutput);
                writeOutput.flush();


                System.out.println("Waiting for server response...");
                Response response = Response.parseDelimitedFrom(readInput);

                if (response == null) {
                    System.out.println("Server sent an invalid response. Disconnecting...");
                    break;
                }

                System.out.println("Server Responded: " + response.toString());

                Request.Builder request = Request.newBuilder();

                switch (response.getResponseType()) {
                    case ACKNOWLEDGE:
                        if (!response.getAccepted()) {
                            System.out.println(response.getErrorMessage());
                            request = initialRequest(); // Ask user for name again if rejected
                        } else {
                            request = dataMessenger(); // Move to next step
                        }
                        break;

                    case FINAL_SUM:
                        System.out.println("Final Computation Result: " + response.getPartialSum());

//                        if (!askToContinue(scanner)) {
//                            System.out.println("Closing connection...");
//                            exitAndClose(readInput, writeOutput, serverSocket);
//                            return;
//                        } else {
//                            request = initialRequest(scanner);
//                        }
                        break;

                    case DISCONNECT:
                        System.out.println("Server has disconnected. Closing connection...");
                        exitAndClose(readInput, writeOutput, serverSocket);
                        return;

                    case ERROR:
                        System.out.println("Error received from server: " + response.getErrorMessage());

                        if(response.getErrorType() == 0){
                            request = initialRequest();
                        }
                        break;

                    default:
                        System.out.println("Unexpected response from server.");
                        break;
                }


                request.build().writeDelimitedTo(writeOutput);
                writeOutput.flush(); // Ensure the request is fully sent
            }
        } catch (IOException e) {
            System.out.println("[Server connection failed - Server might be offline]: " + e.getMessage());
            System.exit(1);
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

        return Request.newBuilder()
                .setOperationType(Request.OperationType.DATA)
                .setNumbers(numericValue);
    }

    static boolean askToContinue(Scanner scanner) {
        String answer;
        do {
            System.out.print("Do you want to continue? (yes/no): ");
            answer = scanner.nextLine().trim().toLowerCase();
            if (!answer.equals("yes") && !answer.equals("no")) {
                System.out.println("Invalid input. Type 'yes' to continue or 'no' to exit.");
            }
        } while (!answer.equals("yes") && !answer.equals("no"));

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
