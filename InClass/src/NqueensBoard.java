/**
 * A class to store a chess board that optimizes an n-Queens problem.
 * 
 * @author Wayne.Brown
 * 
 * @version 1.0 - Spring 2013
 */

import java.util.Arrays;

// =====================================================================
//@formatter:off
public class NqueensBoard {

  private int       n;              // The chess board is n-by-n squares
  private int[]     rowColumn;      // The column of the queen in the jth row
  private boolean[] column_free;    // Is this "row/column" position a free
                                    // column?
  private boolean[] diagonal1_free; // Is this "row/column" diagonal free?
  private boolean[] diagonal2_free; // Is this "row/column" diagonal free?

  // -------------------------------------------------------------------
  // Create a board for the nQueens problem that has boardSize rows and 
  // boardSize columns.
  public NqueensBoard(int boardSize) {
    n = boardSize;
    rowColumn      = new int[n];
    column_free    = new boolean[n];
    diagonal1_free = new boolean[2 * n - 1];
    diagonal2_free = new boolean[2 * n - 1];

    Arrays.fill(rowColumn, -1);
    Arrays.fill(column_free, true);
    Arrays.fill(diagonal1_free, true);
    Arrays.fill(diagonal2_free, true);
  }

  // -------------------------------------------------------------------
  public int getSize() {
    return n;
  }
  // -------------------------------------------------------------------
  // Is the specified location on the board a valid place to put a new queen?
  // That is, does the position allow a new queen that can't be captured by any
  // of the other queens on the board?
  public boolean validPlacement(int row, int col) {
    return (column_free[col] && 
            diagonal1_free[row + col] && 
            diagonal2_free[row - col + n - 1]);
  }

  // -------------------------------------------------------------------
  public void placeQueen(int row, int col) {
    // Place a queen on the board.
    rowColumn[row] = col;  

    // Mark the associated column and diagonals as "used".
    column_free[col] = false;
    diagonal1_free[row + col] = false;
    diagonal2_free[row - col + n - 1] = false;
  }

  // -------------------------------------------------------------------
  public void removeQueen(int row, int col) {
    // Remove a queen from the board.
    rowColumn[row] = -1;  

    // undo the markings so another solution can be tried
    column_free[col] = true;
    diagonal1_free[row + col] = true;
    diagonal2_free[row - col + n - 1] = true;
  }

  // -------------------------------------------------------------------
  public boolean queenAt(int row, int col) {
    return (rowColumn[row] == col);
  }
  
  // -------------------------------------------------------------------
  // Create a string representation of the board.
  public String toString() {
    String divider = createDivider();
    String s = "";
    s += divider;
    for (int row = 0; row < n; row++) {
      s += '|';
      for (int col = 0; col < n; col++) {
        if (col == rowColumn[row]) {
          s += " Q |";
        } else {
          s += "   |";
        }
      }
      s += "\n" + divider;
    }
    return s;
  }

  // -------------------------------------------------------------------
  private String createDivider() {
    String s = "";
    for (int j = 0; j < n * 4 + 1; j++) {
      s += '-';
    }
    s += '\n';
    return s;
  }
}
