package bench;

import algo.Metrics;
import algo.MergeSort;
import algo.QuickSort;
import algo.QuickSelect;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] INPUT_TYPES = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5; 

    public static void main(String[] args) throws IOException {
        FileWriter csv = new FileWriter("results.csv");
        csv.write("algorithm,input,n,time_ms,comparisons,max_depth\n");

        for (int n : SIZES) {
            for (String inputType : INPUT_TYPES) {
                runCase("MergeSort", inputType, n, csv);
                runCase("QuickSort", inputType, n, csv);
                runCase("QuickSelect", inputType, n, csv);
            }
        }

        csv.close();
        System.out.println("done, results are in results.csv");
    }

    private static void runCase(String algorithm, String inputType, int n, FileWriter csv) throws IOException {
        long[] times = new long[RUNS];
        long comparisons = 0;
        int maxDepth = 0;

        for (int run = 0; run < RUNS; run++) {
            int[] data = generateInput(inputType, n);
            Metrics metrics = new Metrics();

            long start = System.nanoTime();
            switch (algorithm) {
                case "MergeSort" -> MergeSort.sort(data, metrics);
                case "QuickSort" -> QuickSort.sort(data, metrics);
                case "QuickSelect" -> QuickSelect.select(data, n / 2, metrics);
                default -> throw new IllegalStateException("unknown algorithm: " + algorithm);
            }
            long end = System.nanoTime();
            times[run] = end - start;

          
            comparisons = metrics.comparisons;
            maxDepth = metrics.maxDepth;
        }

        Arrays.sort(times);
        double medianMs = times[RUNS / 2] / 1_000_000.0;

       
        csv.write(String.format(Locale.US, "%s,%s,%d,%.3f,%d,%d%n",
                algorithm, inputType, n, medianMs, comparisons, maxDepth));

        System.out.printf("%-12s %-11s n=%-8d %.3f ms%n", algorithm, inputType, n, medianMs);
    }

    private static int[] generateInput(String type, int n) {
        Random rnd = new Random();
        int[] a = new int[n];

        switch (type) {
            case "random" -> {
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
            }
            case "sorted" -> {
                for (int i = 0; i < n; i++) a[i] = i;
            }
            case "duplicates" -> {
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt(10);
            }
            default -> throw new IllegalStateException("unknown input type: " + type);
        }

        return a;
    }
}
