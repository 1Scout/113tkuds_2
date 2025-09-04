class Solution {
    // 主要解法：修改版二分搜尋
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // 如果找到目標值，直接返回索引
            if (nums[mid] == target) {
                return mid;
            }
            
            // 判斷哪一半是有序的
            if (nums[left] <= nums[mid]) {
                // 左半部分是有序的
                if (nums[left] <= target && target < nums[mid]) {
                    // 目標值在左半部分的有序區間內
                    right = mid - 1;
                } else {
                    // 目標值在右半部分
                    left = mid + 1;
                }
            } else {
                // 右半部分是有序的
                if (nums[mid] < target && target <= nums[right]) {
                    // 目標值在右半部分的有序區間內
                    left = mid + 1;
                } else {
                    // 目標值在左半部分
                    right = mid - 1;
                }
            }
        }
        
        // 未找到目標值
        return -1;
    }
    
    // 替代解法：先找旋轉點，再分別二分搜尋
    public int searchAlternative(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) return -1;
        if (n == 1) return nums[0] == target ? 0 : -1;
        
        // 步驟1：找到旋轉點（最小值的索引）
        int rotateIndex = findRotateIndex(nums);
        
        // 步驟2：判斷目標值可能在哪個部分
        if (nums[rotateIndex] == target) {
            return rotateIndex;
        }
        
        // 如果沒有旋轉（rotateIndex == 0），直接在整個陣列中搜尋
        if (rotateIndex == 0) {
            return binarySearch(nums, 0, n - 1, target);
        }
        
        // 根據目標值與陣列首尾元素的關係決定搜尋範圍
        if (target >= nums[0]) {
            // 目標值在左半部分（較大的值）
            return binarySearch(nums, 0, rotateIndex - 1, target);
        } else {
            // 目標值在右半部分（較小的值）
            return binarySearch(nums, rotateIndex, n - 1, target);
        }
    }
    
    // 輔助方法：找到旋轉點（最小值的索引）
    private int findRotateIndex(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        // 如果陣列沒有旋轉
        if (nums[left] < nums[right]) {
            return 0;
        }
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // 找到旋轉點
            if (nums[mid] > nums[mid + 1]) {
                return mid + 1;
            }
            if (nums[mid - 1] > nums[mid]) {
                return mid;
            }
            
            if (nums[mid] > nums[0]) {
                // 旋轉點在右半部分
                left = mid + 1;
            } else {
                // 旋轉點在左半部分
                right = mid - 1;
            }
        }
        
        return 0;
    }
    
    // 輔助方法：標準二分搜尋
    private int binarySearch(int[] nums, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}

/*
題目：搜尋旋轉排序陣列

給定一個旋轉排序陣列和目標值，找到目標值的索引。
如果目標值不存在，返回 -1。

關鍵概念：
- 旋轉排序陣列：原本的有序陣列在某個點進行旋轉
- 例：[0,1,2,4,5,6,7] 可能變成 [4,5,6,7,0,1,2]

解法分析：

方法一：一次二分搜尋（推薦）
- 時間複雜度：O(log n)
- 空間複雜度：O(1)
- 核心思路：
  * 在每次二分過程中，判斷哪一半是完全有序的
  * 如果目標值在有序的一半中，就往那一半搜尋
  * 否則往另一半搜尋

判斷邏輯：
1. 如果 nums[left] <= nums[mid]，說明左半部分有序
2. 如果 nums[mid] <= nums[right]，說明右半部分有序
3. 注意邊界條件的處理

方法二：兩步法
- 先找旋轉點，再在對應部分進行標準二分搜尋
- 邏輯更清晰，但需要兩次二分搜尋

測試範例：
- 輸入：nums = [4,5,6,7,0,1,2], target = 0 → 輸出：4
- 輸入：nums = [4,5,6,7,0,1,2], target = 3 → 輸出：-1
- 輸入：nums = [1], target = 0 → 輸出：-1

注意事項：
- 處理邊界情況（陣列長度為0或1）
- 注意等號的使用，避免無窮循環
- 考慮沒有旋轉的情況（原陣列就是有序的）
*/