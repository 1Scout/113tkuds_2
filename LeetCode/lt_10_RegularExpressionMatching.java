
class lt_10_RegularExpressionMatching{
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // dp[i][j] represents whether s[0...i-1] matches p[0...j-1]
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // Empty string matches empty pattern
        dp[0][0] = true;
        
        // Handle patterns like a*, a*b*, a*b*c* that can match empty string
        for (int j = 2; j <= n; j += 2) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);
                
                if (pc == '*') {
                    // Two cases for '*':
                    // 1. Match zero occurrences: dp[i][j-2]
                    // 2. Match one or more occurrences: dp[i-1][j] if characters match
                    dp[i][j] = dp[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    // Direct character match or '.' match
                    if (matches(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        
        return dp[m][n];
    }
    
    private boolean matches(String s, String p, int i, int j) {
        char sc = s.charAt(i - 1);
        char pc = p.charAt(j - 1);
        return pc == '.' || sc == pc;
    }
}
