public class BinomialCoefficients {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // Test cases
    for (int n = 1; n < 10; n++) {
      for (int k = 1; k <= n; k++) {
        System.out.printf("(%d %d) = %d\n", n, k, binomialCoefficient(n, k));
        if (binomialCoefficient(n, k) != binomialCoefficient2(n, k)) {
          System.out.printf("Error for %d %d\n", n, k);
        }
      }
    }

  }

  // -------------------------------------------------------------------
  // Dynamic Programming - calculate a binomial coefficient.
  // (page 95 in textbook)
  public static int binomialCoefficient(int n, int k) {

    int[][] B = new int[n + 1][k + 1];

    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= Math.min(i, k); j++) {
        if (j == 0 || j == i) {
          B[i][j] = 1;
        } else {
          B[i][j] = B[i - 1][j - 1] + B[i - 1][j];

        }
      }
    }

    return B[n][k];
  }

  // -------------------------------------------------------------------
  // Dynamic Programming - calculate a binomial coefficient.
  // Code "cleaned up" to remove if statement in the inner loop.
  public static int binomialCoefficient2(int n, int k) {

    int[][] B = new int[n + 1][k + 1];

    for (int i = 0; i <= n; i++) {
      B[i][0] = 1;
      if (i <= k) {
        B[i][i] = 1;
      }
      
      for (int j = 1; j <= Math.min(i, k); j++) {
        B[i][j] = B[i - 1][j - 1] + B[i - 1][j];
      }
    }

    return B[n][k];
  }
}
