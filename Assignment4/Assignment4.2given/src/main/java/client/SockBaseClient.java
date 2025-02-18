package client;

import Entity.Player;
import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

//import static server.SockBaseServer.menuOptions;

/**
 * Socket Base Client class check send request to the server to enter the Sudoku game and perform
 * task such as Start (to start game, Play (to play game), LeadershipBoard (to view the board of all current player)
 * This class hand Server Error and among Other thingg
 */
class SockBaseClient {
    public static void main (String[] args) throws Exception {
        Socket serverSock = null;
        OutputStream out = null;
        InputStream in = null;
        int i1=0, i2=0;
        int port = 8000; // default port

        // Make sure two arguments are given
        if (args.length != 2) {
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }
        String host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be integer");
            System.exit(2);
        }

        // Build the first request object just including the name
        Request op = nameRequest().build();
        Response response;
        try {
            // connect to the server
            serverSock = new Socket(host, port);

            // write to the server
            out = serverSock.getOutputStream();
            in = serverSock.getInputStream();

            op.writeDelimitedTo(out);

            while (true) {
                // read from the server
                response = Response.parseDelimitedFrom(in);
                System.out.println("Got a response: " + response.toString());

                Request.Builder req = Request.newBuilder();

                switch (response.getResponseType()) {
                    case GREETING:
                        System.out.println(response.getMessage());
                        req = chooseMenu(req, response);
                        break;

                    case LEADERBOARD:
                        System.out.println("Leaderboard:");
                        if (response.getLeaderCount() > 0) {// Check if leaderboard has entries
                            System.out.println("Name " + " - Point ");

                            for (Response.Entry entry : response.getLeaderList()) {
                                System.out.println(entry.getName() + " " + entry.getPoints() );
                            }
                        } else {
                            System.out.println("No entries found in the leaderboard.");
                        }
                        req = chooseMenu(Request.newBuilder(), response); // Back to main menu
                        break;

                    case START:
                        System.out.println("Game started! Here's the board:");
                        System.out.println(response.getBoard()); // Display initial board
                        req = gameMenu(Request.newBuilder(), response); // Enter the in-game menu loop
                        break;

                    case PLAY:
                        System.out.println("Current Board:");
                        System.out.println(response.getBoard()); // Updated board
                        System.out.println("Points: " + response.getPoints()); // Display points
                        req = gamePlay(Request.newBuilder(), response); // Back to in-game menu
                        break;


                    case WON:
                        System.out.println("Congratulations! You solved the puzzle!");
                        System.out.println(response.getBoard()); // Final board
                        System.out.println("Final Points: " + response.getPoints());
                        req = chooseMenu(Request.newBuilder(), response); // Back to main menu
                        break;

//                    case LOST:
//                        System.out.println("Sorry : You lost!");
//                        System.out.println(response.getBoard());
//                        System.out.println("Final Points: " + response.getPoints());
//                        req = chooseMenu(Request.newBuilder(), response);

                    case ERROR:
                        System.out.println("Error: " + response.getMessage() + " Type: " + response.getErrorType());
                        if (response.getNext() == 1) {
                            req = nameRequest(); // Back to name request
                        } else if (response.getNext() == 2) {
                            req = chooseMenu(Request.newBuilder(), response); // Back to main menu
                        } else if (response.getNext() == 3) {
                            req = gamePlay(Request.newBuilder(), response); // send client back to game play because error took place in game play
                        } else {
                            System.out.println("Unhandled error type.");
                            System.out.println(response.getBoard());
                            req = chooseMenu(Request.newBuilder(), response); // Default to main menu
                        }
                        break;

                    case BYE:
                        System.out.println(response.getMessage()); // Goodbye message
                        System.out.println("Client closing connection...");
                        //close the current client connection
                        exitAndClose(in, out, serverSock);
                        return; // Exit the client
                }
                req.build().writeDelimitedTo(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exitAndClose(in, out, serverSock);
        }
    }

    /**
     * handles building a simple name requests, asks the user for their name and builds the request
     * @return Request.Builder which holds all teh information for the NAME request
     */
    static Request.Builder nameRequest() throws IOException {
        System.out.println("Please provide your name for the server.");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String strToSend = stdin.readLine();

        return Request.newBuilder()
                .setOperationType(Request.OperationType.NAME)
                .setName(strToSend);
    }

    /**
     * Shows the main menu and lets the user choose a number, it builds the request for the next server call
     * @return Request.Builder which holds the information the server needs for a specific request
     */
    static Request.Builder chooseMenu(Request.Builder req, Response response) throws IOException {
        while (true) {

            System.out.println(response.getMenuoptions());
//            System.out.print("Enter a number 1-3: ");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String menu_select = stdin.readLine();
            System.out.println(menu_select);
            switch (menu_select) {
                // needs to include the other requests
                case "1":
                    req.setOperationType(Request.OperationType.LEADERBOARD);
                   return req;
                case "2":
                    System.out.print("Enter difficulty (1-20): ");
                    String difficulty = stdin.readLine();
                    try {
                        int diff = Integer.parseInt(difficulty);
                        if (diff < 1 || diff > 20) throw new NumberFormatException();
                        req.setOperationType(Request.OperationType.START).setDifficulty(diff);
                        return req;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid difficulty. Please enter a number between 1 and 20.");
                    }
                    return req;
                case "3":
                    req.setOperationType(Request.OperationType.QUIT);
                    return req;
                default:
                    System.out.println("\nNot a valid choice, please choose again");
                    break;
            }
        }
    }

    /**
     * Exits the connection
     */
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


    /**
     * Handles the clear menu logic when the user chooses that in Game menu. It retuns the values exactly
     * as needed in the CLEAR request row int[0], column int[1], value int[3]
     */
    static int[] boardSelectionClear() throws Exception {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose what kind of clear by entering an integer (1 - 5)");
        System.out.print(" 1 - Clear value" +
                " \n 2 - Clear row " +
                "\n 3 - Clear column " +
                "\n 4 - Clear Grid" +
                " \n 5 - Clear Board \n");

        String selection = stdin.readLine();

        while (true) {
            if (selection.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                int temp = Integer.parseInt(selection);

                if (temp < 1 || temp > 5) {
                    throw new NumberFormatException();
                }

                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.println("Choose what kind of clear by entering an integer (1 - 5)");
                System.out.print("1 - Clear value \n 2 - Clear row \n 3 - Clear column \n 4 - Clear Grid \n 5 - Clear Board \n");
            }
            selection = stdin.readLine();
        }

        int[] coordinates = new int[3];

        switch (selection) {
            case "1":
                // clear value, so array will have {row, col, 1}
                coordinates = boardSelectionClearValue();
                break;
            case "2":
                // clear row, so array will have {row, -1, 2}
                coordinates = boardSelectionClearRow();
                break;
            case "3":
                // clear col, so array will have {-1, col, 3}
                coordinates = boardSelectionClearCol();
                break;
            case "4":
                // clear grid, so array will have {gridNum, -1, 4}
                coordinates = boardSelectionClearGrid();
                break;
            case "5":
                // clear entire board, so array will have {-1, -1, 5}
                coordinates[0] = -1;
                coordinates[1] = -1;
                coordinates[2] = 5;
                break;
            default:
                break;
        }

        return coordinates;
    }

    static int[] boardSelectionClearValue() throws Exception {
        int[] coordinates = new int[3];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose coordinates of the value you want to clear");
        System.out.print("Enter the row as an integer (1 - 9):  - type 'exit' to quite ");
        String row = stdin.readLine();

        while (true) {
            if (row.equalsIgnoreCase("exit")) {
                // return us to game manu not this //
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(row);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the row as an integer (1 - 9): ");
            }
            row = stdin.readLine();
        }

        coordinates[0] = Integer.parseInt(row);

        System.out.print("Enter the column as an integer (1 - 9): ");
        String col = stdin.readLine();

        while (true) {
            if (col.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(col);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the column as an integer (1 - 9): ");
            }
            col = stdin.readLine();
        }

        coordinates[1] = Integer.parseInt(col);

        coordinates[2] = 1;
        // need to add a type so that we can handle this appropriately


        return coordinates;
    }

    static int[] boardSelectionClearRow() throws Exception {
        int[] coordinates = new int[3];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose the row you want to clear");
        System.out.print("Enter the row as an integer (1 - 9): ");
        String row = stdin.readLine();

        while (true) {
            if (row.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(row);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the row as an integer (1 - 9): ");
            }
            row = stdin.readLine();
        }

        coordinates[0] = Integer.parseInt(row);


        coordinates[1] = -1;
        coordinates[2] = 2;


        return coordinates;
    }

    static int[] boardSelectionClearCol() throws Exception {
        int[] coordinates = new int[3];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose the column you want to clear");
        System.out.print("Enter the column as an integer (1 - 9): ");
        String col = stdin.readLine();

        while (true) {
            if (col.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(col);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the column as an integer (1 - 9): ");
            }
            col = stdin.readLine();
        }

        coordinates[0] = -1;
        coordinates[1] = Integer.parseInt(col);
        coordinates[2] = 3;

        return coordinates;
    }

    static int[] boardSelectionClearGrid() throws Exception {
        int[] coordinates = new int[3];

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose area of the grid you want to clear");
        System.out.println(" 1 2 3 \n 4 5 6 \n 7 8 9 \n");
        System.out.print("Enter the grid as an integer (1 - 9): ");
        String grid = stdin.readLine();

        while (true) {
            if (grid.equalsIgnoreCase("exit")) {
                return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
            }
            try {
                Integer.parseInt(grid);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("That's not an integer!");
                System.out.print("Enter the grid as an integer (1 - 9): ");
            }
            grid = stdin.readLine();
        }

        coordinates[0] = Integer.parseInt(grid);
        coordinates[1] = -1;
        coordinates[2] = 4;


        return coordinates;
    }

    static Request.Builder gameMenu(Request.Builder req, Response response) throws Exception {
        while (true) {
            System.out.println(response.getMenuoptions());
            System.out.print("Enter a command (1-5): ");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String command = stdin.readLine();

            if (command.equalsIgnoreCase("exit")) {
                req.setOperationType(Request.OperationType.QUIT);
                return req;
            }

            switch (command) {
                case "1": // Update value
                    int[] updateCoords = boardSelectionUpdate();
                    if (updateCoords[0] == Integer.MIN_VALUE) break; // Exit if input was invalid
                    req.setOperationType(Request.OperationType.UPDATE)
                            .setRow(updateCoords[0])
                            .setColumn(updateCoords[1])
                            .setValue(updateCoords[2]);
                    return req;

                case "2": // Clear
                    int[] clearCoords = boardSelectionClear();
                    if (clearCoords[0] == Integer.MIN_VALUE) break; // Exit if input was invalid
                    req.setOperationType(Request.OperationType.CLEAR)
                            .setRow(clearCoords[0])
                            .setColumn(clearCoords[1])
                            .setValue(clearCoords[2]);
                    return req;

                case "3": // Quit game
                    req.setOperationType(Request.OperationType.QUIT);
                    return req;

                default:
                    System.out.println("Invalid command. Try again.");
                    break;
            }
        }
    }

    static Request.Builder gamePlay(Request.Builder req, Response response) throws Exception {

        do {
            System.out.println(" What action would you like to perform " +
                    "\n m (Make Move) " +
                    "\n c - clear number " +
                    "\n r - New Board ?");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String command = stdin.readLine();

            switch (command){
                case "m":
                    return req = makeMove(req, response);

                case "c":
                    int[] coordinat = boardSelectionClear();
                    int row = coordinat[0];
                    int col = coordinat[1];
                    int clearType = coordinat[2];
                    return req.setOperationType(Request.OperationType.CLEAR)
                            .setRow(row)
                            .setColumn(col)
                            .setType(clearType);
                case "r":
                    return req = newGame(req);


            }

        }while (true);

    }


    static Request.Builder newGame(Request.Builder req) throws Exception {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("Enter difficulty (1-20): ");
            String difficulty = stdin.readLine();
            try {
                int diff = Integer.parseInt(difficulty);
                if (diff < 1 || diff > 20) throw new NumberFormatException();
                req.setOperationType(Request.OperationType.START).setDifficulty(diff);
                return req;
            } catch (NumberFormatException e) {
                System.out.println("Invalid difficulty. Please enter a number between 1 and 20.");
            }
            return req;
        }
    }

