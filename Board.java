
/*
file name:      Board.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: Set the behavior of the Board
*/
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class Board {
    private Cell[][] board; // set the board to be 2x2 array of cells
    public static final int SIZE = 9; // set size of the board
    public boolean finished; // set if the game is finished or not
    private int numLocked;

    // construct a board with 9x9 cells
    public Board() {
        board = new Cell[SIZE][SIZE];
        numLocked = 0;
        for (int i = 0; i < 9; i++) { // loop through 9x9 2D array and add cells to the board with values 0
            for (int j = 0; j < 9; j++) {
                board[i][j] = new Cell(i, j, 0, false);
            }
        }
        finished = false;
    }

    public Board(String file) { // read the board from a file
        board = new Cell[SIZE][SIZE];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new Cell(i, j, 0);
            }
        }
        read(file);
    }

    public Board(int lockedCell) { // create a board
        // Call the default constructor to initialize the empty board
        this();
        numLocked = lockedCell;

        // Create a new Random object to generate rand values
        Random rand = new Random();

        // Initialize the number of locked cells to 0
        int currentLockedCell = 0;

        // Loop until the desired number of locked cells is reached
        while (currentLockedCell < lockedCell) {

            // Generate rand row and column indices
            int randomRow = rand.nextInt(9); // create rand row
            int randomCol = rand.nextInt(9); // create rand column
            int randVal = rand.nextInt(9) + 1; // create rand value
            if (get(randomRow, randomCol).getVal() == 0 && validValue(randomRow, randomCol, randVal)) {
                set(randomRow, randomCol, randVal, true);
                currentLockedCell++;
            }
        }

        // Print the board for debugging purposes
        System.out.println(this);
    }

    // get all the empty cells in the board
    public ArrayList<Cell> getEmptyCells() {
        ArrayList<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell cell = get(i, j);
                // if find an empty cell
                if (cell.getVal() == 0) {
                    emptyCells.add(cell);
                    System.out.println(cell.getCol() + "," + cell.getRow());
                }
            }
        }
        return emptyCells;
    }

    // returns columns number
    public int getCols() {
        return SIZE;
    }

    // returns rows number
    public int getRows() {
        return SIZE;
    }

    // returns the Cell at the specified row and column
    public Cell get(int r, int c) {
        return board[r][c];
    }

    // returns whether the cell at the specified row and column is locked
    public boolean isLocked(int r, int c) {
        return board[r][c].isLocked();
    }

    // returns the number of locked Cells
    public int numLocked() {
        return numLocked;
    }

    // return the cell value at the specified row and column
    public int value(int r, int c) {
        return board[r][c].getVal();
    }

    // set cell value at the specified row and column
    public void set(int r, int c, int value) {
        board[r][c].setVal(value);
    }

    // set cell value at the specified row and column and lock the cell
    public void set(int r, int c, int value, boolean locked) {
        board[r][c].setVal(value);
        board[r][c].setLocked(locked);
    }

    // check whether the cell at the specified row and column is valid
    public boolean validValue(int row, int col, int value) {
        boolean valid = true;
        // Check if the value is in the range [1, 9]
        if (value < 1 || value > 9) {
            valid = false;
        }

        // Check if the value is unique in the row and column
        for (int i = 0; i < 9; i++) {
            if (board[row][i].getVal() == value && i != col) {
                valid = false;
            }
            if (board[i][col].getVal() == value && i != row) {
                valid = false;
            }
        }

        // Check if the value is unique in the 3x3 square
        int rowDiv = row / 3;
        int colDiv = col / 3;
        for (int i = rowDiv * 3; i < (rowDiv * 3 + 3); i++) {
            for (int j = colDiv * 3; j < (colDiv * 3 + 3); j++) {
                if (board[i][j].getVal() == value && (i != row || j != col)) {
                    valid = false;
                }
            }
        }

        // Return whether the value is valid
        return valid;

    }

    // Check if the board is solved
    public boolean validSolution() {
        boolean valid = true;
        // Iterate through every cell in the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Check if the cell's value is in range [1,9] and is valid
                if (board[i][j].getVal() < 1 || board[i][j].getVal() > 9
                        || !validValue(i, j, board[i][j].getVal())) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    // return number of empty cells
    public int emptyCell() {
        int emptyCell = 0; // initialize the count of empty cells to zero
        for (int i = 0; i < 9; i++) { // loop through each row
            for (int j = 0; j < 9; j++) { // loop through each column
                if (board[i][j].getVal() == 0) { // if the cell is empty (value = 0)
                    emptyCell++; // increment the count of empty cells
                }
            }
        }
        return emptyCell; // return the count of empty cells
    }

    // read files
    public boolean read(String filename) {
        try {
            // assign to a variable of type FileReader a new FileReader object, passing
            // filename to the constructor
            FileReader fr = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the
            // FileReader variable to the constructor
            BufferedReader br = new BufferedReader(fr);
            // assign to a variable of type String line the result of calling the readLine
            // method of your BufferedReader object.
            String line = br.readLine();
            // start a while loop that loops while line isn't null
            int currentRow = 0;
            System.out.println(board.toString());
            while (line != null) {
                // print line
                System.out.println(line);
                // assign to an array of Strings the result of splitting the line up by spaces
                // (line.split("[ ]+"))
                String[] array = line.split("[ ]+");
                // print the size of the String array (you can use .length)
                System.out.println(array.length);
                // use the line to set various Cells of this Board accordingly
                for (int i = 0; i < array.length; i++) {
                    board[currentRow][i].setVal(Integer.parseInt(array[i]));
                    if (value(currentRow, i) > 0) {
                        numLocked++;
                        get(currentRow, i).setLocked(true);
                    }
                }
                currentRow++;
                // assign to line the result of calling the readLine method of your
                // BufferedReader object
                line = br.readLine();
            }
            // call the close method of the BufferedReader
            br.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }
        return false;
    }

    // return String representation
    public String toString() {
        String stringBoard = new String();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBoard += board[i][j] + " ";
                if (j == 2 || j == 5) {
                    stringBoard += "| ";
                }
                if (j == 8) {
                    stringBoard += "\n";
                }
            }
            if (i == 2 || i == 5) {
                for (int k = 0; k < 11; k++) {
                    stringBoard += "- ";
                }
                stringBoard += "\n";
            }
        }
        return stringBoard;
    }

    // draw the game

    public void draw(Graphics g, int scale) {
        for (int i = 0; i < 9; i++) { // Loop through each row of the board
            for (int j = 0; j < 9; j++) { // Loop through each column of the board
                board[i][j].draw(g, j * scale + 5, i * scale + 10, scale); // Call the draw method of each Cell object
                                                                           // with the appropriate coordinates and
                                                                           // scaling
                if (i == 3 | i == 6) { // Check if we're at a horizontal line that separates a 3x3 block
                    g.drawLine(i * scale, 0, i * scale, (9 * scale - 10)); // Draw a line to separate the blocks
                }
                if (j == 3 | j == 6) { // Check if we're at a vertical line that separates a 3x3 block
                    g.drawLine(0, j * scale - 10, (9 * scale - 10), j * scale - 10); // Draw a line to separate the
                                                                                     // blocks
                }
            }
        }
        if (finished) { // If the board is finished (all cells are filled)
            if (validSolution()) { // Check if the solution is valid
                g.setColor(new Color(0, 127, 0)); // Set the color to green
                g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale * 3 + 5, scale * 10 + 10); // Display
                                                                                                             // a
                                                                                                             // message
                                                                                                             // indicating
                                                                                                             // a
                                                                                                             // successful
                                                                                                             // solution
            } else {
                g.setColor(new Color(127, 0, 0)); // Set the color to red
                g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale * 3 + 5, scale * 10 + 10); // Display
                                                                                                                       // a
                                                                                                                       // message
                                                                                                                       // indicating
                                                                                                                       // an
                                                                                                                       // invalid
                                                                                                                       // solution
            }
        }
    }
}
