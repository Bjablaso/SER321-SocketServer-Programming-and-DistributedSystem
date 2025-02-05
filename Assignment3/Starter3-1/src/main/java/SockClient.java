import org.json.JSONArray;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 */
class SockClient {
  static Socket sock = null;
  static String host = "localhost";
  static int port = 8888;
  static OutputStream out;
  // Using and Object Stream here and a Data Stream as return. Could both be the same type I just wanted
  // to show the difference. Do not change these types.
  static ObjectOutputStream os;
  static DataInputStream in;
  public static void main (String args[]) {

    if (args.length != 2) {
      System.out.println("Expected arguments: <host(String)> <port(int)>");
      System.exit(1);
    }

    try {
      host = args[0];
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port|sleepDelay] must be an integer");
      System.exit(2);
    }

    try {
      connect(host, port); // connecting to server
      System.out.println("Client connected to server.");
      boolean requesting = true;
      while (requesting) {
        System.out.println("What would you like to do: 1 - echo, 2 - add, 3 - addmany, 4 - charCount, 5 - inventory (0 to quit)");
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        // You can assume the user put in a correct input, you do not need to handle errors here
        // You can assume the user inputs a String when asked and an int when asked. So you do not have to handle user input checking
        JSONObject json = new JSONObject(); // request object
        switch(choice) {
          case 0:
            System.out.println("Choose quit. Thank you for using our services. Goodbye!");
            requesting = false;
            break;
          case 1:
            System.out.println("Choose echo, which String do you want to send?");
            String message = scanner.nextLine();
            json.put("type", "echo");
            json.put("data", message);
            break;
          case 2:
            System.out.println("Choose add, enter first number:");
            String num1 = scanner.nextLine();
            json.put("type", "add");
            json.put("num1", num1);

            System.out.println("Enter second number:");
            String num2 = scanner.nextLine();
            json.put("num2", num2);
            break;
          case 3:
            System.out.println("Choose addmany, enter as many numbers as you like, when done choose 0:");
            JSONArray array = new JSONArray();
            String num = "1";
            while (!num.equals("0")) {
              num = scanner.nextLine();
              array.put(num);
              System.out.println("Got your " + num);
            }
            json.put("type", "addmany");
            json.put("nums", array);
            break;
          case 4:
            System.out.println("Choose charCount. Do you want to search for a specific character? (yes/no):");
            String choice = scanner.nextLine().trim().toLowerCase();

            System.out.println("Enter the input string:");
            String inputString = scanner.nextLine().

            json.put("type", "charcount");

            if (choice.equals("yes")) {
              System.out.println("Enter the character to search for:");
              String charChara = scanner.nextLine();


              if (charChara.length() != 1) {
                System.out.println("Error: Please enter exactly one character.");
                break;
              }

              json.put("findchar", true);
              json.put("find", charChara);
            } else {
              json.put("findchar", false);
            }

            json.put("count", inputString);
            break;

          case 5:
            String inventoryChoice;


            do {
              System.out.println("Choose inventory task: (add: +, view: -v, buy: -b)");
              inventoryChoice = scanner.nextLine().trim();

              if (!inventoryChoice.equals("+") && !inventoryChoice.equals("-v") && !inventoryChoice.equals("-b")) {
                System.out.println("Invalid choice! Please enter +, -v, or -b.");
              }
            } while (!inventoryChoice.equals("+") && !inventoryChoice.equals("-v") && !inventoryChoice.equals("-b"));

            json.put("type", "inventory");

            switch (inventoryChoice) {
              case "+":
                json.put("task", "add");

                // Create an inventory array
                JSONArray inventoryArray = new JSONArray();

                boolean addingProducts = true;

                while (addingProducts) {
                  JSONObject productEntry = new JSONObject();

                  System.out.println("Enter product name to add:");
                  String product = scanner.nextLine();
                  productEntry.put("product", product);

                  System.out.println("Enter quantity to add:");

                  while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid integer for quantity:");
                    scanner.next();
                  }
                  int addQuantity = scanner.nextInt();
                  scanner.nextLine(); // Consume newline
                  productEntry.put("quantity", addQuantity);

                  // Add product entry to inventory array
                  inventoryArray.put(productEntry);

                  // Ask if the user wants to add another product
                  System.out.println("Would you like to add another product? (yes/no)");
                  String response = scanner.nextLine().trim().toLowerCase();
                  if (!response.equals("yes")) {
                    addingProducts = false;
                  }
                }

                // Attach inventory array to JSON object
                json.put("inventory", inventoryArray);
                break;

              case "-v":
                json.put("task", "view");
                break;

              case "-b":
                json.put("task", "buy");

                System.out.println("Enter product name to buy:");
                String buyProduct = scanner.nextLine();
                json.put("productName", buyProduct);

                System.out.println("Enter quantity to buy:");


                while (!scanner.hasNextInt()) {
                  System.out.println("Invalid input. Please enter a valid integer for quantity:");
                  scanner.next();
                }

                int buyQuantity = scanner.nextInt();
                scanner.nextLine();
                json.put("quantity", buyQuantity);
                break;
            }

            break;

             }

              break;

        }
        if(!requesting) {
          continue;
        }

        // write the whole message
        os.writeObject(json.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        // handle the response
        // - not doing anything other than printing payload
        // !! you will most likely need to parse the response for the other 2 services!
        String i = (String) in.readUTF();
        JSONObject res = new JSONObject(i);
        System.out.println("Got response: " + res);
        if (res.getBoolean("ok")){
          if (res.getString("type").equals("echo")) {
            System.out.println(res.getString("echo"));
          } else {
            System.out.println(res.getInt("result"));
          }
        } else {
          System.out.println(res.getString("message"));
        }
      }
      // want to keep requesting services so don't close connection
      //overandout();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void overandout() throws IOException {
    //closing things, could
    in.close();
    os.close();
    sock.close(); // close socked after sending
  }

  public static void connect(String host, int port) throws IOException {
    // open the connection
    sock = new Socket(host, port); // connect to host and socket on port 8888

    // get output channel
    out = sock.getOutputStream();

    // create an object output writer (Java only)
    os = new ObjectOutputStream(out);

    in = new DataInputStream(sock.getInputStream());
  }
}