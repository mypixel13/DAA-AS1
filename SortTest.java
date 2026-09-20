package algo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortTest {

    private final Random rnd = new Random();

    @Test
    void mergeSortMatchesArraysSortOnRandomArrays() {
        for (int t = 0; t < 100; t++) {
            int[] a = randomArray(rnd.nextInt(500) + 1);
            int[] expected = a.clone();
            Arrays.sort(expected);

            MergeSort.sort(a, new Metrics());
            assertArrayEquals(expected, a);
        }
    }

    @Test
    void quickSortMatchesArraysSortOnRandomArrays() {
        for (int t = 0; t < 100; t++) {
            int[] a = randomArray(rnd.nextInt(500) + 1);
            int[] expected = a.clone();
            Arrays.sort(expected);

            QuickSort.sort(a, new Metrics());
            assertArrayEquals(expected, a);
        }
    }

    @Test
    void mergeSortEmptyArray() {
        int[] a = {};
        MergeSort.sort(a, new Metrics());
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void quickSortEmptyArray() {
        int[] a = {};
        QuickSort.sort(a, new Metrics());
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void oneElementArray() {
        int[] a1 = {42};
        int[] a2 = {42};
        MergeSort.sort(a1, new Metrics());
        QuickSort.sort(a2, new Metrics());
        assertArrayEquals(new int[]{42}, a1);
        assertArrayEquals(new int[]{42}, a2);
    }

    @Test
    void allElementsEqual() {
        int[] a1 = new int[200];
        Arrays.fill(a1, 7);
        int[] a2 = a1.clone();

        MergeSort.sort(a1, new Metrics());
        QuickSort.sort(a2, new Metrics());

        assertArrayEquals(a1, a2);
        for (int v : a1) {
            assertEquals(7, v);
        }
    }

    @Test
    void alreadySortedArrayStaysSorted() {
        int[] a = new int[500];
        for (int i = 0; i < a.length; i++) a[i] = i;
        int[] expected = a.clone();

        QuickSort.sort(a, new Metrics());
        assertArrayEquals(expected, a);
    }

    @Test
    void quickSortDepthStaysBoundedOnSortedInput() {
        // this is the whole point of "smaller side first" - without it,
        // a sorted array would blow the recursion depth up to n, not log n
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(a, metrics);

        int limit = (int) (2 * (Math.log(n) / Math.log(2)));
        assertTrue(metrics.maxDepth <= limit,
                "maxDepth was " + metrics.maxDepth + ", expected <= " + limit);
    }

    private int[] randomArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(2000) - 1000;
        }
        return a;
    }
}
