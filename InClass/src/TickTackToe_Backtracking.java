/* A demonstration of Backtracking to find all possible games of Tic Tack Toe.
 * By: Dr. Wayne Brown, Spring 2013
 */

// =====================================================================
public class TickTackToe_Backtracking {

    private static int boardSize = 3;
    private static int numberGames;
    private static int numberXwins;
    private static int numberOwins;
    private static int numberCATwins;
    private static int numberNodes;
    private static int[] levelCounter;

    // -------------------------------------------------------------------
    public static void main(String[] args) {

        // Create the Tick Tack Toe game.
        TickTackToe game = new TickTackToe(boardSize);

        // Initialize the counters that will track the number of games and wins.
        numberGames = 0;
        numberXwins = 0;
        numberOwins = 0;
        numberCATwins = 0;
        numberNodes = 0;
        levelCounter = new int[boardSize * boardSize + 1]; //The size here is questionable

        // Play all possible Tick Tack Toe games using backtracking
        playAllTicTackToeGames(game, TickTackToe.X, 0);

        // Display the results.
        System.out.printf("Total number of possible games               : %9d\n",
                numberGames);
        System.out.printf("Total games that X won (the starting player) : %9d %4.2f%%\n",
                numberXwins, ((double) numberXwins / (double) numberGames));
        System.out.printf("Total games that O won                       : %9d %4.2f%%\n",
                numberOwins, ((double) numberOwins / (double) numberGames));
        System.out.printf("Total games that CAT won                     : %9d %4.2f%%\n",
                numberCATwins, ((double) numberCATwins / (double) numberGames));
        System.out.printf("Total state space tree nodes                 : %9d\n",
                numberNodes);
        System.out.println("\nNodes per level");
        for (int i = 0; i < levelCounter.length; i++) {
            System.out.printf("%d: %d\n", i, levelCounter[i]);
        }
    }

    // -------------------------------------------------------------------
    // Given the game, play the player at every legal position of the board.
    public static void playAllTicTackToeGames(TickTackToe game, char player, int level) {
        numberNodes++;
        levelCounter[level]++;
        char winner = game.whoWon();
        if (winner != TickTackToe.NO_ONE) {
            numberGames++;
            if (winner == TickTackToe.CAT) {
                numberCATwins++;
            } else if (winner == TickTackToe.X) {
                numberXwins++;
            } else if (winner == TickTackToe.O) {
                numberOwins++;
            }
        } else {
            //Try to play every possible position
            for (int i = 0; i < game.getSize(); i++) {
                for (int j = 0; j < game.getSize(); j++) {
                    if (game.getPlayerAt(i, j) == TickTackToe.EMPTY) {
                        game.playAt(i, j, player);
                        playAllTicTackToeGames(game, game.getOpponent(player), level + 1);
                        game.removePlayAt(i, j);
                    }
                }
            }
        }
    }
}

// =====================================================================
class TickTackToe {

    public static char EMPTY = ' ';
    public static char X = 'X';
    public static char O = 'O';
    public static char CAT = 'C';
    public static char NO_ONE = 'N';
    private char[][] board;
    private int positionsTaken;

    // -------------------------------------------------------------------
    public TickTackToe(int boardSize) {
        board = new char[boardSize][boardSize];
        clearBoard();
    }

    // -------------------------------------------------------------------
    public int getSize() {
        return board.length;
    }

    // -------------------------------------------------------------------
    public void clearBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                board[row][col] = EMPTY;
            }
        }
        positionsTaken = 0;
    }

    // -------------------------------------------------------------------
    public char getOpponent(char player) {
        return (player == X) ? O : X;
    }

    // -------------------------------------------------------------------
    public char getPlayerAt(int row, int col) {
        return board[row][col];
    }

    // -------------------------------------------------------------------
    public void playAt(int row, int col, char player) {
        board[row][col] = player;
        positionsTaken++;
    }

    // -------------------------------------------------------------------
    public void removePlayAt(int row, int col) {
        board[row][col] = EMPTY;
        positionsTaken--;
    }

    // -------------------------------------------------------------------
    public boolean boardFull() {
        return (positionsTaken >= (board.length * board.length));
    }

    // -------------------------------------------------------------------
    public char whoWon() {
        // check rows
        for (int row = 0; row < board.length; row++) {
            char player = rowWins(row);
            if (player != EMPTY) {
                return player;
            }
        }

        // check columns
        for (int col = 0; col < board.length; col++) {
            char player = columnWins(col);
            if (player != EMPTY) {
                return player;
            }
        }

        // check diagonal
        char player = diagonalWins();
        if (player != EMPTY) {
            return player;
        }

        // check cross diagonal
        player = crossDiagonalWins();
        if (player != EMPTY) {
            return player;
        }

        if (boardFull()) {
            return CAT;
        } else {
            return NO_ONE;
        }
    }

    // -------------------------------------------------------------------
    private char rowWins(int row) {
        char player = board[row][0];
        for (int col = 1; col < board.length; col++) {
            if (board[row][col] != player) {
                return EMPTY;
            }
        }
        return player;
    }

    // -------------------------------------------------------------------
    private char columnWins(int col) {
        char player = board[0][col];
        for (int row = 1; row < board.length; row++) {
            if (board[row][col] != player) {
                return EMPTY;
            }
        }
        return player;
    }

    // -------------------------------------------------------------------
    private char diagonalWins() {
        char player = board[0][0];
        for (int j = 1; j < board.length; j++) {
            if (board[j][j] != player) {
                return EMPTY;
            }
        }
        return player;
    }

    // -------------------------------------------------------------------
    private char crossDiagonalWins() {
        char player = board[0][board.length - 1];
        for (int row = 1, col = board.length - 2; row < board.length; row++, col--) {
            if (board[row][col] != player) {
                return EMPTY;
            }
        }
        return player;
    }

    // -------------------------------------------------------------------
    public String toString() {
        String s = "";
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                s += board[row][col];
            }
            s += "\n";
        }
        return s;
    }
}
