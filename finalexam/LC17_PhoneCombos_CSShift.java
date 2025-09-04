import java.util.*;

public class LC17_PhoneCombos_CSShift {
    // 電話鍵盤映射表
    private static final String[] PHONE_MAP = {
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
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入
        String digits = scanner.nextLine().trim();
        
        // 產生所有字母組合
        List<String> result = letterCombinations(digits);
        
        // 輸出結果
        for (String combination : result) {
            System.out.println(combination);
        }
        
        scanner.close();
    }
    
    /**
     * 產生電話號碼的所有字母組合
     * 時間複雜度: O(3^n * 4^m) 其中n是對應3個字母的數字個數，m是對應4個字母的數字個數
     * 空間複雜度: O(3^n * 4^m) 用於存儲結果
     * 
     * @param digits 數字字串
     * @return 所有可能的字母組合
     */
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        
        // 處理空字串情況
        if (digits == null || digits.length() == 0) {
            return result;
        }
        
        // 使用回溯法生成所有組合
        backtrack(digits, 0, new StringBuilder(), result);
        
        return result;
    }
    
    /**
     * 回溯法生成字母組合
     * 
     * @param digits 原始數字字串
     * @param index 當前處理的數字索引
     * @param current 當前正在建構的字母組合
     * @param result 結果列表
     */
    private static void backtrack(String digits, int index, StringBuilder current, List<String> result) {
        // 基礎情況：已處理完所有數字
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        
        // 獲取當前數字對應的字母
        char digit = digits.charAt(index);
        String letters = PHONE_MAP[digit - '0'];
        
        // 嘗試當前數字的每個對應字母
        for (char letter : letters.toCharArray()) {
            // 選擇：添加當前字母
            current.append(letter);
            
            // 遞歸：處理下一個數字
            backtrack(digits, index + 1, current, result);
            
            // 回溯：移除剛添加的字母
            current.deleteCharAt(current.length() - 1);
        }
    }
}