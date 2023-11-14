
/*
file name:      Guaranteed.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: Create a Guaranteed board that will always be solvable.
*/
import java.util.Random;

public class Guaranteed {
    private Board board; // Declare a private Board object called board
    private LandscapeDisplay ld; // Declare a private LandscapeDisplay object called ld
    private static int totalTime = 0;

    // Constructor with no arguments
    public Guaranteed() {
        board = new Board(1); // Initialize board to a new Board object with 1 locked cell
        ld = new LandscapeDisplay(board); // Initialize ld to a new LandscapeDisplay object with board as its argument
    }

    // Constructor with one integer argument
    public Guaranteed(int lockedCell) {
        // Create a board with 1 locked cell using the no-argument constructor
        this();
        // Call solve() to solve the board
        solve();
        // Remove values from the board randomly until there are a specified number of
        // locked cells
        Random random = new Random(); // Declare a new Random object called random
        while (board.emptyCell() < (81 - lockedCell)) { // Loop while the number of empty cells on the board is less
                                                        // than the difference between the total number of cells and the
                                                        // specified number of locked cells
            int randRow = random.nextInt(9); // Generate a random integer between 0 and 8 (inclusive) and store it in
                                             // randRow
            int randCol = random.nextInt(9); // Generate a random integer between 0 and 8 (inclusive) and store it in
                                             // randCol
            // Check if the cell at (randRow, randCol) is not locked
            if (!board.get(randRow, randCol).isLocked()) {
                board.set(randRow, randCol, 0); // Set the value of the cell at (randRow, randCol) to 0
            }
        }
        // Lock all the remaining values on the board
        for (int i = 0; i < 9; i++) { // Loop through all the rows on the board
            for (int j = 0; j < 9; j++) { // Loop through all the columns on the board
                // Check if the cell at (i, j) has a non-zero value
                if (board.value(i, j) != 0) {
                    board.get(i, j).setLocked(true); // Set the cell at (i, j) to locked
                }
            }
        }
        // Print out the starting board and the number of locked cells
        System.out.println("Starting board: \n" + board);
        System.out.println("Number of initial cells: " + board.numLocked());
    }

    // Determine if there is a valid value for this cell that hasn't been tried
    public int findNextVal(int row, int col) {
        int value = 0; // Declare an integer called value and initialize it to 0
        // Loop through all the values bigger than the current value of the cell
        for (int i = board.get(row, col).getVal() + 1; i < 10; i++) {
            // Check if this value is valid for the cell at (row, col)
            if (board.validValue(row, col, i)) {
                // If the value is valid, set value to i and exit the loop
                value = i;
                break;
            }
        }
        // Return the value found
        return value;
    }

    // Find the next cell to go to and find an appropriate value for it
    public Cell findNextCell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell cell = board.get(i, j);
                if (cell.getVal() == 0) {
                    cell.setVal(findNextVal(cell.getRow(), cell.getCol()));
                    if (cell.getVal() > 0) {
                        return cell;
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    // solve
    public boolean solve() {
        Stack<Cell> stack = new LinkedList<>(); // create a new stack of cells
        while (stack.size() < (81 - board.numLocked())) { // while the stack size is less than the number of unspecified
                                                          // cells
            Cell next = findNextCell(); // get the next empty cell
            while (next == null && stack.size() > 0) { // while there are no more empty cells and the stack is not empty
                Cell tempCell = stack.pop(); // get the last cell from the stack
                tempCell.setVal(findNextVal(tempCell.getRow(), tempCell.getCol())); // try the next value for that cell
                if (tempCell.getVal() > 0) { // if the value is not 0, meaning a valid value was found
                    next = tempCell; // set the next empty cell to be the last one tried
                }
            }
            if (next == null) { // if there are no more empty cells
                board.finished = true; // set the board to be finished
                return board.finished; // return true to indicate that a solution was not found
            } else { // if there is still an empty cell
                stack.push(next); // push it onto the stack
            }
        }
        board.finished = true; // set the board to be finished
        return board.finished; // return true to indicate that a solution was found
    }

    public boolean solve(int delay) throws InterruptedException {
        long start = System.currentTimeMillis();
        Stack<Cell> stack = new LinkedList<>(); // create a new stack of cells
        while (stack.size() < (81 - board.numLocked())) { // while the stack size is less than the number of unspecified
                                                          // cells
            if (delay > 0) // if there is a delay specified
                Thread.sleep(delay); // wait for the specified amount of time
            if (ld != null) // if there is a display object
                ld.repaint(); // repaint the display
            Cell next = findNextCell(); // get the next empty cell
            while (next == null && stack.size() > 0) { // while there are no more empty cells and the stack is not empty
                Cell tempCell = stack.pop(); // get the last cell from the stack
                tempCell.setVal(findNextVal(tempCell.getRow(), tempCell.getCol())); // try the next value for that cell
                if (tempCell.getVal() > 0) { // if the value is not 0, meaning a valid value was found
                    next = tempCell; // set the next empty cell to be the last one tried
                }
            }
            if (next == null) { // if there are no more empty cells
                board.finished = true; // set the board to be finished
                return board.finished; // return true to indicate that a solution was not found
            } else { // if there is still an empty cell
                stack.push(next); // push it onto the stack
            }
        }

        board.finished = true; // set the board to be finished
        long end = System.currentTimeMillis();
        totalTime += (end - start);
        System.out.println("Solved in " + (end - start) + " milliseconds");
        return board.finished; // return true to indicate that a solution was found
    }

    public static void main(String[] args) throws InterruptedException {
        totalTime = 0;
        int numRuns = 10000;

        NewNextCell sudoku = new NewNextCell();
        for (int i = 0; i < numRuns; i++) {
            System.out.println(sudoku.solve(0));
            System.out.println("Run " + (i + 1) + " took " + totalTime + " milliseconds");
            sudoku.reset();
        }
        double averageTime = (double) totalTime / numRuns;
        System.out.println("Average time taken to solve Sudoku puzzle: " + averageTime + " milliseconds");
    }
}