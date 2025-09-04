import java.util.*;

public class LC32_LongestValidParen_Metro {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入
        String s = scanner.nextLine();
        
        // 計算最長有效括號子字串長度
        int result = longestValidParentheses(s);
        
        // 輸出結果
        System.out.println(result);
        
        scanner.close();
    }
    
    /**
     * 使用索引堆疊法找到最長有效括號子字串
     * 時間複雜度: O(n)
     * 空間複雜度: O(n)
     * 
     * @param s 北捷閘門日誌字串
     * @return 最長有效括號配對長度
     */
    public static int longestValidParentheses(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        
        // 使用堆疊存儲索引
        Stack<Integer> stack = new Stack<>();
        // 初始化：將 -1 作為基準點推入堆疊
        stack.push(-1);
        
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                // 遇到進站事件，將索引推入堆疊
                stack.push(i);
            } else { // c == ')'
                // 遇到出站事件，嘗試配對
                stack.pop();
                
                if (stack.isEmpty()) {
                    // 堆疊為空，表示當前 ')' 無法配對
                    // 將當前索引作為新的基準點
                    stack.push(i);
                } else {
                    // 計算當前有效括號長度
                    int currentLength = i - stack.peek();
                    maxLength = Math.max(maxLength, currentLength);
                }
            }
        }
        
        return maxLength;
    }
    
    /**
     * 替代解法：雙向計數法
     * 時間複雜度: O(n)
     * 空間複雜度: O(1)
     * 
     * @param s 北捷閘門日誌字串
     * @return 最長有效括號配對長度
     */
    public static int longestValidParenthesesTwoPass(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        
        int maxLength = 0;
        
        // 第一遍：從左到右
        int left = 0, right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            
            if (left == right) {
                maxLength = Math.max(maxLength, 2 * right);
            } else if (right > left) {
                left = right = 0; // 重置計數
            }
        }
        
        // 第二遍：從右到左
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            
            if (left == right) {
                maxLength = Math.max(maxLength, 2 * left);
            } else if (left > right) {
                left = right = 0; // 重置計數
            }
        }
        
        return maxLength;
    }
}