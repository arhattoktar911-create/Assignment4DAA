package matching;

import java.util.ArrayList;
import java.util.List;

public class KMPMatching {
    public static MatchResult search(String text, String pattern) {
        List<Integer> indices = new ArrayList<>();
        long comparisons = 0;

        if (text == null || pattern == null) return new MatchResult(indices, comparisons);
        int n = text.length();
        int m = pattern.length();

        if (m == 0 || m > n) return new MatchResult(indices, comparisons);

        int[] lps = new int[m];
        int len = 0;
        int i = 1;
        while (i < m) {
            comparisons++;
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        i = 0;
        int j = 0;
        while (i < n) {
            comparisons++;
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == m) {
                indices.add(i - j);
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return new MatchResult(indices, comparisons);
    }
}