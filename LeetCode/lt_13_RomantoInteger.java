import java.util.HashMap;
import java.util.Map;

class lt_13_RomantoInteger {
    public int romanToInt(String s) {
        // 建立羅馬數字到整數的映射表
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
        
        int result = 0;
        int prevValue = 0;  // 記錄前一個字符的值
        
        // 從右到左遍歷字串
        for (int i = s.length() - 1; i >= 0; i--) {
            char currentChar = s.charAt(i);
            int currentValue = romanMap.get(currentChar);
            
            // 如果當前值小於前一個值，表示這是減法情況（如IV中的I）
            if (currentValue < prevValue) {
                result -= currentValue;  // 減去當前值
            } else {
                result += currentValue;  // 加上當前值
            }
            
            // 更新前一個值為當前值
            prevValue = currentValue;
        }
        
        return result;
    }
}

/*
方法二：更直觀的解法（從左到右遍歷）
*/
class Solution2 {
    public int romanToInt(String s) {
        // 建立羅馬數字到整數的映射表
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
        
        int result = 0;
        
        // 從左到右遍歷字串
        for (int i = 0; i < s.length(); i++) {
            int currentValue = romanMap.get(s.charAt(i));
            
            // 如果不是最後一個字符，且當前值小於下一個值
            // 表示這是減法情況（如IV、IX、XL等）
            if (i < s.length() - 1 && currentValue < romanMap.get(s.charAt(i + 1))) {
                result -= currentValue;  // 減去當前值
            } else {
                result += currentValue;  // 加上當前值
            }
        }
        
        return result;
    }
}

/*
解題思路：
羅馬數字的規則：
- 通常情況下，羅馬數字是從左到右遞減的，直接相加即可
- 特殊情況：當左邊的數字小於右邊時，表示減法（如IV=4, IX=9, XL=40等）

方法一（推薦）：從右到左遍歷
- 比較當前值和前一個值的大小關係
- 如果當前值 < 前一個值，則減去當前值
- 否則加上當前值

方法二：從左到右遍歷  
- 比較當前值和下一個值的大小關係
- 如果當前值 < 下一個值，則減去當前值
- 否則加上當前值

時間複雜度：O(n) - n為字串長度
空間複雜度：O(1) - HashMap大小固定為7

測試案例：
- romanToInt("III") → 3
- romanToInt("IV") → 4
- romanToInt("IX") → 9  
- romanToInt("LVIII") → 58 (L=50, V=5, III=3)
- romanToInt("MCMXCIV") → 1994 (M=1000, CM=900, XC=90, IV=4)
*/