
import java.util.Arrays;

/**
 *
 * @author C14Gavin.Delphia
 */
public class DivideAndConquer {

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

//        int[] start3 = {0, 0};
//        int[] end3 = {0, 99};
//        System.out.println(howMany(100, start3, end3, 50));

        int[] start4 = {0, 0};
        int[] end4 = {0, 9};
        for (int numMoves = 0; numMoves <= 7; numMoves++) {
            System.out.printf("%d  %d \n", numMoves, howMany(10, start4, end4, numMoves));
        }
    }

    public static int howMany(int boardSize, int[] start, int[] end, int movesAllowed) {
        int total = 0;
        if (movesAllowed == 0 && Arrays.equals(start, end)) {
            return 1;
        } else {
            if (movesAllowed > 0) {
                for (int i = 0; i < offsets.length; i++) {
                    int[] newStart = new int[2];
                    newStart[0] = start[0] + offsets[i][0];
                    newStart[1] = start[1] + offsets[i][1];
                    if (newStart[0] >= 0 && newStart[0] < boardSize && newStart[1] >= 0 && newStart[1] < boardSize) {
                        total += howMany(boardSize, newStart, end, movesAllowed - 1);
                    }
                }
            }
        }
        return total;
    }
    
}
