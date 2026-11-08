package matching;

import java.util.ArrayList;
import java.util.List;

public class RabinKarpMatching {
    private static final int D = 256;
    private static final int Q = 101; // ПPrime modulus

    public static MatchResult search(String text, String pattern) {
        List<Integer> indices = new ArrayList<>();
        long comparisons = 0;

        if (text == null || pattern == null) return new MatchResult(indices, comparisons);
        int n = text.length();
        int m = pattern.length();

        if (m == 0 || m > n) return new MatchResult(indices, comparisons);

        int pHash = 0;
        int tHash = 0;
        int h = 1;

        for (int i = 0; i < m - 1; i++) {
            h = (h * D) % Q;
        }

        for (int i = 0; i < m; i++) {
            pHash = (D * pHash + pattern.charAt(i)) % Q;
            tHash = (D * tHash + text.charAt(i)) % Q;
        }

        for (int i = 0; i <= n - m; i++) {
            if (pHash == tHash) {
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    comparisons++;
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    indices.add(i);
                }
            }

            if (i < n - m) {
                tHash = (D * (tHash - text.charAt(i) * h) + text.charAt(i + m)) % Q;
                if (tHash < 0) {
                    tHash = tHash + Q;
                }
            }
        }
        return new MatchResult(indices, comparisons);
    }
}