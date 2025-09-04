class Solution {
    // 主要解法：二分搜尋
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                // 找到目標值，直接返回索引
                return mid;
            } else if (nums[mid] < target) {
                // 中間值小於目標值，在右半部分繼續搜尋
                left = mid + 1;
            } else {
                // 中間值大於目標值，在左半部分繼續搜尋
                right = mid - 1;
            }
        }
        
        // 跳出迴圈時，left 就是插入位置
        // 此時 left > right，left 指向第一個大於 target 的位置
        return left;
    }
    
    // 替代解法一：更直觀的邊界處理
    public int searchInsertAlternative1(int[] nums, int target) {
        int left = 0;
        int right = nums.length; // 注意這裡是 nums.length，不是 nums.length - 1
        
        while (left < right) { // 注意這裡是 <，不是 <=
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                // nums[mid] >= target 的情況
                right = mid;
            }
        }
        
        return left;
    }
    
    // 替代解法二：線性搜尋（不推薦，但邏輯最簡單）
    public int searchInsertLinear(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                // 找到第一個大於等於 target 的位置
                return i;
            }
        }
        
        // 所有元素都小於 target，插入到末尾
        return nums.length;
    }
    
    // 詳細版本：包含所有情況的分析
    public int searchInsertDetailed(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        // 特殊情況：目標值比所有元素都小，插入到開頭
        if (target < nums[0]) {
            return 0;
        }
        
        // 特殊情況：目標值比所有元素都大，插入到末尾
        if (target > nums[right]) {
            return nums.length;
        }
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                // 情況1：找到目標值
                return mid;
            } else if (nums[mid] < target) {
                // 情況2：中間值小於目標值
                left = mid + 1;
            } else {
                // 情況3：中間值大於目標值
                right = mid - 1;
            }
        }
        
        // 情況4：沒有找到目標值，返回插入位置
        // 此時 left 指向第一個大於 target 的位置
        return left;
    }
}

/*
題目：搜尋插入位置

給定一個排序陣列和一個目標值，在陣列中找到目標值，並返回其索引。
如果目標值不存在於陣列中，返回它將會被按順序插入的位置。

要求：演算法的時間複雜度必須是 O(log n)

解法分析：

方法一：標準二分搜尋（推薦）
- 時間複雜度：O(log n)
- 空間複雜度：O(1)
- 核心思路：
  * 如果找到目標值，直接返回索引
  * 如果沒找到，left 指標最終會停在插入位置

關鍵理解：
- 當迴圈結束時，left > right
- left 指向第一個大於 target 的位置
- 這正是 target 應該插入的位置

方法二：統一的搜尋模板
- 使用 [left, right) 區間
- 避免了邊界判斷的複雜性
- 邏輯更統一，但需要適應不同的邊界條件

四種情況分析：
1. target 存在於陣列中 → 返回該位置索引
2. target 小於所有元素 → 返回 0
3. target 大於所有元素 → 返回 nums.length
4. target 在某兩個元素之間 → 返回第一個大於 target 的位置

測試範例：
- 輸入：nums = [1,3,5,6], target = 5 → 輸出：2
- 輸入：nums = [1,3,5,6], target = 2 → 輸出：1
- 輸入：nums = [1,3,5,6], target = 7 → 輸出：4
- 輸入：nums = [1,3,5,6], target = 0 → 輸出：0

重點提示：
1. 使用 left + (right - left) / 2 避免整數溢位
2. 理解二分搜尋結束時指標的含義
3. 插入位置就是第一個大於等於 target 的位置

這題是二分搜尋的基礎應用，理解插入位置的概念對後續題目很重要。
*/