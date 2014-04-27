
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 *
 * @author C14Gavin.Delphia
 */
public class Random {

    public static PrintStream output;
    public static int[] array;
    public static int[] array_copy;
    public static int[] scratch_list;

    public static void main(String[] args) {
        try {
            output = new PrintStream(new File("./src/TimingData_random.csv"));

            // Run algorithm for increasing problem sizes
            output.println("Size, Quick Sort, Merge Sort");
            System.out.println("Size, Quick Sort, Merge Sort");
            for (int n = 2; n <= 100000000; n *= 2) {
                randomArray(n);

                long startTime = System.nanoTime();
                quick_sort();
                double algorithmSeconds = (System.nanoTime() - startTime) / 1.0E09;

                array = Arrays.copyOf(array_copy, n);

                startTime = System.nanoTime();
                merge_sort();
                double algorithmSeconds2 = (System.nanoTime() - startTime) / 1.0E09;

                output.println(n + ", " + algorithmSeconds + ", " + algorithmSeconds2);
                System.out.println(n + ", " + algorithmSeconds + ", " + algorithmSeconds2);
            }
            System.out.println("Completed");
        } catch (IOException error) {
            System.out.printf("File error: %s\n", error.getMessage());
        }
    }

    public static void randomArray(int n) {
        array = new int[n];
        for (int j = 0; j < n; j++) {
            array[j] = (int) Math.round(Math.random() * n);
        }
        array_copy = Arrays.copyOf(array, n);
    }

    //-------------------------------------------------------------------
    public static void quick_sort() {
        quick_sort(0, array.length - 1);
    }

    //-------------------------------------------------------------------
    private static void quick_sort(int lowIndex, int highIndex) {
        if (lowIndex < highIndex) {
            int pivotIndex = partition(lowIndex, highIndex);
            quick_sort(lowIndex, pivotIndex - 1);     // sort left half
            quick_sort(pivotIndex + 1, highIndex);     // sort right half
        }
    }

    //-------------------------------------------------------------------
    private static int partition(int low, int high) {
        int pivotItem = array[low];
        int j = low;
        for (int k = low + 1; k <= high; k++) {
            if (array[k] < pivotItem) {
                j++;
                int temp = array[j];
                array[j] = array[k];
                array[k] = temp;
            }
        }

        // move the pivot value to the pivot position
        int pivotIndex = j;
        int temp = array[low];
        array[low] = array[pivotIndex];
        array[pivotIndex] = temp;

        return pivotIndex;
    }

    // -------------------------------------------------------------------
    public static void merge_sort() {
        scratch_list = new int[array.length];
        merge_sort(0, array.length - 1);
        scratch_list = null;
    }

    // -------------------------------------------------------------------
    private static void merge_sort(int lowIndex, int highIndex) {
        if (lowIndex < highIndex) {
            int middleIndex = (lowIndex + highIndex) / 2;
            merge_sort(lowIndex, middleIndex);        // sort left half
            merge_sort(middleIndex + 1, highIndex);   // sort right half
            merge(lowIndex, middleIndex, highIndex);  // merge
        }
    }

    // -------------------------------------------------------------------
    private static void merge(int lowIndex, int middleIndex, int highIndex) {
        int i = lowIndex;        // index into left sub-array
        int j = middleIndex + 1; // index into right sub-array
        int k = lowIndex;        // index into "scratch" array
        while (i <= middleIndex && j <= highIndex) {
            if (array[i] < array[j]) {
                scratch_list[k] = array[i++];
            } else {
                scratch_list[k] = array[j++];
            }
            k++;
        }

        // copy any remaining values from the left or right sub-array
        if (i <= middleIndex) {
            while (i <= middleIndex) {
                scratch_list[k++] = array[i++];
            }
        } else {
            while (j <= highIndex) {
                scratch_list[k++] = array[j++];
            }
        }

        // copy the sorted scratch array back into the list
        for (int n = lowIndex; n <= highIndex; n++) {
            array[n] = scratch_list[n];
        }
    }
}
