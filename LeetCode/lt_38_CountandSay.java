class Solution {
    // 主要解法：迭代生成
    public String countAndSay(int n) {
        // 基礎情況：第1個序列是"1"
        String result = "1";
        
        // 從第2個序列開始，迭代生成到第n個
        for (int i = 2; i <= n; i++) {
            result = getNextSequence(result);
        }
        
        return result;
    }
    
    // 根據當前序列生成下一個序列
    private String getNextSequence(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        
        while (i < s.length()) {
            char currentChar = s.charAt(i);
            int count = 1;
            
            // 計算相同字符的連續個數
            while (i + count < s.length() && s.charAt(i + count) == currentChar) {
                count++;
            }
            
            // 添加"個數 + 字符"到結果中
            sb.append(count).append(currentChar);
            
            // 移動到下一組不同的字符
            i += count;
        }
        
        return sb.toString();
    }
    
    // 替代解法：遞迴版本
    public String countAndSayRecursive(int n) {
        // 基礎情況
        if (n == 1) {
            return "1";
        }
        
        // 遞迴獲取前一個序列，然後生成當前序列
        String previous = countAndSayRecursive(n - 1);
        return generateNext(previous);
    }
    
    // 遞迴版本的序列生成方法
    private String generateNext(String s) {
        if (s.length() == 0) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        char currentChar = s.charAt(0);
        int count = 1;
        
        // 計算第一個字符的出現次數
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == currentChar) {
                count++;
            } else {
                // 遇到不同字符，記錄當前組並開始新組
                result.append(count).append(currentChar);
                currentChar = s.charAt(i);
                count = 1;
            }
        }
        
        // 處理最後一組字符
        result.append(count).append(currentChar);
        
        return result.toString();
    }
    
    // 優化版本：使用字符陣列減少字符串拼接開銷
    public String countAndSayOptimized(int n) {
        if (n == 1) return "1";
        
        char[] current = {'1'};
        
        for (int i = 2; i <= n; i++) {
            current = generateNextArray(current);
        }
        
        return new String(current);
    }
    
    private char[] generateNextArray(char[] chars) {
        List<Character> result = new ArrayList<>();
        int i = 0;
        
        while (i < chars.length) {
            char currentChar = chars[i];
            int count = 1;
            
            // 計算相同字符的個數
            while (i + count < chars.length && chars[i + count] == currentChar) {
                count++;
            }
            
            // 將個數轉換為字符並添加
            result.add((char)('0' + count));
            result.add(currentChar);
            
            i += count;
        }
        
        // 轉換為字符陣列
        char[] resultArray = new char[result.size()];
        for (int j = 0; j < result.size(); j++) {
            resultArray[j] = result.get(j);
        }
        
        return resultArray;
    }
    
    // 完整示例：展示前幾個序列的生成過程
    public void demonstrateSequence(int n) {
        System.out.println("Count and Say 序列展示：");
        String current = "1";
        
        for (int i = 1; i <= n; i++) {
            System.out.println("第" + i + "個序列: " + current);
            
            if (i < n) {
                // 顯示生成過程
                String next = getNextSequence(current);
                System.out.println("  解讀 \"" + current + "\":");
                explainSequence(current);
                System.out.println("  生成: \"" + next + "\"");
                System.out.println();
                current = next;
            }
        }
    }
    
    // 輔助方法：解釋序列的讀法
    private void explainSequence(String s) {
        int i = 0;
        while (i < s.length()) {
            char currentChar = s.charAt(i);
            int count = 1;
            
            while (i + count < s.length() && s.charAt(i + count) == currentChar) {
                count++;
            }
            
            System.out.println("    " + count + "個" + currentChar);
            i += count;
        }
    }
}

/*
題目：外觀數列（報數序列）

「外觀數列」是一個整數序列，從數字 1 開始，序列中的每一項都是對前一項的描述。

序列規律：
- 第1項：1
- 第2項：11（讀作"一個1"）
- 第3項：21（讀作"兩個1"）  
- 第4項：1211（讀作"一個2，一個1"）
- 第5項：111221（讀作"一個1，一個2，兩個1"）
- ...

核心思路：
每一項都是對前一項的"計數描述"，即統計相同數字的連續個數。

算法步驟：
1. 從 "1" 開始
2. 對當前字符串進行"計數和描述"：
   - 統計連續相同字符的個數
   - 按"個數+字符"的格式構建新字符串
3. 重複 n-1 次

實現方法分析：

方法一：迭代生成（推薦）
- 時間複雜度：O(M)，M是最終字符串長度
- 空間複雜度：O(M)
- 優勢：直觀易懂，空間效率較好

方法二：遞迴版本
- 時間複雜度：O(M)
- 空間複雜度：O(M) + 遞迴堆疊 O(n)
- 優勢：邏輯清晰，符合問題的遞迴性質

方法三：字符陣列優化
- 減少字符串拼接的開銷
- 適用於對性能要求較高的場景

關鍵技巧：
1. 使用雙指標技巧統計連續字符
2. StringBuilder 提高字符串拼接效率  
3. 處理邊界條件（最後一組字符）

序列增長特性：
- 序列長度增長很快（大約每次增長1.3倍）
- 對於 n=30，結果字符串可能有數千個字符
- 實際應用中需要考慮內存限制

測試案例：
- n=1: "1"
- n=2: "11" 
- n=3: "21"
- n=4: "1211"
- n=5: "111221"
- n=6: "312211"

注意事項：
1. 題目保證 1 ≤ n ≤ 30
2. 序列中只會出現數字 1, 2, 3
3. 每次統計時要處理連續相同的字符
4. 注意最後一組字符的處理

這道題考查的是字符串處理和模擬能力，理解規律後實現並不複雜。
*/