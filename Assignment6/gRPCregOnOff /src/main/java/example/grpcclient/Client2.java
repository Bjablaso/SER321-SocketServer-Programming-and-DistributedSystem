package example.grpcclient;


import com.google.protobuf.Timestamp;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.protobuf.Empty; // needed to use Empty
import services.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Client2 {

    // Stub for talking to the registry
    private final RegistryGrpc.RegistryBlockingStub regStub;

    public Client2(ManagedChannel regChannel) {
        regStub = RegistryGrpc.newBlockingStub(regChannel);
    }

    public void run() {
        // 1. Contact Registry to get all available services
        GetServicesReq req = GetServicesReq.newBuilder().build();
        ServicesListRes servicesResponse;
        try {
            servicesResponse = regStub.getServices(req);
        } catch (Exception e) {
            System.err.println("Failed to contact registry: " + e.getMessage());
            return;
        }

        List<String> services = servicesResponse.getServicesList();
        if (services.isEmpty()) {
            System.out.println("No services are registered.");
            return;
        }

        // 2. Display the registered services to the user
        System.out.println("Registered services:");
        for (int i = 0; i < services.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, services.get(i));
        }

        // 3. Let user choose a service by number
        System.out.print("Choose a service by number: ");
        Scanner scanner = new Scanner(System.in);
        int selection;
        try {
            selection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.err.println("Invalid input. Exiting.");
            return;
        }

        if (selection < 1 || selection > services.size()) {
            System.err.println("Selection out of range. Exiting.");
            return;
        }
        String chosenService = services.get(selection - 1);

        // 4. Contact registry to find a server offering the chosen service.
        // Note: You might have a dedicated method on the registry like findServer().
        FindServerReq findReq = FindServerReq.newBuilder().setServiceName(chosenService).build();
        SingleServerRes serverRes;
        try {
            serverRes = regStub.findServer(findReq);
        } catch (Exception e) {
            System.err.println("Failed to get server for chosen service: " + e.getMessage());
            return;
        }

        // Extract server info (assume the response includes host and port)
        String serverHost = serverRes.getConnection().getUri();
        int serverPort = serverRes.getConnection().getPort();

        System.out.println("Connecting to service " + chosenService + " at " + serverHost + ":" + serverPort);

        // 5. Create a new channel for the selected server.
        ManagedChannel serviceChannel = ManagedChannelBuilder.forAddress(serverHost, serverPort)
                .usePlaintext()
                .build();

        // 6. Adjust the blocking stub based on the chosen service.
        // For example, if the chosen service is "services.Echo/parrot", we create an Echo stub.
        // (You may need to create a helper method to map service names to stubs.)
        try {
            if (chosenService.equals("services.Echo/parrot")) {
                EchoGrpc.EchoBlockingStub echoStub = EchoGrpc.newBlockingStub(serviceChannel);
                System.out.print("Enter a message to parrot: ");
                String message = scanner.nextLine();
                ClientRequest echoReq = ClientRequest.newBuilder().setMessage(message).build();
                ServerResponse echoRes = echoStub.parrot(echoReq);
                System.out.println("Response from Echo Service: " + echoRes.getMessage());
            } else if (chosenService.equals("services.Joke/getJoke")) {
                // Similar process for the Joke service.
                JokeGrpc.JokeBlockingStub jokeStub = JokeGrpc.newBlockingStub(serviceChannel);
                System.out.print("How many jokes would you like? ");
                int num = Integer.parseInt(scanner.nextLine());
                JokeReq jokeReq = JokeReq.newBuilder().setNumber(num).build();
                JokeRes jokeRes = jokeStub.getJoke(jokeReq);
                System.out.println("Your jokes:");
                jokeRes.getJokeList().forEach(joke -> System.out.println(joke));
            }
            // Add additional else-if branches for other services...
        } catch (Exception e) {
            System.err.println("Error calling service: " + e.getMessage());
        } finally {
            // Shutdown the created service channel
            serviceChannel.shutdownNow();
        }
    }

    public static void main(String[] args) {
        // Expect same parameters as the original client (host, port, regHost, regPort, message, regOn)
        if (args.length != 6) {
            System.out.println("Expected arguments: <host> <port> <regHost> <regPort> <message> <regOn>");
            System.exit(1);
        }
        String regHost = args[2];
        int regPort = Integer.parseInt(args[3]);

        // Create a channel for connecting to the registry
        ManagedChannel regChannel = ManagedChannelBuilder.forAddress(regHost, regPort)
                .usePlaintext()
                .build();

        Client2 client2 = new Client2(regChannel);
        client2.run();

        // Clean up the registry channel
        regChannel.shutdownNow();
    }
}
