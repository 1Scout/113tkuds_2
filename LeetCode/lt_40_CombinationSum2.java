class Solution {
    // 主要解法：回溯算法 + 去重
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        // 排序是關鍵：有助於去重和剪枝
        Arrays.sort(candidates);
        
        backtrack(candidates, target, 0, path, result);
        return result;
    }
    
    private void backtrack(int[] candidates, int target, int start, 
                          List<Integer> path, List<List<Integer>> result) {
        // 基礎情況：找到一個合法組合
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            // 剪枝1：如果當前數字大於目標值，後面的更大數字都不用試
            if (candidates[i] > target) {
                break;
            }
            
            // 去重關鍵：跳過同一層級的重複元素
            // i > start 確保在同一遞迴層級中跳過重複元素
            // 但在不同層級（遞迴深度）中允許使用相同元素
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            
            // 做選擇：將當前數字加入路徑
            path.add(candidates[i]);
            
            // 遞迴：注意這裡傳入 i+1，因為每個數字只能使用一次
            backtrack(candidates, target - candidates[i], i + 1, path, result);
            
            // 撤銷選擇：回溯
            path.remove(path.size() - 1);
        }
    }
    
    // 替代解法：使用 visited 陣列（更直觀的去重理解）
    public List<List<Integer>> combinationSum2Visited(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[candidates.length];
        
        Arrays.sort(candidates);
        
        backtrackWithVisited(candidates, target, 0, path, result, used);
        return result;
    }
    
    private void backtrackWithVisited(int[] candidates, int target, int start,
                                     List<Integer> path, List<List<Integer>> result, boolean[] used) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target) break;
            
            // 去重邏輯：
            // 如果當前元素與前一個元素相同，且前一個元素沒有被使用過
            // 則跳過當前元素（避免同一層級的重複）
            if (i > 0 && candidates[i] == candidates[i - 1] && !used[i - 1]) {
                continue;
            }
            
            path.add(candidates[i]);
            used[i] = true;
            
            backtrackWithVisited(candidates, target - candidates[i], i + 1, path, result, used);
            
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }
    
    // 詳細註解版本：展示每一步的思考過程
    public List<List<Integer>> combinationSum2Detailed(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        // 步驟1：排序，將相同元素聚集在一起
        Arrays.sort(candidates);
        
        System.out.println("排序後的陣列: " + Arrays.toString(candidates));
        
        backtrackDetailed(candidates, target, 0, path, result, 0);
        return result;
    }
    
    private void backtrackDetailed(int[] candidates, int target, int start,
                                  List<Integer> path, List<List<Integer>> result, int depth) {
        // 打印當前狀態（調試用）
        String indent = "  ".repeat(depth);
        System.out.println(indent + "進入: target=" + target + ", start=" + start + ", path=" + path);
        
        if (target == 0) {
            System.out.println(indent + "找到解: " + path);
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target) {
                System.out.println(indent + "剪枝: " + candidates[i] + " > " + target);
                break;
            }
            
            // 詳細解釋去重邏輯
            if (i > start && candidates[i] == candidates[i - 1]) {
                System.out.println(indent + "跳過重複: candidates[" + i + "]=" + candidates[i] + 
                                 " == candidates[" + (i-1) + "]=" + candidates[i-1]);
                continue;
            }
            
            System.out.println(indent + "選擇: " + candidates[i]);
            path.add(candidates[i]);
            
            backtrackDetailed(candidates, target - candidates[i], i + 1, path, result, depth + 1);
            
            path.remove(path.size() - 1);
            System.out.println(indent + "回溯: 移除 " + candidates[i]);
        }
    }
    
    // 優化版本：提前統計每個數字的數量
    public List<List<Integer>> combinationSum2Optimized(int[] candidates, int target) {
        // 統計每個數字的出現次數
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : candidates) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        
        // 轉換為 (數字, 次數) 的陣列
        List<int[]> uniqueCandidates = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            uniqueCandidates.add(new int[]{entry.getKey(), entry.getValue()});
        }
        
        // 按數字大小排序
        uniqueCandidates.sort((a, b) -> a[0] - b[0]);
        
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        backtrackOptimized(uniqueCandidates, target, 0, path, result);
        return result;
    }
    
    private void backtrackOptimized(List<int[]> candidates, int target, int index,
                                   List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        if (index >= candidates.size() || target < 0) {
            return;
        }
        
        int[] current = candidates.get(index);
        int num = current[0];
        int maxCount = current[1];
        
        // 對於當前數字，嘗試使用 0 到 maxCount 次
        for (int count = 0; count <= maxCount; count++) {
            if (count * num > target) break;
            
            // 添加 count 個當前數字
            for (int i = 0; i < count; i++) {
                path.add(num);
            }
            
            // 遞迴處理下一個不同的數字
            backtrackOptimized(candidates, target - count * num, index + 1, path, result);
            
            // 移除添加的數字
            for (int i = 0; i < count; i++) {
                path.remove(path.size() - 1);
            }
        }
    }
}

/*
題目：組合總和 II

給定一個候選人編號的集合 candidates 和一個目標數 target，
找出 candidates 中所有可以使數字和為 target 的組合。

重要差異（與第39題對比）：
1. candidates 中每個數字在每個組合中只能使用一次
2. candidates 中可能包含重複數字
3. 解集不能包含重複的組合

核心挑戰：去重
需要避免產生重複的組合，如 [1,1,6] 和 [1,1,6] 應該只出現一次。

解法分析：

關鍵技巧 - 去重策略：
1. 先對陣列排序，讓相同元素相鄰
2. 在同一層級跳過重複元素：if (i > start && candidates[i] == candidates[i-1])
3. 在不同層級允許使用相同元素

為什麼 i > start 而不是 i > 0？
- i > start：只在同一遞迴層級跳過重複
- i > 0：會錯誤地跳過不同層級的相同元素

示例理解：
candidates = [1,1,2], target = 3

正確的組合：[1,2] （使用第1個1）
錯誤的組合：[1,2] （使用第2個1）- 這會產生重複

樹狀圖分析：
```
              []
           /  |  \
       [1]   [1]  [2]  <- 第一層：i=1時跳過第二個1
      /  \     |    |
  [1,1] [1,2] [1,2] [2,?] <- 第二層：允許使用第二個1
```

四種解法比較：

方法一：經典去重（推薦）
- 使用 i > start 條件去重
- 簡潔高效，最常用的解法

方法二：visited 陣列
- 使用布爾陣列追蹤使用狀態
- 更直觀理解去重邏輯
- 額外 O(n) 空間

方法三：詳細註解版
- 包含調試輸出，幫助理解遞迴過程
- 學習和調試時使用

方法四：統計優化版
- 預先統計每個數字的數量
- 避免重複元素的遍歷
- 適合重複元素很多的情況

時間複雜度：O(2^n)，最壞情況下每個元素都有選/不選兩種狀態
空間複雜度：O(target)，遞迴深度最大為目標值

常見錯誤：
1. 忘記排序：無法正確去重
2. 去重條件錯誤：i > 0 vs i > start
3. 遞迴參數錯誤：傳遞 i vs i+1

測試用例：
- candidates = [10,1,2,7,6,1,5], target = 8
- 輸出：[[1,1,6],[1,2,5],[1,7],[2,6]]

調試建議：
1. 使用詳細版本觀察遞迴過程
2. 手工畫出遞迴樹，理解去重邏輯
3. 檢查排序是否正確執行

這道題是回溯 + 去重的經典問題，掌握去重技巧對解決類似問題非常重要。
*/