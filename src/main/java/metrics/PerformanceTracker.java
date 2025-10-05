package metrics;

public final class PerformanceTracker {
    private long comparisons;
    private long swaps;
    private long arrayReads;
    private long arrayWrites;
    private long iterations;
    private boolean earlyTerminated;

    public void incComparison() { comparisons++; }
    public void incSwap() { swaps++; }
    public void addReads(long n) { arrayReads += n; }
    public void addWrites(long n) { arrayWrites += n; }
    public void incIteration() { iterations++; }
    public void markEarlyTermination() { earlyTerminated = true; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getArrayReads() { return arrayReads; }
    public long getArrayWrites() { return arrayWrites; }
    public long getIterations() { return iterations; }
    public boolean wasEarlyTerminated() { return earlyTerminated; }

    public void reset() {
        comparisons = swaps = arrayReads = arrayWrites = iterations = 0L;
        earlyTerminated = false;
    }

    @Override
    public String toString() {
        return "PerformanceTracker{" +
                "comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", arrayReads=" + arrayReads +
                ", arrayWrites=" + arrayWrites +
                ", iterations=" + iterations +
                ", earlyTerminated=" + earlyTerminated +
                '}';
    }

    public String csvHeader() {
        return "comparisons,swaps,arrayReads,arrayWrites,iterations,earlyTerminated";
    }
    public String toCsvRow() {
        return comparisons + "," + swaps + "," + arrayReads + "," + arrayWrites + "," + iterations + "," + earlyTerminated;
    }
}
