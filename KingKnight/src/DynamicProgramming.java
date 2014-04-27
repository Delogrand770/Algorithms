
/**
 *
 * @author C14Gavin.Delphia
 */
public class DynamicProgramming {

    public static int offsets[][] = {
        {-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
        {0, 1}, {1, -1}, {1, 0}, {1, 1},
        {-2, -1}, {-2, 1}, {-1, 2}, {1, 2},
        {2, -1}, {2, 1}, {-1, -2}, {1, -2}
    };

    public static void main(String[] args) {
        int[] start = {0, 0};
        int[] end = {1, 0};
        System.out.println(howMany(3, start, end, 1));

        int[] start2 = {0, 0};
        int[] end2 = {0, 0};
        System.out.println(howMany(3, start2, end2, 2));

        int[] start3 = {0, 0};
        int[] end3 = {0, 99};
        System.out.println(howMany(100, start3, end3, 50));

        int[] start4 = {0, 0};
        int[] end4 = {0, 9};
        for (int numMoves = 0; numMoves <= 19; numMoves++) {
            System.out.printf("%d  %d \n", numMoves, howMany(10, start4, end4, numMoves));
        }
    }

    public static long howMany(int boardSize, int[] start, int[] end, int movesAllowed) {
        int COL = 0;
        int ROW = 1;
        long[][] board = new long[boardSize][boardSize];
        long[][] old = new long[boardSize][boardSize];

        //Initialize the board and old to all zeros
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = 0;
                old[row][col] = 0;
            }
        }

        //Put starting piece on old
        board[start[ROW]][start[COL]] = 1;
        //printBoard(board);

        //For all the moves allowed
        for (int m = 0; m < movesAllowed; m++) {

            //Copy board into temp
            for (int i = 0; i < boardSize; i++) {
                System.arraycopy(board[i], 0, old[i], 0, boardSize);
            }

            //Zero out board
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    board[row][col] = 0;
                }
            }

            //Go through entire board
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {

                    //Apply all the offsets
                    for (int l = 0; l < offsets.length; l++) {
                        int newCol = col + offsets[l][COL];
                        int newRow = row + offsets[l][ROW];

                        //Verify the resulting move position is on the board
                        if (newCol >= 0 && newCol < boardSize && newRow >= 0 && newRow < boardSize) {
                            board[row][col] += old[newRow][newCol];
                        }
                    }

                }
            }
            //printBoard(board); //Uncomment to see the board after each iteration.
        }
        return board[end[ROW]][end[COL]];
    }

    public static void printBoard(long[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
