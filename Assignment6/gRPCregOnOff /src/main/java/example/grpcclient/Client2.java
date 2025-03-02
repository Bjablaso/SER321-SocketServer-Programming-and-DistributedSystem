package example.grpcclient;


import com.google.protobuf.Timestamp;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import service.*;



public class Client2 {

    // Stub for talking to the registry
    private final RegistryGrpc.RegistryBlockingStub regStub;

    public Client2(ManagedChannel regChannel) {
        regStub = RegistryGrpc.newBlockingStub(regChannel);
    }

    public void run() {

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


        FindServerReq findReq = FindServerReq.newBuilder().setServiceName(chosenService).build();
        SingleServerRes serverRes;
        try {
            serverRes = regStub.findServer(findReq);
        } catch (Exception e) {
            System.err.println("Failed to get server for chosen service: " + e.getMessage());
            return;
        }


        String serverHost = serverRes.getConnection().getUri();
        int serverPort = serverRes.getConnection().getPort();

        System.out.println("Connecting to service " + chosenService + " at " + serverHost + ":" + serverPort);


        ManagedChannel serviceChannel = ManagedChannelBuilder.forAddress(serverHost, serverPort)
                .usePlaintext()
                .build();

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

        } catch (Exception e) {
            System.err.println("Error calling service: " + e.getMessage());
        } finally {

            serviceChannel.shutdownNow();
        }
    }

    public static void main(String[] args) {

        if (args.length != 6) {
            System.out.println("Expected arguments: <host> <port> <regHost> <regPort> <message> <regOn>");
            System.exit(1);
        }
        String regHost = args[2];
        int regPort = Integer.parseInt(args[3]);


        ManagedChannel regChannel = ManagedChannelBuilder.forAddress(regHost, regPort)
                .usePlaintext()
                .build();

        Client2 client2 = new Client2(regChannel);
        client2.run();


        regChannel.shutdownNow();
    }
}
