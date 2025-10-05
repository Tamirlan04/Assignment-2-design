package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class SelectionSortTest {

    @Test
    void emptyArray_ok() {
        int[] a = new int[0];
        SelectionSort.sort(a, null, true, false);
        assertArrayEquals(new int[0], a);
    }

    @Test
    void singleElement_ok() {
        int[] a = {42};
        SelectionSort.sort(a, null, true, false);
        assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void duplicates_ok() {
        int[] a = {5, 1, 3, 3, 1, 5, 0, 0};
        int[] expected = a.clone(); Arrays.sort(expected);
        SelectionSort.sort(a, null, true, false);
        assertArrayEquals(expected, a);
    }

    @Test
    void random_vs_JDK_standard() {
        int[] a = new Random(123).ints(1_000, -10_000, 10_000).toArray();
        int[] expected = a.clone(); Arrays.sort(expected);
        SelectionSort.sort(a, null, false, false);
        assertArrayEquals(expected, a);
    }

    @Test
    void random_vs_JDK_doubleEnded() {
        int[] a = new Random(456).ints(1_000, -1_000_000, 1_000_000).toArray();
        int[] expected = a.clone(); Arrays.sort(expected);
        SelectionSort.sort(a, null, false, true);
        assertArrayEquals(expected, a);
    }

    @Test
    void earlyExit_triggers_on_sorted_input() {
        int[] a = new int[100];
        for (int i = 0; i < a.length; i++) a[i] = i;
        var t = new metrics.PerformanceTracker();
        SelectionSort.sort(a, t, true, false);
        assertTrue(t.wasEarlyTerminated());
    }
}
