import java.util.*;

public class KthSmallestElement {
    public static int findKthSmallestWithMaxHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("無效的輸入參數");
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int num : nums) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        return maxHeap.peek();
    }
    public static int findKthSmallestWithMinHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("無效的輸入參數");
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.offer(num);
        }
        for (int i = 0; i < k - 1; i++) {
            minHeap.poll();
        }
        return minHeap.poll();
    }
    public static int findKthSmallestWithQuickSelect(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("無效的輸入參數");
        }
        int[] copy = Arrays.copyOf(nums, nums.length);
        return quickSelect(copy, 0, copy.length - 1, k - 1);
    }
    
    private static int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }
    
        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left + 1);
        
        pivotIndex = partition(nums, left, right, pivotIndex);
        
        if (k == pivotIndex) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }
    
    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        swap(nums, pivotIndex, right);
        
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, i, storeIndex);
                storeIndex++;
            }
        }
        
        swap(nums, storeIndex, right);
        return storeIndex;
    }
    
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static class PerformanceTest {
        
        public static void compareAllMethods(int[] nums, int k) {
            System.out.printf("測試陣列: %s, K = %d\n", Arrays.toString(nums), k);
            System.out.println("正確答案 (排序後): " + Arrays.toString(Arrays.stream(nums).sorted().toArray()));
            System.out.println();
            
            long startTime = System.nanoTime();
            int result1 = findKthSmallestWithMaxHeap(nums, k);
            long endTime = System.nanoTime();
            long time1 = endTime - startTime;
            
            System.out.printf("方法1 (Max Heap): 結果 = %d, 時間 = %,d ns\n", result1, time1);
            System.out.println("時間複雜度: O(N log K), 空間複雜度: O(K)");
            
            startTime = System.nanoTime();
            int result2 = findKthSmallestWithMinHeap(nums, k);
            endTime = System.nanoTime();
            long time2 = endTime - startTime;
            
            System.out.printf("方法2 (Min Heap): 結果 = %d, 時間 = %,d ns\n", result2, time2);
            System.out.println("時間複雜度: O(N log N), 空間複雜度: O(N)");
            
            startTime = System.nanoTime();
            int result3 = findKthSmallestWithQuickSelect(nums, k);
            endTime = System.nanoTime();
            long time3 = endTime - startTime;
            
            System.out.printf("方法3 (QuickSelect): 結果 = %d, 時間 = %,d ns\n", result3, time3);
            System.out.println("時間複雜度: O(N) 平均, 空間複雜度: O(1)");
            
            if (result1 == result2 && result2 == result3) {
                System.out.println("所有方法結果一致");
            } else {
                System.out.println("結果不一致！");
            }
            System.out.println("\n效能比較:");
            if (time1 <= time2 && time1 <= time3) {
                System.out.println("Max Heap 最快");
            } else if (time2 <= time1 && time2 <= time3) {
                System.out.println("Min Heap 最快");
            } else {
                System.out.println("QuickSelect 最快");
            }
            
            System.out.println("─".repeat(60));
        }
        
        public static void largeSacleTest() {
            System.out.println("=== 大規模效能測試 ===");
            
            int[] sizes = {1000, 10000, 100000};
            int[] kValues = {10, 100, 1000};
            
            for (int size : sizes) {
                for (int k : kValues) {
                    if (k > size) continue;
                    
                    System.out.printf("\n測試規模: N = %,d, K = %,d\n", size, k);
                    
                    int[] nums = generateRandomArray(size);
                    long[] times = new long[3];
                    int[] results = new int[3];
                    long start = System.nanoTime();
                    results[0] = findKthSmallestWithMaxHeap(nums, k);
                    times[0] = System.nanoTime() - start;

                    start = System.nanoTime();
                    results[1] = findKthSmallestWithMinHeap(nums, k);
                    times[1] = System.nanoTime() - start;
                    
                    start = System.nanoTime();
                    results[2] = findKthSmallestWithQuickSelect(nums, k);
                    times[2] = System.nanoTime() - start;
                    
                    System.out.printf("Max Heap:    %,10d ns\n", times[0]);
                    System.out.printf("Min Heap:    %,10d ns\n", times[1]);
                    System.out.printf("QuickSelect: %,10d ns\n", times[2]);
                    
                    if (results[0] == results[1] && results[1] == results[2]) {
                        System.out.println("結果正確");
                    } else {
                        System.out.println("結果錯誤");
                    }
                    
                    int fastest = 0;
                    for (int i = 1; i < 3; i++) {
                        if (times[i] < times[fastest]) {
                            fastest = i;
                        }
                    }
                    
                    String[] methods = {"Max Heap", "Min Heap", "QuickSelect"};
                    System.out.printf("最快: %s\n", methods[fastest]);
                }
            }
        }
        
        private static int[] generateRandomArray(int size) {
            Random random = new Random();
            int[] nums = new int[size];
            for (int i = 0; i < size; i++) {
                nums[i] = random.nextInt(size * 2);
            }
            return nums;
        }
    }
    public static class MethodSelector {
        
        public static void recommendMethod(int n, int k) {
            System.out.printf("給定 N = %d, K = %d 的建議:\n", n, k);
            
            if (k <= n / 3) {
                System.out.println("建議使用 Max Heap 方法");
                System.out.println("原因: K 相對較小，Max Heap 的空間複雜度 O(K) 更優");
            } else if (k >= 2 * n / 3) {
                System.out.println("建議使用 Min Heap 方法");
                System.out.println("原因: K 相對較大，不需要維護大小限制的 heap");
            } else {
                System.out.println("建議使用 QuickSelect 方法");
                System.out.println("原因: K 在中間範圍，QuickSelect 的平均 O(N) 時間複雜度最優");
            }
            
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 第 K 小元素查找算法比較 ===\n");

        System.out.println("測試案例1:");
        int[] nums1 = {7, 10, 4, 3, 20, 15};
        int k1 = 3;
        PerformanceTest.compareAllMethods(nums1, k1);
        
        System.out.println("測試案例2:");
        int[] nums2 = {1};
        int k2 = 1;
        PerformanceTest.compareAllMethods(nums2, k2);
        System.out.println("測試案例3:");
        int[] nums3 = {3, 1, 4, 1, 5, 9, 2, 6};
        int k3 = 4;
        PerformanceTest.compareAllMethods(nums3, k3);

        System.out.println("邊界測試:");
        int[] nums4 = {5, 5, 5, 5, 5};
        int k4 = 3;
        PerformanceTest.compareAllMethods(nums4, k4);
        
        System.out.println("=== 方法選擇建議 ===");
        MethodSelector.recommendMethod(1000, 10);
        MethodSelector.recommendMethod(1000, 500);
        MethodSelector.recommendMethod(1000, 900);
        
        PerformanceTest.largeSacleTest();

        System.out.println("\n=== 複雜度總結 ===");
        System.out.println("方法1 (Max Heap):");
        System.out.println("  - 時間複雜度: O(N log K)");
        System.out.println("  - 空間複雜度: O(K)");
        System.out.println("  - 適用: K << N");
        
        System.out.println("\n方法2 (Min Heap):");
        System.out.println("  - 時間複雜度: O(N log N)");
        System.out.println("  - 空間複雜度: O(N)");
        System.out.println("  - 適用: 通用，但不是最優");
        
        System.out.println("\n方法3 (QuickSelect):");
        System.out.println("  - 時間複雜度: O(N) 平均, O(N²) 最壞");
        System.out.println("  - 空間複雜度: O(1)");
        System.out.println("  - 適用: 通用，理論最優");
        
        System.out.println("\n🎯 總結: 根據 N 和 K 的關係選擇最適合的方法！");
    }
}