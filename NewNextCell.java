/*
file name:      NewNextCell.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: New algorithm to find the next cell in the grid
*/
public class NewNextCell {
    private Board board;
    private LandscapeDisplay ld;
    private int numLocked;
    public static int noSolutionCount = 0;
    public static int solutionCount = 0;
    public static int totalTime = 0;

    // constructor
    public NewNextCell() {
        this(20);
    }

    public NewNextCell(int numLocked) {
        this.numLocked = numLocked;
        board = new Board(numLocked);
        ld = new LandscapeDisplay(board);
    }

    public void reset() {
        board = new Board(numLocked);
        ld.setScape(board);
    }

    public int findNextVal(int row, int col) {
        int currentVal = board.get(row, col).getVal();
        for (int i = currentVal + 1; i < 10; i++) {
            if (board.validValue(row, col, i)) {
                return i;
            }
        }
        return 0;
    }

    // find the number of possible values
    public int getPossibleValues(int row, int col) {
        int possible = 0;
        // loop through all values
        for (int i = 1; i < 10; i++) {
            if (board.validValue(row, col, i)) {
                possible++;
            }
        }
        return possible;
    }

    // find the next cell to go to and find an appropriate value for it
    public Cell findNextCell() {
        Cell chosen = null;
        int minPossible = 10;
        // loop through all cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell cell = board.get(i, j);
                // if empty cell
                if (cell.getVal() == 0) {
                    int possibleValues = 0;
                    // loop through all values
                    for (int k = 1; k <= 9; k++) {
                        if (board.validValue(i, j, k)) {
                            possibleValues++;
                        }
                    }
                    if (possibleValues == 0)
                        return null;
                    // if this cell has fewer possible values than current chosen cell
                    if (possibleValues < minPossible) {
                        chosen = cell;
                        minPossible = possibleValues;
                    }
                }
            }
        }
        // if a cell is not found
        if (chosen == null) {
            return null;
        } else {
            chosen.setVal(findNextVal(chosen.getRow(), chosen.getCol()));
            return chosen;
        }
    }

    // solve
    public boolean solve(int delay) throws InterruptedException {
        System.out.println("starting solve: " + board.numLocked());
        long start = System.currentTimeMillis();
        // allocate a stack, initially empty
        Stack<Cell> stack = new LinkedList<>();
        // while the stack size is less than the number of unspecified cells
        while (stack.size() < (81 - board.numLocked())) {
            // if (delay > 0)
            // Thread.sleep(delay);
            if (ld != null)
                ld.repaint();
            // create a cell called next by calling findNextCell
            Cell next = findNextCell();
            // while next is null and the stack is non-empty
            while (next == null && stack.size() > 0) {
                // pop a cell off the stack
                Cell tempCell = stack.pop();
                // call findNextVal on this Cell
                tempCell.setVal(findNextVal(tempCell.getRow(), tempCell.getCol()));
                // if the cell's value is no longer 0, set next to this popped cell
                if (tempCell.getVal() > 0) {
                    next = tempCell;
                }
            }
            // if next is still null
            if (next == null) {
                noSolutionCount++;
                // no solution
                board.finished = true;
                return board.finished;
            }
            // else
            else {
                // push next onto the stack
                stack.push(next);
            }
        }
        // return true as the board contains the solution
        board.finished = true;

        long end = System.currentTimeMillis();
        System.out.println("Number of initial cells: " + board.numLocked());
        System.out.println("Solved in " + (end - start) + " milliseconds");
        solutionCount++;
        totalTime += (end - start);
        return board.finished;
    }

    public static void main(String[] args) throws InterruptedException {
        totalTime = 0;
        int numRuns = 100;

        NewNextCell sudoku = new NewNextCell();
        for (int i = 0; i < numRuns; i++) {
            System.out.println(sudoku.solve(0));
            System.out.println("Run " + (i + 1) + " took " + totalTime + " milliseconds");
            sudoku.reset();
        }
        System.out.println("Solved: " + solutionCount);
        System.out.println("Failed: " + noSolutionCount);
        double averageTime = (double) totalTime / numRuns;
        String formattedTime = String.format("%.4f", averageTime);

        System.out.println("Average time taken to solve Sudoku puzzle: " + formattedTime + " milliseconds");
        // Sudoku sudoku = new Sudoku();
        // sudoku.solve(0);
    }
}
