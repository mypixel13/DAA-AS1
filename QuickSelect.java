package algo;

import java.util.Random;

public class QuickSelect {

    private static final Random RNG = new Random();

    public static int select(int[] a, int k) {
        return select(a, k, new Metrics());
    }

    // returns the k-th smallest element, k starts from 0
    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("array is empty, nothing to select from");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("k=" + k + " is out of range for array of length " + a.length);
        }

        // work on a copy so we don't scramble the caller's array as a side effect
        int[] copy = a.clone();
        int lo = 0;
        int hi = copy.length - 1;
        int depth = 1;

        // same partition scheme as QuickSort, but after partitioning we only
        // ever go into the one side that actually contains position k
        while (true) {
            metrics.trackDepth(depth);

            if (lo == hi) {
                return copy[lo];
            }

            int pivotIndex = lo + RNG.nextInt(hi - lo + 1);
            int pivot = copy[pivotIndex];

            int lt = lo;
            int i = lo;
            int gt = hi;

            while (i <= gt) {
                metrics.compare();
                if (copy[i] < pivot) {
                    QuickSort.swap(copy, lt++, i++);
                } else if (copy[i] > pivot) {
                    QuickSort.swap(copy, i, gt--);
                } else {
                    i++;
                }
            }

            if (k < lt) {
                hi = lt - 1;
            } else if (k > gt) {
                lo = gt + 1;
            } else {
                // k fell inside the "equal to pivot" block, so a[k] is the pivot itself
                return copy[k];
            }
            depth++;
        }
    }
}
