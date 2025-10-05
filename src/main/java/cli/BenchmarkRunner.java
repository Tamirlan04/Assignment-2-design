package cli;

import algorithms.SelectionSort;
import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.Random;

public final class BenchmarkRunner {
    private static final String USAGE = """
            Usage:
              mvn -q exec:java -Dexec.args="<size> <mode> <algo> <earlyExit> [seed]"
            
            Args:
              size       : int (e.g., 20, 1000)
              mode       : random | sorted | reversed | duplicates
              algo       : standard | double
              earlyExit  : true | false
              seed       : long (optional)
            
            Examples:
              mvn -q exec:java -Dexec.args="20 random standard true"
              mvn -q exec:java -Dexec.args="1000 reversed double false 42"
            """;

    public static void main(String[] args) {
        if (args.length < 4) {
            runOnce(20, "random", "standard", true, System.nanoTime());
            return;
        }

        final int size = parseInt(args[0], 20);
        final String mode = args[1].toLowerCase();
        final String algo = args[2].toLowerCase();
        final boolean earlyExit = Boolean.parseBoolean(args[3]);
        final long seed = (args.length >= 5) ? parseLong(args[4], System.nanoTime())
                : System.nanoTime();

        runOnce(size, mode, algo, earlyExit, seed);
    }


    private static void autoRun() {
        int[] sizes = {20, 50, 200, 1000};
        boolean[] doubleEndedOptions = {false, true};

        int run = 1;
        for (int size : sizes) {
            for (boolean doubleEnded : doubleEndedOptions) {
                long seed = System.nanoTime() ^ (0x9E3779B97F4A7C15L * run);
                runOnce(size, "random", doubleEnded ? "double" : "standard", true, seed);
                run++;
            }
        }
        System.out.println("\nAuto mode finished.");
    }

    private static void runOnce(int size, String mode, String algo, boolean earlyExit, long seed) {
        final boolean doubleEnded = switch (algo) {
            case "standard" -> false;
            case "double" -> true;
            default -> {
                System.out.println("Unknown algo: " + algo + " (use standard|double)");
                System.out.println(USAGE);
                yield false;
            }
        };

        int[] a = buildArray(size, mode, seed);
        PerformanceTracker tracker = new PerformanceTracker();

        System.out.println("=== Run ===");
        System.out.println("mode=" + mode + ", algo=" + algo + ", earlyExit=" + earlyExit + ", n=" + size + ", seed=" + seed);
        System.out.println("Input:");
        System.out.println(prettyArray(a));

        SelectionSort.sort(a, tracker, earlyExit, doubleEnded);

        System.out.println("Sorted:");
        System.out.println(prettyArray(a));
        System.out.println("Metrics: " + tracker);
        System.out.println("CSV:");
        System.out.println(tracker.csvHeader());
        System.out.println(tracker.toCsvRow());
        System.out.println();
    }

    private static int[] buildArray(int n, String mode, long seed) {
        Random rnd = new Random(seed);
        int[] a = new int[Math.max(n, 0)];
        switch (mode) {
            case "sorted" -> { for (int i = 0; i < a.length; i++) a[i] = i; }
            case "reversed" -> { for (int i = 0; i < a.length; i++) a[i] = a.length - 1 - i; }
            case "duplicates" -> { for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(10); }
            case "random" -> { for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(); }
            default -> {
                System.out.println("Unknown mode: " + mode + " -> using random");
                for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt();
            }
        }
        return a;
    }

    private static String prettyArray(int[] a) {
        int maxToShow = Math.min(a.length, 50);
        return Arrays.toString(Arrays.copyOf(a, maxToShow)) + (a.length > maxToShow ? " …" : "");
    }

    private static int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
    private static long parseLong(String s, long def) {
        try { return Long.parseLong(s); } catch (Exception e) { return def; }
    }
}
