package algo;

// nothing fancy here, classic insertion sort on a[lo..hi] inclusive.
// used as a cutoff for small subarrays in MergeSort, since for tiny n
// the constant factor of insertion sort beats the overhead of recursing further.
public class InsertionSort {

    public static void sort(int[] a, int lo, int hi, Metrics metrics) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                metrics.compare();
                if (a[j] <= key) {
                    break;
                }
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }
}