    static  Request.Builder makeMove( Request.Builder req, Response response) {
        Scanner scanner = new Scanner(System.in);

        // Parse the menu options
        String menuOptions = response.getMenuoptions();
        if (menuOptions == null || menuOptions.isEmpty()) {
            System.out.println("Invalid menu options received. Exiting gamePlay.");
            return req.setOperationType(Request.OperationType.QUIT); // Exit gameplay gracefully
        }

        String[] menuParts = menuOptions.split("\n\n"); // Split by double newline
        if (menuParts.length < 3) {
            System.out.println("Invalid menu options received. Exiting gamePlay.");
            return req.setOperationType(Request.OperationType.QUIT);
        }

        String rowMenu = menuParts[0];
        String columnMenu = menuParts[1];
        String valueMenu = menuParts[2];

        int row = 0, column = 0, value = 0;

        try {
            // Prompt for row
            System.out.println(rowMenu);
            while (true) {
                System.out.print("Enter the row (1-9): ");
                row = Integer.parseInt(scanner.nextLine());
                if (row >= 1 && row <= 9) break;
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
            }

            // Prompt for column
            System.out.println(columnMenu);
            while (true) {
                System.out.print("Enter the column (1-9): ");
                column = Integer.parseInt(scanner.nextLine());
                if (column >= 1 && column <= 9) break;
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
            }

            // Prompt for value
            System.out.println(valueMenu);
            while (true) {
                System.out.print("Enter the value (1-9): ");
                value = Integer.parseInt(scanner.nextLine());
                if (value >= 1 && value <= 9) break;
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input format. Returning to main menu.");
            return req.setOperationType(Request.OperationType.QUIT);
        }

        // Build and return the valid UPDATE request
        return req.setOperationType(Request.OperationType.UPDATE)
                .setRow(row)
                .setColumn(column)
                .setValue(value);
    }

    static int[] boardSelectionUpdate() throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        int[] coordinates = new int[3];

        System.out.print("Enter row (1-9): ");
        coordinates[0] = getInputAsInt(stdin, 1, 9);

        System.out.print("Enter column (1-9): ");
        coordinates[1] = getInputAsInt(stdin, 1, 9);

        System.out.print("Enter value (1-9): ");
        coordinates[2] = getInputAsInt(stdin, 1, 9);

        return coordinates;
    }

