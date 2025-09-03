class Solution {
    public int removeElement(int[] nums, int val) {
        // idx 指向處理後的新陣列位置 (慢指標)
        int idx = 0;

        // i 為目前正在掃描的元素位置 (快指標)
        for (int i = 0; i < nums.length; ++i) {
            // 當 nums[i] 不等於 val 時，保留這個元素
            if (nums[i] != val) {
                nums[idx] = nums[i]; // 把 nums[i] 放到新位置 idx
                idx++;               // idx 往後移動一格
            }
            // 若 nums[i] == val，代表該元素要被移除，不做任何操作
        }

        // idx 最後的位置就是新陣列的長度
        return idx;
    }
}
