import java.util.*;

public class LC20_ValidParentheses_AlertFormat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入
        String s = scanner.nextLine();
        
        // 驗證括號格式是否正確
        boolean result = isValid(s);
        
        // 輸出結果
        System.out.println(result);
        
        scanner.close();
    }
    
    /**
     * 驗證括號字串是否合法
     * 時間複雜度: O(n)
     * 空間複雜度: O(n)
     * 
     * @param s 災防通報格式字串
     * @return 括號配對是否正確
     */
    public static boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true; // 空字串視為合法
        }
        
        // 如果字串長度是奇數，必定不合法
        if (s.length() % 2 == 1) {
            return false;
        }
        
        // 建立閉括號到開括號的映射
        HashMap<Character, Character> closeToOpen = new HashMap<>();
        closeToOpen.put(')', '(');  // 可選段
        closeToOpen.put(']', '[');  // 區域代碼群組
        closeToOpen.put('}', '{');  // 變數替換區
        
        // 使用堆疊追蹤開括號
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (isOpenBracket(c)) {
                // 遇到開括號，推入堆疊
                stack.push(c);
            } else if (isCloseBracket(c)) {
                // 遇到閉括號，檢查配對
                if (stack.isEmpty()) {
                    // 沒有對應的開括號
                    return false;
                }
                
                char topOpen = stack.pop();
                char expectedOpen = closeToOpen.get(c);
                
                if (topOpen != expectedOpen) {
                    // 括號類型不匹配
                    return false;
                }
            }
            // 如果不是括號字元，忽略（題目保證只含括號）
        }
        
        // 最後檢查是否還有未配對的開括號
        return stack.isEmpty();
    }
    
    /**
     * 檢查是否為開括號
     * @param c 字元
     * @return 是否為開括號
     */
    private static boolean isOpenBracket(char c) {
        return c == '(' || c == '[' || c == '{';
    }
    
    /**
     * 檢查是否為閉括號
     * @param c 字元
     * @return 是否為閉括號
     */
    private static boolean isCloseBracket(char c) {
        return c == ')' || c == ']' || c == '}';
    }
}