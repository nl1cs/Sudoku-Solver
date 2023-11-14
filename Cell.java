
/*
file name:      Cell.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: Set the behavior of the Cell
*/
import java.awt.Graphics;
import java.awt.Color;

public class Cell {
    // fields
    private int row;
    private int col;
    private int value;
    private boolean locked;

    public Cell() {
        row = 0;
        col = 0;
        value = 0;
        locked = false;
    }

    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        locked = false;
    }

    public Cell(int row, int col, int value, boolean locked) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }

    // return the row index of this cell
    public int getRow() {
        return row;
    }

    // return the column index of the Cell
    public int getCol() {
        return col;
    }

    // return the value of the Cell
    public int getVal() {
        return value;
    }

    // set the value of the Cell
    public void setVal(int val) {
        value = val;
    }

    // return whether the Cell is locked
    public boolean isLocked() {
        return locked;
    }

    // set the value of the locked field
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    // toString method
    public String toString() {
        return value + "";
    }

    // draws cell value
    public void draw(Graphics g, int x, int y, int scale) {
        char toDraw = (char) ((int) '0' + getVal());
        g.setColor(isLocked() ? Color.RED : Color.GREEN);
        g.drawChars(new char[] { toDraw }, 0, 1, x, y);
    }
}
