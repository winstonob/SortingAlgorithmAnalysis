import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {

    private static int size;
    private static int trials;
    public static boolean traceMode;
    private static Integer [] tempArray;
    private static Integer [] randomArray;
    private static Integer [] sortedArray;
    private static Integer [] reversedArray;
    private static PrintWriter printer;
    private enum DataFormat {
        RANDOM,
        SORTED,
        REVERSED
    }

    public static void main(String[] args) throws IOException {

        // Data parameters
        System.out.println("Enter array size: ");
        Scanner scn = new Scanner(System.in);
        size = scn.nextInt();
        System.out.println("Enter number of trials: ");
        trials = scn.nextInt();

        // Conditions for Trace Mode
        if (size <= 20) {
            traceMode = true;
        }

        // Data construction
        tempArray = new Integer [size];
        randomArray = new Integer [size];
        sortedArray = new Integer [size];
        reversedArray = new Integer [size];
        for(int i = 0; i < size; i++) {
            randomArray[i] = (int) (Math.random() * size);
        }
        for(int i = 0; i < size; i++) {
            sortedArray[i] = i;
        }
        for(int i = 0; i < size; i++) {
            reversedArray[i] = size - i - 1;
        }

        // File construction
        System.out.println("Enter file name: ");
        String filename = scn.next();
        FileWriter writer = new FileWriter(filename + ".txt");
        printer = new PrintWriter(writer);

        // Trial Execution
        if (size > 5) {
            executeTrial(DataFormat.RANDOM, 5);
            executeTrial(DataFormat.SORTED, 5);
            executeTrial(DataFormat.REVERSED, 5);

            if (size > 50) {
                executeTrial(DataFormat.RANDOM, 50);
                executeTrial(DataFormat.SORTED, 50);
                executeTrial(DataFormat.REVERSED, 50);

                if (size > 150) {
                    executeTrial(DataFormat.RANDOM, 150);
                    executeTrial(DataFormat.SORTED, 150);
                    executeTrial(DataFormat.REVERSED, 150);
                }
            }
        }
        else {
            executeTrial(DataFormat.RANDOM, 1);
            executeTrial(DataFormat.SORTED, 1);
            executeTrial(DataFormat.REVERSED, 1);
        }

        // Finalizing text file
        printer.close();
        writer.close();
    }

    public static void executeTrial(DataFormat val, int min) {
        long start;
        long finish;
        double average;
        SortAlgorithms.setMinSize(min);
        // Simple Quick Sort
        if(size <= 100000) {
            average = 0;
            for (int i = 0; i <= trials; i++) {
                Driver.resetData(val);
                start = System.nanoTime();
                SortAlgorithms.quickSortSimple(tempArray, size);
                finish = System.nanoTime();
                average += finish - start;
            }
            average /= trials;
            average /= 1000000000.0;
            printResults("Simple Quicksort", average, val);
        }

        // Median of 3 Quicksort
        average = 0;
        for (int i = 0; i <= trials; i++) {
            Driver.resetData(val);
            start = System.nanoTime();
            SortAlgorithms.quickSortMedian(tempArray, size);
            finish = System.nanoTime();
            average += finish - start;
        }
        average /= trials;
        average /= 1000000000.0;
        printResults("Median of 3 Quicksort", average, val);

        // Random Pivot Quicksort
        average = 0;
        for (int i = 0; i <= trials; i++) {
            Driver.resetData(val);
            start = System.nanoTime();
            SortAlgorithms.quickSortRandom(tempArray, size);
            finish = System.nanoTime();
            average += finish - start;
        }
        average /= trials;
        average /= 1000000000.0;
        printResults("Random Pivot Quicksort", average, val);

        // Random Mergesort
        average = 0;
        for (int i = 0; i <= trials; i++) {
            Driver.resetData(val);
            start = System.nanoTime();
            SortAlgorithms.mergeSort(tempArray, size);
            finish = System.nanoTime();
            average += finish - start;
        }
        average /= trials;
        average /= 1000000000.0;
        printResults("MergeSort", average, val);

    }

    public static void printResults(String alg, double average, DataFormat val ) {
        String output = "Algorithm: " +
                alg +
                "\n" +
                "Array Size: " +
                size +
                "\n" +
                "Base Case Less Than: " +
                SortAlgorithms.getMinSize() +
                "\n" +
                "Data Set Up: " +
                val.name() +
                "\n" +
                "Number of Trials: " +
                trials +
                "\n" +
                "Average Time per trial: " +
                average +
                " sec." +
                "\n";

        // Trace Mode Prints
        if(traceMode) {
            output += "Data Before Sorting: " +
                    Arrays.toString(getData(val)) +
                    "\n" +
                    "Data After Sorting: " +
                    Arrays.toString(tempArray)+
                    "\n";
            System.out.println(output);
        }

        printer.println(output);
    }

    public static void resetData(DataFormat val) {
        switch (val) {
            case RANDOM:
                System.arraycopy(randomArray, 0, tempArray, 0, randomArray.length);
                break;
            case SORTED:
                System.arraycopy(sortedArray, 0, tempArray, 0, sortedArray.length);
                break;
            case REVERSED:
                System.arraycopy(reversedArray, 0, reversedArray, 0, reversedArray.length);
                break;
            default:
                break;
        }
    }

    public static Integer[] getData(DataFormat val) {
        switch (val) {
            case RANDOM:
                return randomArray;
            case SORTED:
                return sortedArray;
            case REVERSED:
                return reversedArray;
            default:
                return null;
        }
    }

}
