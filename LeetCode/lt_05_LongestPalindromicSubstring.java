// Longest Palindromic Substring - LeetCode Problem
public class lt_05_LongestPalindromicSubstring {
    // Global variables to store the result
    private int start = 0;
    private int maxLen = 0;
    
    public String longestPalindrome(String s) {
        if (s.length() < 2) return s;
        
        int n = s.length();
        start = 0;
        maxLen = 0;
        
        for (int i = 0; i < n - 1; i++) {
            // Check for odd-length palindromes (center at i)
            searchPalindrome(s, i, i);
            // Check for even-length palindromes (center between i and i+1)
            searchPalindrome(s, i, i + 1);
        }
        
        return s.substring(start, start + maxLen);
    }
    
    private void searchPalindrome(String s, int left, int right) {
        // Expand around center while characters match
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        
        // Update global maximum if current palindrome is longer
        if (maxLen < right - left - 1) {
            start = left + 1;
            maxLen = right - left - 1;
        }
    }
    
    // Test method
    public static void main(String[] args) {
        lt_05_LongestPalindromicSubstring solution = new lt_05_LongestPalindromicSubstring();
        
        System.out.println("Testing Longest Palindromic Substring:");
        System.out.println("=====================================");
        
        // Test cases
        String[] testCases = {
            "babad",
            "cbbd", 
            "a",
            "ac",
            "racecar",
            "noon",
            "abcdef"
        };
        
        for (String test : testCases) {
            String result = solution.longestPalindrome(test);
            System.out.println("Input: \"" + test + "\" -> Output: \"" + result + "\"");
            
            // Reset for next test (since we're using instance variables)
            solution.start = 0;
            solution.maxLen = 0;
        }
    }
}