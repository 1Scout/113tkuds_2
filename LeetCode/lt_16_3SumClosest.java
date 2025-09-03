class lt_16_3SumClosest {
    public int threeSumClosest(int[] nums, int target) {
        // 首先對陣列進行排序，這樣可以使用雙指針技巧
        Arrays.sort(nums);
        
        int n = nums.length;
        int closestSum = nums[0] + nums[1] + nums[2]; // 初始化最接近的和
        
        // 外層迴圈：固定第一個數字
        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;    // 左指針
            int right = n - 1;   // 右指針
            
            // 雙指針搜尋
            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];
                
                // 如果當前和等於目標值，直接返回（這是最接近的情況）
                if (currentSum == target) {
                    return currentSum;
                }
                
                // 如果當前和比之前記錄的最接近和更接近目標值，則更新
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }
                
                // 根據當前和與目標值的關係移動指針
                if (currentSum < target) {
                    left++;  // 當前和小於目標值，需要增大和，所以左指針右移
                } else {
                    right--; // 當前和大於目標值，需要減小和，所以右指針左移
                }
            }
        }
        
        return closestSum;
    }
}

/*
解題思路：
1. 先對陣列排序，這是使用雙指針的前提
2. 固定第一個數字，用雙指針在剩餘數字中尋找最佳組合
3. 計算三數之和，並與目標值比較
4. 記錄到目前為止最接近目標值的和
5. 根據當前和與目標值的大小關係移動指針：
   - 如果和小於目標值，左指針右移（增大和）
   - 如果和大於目標值，右指針左移（減小和）

時間複雜度：O(n²) - 外層迴圈 O(n)，內層雙指針 O(n)
空間複雜度：O(1) - 只使用了常數額外空間
*/