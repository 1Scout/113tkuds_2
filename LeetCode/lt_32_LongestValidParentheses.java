class Solution {
    // 方法一：動態規劃解法
    public int longestValidParentheses(String s) {
        int n = s.length();
        if (n <= 1) return 0;
        
        // dp[i] 表示以 s[i] 結尾的最長有效括號子字串長度
        int[] dp = new int[n];
        int maxLen = 0;
        
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // 情況1：...() 直接配對
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (dp[i - 1] > 0) {
                    // 情況2：...)) 需要檢查是否能形成新的配對
                    // 找到與當前 ')' 可能配對的 '(' 的位置
                    int matchIndex = i - dp[i - 1] - 1;
                    if (matchIndex >= 0 && s.charAt(matchIndex) == '(') {
                        dp[i] = dp[i - 1] + 2 + (matchIndex > 0 ? dp[matchIndex - 1] : 0);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }
        
        return maxLen;
    }
    
    // 方法二：使用堆疊解法（另一種思路）
    public int longestValidParenthesesStack(String s) {
        int n = s.length();
        if (n <= 1) return 0;
        
        // 用堆疊儲存索引
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // 初始化底部邊界
        int maxLen = 0;
        
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                // 遇到左括號，將索引推入堆疊
                stack.push(i);
            } else {
                // 遇到右括號，彈出堆疊頂部
                stack.pop();
                if (stack.isEmpty()) {
                    // 堆疊為空，說明沒有匹配的左括號
                    // 將當前索引作為新的邊界
                    stack.push(i);
                } else {
                    // 計算當前有效括號的長度
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        
        return maxLen;
    }
    
    // 方法三：兩次遍歷解法（最優空間複雜度）
    public int longestValidParenthesesTwoPass(String s) {
        int left = 0, right = 0, maxLen = 0;
        
        // 從左到右遍歷
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            
            if (left == right) {
                // 左右括號數量相等，形成有效括號
                maxLen = Math.max(maxLen, 2 * right);
            } else if (right > left) {
                // 右括號多於左括號，重置計數
                left = right = 0;
            }
        }
        
        left = right = 0;
        
        // 從右到左遍歷（處理左括號過多的情況）
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            
            if (left == right) {
                maxLen = Math.max(maxLen, 2 * left);
            } else if (left > right) {
                // 左括號多於右括號，重置計數
                left = right = 0;
            }
        }
        
        return maxLen;
    }
}

/*
題目：最長有效括號

給定一個只包含 '(' 和 ')' 的字串，找出最長有效（格式正確且連續）括號子字串的長度。

三種解法比較：

1. 動態規劃解法：
   - 時間複雜度：O(n)
   - 空間複雜度：O(n)
   - 思路：dp[i] 表示以 s[i] 結尾的最長有效括號長度
   - 狀態轉移：
     * 若 s[i] == ')'：
       - 若 s[i-1] == '('：dp[i] = dp[i-2] + 2
       - 若 s[i-1] == ')' 且 dp[i-1] > 0：
         檢查能否形成更大的有效括號組合

2. 堆疊解法：
   - 時間複雜度：O(n)
   - 空間複雜度：O(n)
   - 思路：使用堆疊儲存索引，追蹤有效括號的邊界

3. 兩次遍歷解法：
   - 時間複雜度：O(n)
   - 空間複雜度：O(1)
   - 思路：分別從左到右和從右到左計算，避免單向遍歷的局限性

測試範例：
- 輸入："(()" → 輸出：2
- 輸入：")()())" → 輸出：4
- 輸入："" → 輸出：0
- 輸入："()(()" → 輸出：2

推薦使用動態規劃解法，邏輯清晰且易於理解。
*/