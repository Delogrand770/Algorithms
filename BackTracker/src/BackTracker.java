
import java.util.Iterator;

/**
 This class implements a recursive backtracking algorithm.

 @author William.Collins
 @author Randall.Bower
 */
public class BackTracker {

  private BackTrackable app;

  /**
   Initializes this BackTracker object from an application.

   @param app the application
   */
  public BackTracker(BackTrackable app) {
    this.app = app;
  }

  /**
   Attempts to reach a goal through a given position.

   @param pos the given position.

   @return true if the attempt succeeds; otherwise, false.
   */
  public boolean tryToReachGoal(Position pos) {
    //System.out.println( "Position (" + pos.getRow() + "," + pos.getColumn() + ")" );
    //System.out.println( app );

    if (app.isOK(pos)) {
      app.markAsPossible(pos);

      if (app.isGoal(pos)) {
        return true;
      }

      Iterator<Position> itr = app.iterator(pos);
      while (itr.hasNext()) {
        Position nextPos = itr.next();

        if (app.isOK(nextPos)) {
          if (tryToReachGoal(nextPos)) {
            return true;
          }
        }
      }

      //System.out.println( "Dead End (" + pos.getRow() + "," + pos.getColumn() + ")" );
      //System.out.println( app );
      app.markAsDeadEnd(pos);
    }

    return false;
  }
}
