/*
file name:      BoardTests.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: Tests the behavior of the Board class.
*/
public class BoardTests {

	public static void main(String args[]) {

		// case 1: Board() and toString()
		{
			// set up
			Board newBoard = new Board();
			String expectedOutput = "0 0 0 | 0 0 0 | 0 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"- - - - - - - - - - - \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"- - - - - - - - - - - \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n";

			// verify
			System.out.println(newBoard.toString() + "== \n" + expectedOutput);

			// test
			assert newBoard.toString().equals(expectedOutput) : "Error in Board::toString()";
		}

		// case 2: read()
		{
			// set up
			Board newBoard2 = new Board();
			newBoard2.read("board1.txt");

			// verify
			System.out.println(newBoard2 + " == " + "0 0 0 | 3 0 1 | 0 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"9 0 0 | 0 0 0 | 0 0 0 \n" +
					"- - - - - - - - - - - \n" +
					"0 0 0 | 0 0 0 | 9 0 0 \n" +
					"0 0 6 | 0 0 0 | 0 0 7 \n" +
					"2 0 0 | 0 0 0 | 0 0 0 \n" +
					"- - - - - - - - - - - \n" +
					"0 0 0 | 0 5 0 | 0 0 0 \n" +
					"0 0 0 | 0 7 0 | 8 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n");

			// test
			assert newBoard2.toString().equals("0 0 0 | 3 0 1 | 0 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n" +
					"9 0 0 | 0 0 0 | 0 0 0 \n" +
					"- - - - - - - - - - - \n" +
					"0 0 0 | 0 0 0 | 9 0 0 \n" +
					"0 0 6 | 0 0 0 | 0 0 7 \n" +
					"2 0 0 | 0 0 0 | 0 0 0 \n" +
					"- - - - - - - - - - - \n" +
					"0 0 0 | 0 5 0 | 0 0 0 \n" +
					"0 0 0 | 0 7 0 | 8 0 0 \n" +
					"0 0 0 | 0 0 0 | 0 0 0 \n") : "Error in Board::read()";
		}

		// case 3: getCols(), getRows(), get()
		{
			// set up
			Board newBoard = new Board("board1.txt");

			// verify
			System.out.println(newBoard.getCols() + " == 9");
			System.out.println(newBoard.getRows() + " == 9");
			System.out.println(newBoard.get(0, 3) + " == 3");
			System.out.println(newBoard.get(2, 5) + " == 0");

			// assert
			assert newBoard.getCols() == 9 : "Error in Board::getCols()";
			assert newBoard.getRows() == 9 : "Error in Board::getRows()";
			assert newBoard.get(0, 3).getVal() == 3 : "Error in Board::get()";
			assert newBoard.get(2, 5).getVal() == 0 : "Error in Board::get()";
		}

		// case 4: set() methods
		{
			// set up
			Board newBoard = new Board();
			newBoard.set(1, 2, 3);
			newBoard.set(3, 4, 3, true);
			newBoard.set(2, 3, 2, false);

			// verify
			System.out.println(newBoard.get(1, 2).getVal() + " == 3");
			System.out.println(newBoard.get(3, 4).isLocked() + " == true");
			System.out.println(newBoard.get(3, 4).getVal() + " == 3");
			System.out.println(newBoard.get(2, 3).isLocked() + " == false");

			// test
			assert newBoard.get(1, 2).getVal() == 3 : "Error in Board::set(r, c, value)";
			assert newBoard.get(3, 4).isLocked() == true : "Error in Board::get(r, c, locked)";
			assert newBoard.get(3, 4).getVal() == 3 : "Error in Board::get(r, c, value, locked)";
			assert newBoard.get(2, 3).isLocked() == false : "Error in Board::get(r, c, value, locked)";
		}

		// case 5: value, isLocked(), numLocked()
		{
			// set up
			Board newBoard = new Board();
			newBoard.set(0, 0, 5, true);
			newBoard.set(2, 1, 2, true);
			newBoard.set(4, 3, 6, true);
			newBoard.set(6, 6, 1, true);

			// verify
			System.out.println(newBoard.value(0, 0) + " == 5");
			System.out.println(newBoard.value(2, 1) + " == 2");
			System.out.println(newBoard.value(4, 3) + " == 6");
			System.out.println(newBoard.value(6, 6) + " == 1");
			System.out.println(newBoard.numLocked() + " == 4");
			System.out.println(newBoard.get(0, 0).isLocked() + "== true");
			System.out.println(newBoard.get(2, 1).isLocked() + "== true");
			System.out.println(newBoard.get(4, 3).isLocked() + "== true");
			System.out.println(newBoard.get(6, 6).isLocked() + "== true");

			// test
			assert newBoard.value(0, 0) == 5 : "Error in Board::value()";
			assert newBoard.value(2, 1) == 2 : "Error in Board::value()";
			assert newBoard.value(4, 3) == 6 : "Error in Board::value()";
			assert newBoard.value(6, 6) == 1 : "Error in Board::value()";
			assert newBoard.get(0, 0).isLocked() == true : "Error in Board::value()";
			assert newBoard.get(2, 1).isLocked() == true : "Error in Board::value()";
			assert newBoard.get(4, 3).isLocked() == true : "Error in Board::value()";
			assert newBoard.get(6, 6).isLocked() == true : "Error in Board::value()";

		}

		// case 6: validValue()
		{
			// set up
			Board newBoard = new Board("board1.txt");
			boolean check1 = newBoard.validValue(0, 2, 9); // check block
			boolean check2 = newBoard.validValue(0, 2, 6); // check column
			boolean check3 = newBoard.validValue(0, 2, 3); // check row
			boolean check4 = newBoard.validValue(0, 2, 4); // valid value

			// verify
			System.out.println(check1 + " == false");
			System.out.println(check2 + " == false");
			System.out.println(check3 + " == false");
			System.out.println(check4 + " == true");

			// test
			assert check1 == false : "Error in Board::validValue()";
			assert check2 == false : "Error in Board::validValue()";
			assert check3 == false : "Error in Board::validValue()";
			assert check4 == true : "Error in Board::validValue()";
		}
		System.out.println("*** Done testing Board! ***");
	}
}