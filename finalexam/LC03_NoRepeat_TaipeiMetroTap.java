import java.util.*;

public class LC03_NoRepeat_TaipeiMetroTap {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入
        String s = scanner.nextLine();
        
        // 解決最長無重複字元子字串問題
        int result = lengthOfLongestSubstring(s);
        
        // 輸出結果
        System.out.println(result);
        
        scanner.close();
    }
    
    /**
     * 使用滑動視窗解決最長無重複字元子字串問題
     * 時間複雜度: O(n)
     * 空間複雜度: O(k) where k 是字元集大小
     * 
     * @param s 捷運刷卡流水字串
     * @return 最長不重複字元子字串的長度
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        // Map 儲存 <字元, 最後出現的索引>
        HashMap<Character, Integer> charIndexMap = new HashMap<>();
        
        int maxLength = 0;      // 記錄最長長度
        int left = 0;           // 滑動視窗左邊界
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // 如果當前字元已經在視窗中出現過
            if (charIndexMap.containsKey(currentChar)) {
                // 將左邊界移動到重複字元的下一個位置
                // 注意：左邊界只能向右移動，不能後退
                left = Math.max(left, charIndexMap.get(currentChar) + 1);
            }
            
            // 更新當前字元的最新索引
            charIndexMap.put(currentChar, right);
            
            // 更新最大長度
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}