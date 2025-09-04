class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        
        // 步驟1：從右往左找到第一個遞減的位置 i
        // 即 nums[i] < nums[i+1]
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        // 步驟2：如果找到了這樣的位置 i
        if (i >= 0) {
            // 從右往左找到第一個比 nums[i] 大的數字
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            // 交換 nums[i] 和 nums[j]
            swap(nums, i, j);
        }
        
        // 步驟3：將 i+1 位置之後的所有數字反轉
        // 如果沒找到位置 i（即整個數組是遞減的），則反轉整個數組
        reverse(nums, i + 1);
    }
    
    // 輔助方法：交換數組中兩個位置的元素
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // 輔助方法：反轉數組從 start 位置到末尾的部分
    private void reverse(int[] nums, int start) {
        int left = start;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}

/*
演算法思路解析：

1. 什麼是 Next Permutation？
   - 找到當前排列的下一個字典序更大的排列
   - 如果是最大排列，則返回最小排列

2. 演算法步驟：
   a) 從右往左找到第一個 nums[i] < nums[i+1] 的位置
   b) 如果找到這樣的位置，從右往左找到第一個比 nums[i] 大的數字 nums[j]
   c) 交換 nums[i] 和 nums[j]
   d) 將 i+1 之後的部分反轉，使其變成最小排列

3. 時間複雜度：O(n)
   空間複雜度：O(1)

4. 範例：
   輸入：[1,2,3] → 輸出：[1,3,2]
   輸入：[3,2,1] → 輸出：[1,2,3] (最大排列變為最小排列)
   輸入：[1,1,5] → 輸出：[1,5,1]

5. 邊界情況處理：
   - 如果整個數組都是遞減的，直接反轉整個數組
   - 數組長度為1時，保持不變
*/