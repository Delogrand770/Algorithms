
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

// A comparison of divide and conquer and dynamic programming using the 
// Fibonacci number problem.
public class Fibonacci {

    private static int[] buffer = new int[100];
    public static PrintStream output;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        try {
            output = new PrintStream(new File("./src/TimingData_fib.csv"));

            // Run algorithm for increasing problem sizes
            double results[] = new double[4];
            String data = "Size, Fib1, Fib2, Fib3, Fib4";
            output.println(data);
            output.println(data);
            for (int n = 3; n <= 43; n++) {
                long startTime = System.nanoTime();
                fib1(n);
                results[0] = (System.nanoTime() - startTime) / 1.0E09;

                startTime = System.nanoTime();
                fib2(n);
                results[1] = (System.nanoTime() - startTime) / 1.0E09;

                startTime = System.nanoTime();
                fib3(n);
                results[2] = (System.nanoTime() - startTime) / 1.0E09;

                startTime = System.nanoTime();
                fib4(n);
                results[3] = (System.nanoTime() - startTime) / 1.0E09;

                //Output data
                data = n + "";
                for (int i = 0; i < results.length; i++) {
                    data += ", " + results[i];
                }
                output.println(data);
                System.out.println(data);
            }
            System.out.println("Completed");
        } catch (IOException error) {
            System.out.printf("File error: %s\n", error.getMessage());
        }

//        int problem = 50;
//        System.out.printf("fib1(%d) = %d\n", problem, fib1(problem));
//        System.out.printf("fib2(%d) = %d\n", problem, fib2(problem));
//        System.out.printf("fib3(%d) = %d\n", problem, fib3(problem));
//        System.out.printf("fib4(%d) = %d\n", problem, fib4(problem));
    }

    // -------------------------------------------------------------------
    // A "divide and conquer" solution to calculating a Fibonacci number.
    public static int fib1(int n) {
        if (n <= 2) {
            return 1;
        } else {
            return fib1(n - 1) + fib1(n - 2);
        }
    }

    // -------------------------------------------------------------------
    // A "dynamic programming" solution to calculating a Fibonacci number.
    public static int fib2(int n) {
        int[] answers = new int[n + 1];

        answers[1] = 1;
        answers[2] = 1;
        for (int j = 3; j <= n; j++) {
            answers[j] = answers[j - 1] + answers[j - 2];
        }

        return answers[n];
    }

    // -------------------------------------------------------------------
    // A "dynamic programming" solution that minimizes memory.
    public static int fib3(int n) {
        int t1 = 1;
        int t2 = 1;
        int t3 = t1 + t2;

        for (int j = 3; j <= n; j++) {
            t3 = t1 + t2;

            t1 = t2;
            t2 = t3;
        }

        return t3;
    }

    // -------------------------------------------------------------------
    // A recursive "dynamic programming" solution that uses a buffer to store the
    // previous solutions so they do not have to be calculated multiple times.
    public static int fib4(int n) {
        if (n <= 2) {
            return 1;
        } else {
            int t1 = buffer[n - 1];
            if (t1 == 0) {
                t1 = buffer[n - 1] = fib4(n - 1);
            }
            int t2 = buffer[n - 2];
            if (t2 == 0) {
                t2 = buffer[n - 2] = fib4(n - 2);
            }
            return t1 + t2;
        }
    }
}
