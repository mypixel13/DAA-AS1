package algo;

public class MergeSort {

    // below this size we just switch to insertion sort, it's faster in practice
    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics metrics) {
        if (a.length < 2) {
            return;
        }
        // allocate the helper array ONCE here at the top and pass it down,
        // instead of allocating a new one inside every merge() call
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, metrics, 1);
    }

    private static void sort(int[] a, int[] buffer, int lo, int hi, Metrics metrics, int depth) {
        metrics.trackDepth(depth);

        if (hi - lo <= CUTOFF) {
            InsertionSort.sort(a, lo, hi, metrics);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(a, buffer, lo, mid, metrics, depth + 1);
        sort(a, buffer, mid + 1, hi, metrics, depth + 1);
        merge(a, buffer, lo, mid, hi, metrics);
    }

    private static void merge(int[] a, int[] buffer, int lo, int mid, int hi, Metrics metrics) {
        // copy the range we're about to merge into the shared buffer
        for (int i = lo; i <= hi; i++) {
            buffer[i] = a[i];
        }

        int left = lo;
        int right = mid + 1;
        int k = lo;

        while (left <= mid && right <= hi) {
            metrics.compare();
            if (buffer[left] <= buffer[right]) {
                a[k++] = buffer[left++];
            } else {
                a[k++] = buffer[right++];
            }
        }

        // whichever half still has leftovers, just copy the rest straight in
        while (left <= mid) {
            a[k++] = buffer[left++];
        }
        while (right <= hi) {
            a[k++] = buffer[right++];
        }
    }
}
