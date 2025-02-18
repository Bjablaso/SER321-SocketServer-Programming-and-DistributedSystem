## Sudoku Multiplayer - README

### Overview
This project is a multi-threaded Sudoku game that allows multiple clients to connect to a server, play Sudoku, update values, clear cells, and track their scores. The game includes leaderboard functionality, persistent tracking of player progress, and thread-safe operations for handling concurrent players.

### Features
- Multi-threaded server for handling multiple players.
- Sudoku puzzle generation and validation.
- Leaderboard tracking player scores and session persistence.
- Ability to make moves, clear numbers, rows, columns, grids, or reset the board.
- Graceful handling of client disconnections and game exits.
- Error handling for invalid moves and server-side validations.

### Protocol Overview
The game uses a client-server architecture where clients send requests to the server using structured message formats. Responses from the server include board updates, error messages, and leaderboard updates.

#### Request Format
Each request follows this structure:
```protobuf
message Request {
    enum OperationType {
        NAME = 0;
        LEADERBOARD = 1;
        START = 2;
        UPDATE = 3;
        CLEAR = 4;
        QUIT = 5;
    }
    OperationType operationType = 1;
    optional string name = 2;
    optional int32 row = 3;
    optional int32 column = 4;
    optional int32 value = 5;
    optional int32 type = 6;
}
```

#### Response Format
The server responds with structured messages as follows:
```protobuf
message Response {
    enum ResponseType {
        GREETING = 0;
        LEADERBOARD = 1;
        START = 2;
        PLAY = 3;
        WON = 4;
        ERROR = 5;
        BYE = 6;
    }
    ResponseType responseType = 1;
    optional string message = 2;
    optional string board = 3;
    optional int32 points = 4;
    optional string menuoptions = 5;
    optional int32 errorType = 6;
    optional int32 next = 7;
}
```

### How to Run the Game

#### Prerequisites
- Java Development Kit (JDK 8 or later)
- Gradle (for building and running the project)

#### Running the Server
1. Navigate to the project directory.
2. Compile and run the server using:
   ```sh
   ./gradlew runServer
   ```
   The server will start and wait for client connections.

#### Running the Client
1. Open a new terminal.
2. Run the client with:
   ```sh
   ./gradlew runClient --args="<server-ip> <port>"
   ```
   Replace `<server-ip>` with the server address (e.g., `localhost`) and `<port>` with the server’s port (default: `8000`).

### Game Controls
When the client starts, the following options are available:

#### Main Menu
- `1` - View Leaderboard
- `2` - Start Game (select difficulty from 1 to 20)
- `3` - Quit the game

#### In-Game Menu
- `m` - Make a move
- `c` - Clear a number, row, column, grid, or entire board
- `r` - Reset the board with a new puzzle

#### Making a Move
- Select a row (1-9)
- Select a column (1-9)
- Enter a value (1-9)

#### Clearing a Number
- Select one of the following:
    - `1` - Clear a single value
    - `2` - Clear a row
    - `3` - Clear a column
    - `4` - Clear a 3x3 grid
    - `5` - Reset the entire board

### Server Functionalities
#### `updateRequest` Method
- Validates moves based on Sudoku rules.
- Updates player scores for valid moves.
- Detects game completion and announces winners.

#### `clearRequest` Method
- Handles different types of clearing requests.
- Ensures that preset values remain unchanged.
- Updates the board and sends a response to clients.

#### `addPlayerPoint` Method
- Increases a player's score when they make a correct move.
- Updates the leaderboard accordingly.

#### `writeToLog` Method
- Persists player progress and leaderboard data across sessions.

#### `startGame` Method
- Manages new game sessions and initializes the Sudoku board.
- Handles unexpected client disconnections to maintain game stability.

### Error Handling
The server handles the following errors:
- Invalid moves (conflicts in row, column, or grid).
- Attempting to modify preset values.
- Client disconnection handling.
- Invalid input handling in client requests.

### GitHub Links 
> (Git Hub) https://github.com/Bjablaso/ser321-spring25-A-Bjablaso/tree/main/Assignment4
-> (YouTube) https://youtu.be/ynoWl9YY5ss

### Conclusion
This project demonstrates a fully functional multi-threaded Sudoku ga
me with client-server architecture, real-time updates, and persistent leaderboards. 
The implementation ensures concurrency control, structured communication, 
and an interactive gameplay experience.
