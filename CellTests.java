/*
file name:      CellTests.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: Test the Cell class
*/

public class CellTests {

	public static void main(String[] args) {
		Cell cell = new Cell(10, 5, 3, true);
		// Case 1: Test get row
		assert cell.getRow() == 10 : "Error in row";
		System.out.println(cell.getRow() + " == 10");
		// Case 2: Test get col
		assert cell.getCol() == 5 : "Error in col";
		System.out.println(cell.getCol() + " == 5");
		// Case 3: Test get value
		assert cell.getVal() == 3 : "Error in value";
		System.out.println(cell.getVal() + " == 3");
		// Case 4: Test is locked
		assert cell.isLocked() : "Error in locked";
		System.out.println(cell.isLocked() + " == true");

		// Case 5: Test set locked
		cell.setLocked(false);
		assert !cell.isLocked() : "Error in set locked";
		System.out.println(!cell.isLocked() + " == true");

		// Case 6: Test set value
		cell.setVal(8);
		assert cell.getVal() == 8 : "Error in set value";
		System.out.println(cell.getVal() + " == 8");

		// Case 7: Test to string
		cell.setVal(5);
		assert cell.toString().equals("5") : "Error in toString";
		System.out.println("String: " + cell.toString() + " == 5");

		System.out.println("Done Testing Cell");
	}

}
