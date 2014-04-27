
/**
 This class represents a position on a two-dimensional space.

 @author William.Collins
 @author Randall.Bower
 */
public class Position {

  private int row, column;

  /**
   Initializes this Position object to (0, 0).
   */
  public Position() {
    this(0, 0);
  }

  /**
   Initializes this Position object to (row, column).

   @param row the row this Position object has been initialized to.
   @param column the column this Position object has been initialized
   to.
   */
  public Position(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   Determines the row of this Position object.

   @return the row of this Position object.
   */
  public int getRow() {
    return row;
  }

  /**
   Determines the column of this Position object.

   @return the column of this Position object.
   */
  public int getColumn() {
    return column;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Position) {
      Position that = (Position) obj;
      return this.row == that.row && this.column == that.column;
    }
    return false;
  }

  @Override
  public String toString() {
    return "[Row: " + (this.row + 1) + ", Column: " + (this.column + 1) + "]";
  }
}
