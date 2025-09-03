class Solution {

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        // 開始回溯，初始狀態：空字串，0個左括號，0個右括號
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }
    
    /**
     * 回溯函數
     * @param result 存放所有有效組合的結果列表
     * @param current 當前正在構建的括號字串
     * @param open 目前已使用的左括號數量
     * @param close 目前已使用的右括號數量
     * @param n 括號對數
     */
    private void backtrack(List<String> result, StringBuilder current, 
                          int open, int close, int n) {
        
        // 遞歸終止條件：已經使用了n對括號（總共2n個字符）
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }
        
        // 選擇1：添加左括號
        // 條件：左括號數量還沒達到n個
        if (open < n) {
            current.append('(');           // 做選擇
            backtrack(result, current, open + 1, close, n);  // 遞歸
            current.deleteCharAt(current.length() - 1);      // 撤銷選擇
        }
        
        // 選擇2：添加右括號
        // 條件：右括號數量少於左括號數量（保證括號匹配）
        if (close < open) {
            current.append(')');           // 做選擇
            backtrack(result, current, open, close + 1, n);  // 遞歸
            current.deleteCharAt(current.length() - 1);      // 撤銷選擇
        }
    }
}
/*
解題思路：
1. 使用深度優先搜索遍歷所有可能的組合
2. 維護左括號和右括號的數量
3. 剪枝條件：
   - 左括號數量不能超過n
   - 右括號數量不能超過左括號數量
4. 當總長度達到2n時，找到一個有效組合

核心思想：
1. 有效括號的特點：
   - 任意前綴中，左括號數量 >= 右括號數量
   - 總的左括號數量 = 右括號數量 = n

2. 搜索空間剪枝：
   - 左括號數量超過n時停止
   - 右括號數量超過左括號數量時停止

時間複雜度：
- 回溯法：O(4^n / √n) - 這是卡特蘭數的漸進表達
- 動態規劃：O(4^n / √n)
- 空間複雜度：O(4^n / √n) - 存儲所有結果

卡特蘭數：
第n個卡特蘭數表示n對括號的有效組合數量
C(n) = (2n)! / ((n+1)! * n!) = (1/(n+1)) * C(2n, n)

測試範例：
n = 1: ["()"]
n = 2: ["(())","()()"]
n = 3: ["((()))","(()())","(())()","()(())","()()()"]

回溯過程示例（n=2）：
1. "" -> "(" -> "((" -> "(()" -> "(())"
2. "" -> "(" -> "((" -> "(()" -> "(())" (已完成)
3. "" -> "(" -> "()" -> "()(" -> "()()"
4. "" -> "(" -> "()" -> "()(" -> "()()" (已完成)
*/