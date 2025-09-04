class Solution {
    // 主要解法：兩次二分搜尋
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // 找到第一個出現的位置（左邊界）
        result[0] = findFirstPosition(nums, target);
        
        // 如果沒找到目標值，直接返回
        if (result[0] == -1) {
            return result;
        }
        
        // 找到最後一個出現的位置（右邊界）
        result[1] = findLastPosition(nums, target);
        
        return result;
    }
    
    // 找到目標值第一次出現的位置（左邊界）
    private int findFirstPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int firstPos = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                firstPos = mid;  // 記錄可能的結果
                right = mid - 1; // 繼續向左搜尋，尋找更早的位置
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return firstPos;
    }
    
    // 找到目標值最後一次出現的位置（右邊界）
    private int findLastPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int lastPos = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                lastPos = mid;   // 記錄可能的結果
                left = mid + 1;  // 繼續向右搜尋，尋找更晚的位置
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return lastPos;
    }
    
    
    
    // 統一的二分搜尋邊界函數
    // findLeft = true：找第一個 >= target 的位置
    // findLeft = false：找第一個 > target 的位置
    private int binarySearchBound(int[] nums, int target, boolean findLeft) {
        int left = 0;
        int right = nums.length;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > target || (findLeft && nums[mid] == target)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    

}

/*
題目：在排序陣列中查找元素的第一個和最後一個位置

給定一個按升序排列的整數陣列 nums 和一個目標值 target，
找出給定目標值在陣列中的開始位置和結束位置。
如果數組中不存在目標值 target，返回 [-1, -1]。

要求：時間複雜度必須是 O(log n)

解法分析：

- 時間複雜度：O(log n)
- 空間複雜度：O(1)
- 思路：
  1. 第一次二分搜尋找左邊界（第一次出現的位置）
  2. 第二次二分搜尋找右邊界（最後一次出現的位置）

關鍵點：
- 找左邊界時：找到目標值後繼續向左搜尋
- 找右邊界時：找到目標值後繼續向右搜尋
測試範例：
- 輸入：nums = [5,7,7,8,8,10], target = 8 → 輸出：[3,4]
- 輸入：nums = [5,7,7,8,8,10], target = 6 → 輸出：[-1,-1]
- 輸入：nums = [], target = 0 → 輸出：[-1,-1]

重點技巧：
1. 二分搜尋的邊界處理
2. 找到目標值後不立即返回，而是繼續搜尋邊界
3. 注意搜尋方向：左邊界向左繼續，右邊界向右繼續

這題是二分搜尋的經典變形，掌握後對理解二分搜尋的邊界處理很有幫助。
*/