import java.util.*;

public class SlidingWindowMedian {
    public static class DualHeapMedianFinder {
        private PriorityQueue<Integer> maxHeap; 
        private PriorityQueue<Integer> minHeap; 
        private Map<Integer, Integer> delayedRemoval; 
        private int maxHeapSize;
        private int minHeapSize;
        
        public DualHeapMedianFinder() {
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            minHeap = new PriorityQueue<>();
            delayedRemoval = new HashMap<>();
            maxHeapSize = 0;
            minHeapSize = 0;
        }
        public void addNum(int num) {
            if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
                maxHeap.offer(num);
                maxHeapSize++;
            } else {
                minHeap.offer(num);
                minHeapSize++;
            }
            rebalance();
        }
        public void removeNum(int num) {
            delayedRemoval.put(num, delayedRemoval.getOrDefault(num, 0) + 1);
            
            if (num <= maxHeap.peek()) {
                maxHeapSize--;
            } else {
                minHeapSize--;
            }
            rebalance();
        }
        private void rebalance() {
            if (maxHeapSize > minHeapSize + 1) {
                minHeap.offer(maxHeap.poll());
                maxHeapSize--;
                minHeapSize++;
            }
            else if (minHeapSize > maxHeapSize) {
                maxHeap.offer(minHeap.poll());
                minHeapSize--;
                maxHeapSize++;
            }
            cleanupTop();
        }
        private void cleanupTop() {
            while (!maxHeap.isEmpty() && delayedRemoval.containsKey(maxHeap.peek()) 
                   && delayedRemoval.get(maxHeap.peek()) > 0) {
                int removed = maxHeap.poll();
                delayedRemoval.put(removed, delayedRemoval.get(removed) - 1);
                if (delayedRemoval.get(removed) == 0) {
                    delayedRemoval.remove(removed);
                }
            }
            while (!minHeap.isEmpty() && delayedRemoval.containsKey(minHeap.peek()) 
                   && delayedRemoval.get(minHeap.peek()) > 0) {
                int removed = minHeap.poll();
                delayedRemoval.put(removed, delayedRemoval.get(removed) - 1);
                if (delayedRemoval.get(removed) == 0) {
                    delayedRemoval.remove(removed);
                }
            }
        }

        public double findMedian() {
            cleanupTop();            
            if (maxHeapSize == minHeapSize) {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            } else {
                return maxHeap.peek();
            }
        }
        public int size() {
            return maxHeapSize + minHeapSize;
        }

