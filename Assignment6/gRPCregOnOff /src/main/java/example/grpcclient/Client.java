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


/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class Client {
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
  private final RegistryGrpc.RegistryBlockingStub blockingStub4;
  private final FlowersGrpc.FlowersBlockingStub flowerStub;
  private final WeightTrackerGrpc.WeightTrackerBlockingStub weightTrackerStub;


  /** Construct client for accessing server using the existing channel. */
  public Client(Channel channel, Channel regChannel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
    blockingStub4 = RegistryGrpc.newBlockingStub(channel);
    flowerStub = FlowersGrpc.newBlockingStub(channel);
    weightTrackerStub = WeightTrackerGrpc.newBlockingStub(channel);
  }

  /** Construct client for accessing server using the existing channel. */
  public Client(Channel channel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = null;
    blockingStub4 = null;
    flowerStub = FlowersGrpc.newBlockingStub(channel);
    weightTrackerStub = WeightTrackerGrpc.newBlockingStub(channel);
  }

  // ----------------------- Service Methods -----------------------


  public void askServerToParrot(String message) {

    ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
    ServerResponse response;
    try {
      response = blockingStub.parrot(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e.getMessage());
      return;
    }
    System.out.println("Received from server: " + response.getMessage());
  }

  public void askForJokes(int num) {
    JokeReq request = JokeReq.newBuilder().setNumber(num).build();
    JokeRes response;

    // just to show how to use the empty in the protobuf protocol
    Empty empt = Empty.newBuilder().build();

    try {
      response = blockingStub2.getJoke(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your jokes: ");
    for (String joke : response.getJokeList()) {
      System.out.println("--- " + joke);
    }
  }

  public void setJoke(String joke) {
    JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
    JokeSetRes response;

    try {
      response = blockingStub2.setJoke(request);
      System.out.println(response.getOk());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getNodeServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub4.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub3.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServer(String name) {
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    try {
      response = blockingStub3.findServer(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServers(String name) {
    FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
    ServerListRes response;
    try {
      response = blockingStub3.findServers(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void plantFlower(String flowerName, int waterTimes, int bloomTime) {
    FlowerReq request = FlowerReq.newBuilder()
            .setName(flowerName)
            .setWaterTimes(waterTimes)
            .setBloomTime(bloomTime)
            .build();

    FlowerRes response;
    try {
      response = flowerStub.plantFlower(request);
    } catch (Exception e) {
      System.err.println("RPC plantFlower failed: " + e.getMessage());
      return;
    }

    if (response.getIsSuccess()) {
      System.out.println("Plant Flower Success: " + response.getMessage());
    } else {
      System.err.println("Plant Flower Error: " + response.getError());
    }
  }

  public void viewFlowers() {
    Empty request = Empty.newBuilder().build();
    FlowerViewRes response;
    try {
      response = flowerStub.viewFlowers(request);
    } catch (Exception e) {
      System.err.println("RPC viewFlowers failed: " + e.getMessage());
      return;
    }

    if (response.getIsSuccess()) {
      System.out.println("Flower List:");
      response.getFlowersList().forEach(flower -> {
        System.out.println("Name: " + flower.getName() +
                ", WaterTimes: " + flower.getWaterTimes() +
                ", BloomTime: " + flower.getBloomTime() +
                ", State: " + flower.getFlowerState());
      });
    } else {
      System.err.println("View Flowers Error: " + response.getError());
    }
  }

  public void waterFlower(String flowerName) {
    FlowerCare request = FlowerCare.newBuilder()
            .setName(flowerName)
            .build();

    WaterRes response;
    try {
      response = flowerStub.waterFlower(request);
    } catch (Exception e) {
      System.err.println("RPC waterFlower failed: " + e.getMessage());
      return;
    }

    if (response.getIsSuccess()) {
      System.out.println("Water Flower Success: " + response.getMessage());
    } else {
      System.err.println("Water Flower Error: " + response.getError());
    }
  }

  public void careForFlower(String flowerName) {
    FlowerCare request = FlowerCare.newBuilder()
            .setName(flowerName)
            .build();

    CareRes response;
    try {
      response = flowerStub.careForFlower(request);
    } catch (Exception e) {
      System.err.println("RPC careForFlower failed: " + e.getMessage());
      return;
    }

    if (response.getIsSuccess()) {
      System.out.println("Care For Flower Success: " + response.getMessage() +
              " New bloom time: " + response.getBloomTime());
    } else {
      System.err.println("Care For Flower Error: " + response.getError());
    }
  }

  public void addWeight(String userName, double weight) {
    Weight userWeght = Weight.newBuilder()
            .setName(userName)
            .setWeightNum(weight)
            .build();

    AddWeightRequest req = AddWeightRequest.newBuilder()
            .setWeight(userWeght)
            .build();



    AddWeightResponse resp;
    try {
      resp = weightTrackerStub.addWeight(req);
    } catch (Exception e) {
      System.out.println("AddWeight failed: " + e.getMessage());
      return;
    }


    if(resp.getIsSuccess()){
      System.out.println("Weight Added Success for client " + userName);
    } else {
      System.err.println("Weight Added Error: " + resp.getError());
    }

  }

  public void viewWeightsHistory(String userName) {
    GetWeightRequest req = GetWeightRequest.newBuilder()
            .setName(userName)
            .build();

    GetWeightResponse resp;
    try{
      resp = weightTrackerStub.getWeight(req);
    }catch(Exception e){
      System.err.println("System could not get user weight: " + e.getMessage());
      return;
    }

    if(resp.getIsSuccess()){
      resp.getWeightList().forEach(weight -> {
        LocalDateTime localDateTime = convertTimestampToLocalDateTime(weight.getTimeStamp());
        System.out.println("Name " + userName + "\t" +  + weight.getWeightNum());

      });
    }else {
      System.out.println("View Weight Error: " + resp.getError());
    }
  }

  public void calculateBMI(double weight, double height, String unit){
    BMIRequest req = BMIRequest.newBuilder()
            .setWeight(weight)
            .setHeight(height)
            .setUnits(unit)
            .build();

    BMIResponse resp;

    try{
      resp = weightTrackerStub.getBMI(req);
    }catch(Exception e){
      System.err.println("System could not calculate  user bmi: " + e.getMessage());
      return;
    }

    if(resp.getIsSuccess()){
      System.out.println("Your BMI is : " + resp.getBMI());
    }else{
      System.out.println("Error while calculating user bmi: " + resp.getError());
    }
  }

  public static LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {

    Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());

    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
  }


  // ----------------------- Main: Interactive Menu & Disconnect -----------------------

  public static void main(String[] args) throws Exception {
    if (args.length != 6) {
      System.out
              .println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)> <regOn(bool)>");
      System.exit(1);
    }
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
    try {
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port] must be an integer");
      System.exit(2);
    }

    // Create a communication channel to the server (Node), known as a Channel. Channels
    // are thread-safe
    // and reusable. It is common to create channels at the beginning of your
    // application and reuse
    // them until the application shuts down.
    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
            // Channels are secure by default (via SSL/TLS). For the example we disable TLS
            // to avoid
            // needing certificates.
            .usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
    try {

      // ##############################################################################
      // ## Assume we know the port here from the service node it is basically set through Gradle
      // here.
      // In your version you should first contact the registry to check which services
      // are available and what the port
      // etc is.

      /**
       * Your client should start off with
       * 1. contacting the Registry to check for the available services
       * 2. List the services in the terminal and the client can
       *    choose one (preferably through numbering)
       * 3. Based on what the client chooses
       *    the terminal should ask for input, eg. a new sentence, a sorting array or
       *    whatever the request needs
       * 4. The request should be sent to one of the
       *    available services (client should call the registry again and ask for a
       *    Server providing the chosen service) should send the request to this service and
       *    return the response in a good way to the client
       *
       * You should make sure your client does not crash in case the service node
       * crashes or went offline.
       */

      // Just doing some hard coded calls to the service node without using the
      // registry
      // create client
      Client client = new Client(channel, regChannel);

      // call the parrot service on the server
      client.askServerToParrot(message);

       runInteractiveMenu(client);

      // ask the user for input how many jokes the user wants
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


      // Reading data using readLine
      System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
      String num = reader.readLine();

      // calling the joked service from the server with num from user input
      client.askForJokes(Integer.valueOf(num));

      // adding a joke to the server
      client.setJoke("I made a pencil with two erasers. It was pointless.");

      // showing 6 joked
      client.askForJokes(Integer.valueOf(6));

      // list all the services that are implemented on the node that this client is connected to

      System.out.println("Services on the connected node. (without registry)");
      client.getNodeServices(); // get all registered services

      // ############### Contacting the registry just so you see how it can be done

      if (args[5].equals("true")) {
        // Comment these last Service calls while in Activity 1 Task 1, they are not needed and wil throw issues without the Registry running
        // get thread's services
        client.getServices(); // get all registered services

        // get parrot
        client.findServer("services.Echo/parrot"); // get ONE server that provides the parrot service

        // get all setJoke
        client.findServers("services.Joke/setJoke"); // get ALL servers that provide the setJoke service

        // get getJoke
        client.findServer("services.Joke/getJoke"); // get ALL servers that provide the getJoke service

        // does not exist
        client.findServer("random"); // shows the output if the server does not find a given service
      }

    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent
      // leaking these
      // resources the channel should be shut down when it will no longer be used. If
      // it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }

  private static void runInteractiveMenu(Client client) {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("\n=== Main Service Groups ===");
      System.out.println("1. Echo Service");
      System.out.println("2. Joke Service");
      System.out.println("3. Flower Service");
      System.out.println("4 Weight Watcher");
      System.out.println("5. Registry Service");
      System.out.println("6. Exit");
      System.out.print("Enter your choice: ");

      String input = scanner.nextLine();
      try {
        int option = Integer.parseInt(input);
        switch (option) {
          case 1:
            // TODO: Open the Echo Service window.
            // Example: runEchoWindow(client);
            System.out.println("TODO: Implement Echo Service window.");
            break;
          case 2:
            // TODO: Open the Joke Service window.
            // Example: runJokeWindow(client);
            System.out.println("TODO: Implement Joke Service window.");
            break;
          case 3:
            // For the Flower Service, open a dedicated sub-menu.
               flowerGardenWindow(client);
            break;
          case 4:
            //TODO: Open the weight watcher program
            break;
          case 5:
            // TODO: Open the Registry Service window.
            // Example: runRegistryWindow(client);
            System.out.println("TODO: Implement Registry Service window.");
            break;
          case 6:
            System.out.println("Exiting from main menu. Goodbye!");
            scanner.close();
            return;
          default:
            System.out.println("Invalid option. Please try again.");
            break;
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input; please enter a number.");
      } catch (Exception ex) {
        System.out.println("An error occurred: " + ex.getMessage());
      }
    }
  }


  public static void flowerGardenWindow(Client client) {
    Scanner scanner = new Scanner(System.in);
    char choice;

    do {
      // Display menu options for the Flower Garden service.
      flowerManueChioce();  // This method prints the menu

      System.out.print("Enter your choice: ");
      // Read the first character of the input and convert it to uppercase.
      choice = Character.toUpperCase(scanner.nextLine().charAt(0));
      String name;

      switch (choice) {
        case 'P':

          System.out.println("Plant Flower selected.");
          System.out.print("Enter name for the flower you would like to plant: ");
           name = scanner.nextLine();
          // You can prompt for additional details if needed.
          client.plantFlower(name, 2, 1);
          System.out.println();
          break;
        case 'W':

          System.out.println("Enter name for the flower you would like to water: ");
           name = scanner.nextLine();
          client.waterFlower(name);

          System.out.println();
          break;
        case 'V':


          client.viewFlowers();
          System.out.println();

          break;
        case 'C':

          System.out.println("Enter name for the flower you would like to water: ");
          name = scanner.nextLine();


          client.careForFlower(name);
          System.out.println("Care For Flower selected.");
          break;
        case 'E':
          System.out.println("Exiting Flower Garden window...");
          break;
        default:
          System.out.println("Invalid option. Please choose P, W, V, C, or E.");
          break;
      }

    } while (choice != 'E');

  }


  public static  void flowerManueChioce(){
    // Display menu options for the Flower Garden service.
    System.out.println("=== Flower Garden Menu ===");
    System.out.println(" (P) Plant Flower");
    System.out.println(" (W) Water Flower");
    System.out.println(" (V) View Flowers");
    System.out.println(" (C) Care For Flower");
    System.out.println(" (E) Exit Flower Garden");
    System.out.println();
  }

  public static void WaitWtacher(Client client){
    Scanner scanner = new Scanner(System.in);
    char choice;

    do{
      waitWatcherManueChioce();

      System.out.println("Enter your choice: ");
      choice = Character.toUpperCase(scanner.nextLine().charAt(0));
      String name;

      switch (choice) {
        case 'A':
          System.out.println("What is  your name ");
           name = scanner.nextLine();
          scanner.nextLine();
          System.out.println("How much do you weight");
         double weight = scanner.nextDouble();

         client.addWeight(name, weight);

          break;
        case 'R':
          System.out.println("Enter user name to view their weight record");
          name = scanner.nextLine();

          client.viewWeightsHistory(name);
          break;
        case 'B':
          break;
        case 'E':
          System.out.println("Exiting wait watcher  service window...");
          return;
        default:
          System.out.println("Please enter a valid choice");
      }
    }while(choice != 'E');


  }

  public static  void waitWatcherManueChioce(){
    // Display menu options for the Flower Garden service.
    System.out.println("=== Flower Garden Menu ===");
    System.out.println(" (A) Add wait to be Watch");
    System.out.println(" (R) Get wait from watcher");
    System.out.println(" (B) Calculate BMI of Weght");
    System.out.println(" (E) Exit Flower Garden");

    System.out.println();
  }
}
