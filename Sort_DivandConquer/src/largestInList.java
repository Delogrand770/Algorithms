/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author C14Gavin.Delphia
 */
public class largestInList {

    public static int[] myList;
    public static int runs = 0;

    public static void main(String[] args) {
        myList = randomList(256);
        System.out.println(getHighest(0, myList.length - 1));
        System.out.println(runs);
    }

    public static int getHighest(int start, int end) {
        runs++;
        if (start == end) {
            return myList[start];
        } else {
            int half = (start + end) / 2;
            int r1 = getHighest(start, half);
            int r2 = getHighest(half + 1, end);

            return (r1 > r2) ? r1 : r2;
        }
    }

    public static int[] randomList(int size) {
        int[] data = new int[size];
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = r.nextInt(1000);
        }
        return data;
    }
}
