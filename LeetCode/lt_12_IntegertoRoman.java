class lt_12_IntegertoRoman {
    public String intToRoman(int num) {
        // 定義羅馬數字的對應值和符號，按照從大到小的順序排列
        // 包含特殊情況：4(IV), 9(IX), 40(XL), 90(XC), 400(CD), 900(CM)
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        
        // 使用StringBuilder來構建結果字串，效率較高
        StringBuilder result = new StringBuilder();
        
        // 遍歷所有的值和對應的羅馬數字符號
        for (int i = 0; i < values.length; i++) {
            // 計算當前值可以使用多少次
            int count = num / values[i];
            
            // 如果可以使用（count > 0），則加入對應的羅馬數字符號
            if (count > 0) {
                // 將符號重複count次加入結果中
                for (int j = 0; j < count; j++) {
                    result.append(symbols[i]);
                }
                // 更新剩餘的數字
                num -= values[i] * count;
            }
        }
        
        return result.toString();
    }
}

/*
解題思路：
1. 羅馬數字有固定的表示規則，我們需要從最大的值開始處理
2. 關鍵是要包含特殊的減法情況：IV(4), IX(9), XL(40), XC(90), CD(400), CM(900)
3. 貪心算法：每次都用最大可能的羅馬數字來表示

時間複雜度：O(1) - 因為羅馬數字符號數量是固定的（13個）
空間複雜度：O(1) - 使用的額外空間是固定的

測試案例：
- intToRoman(3) → "III"
- intToRoman(4) → "IV" 
- intToRoman(9) → "IX"
- intToRoman(58) → "LVIII" (L=50, V=5, III=3)
- intToRoman(1994) → "MCMXCIV" (M=1000, CM=900, XC=90, IV=4)
*/