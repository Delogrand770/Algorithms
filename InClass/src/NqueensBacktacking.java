
/**
 * Solve the n-Queens problem using backtracking - Use an optimized
 * representation of the board so that the placing of new queens on the board is
 * quick.
 *
 * @author
 *
 * @version 1.0 - Spring 2013
 */
// =====================================================================
public class NqueensBacktacking {

    private static int boardSize = 8;
    private static int numberSolutions;
    private static int numberNodes;
    private static int[] levelCounter;

    // -------------------------------------------------------------------
    public static void main(String[] args) {

        NqueensBoard board = new NqueensBoard(boardSize);

        numberSolutions = 0;
        numberNodes = 0;
        levelCounter = new int[boardSize + 1];
        nQueens(board, 0, 0);

        System.out.printf("There are %d solutions\n", numberSolutions);
        System.out.printf("There are %d nodes in the state space tree\n", numberNodes);
        System.out.println("\nNodes per level");
        for (int i = 0; i < levelCounter.length; i++) {
            System.out.printf("%d: %d\n", i, levelCounter[i]);
        }
    }

    // -------------------------------------------------------------------
    // The board has queens placed on rows 0-(row-1).
    // Try to place a queen on the board on this row.
    public static void nQueens(NqueensBoard board, int row, int level) {
        numberNodes++;
        levelCounter[level]++;
        if (row == boardSize) {
            numberSolutions++;
        } else {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.validPlacement(row, col)) {
                    board.placeQueen(row, col);
                    nQueens(board, row + 1, level + 1);
                    board.removeQueen(row, col);
                }
            }
        }
    }
}
