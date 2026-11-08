package matching;

import java.util.ArrayList;
import java.util.List;

public class NaiveMatching {
    public static MatchResult search(String text, String pattern) {
        List<Integer> indices = new ArrayList<>();
        long comparisons = 0;

        if (text == null || pattern == null) return new MatchResult(indices, comparisons);
        int n = text.length();
        int m = pattern.length();

        if (m == 0 || m > n) return new MatchResult(indices, comparisons);

        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m) {
                comparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
                j++;
            }
            if (j == m) {
                indices.add(i);
            }
        }
        return new MatchResult(indices, comparisons);
    }
}