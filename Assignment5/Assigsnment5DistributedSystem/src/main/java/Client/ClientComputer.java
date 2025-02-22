package Client;

import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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

      Request op; //  = inital().build(); // inial request
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
                    case GREATING :
                        System.out.println(response.toString());
                        //sent request back to server
                        break;
                        
                }


            }

        }catch (IOException e){
            System.out.println("[Server connection failed [Server-Offline]");
            System.exit(1);
        }

    }



}
