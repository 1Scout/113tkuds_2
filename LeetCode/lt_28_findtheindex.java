class Solution {
    public int strStr(String haystack, String needle) {
        // needle 為空字串時，根據題意回傳 0
        if (needle.isEmpty()) return 0;

        // m 為母字串長度, n 為子字串長度
        int m = haystack.length(), n = needle.length();

        // 只需要檢查到 m - n 的位置，避免越界
        for (int i = 0; i <= m - n; ++i) {
            // 取出母字串中長度為 n 的子字串
            String sub = haystack.substring(i, i + n);

            // 如果子字串與 needle 相同，回傳起始位置 i
            if (sub.equals(needle)) {
                return i;
            }
        }

        // 沒有找到則回傳 -1
        return -1;
    }
}
