package edu.neu.coe.info6205.union_find;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class UF_Client {

    private static long singleComponentEdges = 0;

    public static void main(String[] args) {
        int start_N = 550;
        int end_N = 240000;
        int experiments = 600;
        int increment = 2;

        for (int i = start_N; i < end_N; i *= increment) {
            runExperiments(i, experiments);
        }
    }

    public static long performExperiment(int n) {
        long count = 0;
        UF_HWQUPC unionFind = new UF_HWQUPC(n);
        Random random = new Random();
        try {
            while (unionFind.components() != 1) {
                int one = random.nextInt(n);
                int two = random.nextInt(n);
                if (!unionFind.connected(one, two)) {
                    unionFind.union(one, two);
                    singleComponentEdges++;
                }
                count++;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred for N: " + n + ", count: " + count + ", Error: " + e);
        }

        return count;
    }

    public static void runExperiments(int n, int m) {
        long avgUnionCount = 0, avgPairCount = 0;

        BigDecimal constantValue = BigDecimal.valueOf(n);
        constantValue = constantValue.pow(2);
        constantValue = constantValue.subtract(BigDecimal.valueOf(n));
        constantValue = constantValue.divide(BigDecimal.valueOf(n).pow(2), new MathContext(20));
        BigDecimal resValue = BigDecimal.ONE;
        for (int start = 0; start < n - 1; start++) {
            BigDecimal bigDec = constantValue.subtract(BigDecimal.valueOf(start).divide(BigDecimal.valueOf(n).pow(2), new MathContext(20)));
            resValue = resValue.multiply(bigDec);
        }

        long max = Integer.MIN_VALUE;
        for (int start = 0; start < m; start++) {
            singleComponentEdges = 0;
            long temp = performExperiment(n);
            if (temp > max) {
                max = temp;
            }
            avgUnionCount += singleComponentEdges;
            avgPairCount += temp;
        }
        String format = "| %-25s | %-30s |%n";
        System.out.format("+-------------------------+--------------------------------+-%n");
        System.out.format("|            Metric       |              Value             |%n");
        System.out.format("+-------------------------+--------------------------------+-%n");
        System.out.format(format, "N", n);
        System.out.format(format, "Average pairs generated", (avgPairCount / m));
        System.out.format(format, "Average union operations", (avgUnionCount / m));
        System.out.format("+-------------------------+--------------------------------+-%n");
    }
}