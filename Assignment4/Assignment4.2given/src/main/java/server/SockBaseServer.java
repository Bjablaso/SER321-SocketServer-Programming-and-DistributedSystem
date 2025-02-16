package server;

import Entity.Player;
import Entity.Point;
import SystemLogic.Game;
import SystemLogic.ScoreBoard;
import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

class SockBaseServer implements Runnable {
    static String logFilename = "logs.txt";

    // Please use these as given so it works with our test cases
    static String menuOptions = "\nWhat would you like to do? \n 1 - to see the leader board \n 2 - to enter a game \n 3 - quit the game";
    static String gameOptions = "\nChoose an action: \n (1-9) - Enter an int to specify the row you want to update \n c - Clear number \n r - New Board";
    static String gameOption2 = "Choose an action: \n (1-9) - Enter an int to specify the column you want to update \n c - Clear number \n r - New Board ";
    static  String gameOption3 = "Choose an action: \n (1-9) - Enter an int to specify the value you want to update \n c - Clear number \n r - New Board";

    ServerSocket serv = null;
    InputStream in = null;
    OutputStream out = null;
    Socket clientSocket = null;
    private final int id; // client id

    // current game
    private static final ScoreBoard scoreBoard = new ScoreBoard();
    private Player player;


    Game game;
    private boolean inGame = false; // a game was started (you can decide if you want this
    private String name; // player name
    private int currentState =1; // I used something like this to keep track of where I am in the game, you can decide if you want that as well
    private static boolean grading = true; // if the grading board should be used

    public SockBaseServer(Socket sock, Game game, int id) {
        this.clientSocket = sock;
        this.game = game;
        this.id = id;
        try {
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        } catch (Exception e){
            System.out.println("Error in constructor: " + e);
        }
    }

    /**
     * Received a request, starts to evaluate what it is and handles it, not complete
     */
    @Override
    public void run() {
        try{
            startGame();

        }catch (Exception e){
            System.out.println("Client " + id + " could not connect to server: " + "\n\n"+ e.getMessage());
        }

    }
    public void startGame() throws IOException {
        try {
            while (true) {
                // Read the request
                Request op = Request.parseDelimitedFrom(in);
                System.out.println("Got request: " + op.toString());
                Response response;

                // Determine the type of operation
                switch (op.getOperationType()) {
                    case NAME:
                        if (op.getName().isBlank()) {
                            response = error(1, "Name is blank.");
                        } else {
                            response = nameRequest(op);
                        }
                        break;

                    case START:

                        response = startGame(op);
                        break;

                    case LEADERBOARD:
                        response = scoreBoard();
//
                        System.out.println("leader board");
                        break;

                    case UPDATE:
                        response = updateRequest(op);
                        break;

                    case CLEAR:
                        response = clearRequest(op);
                        break;

                    case QUIT:
                        response = quit();
                        return; // Exit the loop on quit

                    default:
                        response = error(2, "Unsupported operation.");
                        break;
                }

                // Send the response to the client
                response.writeDelimitedTo(out);
            }
        } catch (SocketException se) {
            System.out.println("Client disconnected.");
        } catch (Exception ex) {
            Response error = error(0, "Unexpected server error: " + ex.getMessage());
            error.writeDelimitedTo(out);
        } finally {
            System.out.println("Client ID " + id + " disconnected.");
            this.inGame = false;
            exitAndClose(in, out, clientSocket);
        }
    }


    void exitAndClose(InputStream in, OutputStream out, Socket serverSock) throws IOException {
        if (in != null)   in.close();
        if (out != null)  out.close();
        if (serverSock != null) serverSock.close();
    }

    /**
     * Handles the name request and returns the appropriate response
     * @return Request.Builder holding the reponse back to Client as specified in Protocol
     */
    private Response nameRequest(Request op) throws IOException {
        name = op.getName();

        synchronized (scoreBoard) {
            Player existingPlayer = scoreBoard.getRankedPlayers().stream()
                    .filter(player -> player.getName().equals(name))
                    .findFirst()
                    .orElse(null);

            if (existingPlayer == null) {
                player = new Player(name, 0);
                scoreBoard.addPlayer(player);
            } else {
                player = existingPlayer;
            }
        }


        writeToLog(name, Message.CONNECT);// com dak here
        currentState = 2;

        System.out.println("Got a connection and a name: " + name);
        return Response.newBuilder()
                .setResponseType(Response.ResponseType.GREETING)
                .setMessage("Hello " + name + " and welcome to a simple game of Sudoku.")
                .setMenuoptions(menuOptions)
                .setNext(currentState)
                .build();
    }