    static int getInputAsInt(BufferedReader stdin, int min, int max) throws IOException {
        while (true) {
            String input = stdin.readLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.print("Invalid input. Enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("That's not a valid number. Try again: ");
            }
        }
    }

//    static Request.Builder handleErrorResponse(Response response) throws Exception {
//        switch (response.getNext()) {
//            case 1:
//                return nameRequest(); // Back to name request
//            case 2:
//                return chooseMenu(Request.newBuilder(), response); // Back to main menu
//            case 3:
//                return gameMenu(Request.newBuilder(), response); // Back to game menu
//            default:
//                System.out.println("Unhandled error type. Returning to main menu.");
//                return chooseMenu(Request.newBuilder(), response); // Default to main menu
//        }
//    }

//    static void handleGamePlay(InputStream in, OutputStream out) throws Exception {
//        Response response;
//        Request.Builder req = Request.newBuilder();
//
//        while (true) {
//            response = Response.parseDelimitedFrom(in);
//            System.out.println("Response: " + response);
//
//            switch (response.getResponseType()) {
//                case PLAY:
//                    System.out.println("Current Board:");
//                    System.out.println(response.getBoard());
//                    System.out.println("Points: " + response.getPoints());
//                    req = gameMenu(Request.newBuilder(), response);
//                    break;
//
//                case WON:
//                    System.out.println("Congratulations! You won the game!");
//                    System.out.println(response.getBoard());
//                    System.out.println("Final Points: " + response.getPoints());
//                    return;
//
//                case ERROR:
//                    System.out.println("Error: " + response.getMessage());
//                    if (response.getNext() == 2) { // Back to main menu
//                        req = chooseMenu(Request.newBuilder(), response);
//                    } else if (response.getNext() == 3) { // Back to game menu
//                        req = gameMenu(Request.newBuilder(), response);
//                    }
//                    break;
//
//                case BYE:
//                    System.out.println(response.getMessage());
//                    return;
//
//                default:
//                    System.out.println("Unhandled response type: " + response.getResponseType());
//                    break;
//            }
//
//            req.build().writeDelimitedTo(out);
//        }
//    }




}
