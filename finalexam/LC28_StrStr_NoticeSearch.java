import java.util.*;

/**
 * LC28_StrStr_NoticeSearch
 * 題目：在 haystack 中尋找 needle 第一次出現的索引。
 * 方法：KMP (Knuth-Morris-Pratt)，利用失敗函數 (lps) 陣列。
 * 時間複雜度：O(n+m)，n=haystack 長度, m=needle 長度
 */
public class LC28_StrStr_NoticeSearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String haystack = sc.hasNextLine() ? sc.nextLine() : "";
        String needle = sc.hasNextLine() ? sc.nextLine() : "";
        sc.close();

        int index = strStr(haystack, needle);
        System.out.println(index);
    }

    // 主函式：回傳 needle 在 haystack 的首個索引，否則 -1
    static int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) return 0;        // needle 空字串 → 回 0
        if (m > n) return -1;        // needle 長於 haystack → -1

        // 建立 KMP 失敗函數 (longest prefix suffix)
        int[] lps = buildLPS(needle);

        int i = 0, j = 0; // i=haystack 指標, j=needle 指標
        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    return i - m; // 完全比對成功
                }
            } else {
                if (j > 0) {
                    j = lps[j - 1]; // 回退到前一個可能前綴
                } else {
                    i++;
                }
            }
        }
        return -1;
    }

    // 建立 needle 的 LPS 陣列
    static int[] buildLPS(String pat) {
        int m = pat.length();
        int[] lps = new int[m];
        int len = 0; // 目前最長前綴後綴長度
        for (int i = 1; i < m; ) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len > 0) {
                    len = lps[len - 1]; // 回退
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}
