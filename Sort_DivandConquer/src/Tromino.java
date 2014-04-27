
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/*
 * To change the gridsize only modify the POWER variable. It will create a square grid of size 2^POWER.
 *
 * @author C14Gavin.Delphia
 */
public class Tromino {

    public static final int POWER = 4;
    public static final int SQUARESIZE = 40;
    public static final int GRIDSIZE = (int) Math.pow(2, POWER);
    public static int[][] grid = new int[GRIDSIZE][GRIDSIZE];
    public static int counter = 1;

    public static void main(String[] args) {
        System.out.println("Grid: " + GRIDSIZE + "x" + GRIDSIZE);
        randomEmpty();
        solve(new Point(0, 0), new Point(GRIDSIZE - 1, GRIDSIZE - 1));

        DrawingPanel panel = new DrawingPanel(GRIDSIZE * SQUARESIZE, GRIDSIZE * SQUARESIZE);
        displayGrid(panel);
    }

    /**
     * Pick a random square in the grid to be the starting position.
     */
    public static void randomEmpty() {
        int i = (int) Math.round(Math.random() * (GRIDSIZE - 1));
        int j = (int) Math.round(Math.random() * (GRIDSIZE - 1));
        grid[i][j] = 1;
    }

    /**
     * Displays the grid as text and as a image.
     */
    public static void displayGrid(DrawingPanel p) {
        Graphics2D g = p.getGraphics();

        for (int i = 0; i < GRIDSIZE; i++) {
            System.out.println();
            for (int j = 0; j < GRIDSIZE; j++) {
                //String output
                String padding = "";
                if (grid[i][j] < 10) {
                    padding = " ";
                }
                System.out.print(padding + grid[i][j] + " ");

                //Graphical output
                java.util.Random r = new java.util.Random();
                r.setSeed(grid[i][j] * 1000);
                g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
                if (grid[i][j] != 1) {
                    g.fillRect(i * SQUARESIZE, j * SQUARESIZE, SQUARESIZE, SQUARESIZE);
                }

            }
        }
        Mosaic filter = new Mosaic(GRIDSIZE * SQUARESIZE, GRIDSIZE * SQUARESIZE, 10);
        filter.generateMosaic();
        g.drawImage(filter.getMosaic(), 0, 0, null);

        p.copyGraphicsToScreen();
        System.out.println();
    }

    /**
     * Recursively places the trominos into the grid.
     *
     * @param upperLeft The upperLeft most square in the grid as a point.
     * @param lowerRight The lowerRight most square in the grid as a point.
     */
    public static void solve(Point upperLeft, Point lowerRight) {
        counter++; //This is what makes the torominos distinguishable.

        if ((int) (Math.abs(upperLeft.x - lowerRight.x)) == 1) { //Base case. The square is a 2x2

            //Fill the remaining squares of the quadrant with the current tromino.
            for (int i = upperLeft.y; i <= lowerRight.y; i++) {
                for (int j = upperLeft.x; j <= lowerRight.x; j++) {
                    grid[i][j] = grid[i][j] == 0 ? counter : grid[i][j];
                }
            }
        } else { //We have more quadrants to make!

            //Used to calculate the quadrant points
            int mid = (int) (Math.abs(upperLeft.x - lowerRight.x) + 1) / 2 - 1;

            //Quadrant 1
            Point upperLeft1 = new Point(upperLeft);
            Point lowerRight1 = new Point(upperLeft);
            lowerRight1.translate(mid, mid);

            //Quadrant 2
            Point upperLeft2 = new Point(upperLeft);
            upperLeft2.translate(mid + 1, 0);
            Point lowerRight2 = new Point(lowerRight);
            lowerRight2.translate(0, -(mid + 1));

            //Quadrant 3
            Point upperLeft3 = new Point(lowerRight);
            upperLeft3.translate(-mid, -mid);
            Point lowerRight3 = new Point(lowerRight);

            //Quadrant 4
            Point upperLeft4 = new Point(upperLeft);
            upperLeft4.translate(0, mid + 1);
            Point lowerRight4 = new Point(lowerRight);
            lowerRight4.translate(-(mid + 1), 0);

            //Determine if the quadrants can be played in.
            boolean q1 = isPlayable(upperLeft1, lowerRight1);
            boolean q2 = isPlayable(upperLeft2, lowerRight2);
            boolean q3 = isPlayable(upperLeft3, lowerRight3);
            boolean q4 = isPlayable(upperLeft4, lowerRight4);

            //The 4 possible quadrant playable scenarios
            if (!q1) {
                grid[upperLeft2.y + mid][lowerRight2.x - mid] = counter;
                grid[upperLeft3.y][upperLeft3.x] = counter;
                grid[upperLeft4.y][upperLeft4.x + mid] = counter;
            } else if (!q2) {
                grid[lowerRight1.y][lowerRight1.x] = counter;
                grid[upperLeft3.y][upperLeft3.x] = counter;
                grid[upperLeft4.y][upperLeft4.x + mid] = counter;
            } else if (!q3) {
                grid[lowerRight1.y][lowerRight1.x] = counter;
                grid[upperLeft2.y + mid][lowerRight2.x - mid] = counter;
                grid[upperLeft4.y][upperLeft4.x + mid] = counter;
            } else if (!q4) {
                grid[lowerRight1.y][lowerRight1.x] = counter;
                grid[upperLeft2.y + mid][lowerRight2.x - mid] = counter;
                grid[upperLeft3.y][upperLeft3.x] = counter;
            }

            //Solve the sub quadrants
            solve(upperLeft1, lowerRight1);
            solve(upperLeft2, lowerRight2);
            solve(upperLeft3, lowerRight3);
            solve(upperLeft4, lowerRight4);
        }
    }

    /**
     * Searches a quadrant to see if it can be played in.
     *
     * @param upperLeft The upperLeft most square in the quadrant as a point.
     * @param lowerRight The lowerRight most square in the quadrant as a point.
     * @return true if it can be played in or false if it cant.
     */
    public static boolean isPlayable(Point upperLeft, Point lowerRight) {
        for (int i = upperLeft.y; i <= lowerRight.y; i++) {
            for (int j = upperLeft.x; j <= lowerRight.x; j++) {
                if (grid[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
