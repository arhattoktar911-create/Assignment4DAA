package dp;

public class EditDistance {
    public static int compute(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                int deleteCost = dp[i - 1][j] + 1;
                int insertCost = dp[i][j - 1] + 1;
                int substituteCost = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(deleteCost, Math.min(insertCost, substituteCost));
            }
        }
        return dp[m][n];
    }
}