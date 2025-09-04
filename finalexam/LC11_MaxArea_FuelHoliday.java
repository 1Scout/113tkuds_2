import java.util.*;

public class LC11_MaxArea_FuelHoliday {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入
        int n = scanner.nextInt();
        int[] heights = new int[n];
        
        for (int i = 0; i < n; i++) {
            heights[i] = scanner.nextInt();
        }
        
        // 計算最大輸出帶寬
        int result = maxArea(heights);
        
        // 輸出結果
        System.out.println(result);
        
        scanner.close();
    }
    
    /**
     * 使用雙指針找到最大輸出帶寬
     * 時間複雜度: O(n)
     * 空間複雜度: O(1)
     * 
     * @param height 各位置的油槽高度陣列
     * @return 最大輸出帶寬 (距離 * 最小高度)
     */
    public static int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        
        int left = 0;                    // 左指針，從最左端開始
        int right = height.length - 1;   // 右指針，從最右端開始
        int maxBandwidth = 0;            // 記錄最大輸出帶寬
        
        while (left < right) {
            // 計算當前配置的輸出帶寬
            int distance = right - left;
            int minHeight = Math.min(height[left], height[right]);
            int currentBandwidth = distance * minHeight;
            
            // 更新最大帶寬
            maxBandwidth = Math.max(maxBandwidth, currentBandwidth);
            
            // 核心策略：移動較短的一邊
            // 因為只有提升短邊才有可能增大面積
            if (height[left] < height[right]) {
                left++;   // 左邊較短，嘗試找更高的左端點
            } else {
                right--;  // 右邊較短或相等，嘗試找更高的右端點
            }
        }
        
        return maxBandwidth;
    }
}