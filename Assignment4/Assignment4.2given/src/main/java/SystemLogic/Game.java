package SystemLogic;
import Entity.Level;

import java.util.*;

/**
 * Class: Game 
 * Description: Game class that can load an ascii image
 * Class can be used to hold the persistent state for a game for different threads
 * synchronization is not taken care of .
 * You can change this Class in any way you like or decide to not use it at all
 * I used this class in my SockBaseServer to create a new game and keep track of the current image evenon differnt threads. 
 * My threads each get a reference to this Game
 */

public class Game {

    /**
     * fullBoard: Solution with no empty spots
     * referenceBoard: the board with the initial empty spaces, used for clearing, determining if spot being cleared is
     *                  one that was generated with the board, etc.
     * playerBoard: the board directly modified by the player selecting spots
     * difficulty: how many empty cells in symmetry, if you notice the empty spots on the board the opposite
     *          grids are a mirror of themselves
     */
    private final int size = 9;

    private final char[][] solvedBoard =  new char[size][size]; // the solution
    private final char[][] referenceBoard =  new char[size][size]; // the given board to player at start
    private final char[][] playerBoard =  new char[size][size]; // current board player sees
    private int difficulty;

    private int points = 0;// if player win game they get point

    private boolean won; // if the game is won or not
  //  private Level currentLevel = new Level;

    public Game(){
        // you can of course add more or change this setup completely. You are totally free to also use just Strings in your Server class instead of this class
        won = true; // setting it to true, since then in newGame() a new image will be created
        difficulty = 0; // -> start at level one

    }

    /**
     * Sets the won flag to true
     * @param args Unused.
     * @return Nothing.
     */
    public void setWon(){
        won = true;
    }

    public boolean getWon(){
        return won;
    }

    /**
     * Method set the difficulty of our game which
     * than generate out board for user to use
     * @param difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Good to use for getting the first board of game
     * Method loads in a new image from the specified files and creates the hidden image for it.
     * @return Nothing.
     */
    public void newGame(boolean grading, int difficulty) {

        this.difficulty = difficulty;
        points = 0; // reset points
        if (won) {
            won = false;
            if (!grading) {
                create();
                prepareForPlay();
            } else {

                String[] inputData = {
                        "5631X94X2",
                        "17948X563",
                        "482563179",
                        "631794825",
                        "794825631",
                        "825631794",
                        "317948256",
                        "X48X56317",
                        "2X63X7948"
                };

                // Loop through the input data and load it into the fullBoard array
                for (int i = 0; i < size; i++) {
                    String line = inputData[i];  // Get the line from inputData

                    // Loop through each character in the line and populate the 2D array
                    for (int j = 0; j < size; j++) {
                        referenceBoard[i][j] = line.charAt(j);  // Assign each character
                        playerBoard[i][j] = line.charAt(j);
                    }
                }

                char[][] solvedBoard = {
                        {'5', '6', '3', '1', '7', '9', '4', '8', '2'},
                        {'1', '7', '9', '4', '8', '2', '5', '6', '3'},
                        {'4', '8', '2', '5', '6', '3', '1', '7', '9'},
                        {'6', '3', '1', '7', '9', '4', '8', '2', '5'},
                        {'7', '9', '4', '8', '2', '5', '6', '3', '1'},
                        {'8', '2', '5', '6', '3', '1', '7', '9', '4'},
                        {'3', '1', '7', '9', '4', '8', '2', '5', '6'},
                        {'9', '4', '8', '2', '5', '6', '3', '1', '7'},
                        {'2', '5', '6', '3', '1', '7', '9', '4', '8'}
                };


            }

        }

    }


    /**
     * Might be good to use when CLEAR and getting a new board
     * Method that creates a new board with given grading flag but same difficulty as was provided before
     * @return Nothing.
     */
    public void newBoard(boolean grading){
        newGame(grading, difficulty);
    }


