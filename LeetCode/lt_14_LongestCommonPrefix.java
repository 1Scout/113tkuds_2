class lt_14_LongestCommonPrefix{
    public String longestCommonPrefix(String[] strs) {
        // 如果輸入陣列為空或為 null，則沒有公共前綴
        if (strs == null || strs.length == 0) return "";

        // 外層迴圈：逐字元檢查第一個字串的每個位置 j
        for (int j = 0; j < strs[0].length(); ++j) {
            // 內層迴圈：比較所有字串在第 j 個字元是否相同
            for (int i = 0; i < strs.length; ++i) {
                // 檢查條件：
                // 1. 當前字串長度不夠長 (j 已經超出該字串長度)
                // 2. 當前字串在第 j 個字元 與 第一個字串對應字元不同
                if (j >= strs[i].length() || strs[i].charAt(j) != strs[0].charAt(j)) {
                    // 回傳該字串從 0 到 j (不包含 j) 的子字串
                    // 這段區間就是目前找到的最長公共前綴
                    return strs[i].substring(0, j); 
                }   
            }
        }

        // 如果整個第一個字串都符合，那麼它就是最長公共前綴
        return strs[0];
    }
}
