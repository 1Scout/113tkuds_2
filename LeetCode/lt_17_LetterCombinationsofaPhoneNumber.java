class lt_17_LetterCombinationsofaPhoneNumber {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        
        // 如果輸入為空，直接返回空列表
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        // 建立數字到字母的映射表（模擬手機按鍵）
        String[] phoneMap = {
            "",     // 0
            "",     // 1
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
        };
        
        // 開始回溯搜尋
        backtrack(result, new StringBuilder(), digits, 0, phoneMap);
        
        return result;
    }
    
    /**
     * 回溯函數
     * @param result 存放所有可能組合的結果列表
     * @param current 當前正在構建的字串
     * @param digits 輸入的數字字串
     * @param index 當前處理到第幾個數字
     * @param phoneMap 數字到字母的映射表
     */
    private void backtrack(List<String> result, StringBuilder current, 
                          String digits, int index, String[] phoneMap) {
        
        // 遞歸終止條件：已經處理完所有數字
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        
        // 取得當前數字對應的字母字串
        int digit = digits.charAt(index) - '0';
        String letters = phoneMap[digit];
        
        // 遍歷當前數字對應的所有字母
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            
            // 選擇：將當前字母加入組合
            current.append(letter);
            
            // 遞歸：處理下一個數字
            backtrack(result, current, digits, index + 1, phoneMap);
            
            // 撤銷選擇：回溯，移除最後加入的字母
            current.deleteCharAt(current.length() - 1);
        }
    }
}

/* 
另一種實現方式：使用迭代法
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        String[] phoneMap = {
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
        };
        
        result.add(""); // 初始化一個空字串
        
        // 逐個處理每個數字
        for (char digit : digits.toCharArray()) {
            List<String> temp = new ArrayList<>();
            String letters = phoneMap[digit - '0'];
            
            // 對於每個已存在的組合，加上當前數字的所有可能字母
            for (String combination : result) {
                for (char letter : letters.toCharArray()) {
                    temp.add(combination + letter);
                }
            }
            
            result = temp; // 更新結果列表
        }
        
        return result;
    }
}
*/

/*
解題思路：

方法一：回溯法（Backtracking）
1. 建立數字到字母的映射關係
2. 使用遞歸回溯，對每個數字的每個字母都嘗試一遍
3. 當處理完所有數字時，將當前組合加入結果
4. 回溯時撤銷選擇，嘗試其他可能

方法二：迭代法
1. 從空字串開始
2. 每次處理一個數字，將該數字對應的所有字母與現有的所有組合進行拼接
3. 逐步構建出所有可能的組合

時間複雜度：O(3^m × 4^n) - m是對應3個字母的數字個數，n是對應4個字母的數字個數
空間複雜度：O(3^m × 4^n) - 存儲所有可能的組合

範例：
輸入："23"
輸出：["ad","ae","af","bd","be","bf","cd","ce","cf"]

解釋：
2 -> "abc"
3 -> "def"
所有組合：a+d, a+e, a+f, b+d, b+e, b+f, c+d, c+e, c+f
*/