import java.util.*;

public class LC04_Median_QuakeFeeds {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        
        double[] nums1 = new double[n];
        for (int i = 0; i < n; i++) {
            nums1[i] = scanner.nextDouble();
        }
        
        double[] nums2 = new double[m];
        for (int i = 0; i < m; i++) {
            nums2[i] = scanner.nextDouble();
        }
        
        // 計算兩個已排序陣列的中位數
        double result = findMedianSortedArrays(nums1, nums2);
        
        // 輸出結果（保留 1 位小數）
        System.out.printf("%.1f%n", result);
        
        scanner.close();
    }
    
    /**
     * 使用二分搜尋找到兩個已排序陣列的中位數
     * 時間複雜度: O(log(min(n,m)))
     * 空間複雜度: O(1)
     * 
     * @param nums1 第一個已排序陣列（中央氣象署數據）
     * @param nums2 第二個已排序陣列（海外機構數據）
     * @return 聯合中位數
     */
    public static double findMedianSortedArrays(double[] nums1, double[] nums2) {
        // 確保 nums1 是較短的陣列，優化時間複雜度
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int n = nums1.length;
        int m = nums2.length;
        int totalLength = n + m;
        int halfLength = (totalLength + 1) / 2;
        
        int left = 0, right = n;
        
        while (left <= right) {
            // 在 nums1 中的切割位置
            int cut1 = (left + right) / 2;
            // 在 nums2 中的切割位置
            int cut2 = halfLength - cut1;
            
            // 計算左半部分的最大值
            double maxLeft1 = (cut1 == 0) ? Double.NEGATIVE_INFINITY : nums1[cut1 - 1];
            double maxLeft2 = (cut2 == 0) ? Double.NEGATIVE_INFINITY : nums2[cut2 - 1];
            double maxLeft = Math.max(maxLeft1, maxLeft2);
            
            // 計算右半部分的最小值
            double minRight1 = (cut1 == n) ? Double.POSITIVE_INFINITY : nums1[cut1];
            double minRight2 = (cut2 == m) ? Double.POSITIVE_INFINITY : nums2[cut2];
            double minRight = Math.min(minRight1, minRight2);
            
            // 檢查是否找到正確的切割點
            if (maxLeft <= minRight) {
                // 找到了正確的切割點
                if (totalLength % 2 == 0) {
                    // 總長度為偶數，取兩個中間值的平均
                    return (maxLeft + minRight) / 2.0;
                } else {
                    // 總長度為奇數，取左半部分的最大值
                    return maxLeft;
                }
            } else if (maxLeft1 > minRight2) {
                // nums1 切得太靠右，需要左移
                right = cut1 - 1;
            } else {
                // nums1 切得太靠左，需要右移
                left = cut1 + 1;
            }
        }
        
        // 理論上不會到達這裡
        throw new IllegalArgumentException("輸入陣列未正確排序");
    }
}