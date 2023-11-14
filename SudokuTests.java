/*
file name:      SudokuTests.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: Tests the behavior of the Sudoku class.
*/
public class SudokuTests {
    public static void sudokuTests() throws InterruptedException {
        // case 1: test constructor
        {
            Sudoku s1 = new Sudoku();
            // verify
            assert s1 != null : "Error in Sudoku() constructor";
        }

        // case 2: test findNextVal(int row, int col)
        {
            // set up
            Sudoku s2 = new Sudoku();
            s2.board.set(0, 0, 9);
            s2.board.set(0, 2, 3);
            s2.board.set(0, 3, 1);
            s2.board.set(0, 5, 5);
            s2.board.set(0, 7, 2);
            s2.board.set(1, 0, 5);
            s2.board.set(1, 2, 8);
            s2.board.set(1, 6, 7);
            s2.board.set(2, 0, 1);
            s2.board.set(2, 4, 4);
            s2.board.set(2, 8, 6);
            s2.board.set(3, 1, 6);
            s2.board.set(3, 2, 4);
            s2.board.set(3, 4, 7);
            s2.board.set(3, 6, 1);
            s2.board.set(4, 3, 6);
            s2.board.set(4, 4, 3);
            s2.board.set(4, 5, 4);
            s2.board.set(5, 2, 2);
            s2.board.set(5, 4, 6);
            s2.board.set(5, 6, 9);
            s2.board.set(6, 0, 2);
            s2.board.set(6, 4, 5);
            s2.board.set(6, 8, 7);
            s2.board.set(7, 2, 7);
            s2.board.set(7, 6, 5);
            s2.board.set(8, 1, 8);
            s2.board.set(8, 3, 9);
            s2.board.set(8, 5, 6);
            s2.board.set(8, 6, 3);

            // verify
            System.out.println(s2.findNextVal(0, 0));
            System.out.println(s2.findNextVal(1, 2));
            System.out.println(s2.findNextVal(3, 4));

            assert s2.findNextVal(0, 0) == 0 : "Error in findNextVal(int row, int col)";
            assert s2.findNextVal(1, 2) == 2 : "Error in findNextVal(int row, int col)";
            assert s2.findNextVal(3, 4) == 1 : "Error in findNextVal(int row, int col)";
        }

        // case 3: test findNextCell()
        {
            // set up
            Sudoku s3 = new Sudoku();
            s3.board.set(0, 0, 1);
            s3.board.set(0, 1, 2);
            // verify
            assert s3.findNextCell().getRow() == 0 && s3.findNextCell().getCol() == 2 : "Error in findNextCell()";
        }

        // case 4: test solve(int delay)
        {
            // set up
            Sudoku s4 = new Sudoku();
            s4.board.set(0, 0, 5);
            s4.board.set(0, 1, 3);
            s4.board.set(0, 4, 7);
            s4.board.set(1, 0, 6);
            s4.board.set(1, 3, 1);
            s4.board.set(1, 4, 9);
            s4.board.set(1, 5, 5);
            s4.board.set(2, 1, 9);
            s4.board.set(2, 2, 8);
            s4.board.set(2, 7, 6);
            s4.board.set(3, 0, 8);
            s4.board.set(3, 4, 6);
            s4.board.set(3, 8, 3);
            s4.board.set(4, 0, 4);
            s4.board.set(4, 3, 8);
            s4.board.set(4, 5, 3);
            s4.board.set(4, 8, 1);
            s4.board.set(5, 0, 7);
            s4.board.set(5, 4, 2);
            s4.board.set(5, 8, 6);
            s4.board.set(6, 1, 6);
            s4.board.set(6, 6, 2);
            s4.board.set(6, 7, 8);
            s4.board.set(7, 3, 4);
            s4.board.set(7, 4, 1);
            s4.board.set(7, 5, 9);
            s4.board.set(7, 8, 5);
            s4.board.set(8, 4, 8);
            s4.board.set(8, 7, 7);
            s4.board.set(8, 8, 9);
            // verify
            assert s4.solve(0) == true : "Error in solve()";
        }
        System.out.println("Finish testing");
    }

    public static void main(String[] args) throws InterruptedException {
        sudokuTests();
    }
}
