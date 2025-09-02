import java.util.*;

class lt_15_3Sum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        // 如果陣列長度小於3，無法組成三元組
        if (nums.length < 3) {
            return result;
        }
        
        // 先對陣列進行排序，這樣可以使用雙指針技巧
        Arrays.sort(nums);
        
        // 遍歷陣列，固定第一個數字
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳過重複的第一個數字，避免重複的三元組
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 使用雙指針技巧尋找剩下的兩個數字
            int left = i + 1;           // 左指針從當前位置的下一個開始
            int right = nums.length - 1; // 右指針從陣列末尾開始
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    // 找到一組解，加入結果列表
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳過左指針的重複元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    
                    // 跳過右指針的重複元素
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    // 移動兩個指針繼續尋找
                    left++;
                    right--;
                    
                } else if (sum < 0) {
                    // 和太小，需要增大，移動左指針
                    left++;
                } else {
                    // 和太大，需要減小，移動右指針
                    right--;
                }
            }
        }
        
        return result;
    }
}

/*
優化版本：使用更簡潔的寫法
*/
class SolutionOptimized {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // 剪枝：如果最小的數字都大於0，後面不可能有和為0的組合
            if (nums[i] > 0) break;
            
            // 跳過重複的第一個數字
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            int left = i + 1, right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 同時移動兩個指針並跳過重複元素
                    while (left < right && nums[left] == nums[++left]);
                    while (left < right && nums[right] == nums[--right]);
                    
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
}

/*
解題思路詳解：

1. 問題分析：
   - 需要找到所有不重複的三元組，使得 a + b + c = 0
   - 關鍵是避免重複的解

2. 算法步驟：
   - 先排序陣列，這樣可以使用雙指針技巧
   - 固定第一個數字，用雙指針尋找另外兩個數字
   - 通過跳過重複元素來避免重複解

3. 雙指針技巧：
   - 如果三數之和等於0，記錄解並移動兩個指針
   - 如果和小於0，說明需要更大的數，移動左指針
   - 如果和大於0，說明需要更小的數，移動右指針

4. 去重策略：
   - 對於第一個數字：跳過與前一個相同的數字
   - 對於雙指針：找到解後跳過所有重複的數字

時間複雜度：O(n²) - 外層循環O(n)，內層雙指針O(n)
空間複雜度：O(1) - 不考慮輸出陣列的話，只使用常數額外空間

測試案例：
- threeSum([-1,0,1,2,-1,-4]) → [[-1,-1,2],[-1,0,1]]
- threeSum([]) → []
- threeSum([0]) → []
- threeSum([0,0,0]) → [[0,0,0]]
*/