// A comparison of divide and conquer and dynamic programming using the 
// Fibonacci number problem.

public class Fibonacci {

  private static int[] buffer = new int[100];

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    int problem = 50;
    System.out.printf("fib1(%d) = %d\n", problem, fib1(problem));
    System.out.printf("fib2(%d) = %d\n", problem, fib2(problem));
    System.out.printf("fib3(%d) = %d\n", problem, fib3(problem));
    System.out.printf("fib4(%d) = %d\n", problem, fib4(problem));
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
    for (int j = 3; j <= n; j++)
      answers[j] = answers[j - 1] + answers[j - 2];

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