    ////////////////////////
    // The next three methods are used in the game to create a new random board,
    // you should not need to touch or call them
    /**
     * Creates a completely new Sudoku board (should not need to be changed)
     * @return Nothing.
     */
    public void create() {

        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(numbers);

        List<Integer> positions = new ArrayList<>(Arrays.asList(0, 3, 6, 1, 4, 7, 2, 5, 8));

        List<Integer> rows = shuffle();
        List<Integer> columns = shuffle();

        for (int row = 0; row < rows.size(); row++) {
            List<Integer> newRow = new ArrayList<>();
            for (int col = 0; col < columns.size(); col++) {
                int position = (positions.get(row) + col) % numbers.size();
                newRow.add(numbers.get(position));
            }
            int i = 0;
            for (Integer num : newRow) {
                solvedBoard[row][i++] = (char) (num + '0');
            }
        }

        for (int row = 0; row < rows.size(); row++) {
            for (int col = 0; col < columns.size(); col++) {
                playerBoard[row][col] = solvedBoard[row][col];
                referenceBoard[row][col] = solvedBoard[row][col];
            }
        }
    }

    /**
     * Creates a completely new Sudoku board with Xs
     * @return Nothing.
     */
    private void prepareForPlay() {
        int empties = difficulty;

        int maxCells = (int) Math.ceil((double) (size * size) / 2);
        List<Integer> allCells = new ArrayList<>();
        for (int i = 0; i < maxCells; i++) {
            allCells.add(i);
        }

        Collections.shuffle(allCells);

        List<Integer> cells = allCells.subList(0, Math.min(empties, allCells.size()));

        for (Integer cell : cells) {
            int row = cell / size;
            int col = cell % size;

            playerBoard[row][col] = 'X';
            playerBoard[8 - row][8 - col] = 'X';

            referenceBoard[row][col] = 'X';
            referenceBoard[8 - row][8 - col] = 'X';
        }
    }

    /**
     * Creates a completely new Sudoku board (should not need to be changed)
     * @return Nothing.
     */
    private List<Integer> shuffle() {
        List<Integer> first = new ArrayList<>(Arrays.asList(0, 1, 2));
        List<Integer> second = new ArrayList<>(Arrays.asList(3, 4, 5));
        List<Integer> third = new ArrayList<>(Arrays.asList(6, 7, 8));

        Collections.shuffle(first);
        Collections.shuffle(second);
        Collections.shuffle(third);

        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(first);
        numbers.addAll(second);
        numbers.addAll(third);

        Collections.shuffle(numbers);

        return numbers;
    }

    /**
     * Good to use for an UPDATE call
     * Method changes the given row column with value if type is 0 and the move is valid.
     * If move is not valid it returns a number specifying what went wrong
     * 0 - board was changed - new number added or clear operation
     * 1 - tried to change given number
     * 2 - number was already in row so cannot be added
     * 3 - number was already in column so cannot be added
     * 4 - number was already in grid so cannot be added
     */
    public int updateBoard(int row, int column, int value, int type) {
        System.out.println("Game Class: updateBoard - row=" + row + ", column=" + column + ", value=" + value + ", type=" + type);

        int resultType = 0;

        switch (type) {
            case 0:  // Regular move (placing a value)
                if (referenceBoard[row][column] != 'X') {
                    System.out.println("Cannot modify a preset value at row=" + row + ", column=" + column);
                    resultType = 1; // Cannot modify preset values
                } else {
                    playerBoard[row][column] = (char) (value + '0');
                    referenceBoard[row][column] = (char) (value + '0'); // ✅ Update referenceBoard

                    int moveOK = checkMove(row, column);
                    if (moveOK == 0) { // Move is valid
                        won = checkWon();
                    } else { // Invalid move: revert change
                        playerBoard[row][column] = 'X';
                        referenceBoard[row][column] = 'X';
                        resultType = moveOK;
                    }
                }
                break;

            case 1: // Clear a single cell
                if (referenceBoard[row][column] != 'X') {
                    playerBoard[row][column] = 'X';  // ✅ Clear the cell
                } else {
                    resultType = 1; // Cannot clear preset values
                }
                break;

            case 2: // Clear an entire row
                for (int i = 0; i < size; i++) {
                    if (referenceBoard[row][i] != 'X') {
                        playerBoard[row][i] = 'X';
                    }
                }
                resultType = 2;
                break;

            case 3: // Clear an entire column
                for (int i = 0; i < size; i++) {
                    if (referenceBoard[i][column] != 'X') {
                        playerBoard[i][column] = 'X';
                    }
                }
                resultType = 3;
                break;

            case 4: // Clear a 3x3 grid
                int startRow = (row / 3) * 3;
                int startCol = (column / 3) * 3;

                for (int i = startRow; i < startRow + 3; i++) {
                    for (int j = startCol; j < startCol + 3; j++) {
                        if (referenceBoard[i][j] != 'X') {
                            playerBoard[i][j] = 'X';
                        }
                    }
                }
                resultType = 4;
                break;

            case 5: // Reset the board (new game)
                prepareForPlay();
                resultType = 5;
                break;

            default:
                System.out.println("Unknown clear type: " + type);
                resultType = 5; // Unknown error
                break;
        }

        System.out.println("Result Type: " + resultType);
        return resultType;
    }




