class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        
        // 邊界情況：陣列長度小於4，無法組成4個數的組合
        if (nums == null || nums.length < 4) {
            return result;
        }
        
        // 排序陣列，為使用雙指針做準備
        Arrays.sort(nums);
        int n = nums.length;
        
        // 第一層迴圈：固定第一個數
        for (int i = 0; i < n - 3; i++) {
            // 跳過重複的第一個數，避免重複結果
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 剪枝優化：如果當前最小可能和已經大於目標值，後續都不可能滿足
            if ((long)nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            
            // 剪枝優化：如果當前最大可能和小於目標值，跳過當前i
            if ((long)nums[i] + nums[n - 1] + nums[n - 2] + nums[n - 3] < target) {
                continue;
            }
            
            // 第二層迴圈：固定第二個數
            for (int j = i + 1; j < n - 2; j++) {
                // 跳過重複的第二個數
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                
                // 剪枝優化：當前兩個數固定後的最小可能和
                if ((long)nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                
                // 剪枝優化：當前兩個數固定後的最大可能和
                if ((long)nums[i] + nums[j] + nums[n - 1] + nums[n - 2] < target) {
                    continue;
                }
                
                // 使用雙指針尋找剩餘的兩個數
                int left = j + 1;
                int right = n - 1;
                
                while (left < right) {
                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
                    
                    if (sum == target) {
                        // 找到一組解，加入結果集
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
                        // 跳過重複的left值
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        
                        // 跳過重複的right值
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        
                        // 移動兩個指針繼續尋找
                        left++;
                        right--;
                        
                    } else if (sum < target) {
                        // 和小於目標值，需要增大，左指針右移
                        left++;
                    } else {
                        // 和大於目標值，需要減小，右指針左移
                        right--;
                    }
                }
            }
        }
        
        return result;
    }



    private List<List<Integer>> kSum(int[] nums, long target, int start, int k) {
        List<List<Integer>> result = new ArrayList<>();
        
        // 邊界檢查
        if (start == nums.length || nums[start] * k > target || target > nums[nums.length - 1] * k) {
            return result;
        }
        
        // 如果k等於2，使用雙指針解決2Sum問題
        if (k == 2) {
            return twoSum(nums, target, start);
        }
        
        // 遞歸解決k > 2的情況
        for (int i = start; i < nums.length; i++) {
            // 跳過重複元素
            if (i == start || nums[i - 1] != nums[i]) {
                // 遞歸求解(k-1)Sum問題
                for (List<Integer> subset : kSum(nums, target - nums[i], i + 1, k - 1)) {
                    result.add(new ArrayList<>(Arrays.asList(nums[i])));
                    result.get(result.size() - 1).addAll(subset);
                }
            }
        }
        
        return result;
    }
    
   
    private List<List<Integer>> twoSum(int[] nums, long target, int start) {
        List<List<Integer>> result = new ArrayList<>();
        int left = start, right = nums.length - 1;
        
        while (left < right) {
            long sum = nums[left] + nums[right];
            
            if (sum < target || (left > start && nums[left] == nums[left - 1])) {
                left++;
            } else if (sum > target || (right < nums.length - 1 && nums[right] == nums[right + 1])) {
                right--;
            } else {
                result.add(Arrays.asList(nums[left++], nums[right--]));
            }
        }
        
        return result;
    }
}


/*
解題思路：

方法一：雙層迴圈 + 雙指針
1. 排序陣列，使雙指針技巧可行
2. 外層兩個迴圈固定前兩個數
3. 內層使用雙指針尋找後兩個數
4. 使用剪枝優化提高效率
5. 跳過重複元素避免重複解

方法二：遞歸通用解法
1. 將4Sum問題轉化為3Sum問題
2. 將3Sum問題轉化為2Sum問題
3. 用雙指針解決2Sum問題
4. 遞歸回溯得到最終結果

關鍵優化：
1. 排序：使雙指針可行
2. 去重：在多個層次跳過重複元素
3. 剪枝：提前判斷不可能的情況
4. 使用long防止整數溢出

時間複雜度：O(n³) - 兩層迴圈 + 雙指針
空間複雜度：O(1) - 不計算結果空間的話

範例：
輸入：nums = [1,0,-1,0,-2,2], target = 0
輸出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
*/