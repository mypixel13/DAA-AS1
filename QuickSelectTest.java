package algo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuickSelectTest {

    private final Random rnd = new Random();

    @Test
    void matchesSortedArrayOnRandomInput() {
        for (int t = 0; t < 100; t++) {
            int n = rnd.nextInt(300) + 1;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(1000) - 500;
            }

            int[] sorted = a.clone();
            Arrays.sort(sorted);

            int k = rnd.nextInt(n);
            assertEquals(sorted[k], QuickSelect.select(a, k));
        }
    }

    @Test
    void throwsOnEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{}, 0));
    }

    @Test
    void throwsOnKOutOfRange() {
        int[] a = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a, -1));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a, 3));
    }

    @Test
    void doesNotMutateOriginalArray() {
        int[] a = {5, 3, 8, 1, 9, 2};
        int[] original = a.clone();
        QuickSelect.select(a, 2);
        assertArrayEquals(original, a);
    }
}
