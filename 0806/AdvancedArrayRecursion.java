import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class AdvancedArrayRecursion {
    static class RecursionStatistics {
        int calls;
        int comparisons;
        int swaps;
        long executionTime;
        String operationName;
        
        RecursionStatistics(String name) {
            this.operationName = name;
            reset();
        }
        
        void reset() {
            calls = 0;
            comparisons = 0;
            swaps = 0;
            executionTime = 0;
        }
        
        @Override
        public String toString() {
            return String.format("%s - 呼叫: %d, 比較: %d, 交換: %d, 時間: %d ns", 
                               operationName, calls, comparisons, swaps, executionTime);
        }
    }
    
    private static RecursionStatistics currentStats;
    public static void quickSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        quickSortHelper(array, 0, array.length - 1);
    }
    
    public static void quickSort(int[] array, RecursionStatistics stats) {
        if (array == null || array.length == 0) {
            return;
        }
        
        currentStats = stats;
        long startTime = System.nanoTime();
        quickSortHelper(array, 0, array.length - 1);
        long endTime = System.nanoTime();
        
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
    }
    
    private static void quickSortHelper(int[] array, int low, int high) {
        if (currentStats != null) currentStats.calls++;
        
        if (low < high) {

            int pivotIndex = partition(array, low, high);
            quickSortHelper(array, low, pivotIndex - 1);
            quickSortHelper(array, pivotIndex + 1, high);
        }
    }
    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (currentStats != null) currentStats.comparisons++;
            
            if (array[j] <= pivot) {
                i++;
                if (i != j) {
                    if (currentStats != null) currentStats.swaps++;
                    swap(array, i, j);
                }
            }
        }
        if (currentStats != null) currentStats.swaps++;
        swap(array, i + 1, high);
        
        return i + 1;
    }  
    public static void quickSortOptimized(int[] array, RecursionStatistics stats) {
        if (array == null || array.length == 0) {
            return;
        }
        
        currentStats = stats;
        long startTime = System.nanoTime();
        quickSortOptimizedHelper(array, 0, array.length - 1);
        long endTime = System.nanoTime();
        
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
    }
    
    private static void quickSortOptimizedHelper(int[] array, int low, int high) {
        if (currentStats != null) currentStats.calls++;
        
        if (low < high) {
            if (high - low < 10) {
                insertionSort(array, low, high);
                return;
            }

            medianOfThree(array, low, high);
            
            int pivotIndex = partition(array, low, high);
            quickSortOptimizedHelper(array, low, pivotIndex - 1);
            quickSortOptimizedHelper(array, pivotIndex + 1, high);
        }
    }

    private static void medianOfThree(int[] array, int low, int high) {
        int mid = low + (high - low) / 2;
        
        if (array[mid] < array[low]) {
            swap(array, low, mid);
        }
        if (array[high] < array[low]) {
            swap(array, low, high);
        }
        if (array[high] < array[mid]) {
            swap(array, mid, high);
        }
    }

    private static void insertionSort(int[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;
            
            while (j >= low && array[j] > key) {
                if (currentStats != null) currentStats.comparisons++;
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    public static int[] mergeArrays(int[] array1, int[] array2) {
        if (array1 == null) array1 = new int[0];
        if (array2 == null) array2 = new int[0];
        
        int[] result = new int[array1.length + array2.length];
        mergeHelper(array1, 0, array2, 0, result, 0);
        return result;
    }
    
    public static int[] mergeArrays(int[] array1, int[] array2, RecursionStatistics stats) {
        currentStats = stats;
        long startTime = System.nanoTime();
        
        int[] result = mergeArrays(array1, array2);
        
        long endTime = System.nanoTime();
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
        
        return result;
    }
    
    private static void mergeHelper(int[] array1, int i1, int[] array2, int i2, 
                                   int[] result, int resultIndex) {
        if (currentStats != null) currentStats.calls++;
        
        if (i1 >= array1.length) {

            while (i2 < array2.length) {
                result[resultIndex++] = array2[i2++];
            }
            return;
        }
        
        if (i2 >= array2.length) {
            while (i1 < array1.length) {
                result[resultIndex++] = array1[i1++];
            }
            return;
        }

        if (currentStats != null) currentStats.comparisons++;
        
        if (array1[i1] <= array2[i2]) {
            result[resultIndex] = array1[i1];
            mergeHelper(array1, i1 + 1, array2, i2, result, resultIndex + 1);
        } else {
            result[resultIndex] = array2[i2];
            mergeHelper(array1, i1, array2, i2 + 1, result, resultIndex + 1);
        }
    }
    
    public static int findKthSmallest(int[] array, int k) {
        if (array == null || array.length == 0 || k <= 0 || k > array.length) {
            throw new IllegalArgumentException("無效的參數");
        }
        int[] copy = Arrays.copyOf(array, array.length);
        return quickSelect(copy, 0, copy.length - 1, k - 1);
    }
    
    public static int findKthSmallest(int[] array, int k, RecursionStatistics stats) {
        currentStats = stats;
        long startTime = System.nanoTime();
        
        int result = findKthSmallest(array, k);
        
        long endTime = System.nanoTime();
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
        
        return result;
    }
    private static int quickSelect(int[] array, int low, int high, int k) {
        if (currentStats != null) currentStats.calls++;
        
        if (low == high) {
            return array[low];
        }
        
        if (high - low > 5) {
            medianOfThree(array, low, high);
        }
        
        int pivotIndex = partition(array, low, high);
        
        if (k == pivotIndex) {
            return array[k];
        } else if (k < pivotIndex) {
            return quickSelect(array, low, pivotIndex - 1, k);
        } else {
            return quickSelect(array, pivotIndex + 1, high, k);
        }
    }

    public static boolean hasSubsetSum(int[] array, int targetSum) {
        if (array == null || array.length == 0) {
            return targetSum == 0;
        }
        
        return subsetSumHelper(array, 0, targetSum);
    }
    
    public static boolean hasSubsetSum(int[] array, int targetSum, RecursionStatistics stats) {
        currentStats = stats;
        long startTime = System.nanoTime();
        
        boolean result = hasSubsetSum(array, targetSum);
        
        long endTime = System.nanoTime();
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
        
        return result;
    }
    
    private static boolean subsetSumHelper(int[] array, int index, int remainingSum) {
        if (currentStats != null) currentStats.calls++;

        if (remainingSum == 0) {
            return true;
        }
        if (index >= array.length || remainingSum < 0) {
            return false;
        }
        return subsetSumHelper(array, index + 1, remainingSum - array[index]) || 
               subsetSumHelper(array, index + 1, remainingSum);                
    }
    public static List<List<Integer>> findAllSubsetSums(int[] array, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentSubset = new ArrayList<>();
        findSubsetSumsHelper(array, 0, targetSum, currentSubset, result);
        return result;
    }
    
    private static void findSubsetSumsHelper(int[] array, int index, int remainingSum,
                                           List<Integer> currentSubset, List<List<Integer>> result) {
        if (remainingSum == 0) {
            result.add(new ArrayList<>(currentSubset));
            return;
        }
        
        if (index >= array.length || remainingSum < 0) {
            return;
        }
    
        currentSubset.add(array[index]);
        findSubsetSumsHelper(array, index + 1, remainingSum - array[index], currentSubset, result);
        currentSubset.remove(currentSubset.size() - 1);
        findSubsetSumsHelper(array, index + 1, remainingSum, currentSubset, result);
    }
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public static void printArray(int[] array, String title) {
        System.out.print(title + ": [");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    public static int[] copyArray(int[] array) {
        return Arrays.copyOf(array, array.length);
    }
    
    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
    public static void testQuickSort() {
        System.out.println("=== 快速排序測試 ===");
        
        int[] testArray1 = {64, 34, 25, 12, 22, 11, 90};
        int[] testArray2 = {5, 2, 4, 6, 1, 3};
        int[] testArray3 = {1}; 
        int[] testArray4 = {5, 4, 3, 2, 1};
        int[] testArray5 = {1, 2, 3, 4, 5};
        
        int[][] testArrays = {testArray1, testArray2, testArray3, testArray4, testArray5};
        String[] descriptions = {"一般陣列", "小陣列", "單元素", "逆序陣列", "已排序"};
        
        for (int i = 0; i < testArrays.length; i++) {
            System.out.println("\n" + descriptions[i] + ":");
            
            int[] original = copyArray(testArrays[i]);
            int[] basicSort = copyArray(testArrays[i]);
            int[] optimizedSort = copyArray(testArrays[i]);
            
            printArray(original, "原始陣列");
            RecursionStatistics basicStats = new RecursionStatistics("基本快排");
            quickSort(basicSort, basicStats);
            printArray(basicSort, "基本快排結果");
            System.out.println(basicStats);
            RecursionStatistics optimizedStats = new RecursionStatistics("優化快排");
            quickSortOptimized(optimizedSort, optimizedStats);
            printArray(optimizedSort, "優化快排結果");
            System.out.println(optimizedStats);
            
            System.out.println("排序正確性: " + isSorted(basicSort));
        }
    }
    public static void testMergeArrays() {
        System.out.println("\n=== 陣列合併測試 ===");
        
        int[][] array1Tests = {{1, 3, 5, 7}, {2, 4, 6}, {}, {1}};
        int[][] array2Tests = {{2, 4, 6, 8}, {1, 3, 5, 7, 9}, {1, 2, 3}, {2}};
        
        for (int i = 0; i < array1Tests.length; i++) {
            System.out.printf("\n測試 %d:\n", i + 1);
            
            RecursionStatistics mergeStats = new RecursionStatistics("陣列合併");
            int[] result = mergeArrays(array1Tests[i], array2Tests[i], mergeStats);
            
            printArray(array1Tests[i], "陣列1");
            printArray(array2Tests[i], "陣列2");
            printArray(result, "合併結果");
            System.out.println(mergeStats);
            System.out.println("結果正確性: " + isSorted(result));
        }
    }
    public static void testKthSmallest() {
        System.out.println("\n=== 第k小元素測試 ===");
        
        int[] testArray = {7, 10, 4, 3, 20, 15};
        printArray(testArray, "測試陣列");
        
        System.out.println("排序後參考: " + Arrays.toString(Arrays.stream(testArray).sorted().toArray()));
        
        for (int k = 1; k <= testArray.length; k++) {
            RecursionStatistics kthStats = new RecursionStatistics("第" + k + "小");
            int result = findKthSmallest(testArray, k, kthStats);
            System.out.printf("第 %d 小的元素: %d\n", k, result);
            System.out.println(kthStats);
        }
        System.out.println("\n極端情況測試:");
        int[] extremeArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("最小元素: " + findKthSmallest(extremeArray, 1));
        System.out.println("最大元素: " + findKthSmallest(extremeArray, extremeArray.length));
        System.out.println("中位數: " + findKthSmallest(extremeArray, extremeArray.length / 2));
    }
    
    public static void testSubsetSum() {
        System.out.println("\n=== 子集和測試 ===");
        
        int[] testArray = {3, 34, 4, 12, 5, 2};
        printArray(testArray, "測試陣列");
        
        int[] targetSums = {9, 11, 30, 50, 0};
        
        for (int target : targetSums) {
            RecursionStatistics subsetStats = new RecursionStatistics("子集和");
            boolean hasSum = hasSubsetSum(testArray, target, subsetStats);
            
            System.out.printf("目標和 %d: %s\n", target, hasSum ? "存在" : "不存在");
            System.out.println(subsetStats);
            
            if (hasSum) {
                List<List<Integer>> allSubsets = findAllSubsetSums(testArray, target);
                System.out.printf("符合條件的子集 (%d個): ", allSubsets.size());
                for (List<Integer> subset : allSubsets) {
                    System.out.print(subset + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("特殊情況測試:");
        System.out.println("空陣列，目標0: " + hasSubsetSum(new int[]{}, 0));
        System.out.println("空陣列，目標5: " + hasSubsetSum(new int[]{}, 5));
        System.out.println("單元素[5]，目標5: " + hasSubsetSum(new int[]{5}, 5));
        System.out.println("單元素[5]，目標0: " + hasSubsetSum(new int[]{5}, 0));
    }
    public static void performanceComparison() {
        System.out.println("\n=== 效能比較測試 ===");
        
        int[] largeArray = new int[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (int) (Math.random() * 1000);
        }
        
        System.out.println("大陣列快速排序效能 (1000 元素):");
        RecursionStatistics largeQuickSort = new RecursionStatistics("大陣列快排");
        int[] largeCopy = copyArray(largeArray);
        quickSort(largeCopy, largeQuickSort);
        System.out.println(largeQuickSort);
        
        System.out.println("\n第k小元素查找效能:");
        RecursionStatistics kthLarge = new RecursionStatistics("大陣列第k小");
        int kthResult = findKthSmallest(largeArray, 500, kthLarge);
        System.out.println("第500小的元素: " + kthResult);
        System.out.println(kthLarge);
    }
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("         進階遞迴陣列操作測試");
        System.out.println("════════════════════════════════════════");
        
        testQuickSort();
        testMergeArrays();
        testKthSmallest();
        testSubsetSum();
        
        performanceComparison();
        
        System.out.println("\n=== 演算法理論分析 ===");
        System.out.println("1. 快速排序:");
        System.out.println("   • 平均時間複雜度: O(n log n)");
        System.out.println("   • 最壞時間複雜度: O(n²)");
        System.out.println("   • 空間複雜度: O(log n) - 遞迴堆疊");
        System.out.println("   • 原地排序，不穩定");
        
        System.out.println("\n2. 陣列合併:");
        System.out.println("   • 時間複雜度: O(m + n)");
        System.out.println("   • 空間複雜度: O(m + n)");
        System.out.println("   • 穩定操作");
        
        System.out.println("\n3. 第k小元素 (快速選擇):");
        System.out.println("   • 平均時間複雜度: O(n)");
        System.out.println("   • 最壞時間複雜度: O(n²)");
        System.out.println("   • 空間複雜度: O(log n)");
        
        System.out.println("\n4. 子集和問題:");
        System.out.println("   • 時間複雜度: O(2^n)");
        System.out.println("   • 空間複雜度: O(n) - 遞迴深度");
        System.out.println("   • NP-Complete 問題");
        
        System.out.println("\n════════════════════════════════════════");
        System.out.println("            測試完成");
        System.out.println("════════════════════════════════════════");
    }
}