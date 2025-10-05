package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public final class SelectionSort {
    private SelectionSort() {}

    /**
     * Selection Sort with optional early-exit and double-ended variant.
     *
     * @param a           array to sort (in-place)
     * @param tracker     performance metrics (nullable)
     * @param earlyExit   check "already sorted" after each outer pass to exit early
     * @param doubleEnded find min and max each pass and place to both ends
     */
    public static void sort(int[] a, PerformanceTracker tracker, boolean earlyExit, boolean doubleEnded) {
        if (a == null) throw new IllegalArgumentException("Input array must not be null");
        if (a.length < 2) return;

        if (doubleEnded) {
            doubleEndedSelection(a, tracker, earlyExit);
        } else {
            standardSelection(a, tracker, earlyExit);
        }
    }

    private static void standardSelection(int[] a, PerformanceTracker t, boolean earlyExit) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            if (t != null) t.incIteration();
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (t != null) { t.incComparison(); t.addReads(2); }
                if (a[j] < a[minIdx]) minIdx = j;
            }
            if (minIdx != i) swap(a, i, minIdx, t);
            if (earlyExit && isSorted(a, t)) {
                if (t != null) t.markEarlyTermination();
                break;
            }
        }
    }

    private static void doubleEndedSelection(int[] a, PerformanceTracker t, boolean earlyExit) {
        int left = 0, right = a.length - 1;
        while (left < right) {
            if (t != null) t.incIteration();
            int minIdx = left, maxIdx = right;

            for (int j = left; j <= right; j++) {
                if (t != null) { t.addReads(1); t.addReads(1); t.incComparison(); }
                if (a[j] < a[minIdx]) minIdx = j;
                if (t != null) { t.addReads(1); t.incComparison(); }
                if (a[j] > a[maxIdx]) maxIdx = j;
            }

            if (minIdx != left) swap(a, left, minIdx, t);
            if (maxIdx == left) maxIdx = minIdx;
            if (maxIdx != right) swap(a, right, maxIdx, t);

            left++; right--;

            if (earlyExit && isSorted(a, t)) {
                if (t != null) t.markEarlyTermination();
                break;
            }
        }
    }

    private static void swap(int[] a, int i, int j, PerformanceTracker t) {
        if (t != null) { t.incSwap(); t.addReads(2); t.addWrites(2); }
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    private static boolean isSorted(int[] a, PerformanceTracker t) {
        for (int k = 1; k < a.length; k++) {
            if (t != null) { t.incComparison(); t.addReads(2); }
            if (a[k - 1] > a[k]) return false;
        }
        return true;
    }

    // quick local run
    public static void main(String[] args) {
        int[] data = {5, 3, 4, 1, 2};
        PerformanceTracker tracker = new PerformanceTracker();
        sort(data, tracker, true, false);
        System.out.println(Arrays.toString(data));
        System.out.println(tracker);
    }
}
