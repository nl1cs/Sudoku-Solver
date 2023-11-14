/*
file name:      Sudoku.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: Set the behavior of the Sudoku class.
*/
public class Sudoku {
    Board board;
    private LandscapeDisplay ld;
    private int numLocked;
    public static int noSolutionCount = 0;
    public static int solutionCount = 0;
    public static int totalTime = 0;

    // constructor
    public Sudoku() {
        this(10);
    }

    public Sudoku(int numLocked) {
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
        int numRuns = 1;

        Sudoku sudoku = new Sudoku();
        for (int i = 0; i < numRuns; i++) {
            System.out.println(sudoku.solve(0));
            System.out.println("Run " + (i + 1) + " took " + totalTime + " milliseconds");
            // sudoku.reset();
        }
        System.out.println("Solved: " + solutionCount);
        System.out.println("Failed: " + noSolutionCount);
        double averageTime = (double) totalTime / numRuns;
        String formattedTime = String.format("%.50f", averageTime);

        System.out.println("Average time taken to solve Sudoku puzzle: " + formattedTime + " milliseconds");
        // Sudoku sudoku = new Sudoku();
        // sudoku.solve(0);
    }
}