    /**
     * Starts to handle start of a game after START request, is not complete of course, just shows how to get to the board
     */
    private Response startGame(Request op) throws IOException {

        System.out.println("Processing START request...");

        int difficulty = op.getDifficulty(); // Read difficulty from the request

        // Validate difficulty (must be between 1 and 20)
        if (difficulty < 1 || difficulty > 20) {
            System.out.println("Invalid difficulty: " + difficulty);
            String message = "Error: difficulty is out of range";
            writeToLog(name, Message.valueOf(message));

            // Return an ERROR response for invalid difficulty
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.ERROR)
                    .setErrorType(5) // Error type 5: Difficulty is out of range
                    .setMessage(message)
                    .setMenuoptions(menuOptions) // Return to main menu
                    .setNext(2) // Main menu
                    .build();
        }
            // Start a new game with the given difficulty
            game.newGame(grading, difficulty);

        String gameMenu = gameOptions + "\n\n" + gameOption2 + "\n\n" + gameOption3;

            System.out.println("New game started with difficulty: " + difficulty);
            System.out.println(game.getDisplayBoard()); // Print the current board for debugging

            // Return a START response with the current board and game menu
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.PLAY)
                    .setBoard(game.getDisplayBoard()) // Send the game board to the client
                    .setMenuoptions(gameMenu) // In-game menu options
                    .setMessage("Game started successfully! Here's your board.")
                    .setNext(3) // In-game menu
                    .build();


    }

    /**
     * Handles the quit request, might need adaptation
     * @return Request.Builder holding the reponse back to Client as specified in Protocol
     */
    private  Response quit() throws IOException {
        this.inGame = false;
        return Response.newBuilder()
                .setResponseType(Response.ResponseType.BYE)
                .setMessage("Thank you for playing! goodbye.")
                .build();
    }

    /**
     * Start of handling errors, not fully done
     * @return Request.Builder holding the reponse back to Client as specified in Protocol
     */
    private Response error(int err, String field) throws IOException {
        String message = "";
        int type = err;
        Response.Builder response = Response.newBuilder();

        switch (err) {
            case 1:
                message = "\nError: required field missing or empty";
                break;
            case 2:
                message = "\nError: request not supported";
                break;
            default:
                message = "\nError: cannot process your request";
                type = 0;
                break;
        }

        response
                .setResponseType(Response.ResponseType.ERROR)
                .setErrorType(type)
                .setMessage(message)
                .setNext(currentState)
                .build();

        return response.build();
    }
    
    /**
     * Writing a new entry to our log
     * @param name - Name of the person logging in
     * @param message - type Message from Protobuf which is the message to be written in the log (e.g. Connect) 
     * @return String of the new hidden image
     */
    public void writeToLog(String name, Message message) {
        try {
            // read old log file
            Logs.Builder logs = readLogFile();

            Date date = java.util.Calendar.getInstance().getTime();

            // we are writing a new log entry to our log
            // add a new log entry to the log list of the Protobuf object
            logs.addLog(date + ": " +  name + " - " + message);

            // open log file
            FileOutputStream output = new FileOutputStream(logFilename);
            Logs logsObj = logs.build();

            // write to log file
            logsObj.writeTo(output);
        } catch(Exception e) {
            System.out.println("Issue while trying to save");
        }
    }

    private Response scoreBoard() throws IOException {
        List<Player> rankedPlayers;

        synchronized (scoreBoard) { // Synchronize to ensure thread safety
            rankedPlayers = new ArrayList<>(scoreBoard.getRankedPlayers()); // Copy the list to avoid holding the lock
        }

        Response.Builder responseBuilder = Response.newBuilder()
                .setResponseType(Response.ResponseType.LEADERBOARD)
                .setMenuoptions(menuOptions)
                .setNext(2);

        if (rankedPlayers.isEmpty()) {
            // Return an empty scoreboard
            Response.Entry title = Response.Entry.newBuilder()
                    .setName("Player")
                    .setPoints(0)
                    .build();
            responseBuilder.addLeader(title);
        }else {
            for (Player p : rankedPlayers) {
                Response.Entry entry = Response.Entry.newBuilder()
                        .setName(p.getName())
                        .setPoints(p.getPoint())
                        .setLogins(p.getWins())
                        .build();
                responseBuilder.addLeader(entry);
            }
        }

        return responseBuilder.build();
    }

