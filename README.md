# Selection Sort — Assignment 2 (Student B)

Java/Maven project with **Selection Sort** + **early-exit**, optional **double-ended** variant, a **CLI** benchmark, **JUnit 5** tests, and simple **metrics**.

## Quick Start

```bash
# run tests
mvn -q -DskipTests=false test

# run benchmark (single default run if no args)
mvn -q exec:java -Dexec.args="20 random standard true"
```

**Args format:** `<size> <mode> <algo> <earlyExit> [seed]`
Modes: `random|sorted|reversed|duplicates` • Algos: `standard|double`.

## Project Structure

```
src/main/java/algorithms/SelectionSort.java
src/main/java/metrics/PerformanceTracker.java
src/main/java/cli/BenchmarkRunner.java
src/test/java/algorithms/SelectionSortTest.java
pom.xml
```

## API

```java
SelectionSort.sort(int[] a, PerformanceTracker t, boolean earlyExit, boolean doubleEnded);
```

## Metrics

`comparisons, swaps, arrayReads, arrayWrites, iterations, earlyTerminated`
Printed as text + a CSV row.

## Notes

* **Default run:** launching `BenchmarkRunner` with no args performs one random run and prints metrics.
* **JUnit red in IDE?** Ensure tests live in `src/test/java`, mark as *Test Sources Root*, then **Reload All Maven Projects**.

Kyzylov Tamirlan
