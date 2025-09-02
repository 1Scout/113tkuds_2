public class lt_11_ContainerWithMostWater {
    public int maxArea(int[] height) {
        // res 用來記錄目前找到的最大容積
        int res = 0;
        // i 指向左邊界，j 指向右邊界
        int i = 0, j = height.length - 1;

        // 當左右邊界尚未相遇時，持續嘗試
        while (i < j) {
            // 計算當前容積 = 兩邊較矮的高度 * 寬度(右邊界 - 左邊界)
            res = Math.max(res, Math.min(height[i], height[j]) * (j - i));

            // 移動指標的策略：
            // 因為容積受限於「較矮的那一邊」，所以嘗試移動較矮的一邊，
            // 才有可能找到更大的容積
            if (height[i] < height[j]) {
                ++i; // 左邊比較矮 → 左指標右移
            } else {
                --j; // 右邊比較矮或相等 → 右指標左移
            }
        }

        // 回傳最大容積
        return res;
    }
}
