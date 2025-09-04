class Solution {
    // 主要解法：回溯算法
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        // 對陣列排序，有助於剪枝優化
        Arrays.sort(candidates);
        
        backtrack(candidates, target, 0, path, result);
        return result;
    }
    
    // 回溯方法
    private void backtrack(int[] candidates, int target, int start, 
                          List<Integer> path, List<List<Integer>> result) {
        // 基礎情況：找到一個合法組合
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        // 遍歷候選數字
        for (int i = start; i < candidates.length; i++) {
            // 剪枝：如果當前數字已經大於目標值，後面的更大數字都不用試
            if (candidates[i] > target) {
                break;
            }
            
            // 做選擇：將當前數字加入路徑
            path.add(candidates[i]);
            
            // 遞迴：注意這裡傳入的是 i 而不是 i+1，因為可以重複使用
            backtrack(candidates, target - candidates[i], i, path, result);
            
            // 撤銷選擇：回溯
            path.remove(path.size() - 1);
        }
    }
    
    // 替代解法：動態規劃（較複雜，但展示不同思路）
    public List<List<Integer>> combinationSumDP(int[] candidates, int target) {
        // dp[i] 表示目標值為 i 時所有可能的組合
        List<List<List<Integer>>> dp = new ArrayList<>();
        
        // 初始化
        for (int i = 0; i <= target; i++) {
            dp.add(new ArrayList<>());
        }
        
        // 目標值為0時，有一個空組合
        dp.get(0).add(new ArrayList<>());
        
        // 對每個目標值計算可能的組合
        for (int i = 1; i <= target; i++) {
            for (int candidate : candidates) {
                if (candidate <= i && !dp.get(i - candidate).isEmpty()) {
                    // 基於 dp[i - candidate] 的結果構建新組合
                    for (List<Integer> combination : dp.get(i - candidate)) {
                        // 只添加不小於組合中最大元素的候選數字，避免重複
                        if (combination.isEmpty() || candidate >= combination.get(combination.size() - 1)) {
                            List<Integer> newCombination = new ArrayList<>(combination);
                            newCombination.add(candidate);
                            dp.get(i).add(newCombination);
                        }
                    }
                }
            }
        }
        
        return dp.get(target);
    }
    
    // 迭代版本的回溯（使用堆疊模擬遞迴）
    public List<List<Integer>> combinationSumIterative(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        
        // 使用堆疊來模擬遞迴過程
        // 每個元素包含：當前路徑、剩餘目標值、起始索引
        Stack<State> stack = new Stack<>();
        stack.push(new State(new ArrayList<>(), target, 0));
        
        while (!stack.isEmpty()) {
            State current = stack.pop();
            
            // 找到一個合法組合
            if (current.target == 0) {
                result.add(new ArrayList<>(current.path));
                continue;
            }
            
            // 嘗試所有可能的候選數字
            for (int i = current.start; i < candidates.length; i++) {
                if (candidates[i] > current.target) {
                    break; // 剪枝
                }
                
                // 創建新狀態
                List<Integer> newPath = new ArrayList<>(current.path);
                newPath.add(candidates[i]);
                stack.push(new State(newPath, current.target - candidates[i], i));
            }
        }
        
        return result;
    }
    
    // 輔助類：用於迭代版本
    class State {
        List<Integer> path;
        int target;
        int start;
        
        State(List<Integer> path, int target, int start) {
            this.path = path;
            this.target = target;
            this.start = start;
        }
    }
    
    // 優化版本：提前計算最小值，更好的剪枝
    public List<List<Integer>> combinationSumOptimized(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        // 排序並過濾掉大於目標值的數字
        Arrays.sort(candidates);
        int validLength = 0;
        for (int i = 0; i < candidates.length && candidates[i] <= target; i++) {
            validLength++;
        }
        
        if (validLength == 0) return result;
        
        // 創建只包含有效候選數字的陣列
        int[] validCandidates = Arrays.copyOf(candidates, validLength);
        
        backtrackOptimized(validCandidates, target, 0, path, result);
        return result;
    }
    
    private void backtrackOptimized(int[] candidates, int target, int start, 
                                   List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target) break;
            
            path.add(candidates[i]);
            // 優化：如果剩餘目標值能被當前數字整除，直接添加對應數量
            int quotient = target / candidates[i];
            if (target % candidates[i] == 0) {
                // 可以完全用當前數字湊成目標值
                for (int j = 1; j < quotient; j++) {
                    path.add(candidates[i]);
                }
                result.add(new ArrayList<>(path));
                // 清理添加的數字
                for (int j = 1; j < quotient; j++) {
                    path.remove(path.size() - 1);
                }
            }
            
            backtrackOptimized(candidates, target - candidates[i], i, path, result);
            path.remove(path.size() - 1);
        }
    }
}

/*
題目：組合總和

給定一個無重複元素的陣列 candidates 和一個目標數 target，
找出 candidates 中所有可以使數字和為 target 的組合。

重要條件：
1. candidates 中的數字可以無限制重複被選取
2. 所有數字（包括 target）都是正整數
3. 解集不能包含重複的組合

解法分析：

方法一：回溯算法（推薦）
- 時間複雜度：O(N^(T/M))，N是候選數字個數，T是目標值，M是最小候選數字
- 空間複雜度：O(T/M) - 遞迴深度
- 核心思路：
  * 對於每個位置，嘗試所有可能的候選數字
  * 同一個數字可以重複使用（傳遞索引 i 而非 i+1）
  * 通過排序和剪枝提高效率

關鍵技巧：
1. 排序陣列：有助於提前結束（剪枝）
2. start 參數：避免產生重複組合（如 [2,2,3] 和 [2,3,2]）
3. 及時剪枝：當前數字 > 剩餘目標值時直接結束

方法二：動態規劃
- 思路：dp[i] 存儲所有和為 i 的組合
- 適合理解問題結構，但實現較複雜
- 空間複雜度較高

方法三：迭代版本
- 使用堆疊模擬遞迴過程
- 避免遞迴帶來的堆疊溢出風險
- 邏輯與遞迴版本相同

回溯算法模板：
```java
void backtrack(選擇列表, 路徑, 結果集) {
    if (滿足結束條件) {
        結果集.add(路徑);
        return;
    }
    
    for (選擇 : 選擇列表) {
        做選擇;
        backtrack(選擇列表, 路徑, 結果集);
        撤銷選擇;
    }
}
```

避免重複組合的技巧：
- 使用 start 參數確保後續選擇的索引 >= 當前索引
- 例：選了 candidates[1] 後，下次只能從 candidates[1] 開始選
- 這樣 [2,2,3] 是合法的，但 [2,3,2] 會被跳過

測試範例：
- 輸入：candidates = [2,3,6,7], target = 7
- 輸出：[[2,2,3],[7]]

- 輸入：candidates = [2,3,5], target = 8  
- 輸出：[[2,2,2,2],[2,3,3],[3,5]]

調試技巧：
1. 在遞迴中添加 System.out.println 觀察路徑變化
2. 檢查是否正確處理重複使用數字的邏輯
3. 驗證剪枝條件是否正確

這道題是回溯算法的經典應用，掌握後對解決類似的組合、排列問題很有幫助。
*/