    /** Might be useful in server
     * Method that checks if there is still an X on board, if so return false else true (basically checks if won)
     */
    public boolean checkWon() {
        for (int row = 0; row < playerBoard.length; row++) {
            for (int col = 0; col < playerBoard[row].length; col++) {
                if (playerBoard[row][col] == 'X') {
                    return false;
                }
            }
        }
        return true; //
    }

    /**
     * I never called this separatly from server
     * Checks if the move was valid for setting a number used in previous method
     */
    public int checkMove(int row, int col){
        if(isExistsInRow(row)){
            return 2;
        } else if (isExistsInCol(col)){
            return 3;
        } else if(isExistsInGrid(row, col)){
            return 4;
        } else { // X was replaced
            return 0;
        }
    }

    /**
     * Method checks if in the current board there is a duplicate number in the current grid and returns true if there is a duplicate
     */
    public boolean isExistsInGrid(int row, int col) {
        String currGrid = getGrid(getBoard(), row, col);
        int[] currentGridBucket = new int[10];
        for (int i = 0; i < 9; i++) {
            if (currGrid.charAt(i) == 'X') {
                continue;
            }
            int ind = Character.getNumericValue(currGrid.charAt(i));
            currentGridBucket[ind]++;
            if (currentGridBucket[ind] > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method checks if in the current board there is a duplicate number in the current column and returns true if there is a duplicate
     */
    public boolean isExistsInCol(int col) {
        String currCol = getColumn(getBoard(), col);
        int[] currentColumnBucket = new int[10];
        for (int i = 0; i < 9; i++) {
            if (currCol.charAt(i) == 'X') {
                continue;
            }
            int ind = Character.getNumericValue(currCol.charAt(i));
            currentColumnBucket[ind]++;
            if (currentColumnBucket[ind] > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method checks if in the current board there is a duplicate number in the current row and returns true if there is a duplicate
     */
    public boolean isExistsInRow(int row) {
        String currRow = getBoard().substring((row * 10), (row * 10) + 10);
        int[] currentRowBucket = new int[10];
        for (int i = 0; i < 9; i++) {
            if (currRow.charAt(i) == 'X') {
                continue;
            }
            int ind = Character.getNumericValue(currRow.charAt(i));
            currentRowBucket[ind]++;
            if (currentRowBucket[ind] > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all values in column
     */
    public String getColumn(String board, int col) {
        StringBuilder column = new StringBuilder();

        for (int row = 0; row < 9; row++) {
            int position = (row * 10) + col;
            column.append(board.charAt(position));
        }

        return column.toString();
    }

    /**
     * Gets all values in grid
     */
    public String getGrid(String board, int row, int col) {
        StringBuilder grid = new StringBuilder();
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int index = (i * 10) + j;
                grid.append(board.charAt(index));
            }
        }

        return grid.toString();
    }


    /**
     * Method returns the String of the current board
     * @return String of the current board
     */
    public String getBoard() {
        StringBuilder sb = new StringBuilder();
        for (char[] subArray : playerBoard) {
            sb.append(subArray);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * returns version of board to be shown on CLI, nicer way of seeing it and splitting it up
     */
    public String getDisplayBoard() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < playerBoard.length; row++) {
            if (row > 0 && row % 3 == 0) {
                sb.append("\n");
            }
            for (int col = 0; col < playerBoard.length; col++) {
                if (col > 0 && col % 3 == 0) {
                    sb.append(" ");
                }
                sb.append(playerBoard[row][col]).append(" ");
            }
            sb.append("\n");
        }

        return(sb.toString());
    }
    public int getPoints() {
        return points;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int setPoints(int diff) {
        return points += diff;
    }
}
