package benchmark;

import dp.EditDistance;
import dp.LCS;
import matching.*;
import advanced.KasaiLCP;

import java.util.Random;

public class BenchmarkRunner {
    private static final int SEED = 42;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String generateRandomString(int length, Random rnd) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Random rnd = new Random(SEED);

        System.out.println("==========================================================");
        System.out.println("PART A: DYNAMIC PROGRAMMING BENCHMARKS (LCS & Edit Distance)");
        System.out.println("==========================================================");
        int[] dpSizes = {100, 500, 1000};
        for (int size : dpSizes) {
            String s1 = generateRandomString(size, rnd);
            String s2 = generateRandomString(size, rnd);

            // LCS
            long totalLcs = 0;
            for (int r = 0; r < 5; r++) {
                long start = System.nanoTime();
                LCS.compute(s1, s2);
                totalLcs += (System.nanoTime() - start);
            }
            double avgLcsMs = (totalLcs / 5.0) / 1e6;

            // Edit Distance
            long totalEd = 0;
            for (int r = 0; r < 5; r++) {
                long start = System.nanoTime();
                EditDistance.compute(s1, s2);
                totalEd += (System.nanoTime() - start);
            }
            double avgEdMs = (totalEd / 5.0) / 1e6;

            System.out.printf("Size: %4d | LCS Avg Time: %8.4f ms | EditDistance Avg Time: %8.4f ms\n", size, avgLcsMs, avgEdMs);
        }

        System.out.println("\n==========================================================");
        System.out.println("PART B: STRING MATCHING BENCHMARKS (Naive vs KMP vs Rabin-Karp)");
        System.out.println("==========================================================");
        int[] matchSizes = {10000, 50000, 100000};
        for (int size : matchSizes) {
            String text = generateRandomString(size, rnd);
            String patternPresent = text.substring(size / 2, (size / 2) + 10);
            String patternAbsent = "1234567890"; // Not in alphabet

            System.out.println("--- Text Length: " + size + " ---");
            runMatchingBench("Present Pattern", text, patternPresent);
            runMatchingBench("Absent Pattern ", text, patternAbsent);
        }

        System.out.println("\n==========================================================");
        System.out.println("PART C: KASAI'S ALGORITHM BENCHMARKS");
        System.out.println("==========================================================");
        int[] kasaiSizes = {1000, 5000, 10000};
        for (int size : kasaiSizes) {
            String text = generateRandomString(size, rnd);
            int[] sa = KasaiLCP.buildSuffixArray(text);

            long totalKasai = 0;
            for (int r = 0; r < 5; r++) {
                long start = System.nanoTime();
                KasaiLCP.computeLCP(text, sa);
                totalKasai += (System.nanoTime() - start);
            }
            double avgKasaiMs = (totalKasai / 5.0) / 1e6;
            System.out.printf("Kasai Text Size: %5d | Avg Time: %8.4f ms\n", size, avgKasaiMs);
        }
    }

    private static void runMatchingBench(String label, String text, String pattern) {
        long tNaive = 0, tKMP = 0, tRK = 0;
        MatchResult rNaive = null, rKMP = null, rRK = null;

        for (int r = 0; r < 5; r++) {
            long s1 = System.nanoTime();
            rNaive = NaiveMatching.search(text, pattern);
            tNaive += (System.nanoTime() - s1);

            long s2 = System.nanoTime();
            rKMP = KMPMatching.search(text, pattern);
            tKMP += (System.nanoTime() - s2);

            long s3 = System.nanoTime();
            rRK = RabinKarpMatching.search(text, pattern);
            tRK += (System.nanoTime() - s3);
        }

        System.out.printf("[%s] Naive: %7.3f ms (%d comps) | KMP: %7.3f ms (%d comps) | RK: %7.3f ms (%d comps)\n",
                label, (tNaive / 5.0) / 1e6, rNaive.comparisons, (tKMP / 5.0) / 1e6, rKMP.comparisons, (tRK / 5.0) / 1e6, rRK.comparisons);
    }
}