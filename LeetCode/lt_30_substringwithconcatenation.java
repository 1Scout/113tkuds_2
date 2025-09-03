import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return res;
        }

        int wordLen = words[0].length();   // 每個單字的長度 (題目保證一致)
        int wordCount = words.length;      // 單字數量
        int totalLen = wordLen * wordCount; // 所有單字串接後的總長度

        // 建立 words 的詞頻表
        Map<String, Integer> wordMap = new HashMap<>();
        for (String w : words) {
            wordMap.put(w, wordMap.getOrDefault(w, 0) + 1);
        }

        // 由於單字長度固定，為了避免錯過，需嘗試 wordLen 種偏移量
        for (int i = 0; i < wordLen; i++) {
            int left = i;                  // 視窗左邊界
            int count = 0;                 // 當前匹配的單字數
            Map<String, Integer> windowMap = new HashMap<>();

            // 以 wordLen 為單位向右移動
            for (int right = i; right + wordLen <= s.length(); right += wordLen) {
                String sub = s.substring(right, right + wordLen);

                // 如果 sub 在 words 中
                if (wordMap.containsKey(sub)) {
                    windowMap.put(sub, windowMap.getOrDefault(sub, 0) + 1);
                    count++;

                    // 如果某單字出現次數超過應有的次數，縮小左邊界
                    while (windowMap.get(sub) > wordMap.get(sub)) {
                        String leftWord = s.substring(left, left + wordLen);
                        windowMap.put(leftWord, windowMap.get(leftWord) - 1);
                        left += wordLen;
                        count--;
                    }

                    // 當匹配的單字數剛好等於 wordCount，紀錄起始位置
                    if (count == wordCount) {
                        res.add(left);
                    }
                } else {
                    // sub 不在 words，清空視窗並重置計數
                    windowMap.clear();
                    count = 0;
                    left = right + wordLen;
                }
            }
        }

        return res;
    }
}