private Response updateRequest(Request op) throws IOException {

    int row = op.getRow() - 1;
    int col = op.getColumn() - 1;
    int value = op.getValue();

    // Log the incoming request
    System.out.println("Update Request: row=" + (row + 1) + ", col=" + (col + 1) + ", value=" + value);

    // Validate and update the board
    int resultType = game.updateBoard(row, col, value, 0);

    // Log the result
    System.out.println("Result Type: " + resultType);
    System.out.println("Player Board After Update:\n" + game.getDisplayBoard());

    String gameMenu = gameOptions + "\n\n" + gameOption2 + "\n\n" + gameOption3;

    switch (resultType) {
        case 0: // Successful move
            if (game.checkWon()) {

                addPlayerPoint(game.getDifficulty());


                writeToLog(name, Message.WIN);// Log the win

                writeToLog(name, Message.WIN); // Log the win
                return Response.newBuilder()
                        .setResponseType(Response.ResponseType.WON)
                        .setBoard(game.getDisplayBoard()) // Updated board
                        .setMessage("Congratulations! You solved the puzzle!")
                        .setPoints(game.getPoints()) // Final points
                        .setMenuoptions(menuOptions) // Back to main menu
                        .setNext(2) // Main menu
                        .build();
            }

            // If game not won, continue playing
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.PLAY)
                    .setBoard(game.getDisplayBoard()) // Updated board
                    .setPoints(game.getPoints()) // Current points
                    .setMenuoptions(gameMenu) // Stay in game menu
                    .setNext(3) // Game menu
                    .build();

        case 1: // Tried to change a preset value
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.ERROR)
                    .setErrorType(1)
                    .setMessage("Cannot change preset value.")
                    .setBoard(game.getDisplayBoard()) // Send board for reference
                    .setMenuoptions(gameMenu) // Stay in game menu
                    .setNext(3)
                    .build();

        case 2: // Duplicate in row
        case 3: // Duplicate in column
        case 4: // Duplicate in grid
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.PLAY)
                    .setErrorType(resultType)
                    .setMessage("Invalid move: conflict detected in row/column/grid.")
                    .setBoard(game.getDisplayBoard()) // Send current board
                    .setMenuoptions(gameMenu) // Stay in game menu
                    .setNext(3)
                    .build();

        default: // Unexpected error
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.ERROR)
                    .setErrorType(0)
                    .setMessage("Unexpected error occurred.")
                    .setBoard(game.getDisplayBoard())
                    .setMenuoptions(gameOptions)
                    .setNext(3)
                    .build();
    }
}

    private void addPlayerPoint(int difficulty) {
        // Update points directly on the player object
        if (difficulty <= 10) {
            game.setPoints(Point.LEVELEASY.getPoints()); // Add points for winning
            player.addScore(Point.LEVELEASY.getPoints());
        } else if (difficulty <= 15) {
            game.setPoints(Point.LEVELMEDIUM.getPoints()); // Add points for winning
            player.addScore(Point.LEVELMEDIUM.getPoints());
        } else {
            game.setPoints(Point.LEVELHARD.getPoints()); // Add points for winning
            player.addScore(Point.LEVELHARD.getPoints());
        }

    }


    private Response clearRequest(Request op) throws IOException {
        int row = op.getRow();
        int col = op.getColumn();
        int value = op.getValue();

        // Use the updateBoard method to handle clearing logic
        int resultType = game.updateBoard(row, col, value, value); // `value` specifies the type of clear operation

        if (resultType == 0) {
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.PLAY)
                    .setBoard(game.getDisplayBoard())
                    .setPoints(game.getPoints())
                    .setMenuoptions(gameOptions)
                    .setNext(3)
                    .build();
        } else {
            return Response.newBuilder()
                    .setResponseType(Response.ResponseType.ERROR)
                    .setErrorType(resultType)
                    .setMessage("Error while clearing the board.")
                    .setBoard(game.getDisplayBoard())
                    .setMenuoptions(gameOptions)
                    .setNext(3)
                    .build();
        }
    }







    /**
     * Reading the current log file
     * @return Logs.Builder a builder of a logs entry from protobuf
     */
    public Logs.Builder readLogFile() throws Exception {
        Logs.Builder logs = Logs.newBuilder();

        try {
            return logs.mergeFrom(new FileInputStream(logFilename));
        } catch (FileNotFoundException e) {
            System.out.println(logFilename + ": File not found.  Creating a new file.");
            return logs;
        }
    }


    public static void main (String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Expected arguments: <port(int)> <delay(int)>");
            System.exit(1);
        }
        int port = 8000; // default port
        grading = Boolean.parseBoolean(args[1]);
        Socket clientSocket = null;
        ServerSocket socket = null;

        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port|sleepDelay] must be an integer");
            System.exit(2);
        }
        try {
            socket = new ServerSocket(port);
            System.out.println("Server started..");
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
        int id = 1;
        while (true) {
            try {
                System.out.println("Threaded server waiting for connects on port " + port);
                clientSocket = socket.accept();
                System.out.println("Attempting to connect to client-" + id);
                Game game = new Game();
                Thread threadSudoku = new Thread(new SockBaseServer(clientSocket, game, id++));
                threadSudoku.start();
//                SockBaseServer server = new SockBaseServer(clientSocket, game, id++);
//                server.startGame();
            } catch (Exception e) {
                System.out.println("Error in accepting client connection.");
            }
        }
    }


}
