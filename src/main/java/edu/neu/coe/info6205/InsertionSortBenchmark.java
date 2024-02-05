package edu.neu.coe.info6205;

import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.Random;
import java.util.function.Supplier;

public class InsertionSortBenchmark {

    public static void main(String[] args) {
        InsertionSort<Integer> insertionSort = new InsertionSort<>();

        // Print table header
        System.out.printf("%-20s %-15s %-15s %-15s %-15s%n", "Array Size", "Condition", "Avg Time (ms)", "Min Time (ms)", "Max Time (ms)");

        // Loop through array sizes, doubling each time, starting from 450 to under 20,000
        for (int arraySize = 450; arraySize < 20000; arraySize *= 2) {
            measureSortingPerformance(arraySize, insertionSort, false, false, false);
            measureSortingPerformance(arraySize, insertionSort, true, false, false);
            measureSortingPerformance(arraySize, insertionSort, false, true, false);
            measureSortingPerformance(arraySize, insertionSort, false, false, true);
        }
    }

    private static void measureSortingPerformance(int arraySize, InsertionSort<Integer> sorter,
                                                  boolean isSorted, boolean isReverse, boolean isPartiallyOrdered) {
        Supplier<Integer[]> supplier = () -> {
            Integer[] array = new Integer[arraySize];
            Random rand = new Random();

            if (isSorted) {
                for (int start = 0; start < arraySize; start++) {
                    array[start] = start; // Ordered
                }
            } else if (isReverse) {
                for (int start = 0; start < arraySize; start++) {
                    array[start] = arraySize - start; // Reverse Ordered
                }
            } else if (isPartiallyOrdered) {
                for (int start = 0; start < arraySize; start++) {
                    array[start] = rand.nextInt(arraySize); // Partially Ordered
                }
            } else {
                for (int start = 0; start < arraySize; start++) {
                    array[start] = rand.nextInt(arraySize); // Random
                }
            }

            return array;
        };

        double[] times = measureSortingTime(sorter, supplier, 10);

        // Print table row
        System.out.printf("%-20d %-15s %-15.2f %-15.2f %-15.2f%n",
                arraySize, getConditionString(isSorted, isReverse, isPartiallyOrdered),
                times[0], times[1], times[2]);
    }

    private static double[] measureSortingTime(InsertionSort<Integer> sorter, Supplier<Integer[]> supplier, int runs) {
        Benchmark_Timer<Integer[]> timer = new Benchmark_Timer<>("Insertion Sort Performance",
                null,
                arrayToSort -> sorter.sort(arrayToSort, 0, arrayToSort.length),
                null);

        double[] timings = new double[3]; // 0: Avg Time, 1: Min Time, 2: Max Time
        double total = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (int i = 0; i < runs; i++) {
            double time = timer.runFromSupplier(supplier, 5);
            total += time;
            min = Math.min(min, time);
            max = Math.max(max, time);
        }

        timings[0] = total / runs; // Avg Time
        timings[1] = min;          // Min Time
        timings[2] = max;          // Max Time

        return timings;
    }

    private static String getConditionString(boolean isSorted, boolean isReverse, boolean isPartiallyOrdered) {
        if (isSorted) return "Ordered";
        if (isReverse) return "Reverse Ordered";
        if (isPartiallyOrdered) return "Partially Ordered";
        return "Random";
    }
}


