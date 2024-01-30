package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Stopwatch;
import edu.neu.coe.info6205.util.TimeLogger;
import edu.neu.coe.info6205.util.Utilities;

import java.util.function.Consumer;
import java.util.function.Supplier;


public class ThreeSumBenchmark {
    public ThreeSumBenchmark(int runs, int n, int m) {
        this.runs = runs;
        this.supplier = new Source(n, m).intsSupplier(10);
        this.n = n;
    }

    public void runBenchmarks() {
        System.out.println("ThreeSumBenchmark: N=" + n);
        benchmarkThreeSum("ThreeSumQuadratic", (xs) -> new ThreeSumQuadratic(xs).getTriples(), n, timeLoggersQuadratic);
        benchmarkThreeSum("ThreeSumQuadrithmic", (xs) -> new ThreeSumQuadrithmic(xs).getTriples(), n, timeLoggersQuadrithmic);
        benchmarkThreeSum("ThreeSumCubic", (xs) -> new ThreeSumCubic(xs).getTriples(), n, timeLoggersCubic);
    }

    public static void main(String[] args) {
        new ThreeSumBenchmark(100, 250, 125).runBenchmarks();
        new ThreeSumBenchmark(50, 500, 250).runBenchmarks();
        new ThreeSumBenchmark(20, 1000, 500).runBenchmarks();
        new ThreeSumBenchmark(3, 4000, 2000).runBenchmarks();
        new ThreeSumBenchmark(2, 6000, 3000).runBenchmarks();
    }

    private void benchmarkThreeSum(final String description, final Consumer<int[]> function, int n, final TimeLogger[] timeLoggers) {
        double totalTime = 0;

        for (int i = 0; i < this.runs; i++) {
            int[] inputArray = this.supplier.get();
            // Start the stopwatch
            Stopwatch stopwatch = new Stopwatch();
            try {
                // Execute the function on the input array
                function.accept(inputArray);
            } finally {
                // Stop the stopwatch and accumulate the elapsed time
                totalTime += stopwatch.lap();
                stopwatch.close();
            }
        }
        System.out.println("Benchmark Results for " + description + ":");
        System.out.println();
        timeLoggers[0].log(totalTime / this.runs, n);
        timeLoggers[1].log(totalTime / this.runs, n);
        System.out.println();
//    throw new RuntimeException("Not yet implemented");
    }

    private final static TimeLogger[] timeLoggersCubic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n^3): ", (time, n) -> time / n / n / n * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadrithmic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n^2 log n): ", (time, n) -> time / n / n / Utilities.lg(n) * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadratic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n^2): ", (time, n) -> time / n / n * 1e6)
    };

    private final int runs;
    private final Supplier<int[]> supplier;
    private final int n;
}