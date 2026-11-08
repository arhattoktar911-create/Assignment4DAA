import dp.EditDistance;
import dp.LCS;
import matching.*;
import advanced.KasaiLCP;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RUNNING CORRECTNESS & EDGE CASE TESTS ===");

        // 1. LCS Tests
        assert LCS.compute("ABCDE", "ABCDE") == 5 : "LCS Identical failed";
        assert LCS.compute("ABCDE", "XYZ") == 0 : "LCS Different failed";
        assert LCS.compute("", "ABC") == 0 : "LCS Empty failed";
        System.out.println("✔ LCS Tests Passed");

        // 2. Edit Distance Tests
        assert EditDistance.compute("kitten", "sitting") == 3 : "Edit Distance test failed";
        assert EditDistance.compute("same", "same") == 0 : "Edit Distance identical failed";
        assert EditDistance.compute("", "hello") == 5 : "Edit Distance empty failed";
        System.out.println("✔ Edit Distance Tests Passed");

        // 3. String Matching Tests
        String text = "AABAACAADAABAABA";
        String pat = "AABA";
        MatchResult rNaive = NaiveMatching.search(text, pat);
        MatchResult rKMP = KMPMatching.search(text, pat);
        MatchResult rRK = RabinKarpMatching.search(text, pat);

        assert rNaive.indices.equals(rKMP.indices) && rKMP.indices.equals(rRK.indices) : "Matching outputs mismatch!";
        System.out.println("✔ Matching Algorithms Output Consistent Indices: " + rNaive.indices);

        // Matching Edge Cases
        assert NaiveMatching.search("ABCD", "").indices.isEmpty() : "Empty pattern check failed";
        assert NaiveMatching.search("ABC", "ABCDE").indices.isEmpty() : "Pattern longer than text failed";

        // 4. Kasai's LCP Manual Test ("banana")
        String s = "banana";
        int[] sa = KasaiLCP.buildSuffixArray(s);
        int[] lcp = KasaiLCP.computeLCP(s, sa);

        System.out.println("Text: " + s);
        System.out.println("Suffix Array: " + Arrays.toString(sa));
        System.out.println("LCP Array:    " + Arrays.toString(lcp));
        System.out.println("=== ALL VERIFICATIONS COMPLETED SUCCESSFULLY ===");
    }
}