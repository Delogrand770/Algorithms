
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Timing {

    public static PrintStream output;
    public static final int START_SIZE = 1;
    public static final int END_SIZE = 1000;

    public static void main(String[] args) {
        try {
            output = new PrintStream(new File("./src/TimingData_.csv"));

            // Run algorithm for increasing problem sizes
            double results[] = new double[4];
            String data = "Size, alg1, alg2, alg3, alg4";
            output.println(data);
            output.println(data);
            for (int n = START_SIZE; n <= END_SIZE; n++) {
                long startTime = System.nanoTime();
                results[0] = (System.nanoTime() - startTime) / 1.0E09;

                startTime = System.nanoTime();
                results[1] = (System.nanoTime() - startTime) / 1.0E09;

                startTime = System.nanoTime();
                results[2] = (System.nanoTime() - startTime) / 1.0E09;

                startTime = System.nanoTime();
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
    }
}
