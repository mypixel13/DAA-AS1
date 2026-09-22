package algo;

import java.util.Random;

public class QuickSort {

    private static final Random RNG = new Random();

    public static void sort(int[] a, Metrics metrics) {
        sort(a, 0, a.length - 1, metrics, 1);
    }

    private static void sort(int[] a, int lo, int hi, Metrics metrics, int depth) {
        // instead of always doing two recursive calls, we recurse into the
        // smaller half and just loop back around for the bigger one.
        // that's what actually keeps the recursion depth around log n
        // even on already-sorted input (no stack overflow on 100k+ elements).
        while (lo < hi) {
            metrics.trackDepth(depth);

            int pivotIndex = lo + RNG.nextInt(hi - lo + 1);
            int pivot = a[pivotIndex];

            // 3-way partition:
            // [lo, lt-1] < pivot, [lt, gt] == pivot, [gt+1, hi] > pivot
            int lt = lo;
            int i = lo;
            int gt = hi;

            while (i <= gt) {
                metrics.compare();
                if (a[i] < pivot) {
                    swap(a, lt++, i++);
                } else if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }

            int leftSize = lt - lo;
            int rightSize = hi - gt;

            if (leftSize < rightSize) {
                sort(a, lo, lt - 1, metrics, depth + 1);
                lo = gt + 1; // continue the loop on the right part
            } else {
                sort(a, gt + 1, hi, metrics, depth + 1);
                hi = lt - 1; // continue the loop on the left part
            }
        }
    }

    static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