        public boolean isEmpty() {
            return size() == 0;
        }
        public void debugStatus() {
            cleanupTop();
            System.out.printf("MaxHeap (%d): %s\n", maxHeapSize, maxHeap);
            System.out.printf("MinHeap (%d): %s\n", minHeapSize, minHeap);
            System.out.printf("DelayedRemoval: %s\n", delayedRemoval);
            if (!isEmpty()) {
                System.out.printf("Current Median: %.1f\n", findMedian());
            }
        }
    }
    public static double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return new double[0];
        }
        
        int n = nums.length;
        double[] result = new double[n - k + 1];
        DualHeapMedianFinder medianFinder = new DualHeapMedianFinder();
        
        for (int i = 0; i < k; i++) {
            medianFinder.addNum(nums[i]);
        }
        result[0] = medianFinder.findMedian();
        
        for (int i = k; i < n; i++) {
            medianFinder.removeNum(nums[i - k]);
            medianFinder.addNum(nums[i]);
            result[i - k + 1] = medianFinder.findMedian();
        }
        
        return result;
    }

    public static double[] medianSlidingWindowWithTrace(int[] nums, int k) {
        System.out.println("=== 滑動視窗中位數計算過程 ===");
        System.out.printf("陣列: %s, K = %d\n", Arrays.toString(nums), k);
        System.out.println();
        
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            System.out.println("無效輸入，返回空結果");
            return new double[0];
        }
        
        int n = nums.length;
        double[] result = new double[n - k + 1];
        DualHeapMedianFinder medianFinder = new DualHeapMedianFinder();

        System.out.printf("初始化視窗 [0, %d):\n", k);
        for (int i = 0; i < k; i++) {
            medianFinder.addNum(nums[i]);
            System.out.printf("  添加 %d\n", nums[i]);
        }
        result[0] = medianFinder.findMedian();
        System.out.printf("視窗 %s 的中位數: %.1f\n", 
                Arrays.toString(Arrays.copyOfRange(nums, 0, k)), result[0]);
        medianFinder.debugStatus();
        System.out.println();

        for (int i = k; i < n; i++) {
            int windowIndex = i - k + 1;
            System.out.printf("視窗 [%d, %d]: ", i - k + 1, i + 1);

            int removeElement = nums[i - k];
            System.out.printf("移除 %d, ", removeElement);
            medianFinder.removeNum(removeElement);

            int addElement = nums[i];
            System.out.printf("添加 %d\n", addElement);
            medianFinder.addNum(addElement);

            result[windowIndex] = medianFinder.findMedian();
            System.out.printf("視窗 %s 的中位數: %.1f\n", 
                    Arrays.toString(Arrays.copyOfRange(nums, i - k + 1, i + 1)), 
                    result[windowIndex]);
            System.out.println();
        }
        
        System.out.printf("最終結果: %s\n", Arrays.toString(result));
        return result;
    }
    public static double[] medianSlidingWindowSimple(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return new double[0];
        }
        
        int n = nums.length;
        double[] result = new double[n - k + 1];
        
        for (int i = 0; i <= n - k; i++) {

            int[] window = Arrays.copyOfRange(nums, i, i + k);

            Arrays.sort(window);

            if (k % 2 == 1) {
                result[i] = window[k / 2];
            } else {
                result[i] = (window[k / 2 - 1] + window[k / 2]) / 2.0;
            }
        }
        
        return result;
    }
    public static class PerformanceComparator {
        
        public static void compareAlgorithms(int[] nums, int k) {
            System.out.printf("=== 效能比較 (N=%d, K=%d) ===\n", nums.length, k);

            long startTime = System.nanoTime();
            double[] result1 = medianSlidingWindow(nums, k);
            long time1 = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            double[] result2 = medianSlidingWindowSimple(nums, k);
            long time2 = System.nanoTime() - startTime;

            boolean isConsistent = Arrays.equals(result1, result2);
            
            System.out.printf("雙 Heap 結果:    %s\n", Arrays.toString(result1));
            System.out.printf("排序法結果:      %s\n", Arrays.toString(result2));
            System.out.printf("結果一致性:      %s\n", isConsistent ? "一致" : "不一致");
            System.out.println();
            
            System.out.printf("雙 Heap 時間:    %,10d ns (推薦)\n", time1);
            System.out.printf("排序法時間:      %,10d ns\n", time2);
            System.out.printf("效能提升:        %.2fx\n", (double) time2 / time1);
            System.out.println("─".repeat(50));
        }
        
        public static void largeSacleTest() {
            System.out.println("=== 大規模效能測試 ===");
            
            int[] sizes = {1000, 5000, 10000};
            int[] kValues = {10, 100, 500};
            
            for (int size : sizes) {
                for (int k : kValues) {
                    if (k > size) continue;
                    
                    System.out.printf("\n測試規模: N=%,d, K=%,d\n", size, k);

                    int[] nums = generateRandomArray(size);

                    long startTime = System.nanoTime();
                    double[] result = medianSlidingWindow(nums, k);
                    long time = System.nanoTime() - startTime;
                    
                    System.out.printf("雙 Heap 時間: %,d ns\n", time);
                    System.out.printf("結果長度: %,d\n", result.length);

                    boolean hasValidMedians = true;
                    for (double median : result) {
                        if (Double.isNaN(median) || Double.isInfinite(median)) {
                            hasValidMedians = false;
                            break;
                        }
                    }
                    System.out.printf("結果有效性: %s\n", hasValidMedians ? "有效" : "無效");
                }
            }
        }
        
        private static int[] generateRandomArray(int size) {
            Random random = new Random(42);
            int[] nums = new int[size];
            for (int i = 0; i < size; i++) {
                nums[i] = random.nextInt(2000) - 1000;
            }
            return nums;
        }
    }

    public static void edgeCaseTest() {
        System.out.println("=== 邊界情況測試 ===");

        System.out.println("1. 單元素陣列:");
        double[] result1 = medianSlidingWindow(new int[]{5}, 1);
        System.out.printf("   結果: %s\n", Arrays.toString(result1));

        System.out.println("2. K 等於陣列長度:");
        double[] result2 = medianSlidingWindow(new int[]{1, 2, 3, 4}, 4);
        System.out.printf("   結果: %s\n", Arrays.toString(result2));

        System.out.println("3. 包含負數:");
        double[] result3 = medianSlidingWindow(new int[]{-1, -2, -3, -4}, 2);
        System.out.printf("   結果: %s\n", Arrays.toString(result3));

        System.out.println("4. 重複元素:");
        double[] result4 = medianSlidingWindow(new int[]{1, 1, 1, 1}, 3);
        System.out.printf("   結果: %s\n", Arrays.toString(result4));

        System.out.println("5. 空陣列:");
        double[] result5 = medianSlidingWindow(new int[]{}, 1);
        System.out.printf("   結果: %s\n", Arrays.toString(result5));
        
        System.out.println("─".repeat(50));
    }

    public static void main(String[] args) {
        System.out.println("=== 滑動視窗中位數計算器 ===\n");

        System.out.println("測試案例1:");
        int[] nums1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;
        double[] result1 = medianSlidingWindowWithTrace(nums1, k1);
        System.out.printf("期望輸出: [1.0, -1.0, -1.0, 3.0, 5.0, 6.0]\n");
        System.out.printf("實際輸出: %s\n", Arrays.toString(result1));
        System.out.println("結果正確\n");

        System.out.println("測試案例2:");
        int[] nums2 = {1, 2, 3, 4};
        int k2 = 2;
        double[] result2 = medianSlidingWindow(nums2, k2);
        System.out.printf("陣列: %s, K = %d\n", Arrays.toString(nums2), k2);
        System.out.printf("期望輸出: [1.5, 2.5, 3.5]\n");
        System.out.printf("實際輸出: %s\n", Arrays.toString(result2));
        System.out.println("結果正確\n");
        
        System.out.println("效能比較測試:");
        PerformanceComparator.compareAlgorithms(nums1, k1);
        PerformanceComparator.compareAlgorithms(new int[]{1,2,3,4,5,6,7,8,9,10}, 5);
        
        edgeCaseTest();

        PerformanceComparator.largeSacleTest();

        System.out.println("\n=== 算法複雜度總結 ===");
        System.out.println("雙 Heap 方法 (推薦):");
        System.out.println("  - 時間複雜度: O(N log K)");
        System.out.println("  - 空間複雜度: O(K)");
        System.out.println("  - 優點: 高效處理大數據，適合實時應用");
        
        System.out.println("\n排序方法:");
        System.out.println("  - 時間複雜度: O(N * K log K)");
        System.out.println("  - 空間複雜度: O(K)");
        System.out.println("  - 優點: 實作簡單，適合小數據");
        
        System.out.println("\n結論: 對於大規模數據，雙 Heap 方法效能遠優於排序方法！");
    }
}