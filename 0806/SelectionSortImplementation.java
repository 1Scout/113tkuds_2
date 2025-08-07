public class SelectionSortImplementation {
    static class SortStatistics {
        int comparisons;
        int swaps;
        long executionTime;
        String algorithmName;
        
        SortStatistics(String name) {
            this.algorithmName = name;
            this.comparisons = 0;
            this.swaps = 0;
            this.executionTime = 0;
        }
        
        void reset() {
            comparisons = 0;
            swaps = 0;
            executionTime = 0;
        }
        
        @Override
        public String toString() {
            return String.format("%s - 比較: %d 次, 交換: %d 次, 執行時間: %d ns", 
                               algorithmName, comparisons, swaps, executionTime);
        }
    }
    
    public static void selectionSort(int[] array) {
        selectionSort(array, false, null);
    }
    
    public static void selectionSort(int[] array, boolean showProcess, SortStatistics stats) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        long startTime = System.nanoTime();
        
        if (showProcess) {
            System.out.println("\n=== 選擇排序過程 ===");
            System.out.print("初始陣列: ");
            printArray(array);
        }
        
        int n = array.length;
        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            
            for (int j = i + 1; j < n; j++) {
                if (stats != null) stats.comparisons++;
                
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            
            if (minIndex != i) {
                if (stats != null) stats.swaps++;
                swap(array, i, minIndex);
                
                if (showProcess) {
                    System.out.printf("第 %d 輪: 交換位置 %d 和 %d (值: %d 和 %d) -> ", 
                                    i + 1, i, minIndex, array[minIndex], array[i]);
                    printArray(array);
                }
            } else if (showProcess) {
                System.out.printf("第 %d 輪: 位置 %d 已是最小值，無需交換 -> ", 
                                i + 1, i);
                printArray(array);
            }
        }
        
        long endTime = System.nanoTime();
        
        if (stats != null) {
            stats.executionTime = endTime - startTime;
        }
        
        if (showProcess) {
            System.out.print("最終結果: ");
            printArray(array);
            System.out.println("===================\n");
        }
    }
    
    public static void bubbleSort(int[] array, SortStatistics stats) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        long startTime = System.nanoTime();
        int n = array.length;
        
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            
            for (int j = 0; j < n - 1 - i; j++) {
                stats.comparisons++;
                
                if (array[j] > array[j + 1]) {
                    stats.swaps++;
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }
            
            if (!swapped) {
                break;
            }
        }
        
        long endTime = System.nanoTime();
        stats.executionTime = endTime - startTime;
    }
    
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public static void printArray(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }
    
    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    public static int[] generateRandomArray(int size, int maxValue) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * maxValue) + 1;
        }
        return array;
    }
    
    public static int[] generateWorstCaseArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }
    
    public static int[] generateBestCaseArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
        return array;
    }
    
    public static void performanceComparison(int[] testArray) {
        System.out.println("════════════════════════════════════════");
        System.out.println("         效能比較測試");
        System.out.println("════════════════════════════════════════");
        
        System.out.print("測試陣列: ");
        printArray(testArray);
        System.out.println("陣列大小: " + testArray.length);
        System.out.println();
        
        int[] selectionArray = copyArray(testArray);
        SortStatistics selectionStats = new SortStatistics("選擇排序");
        selectionSort(selectionArray, false, selectionStats);
        
        int[] bubbleArray = copyArray(testArray);
        SortStatistics bubbleStats = new SortStatistics("氣泡排序");
        bubbleSort(bubbleArray, bubbleStats);
        
        System.out.println("排序結果比較:");
        System.out.println(selectionStats);
        System.out.println(bubbleStats);
        
        System.out.println("\n排序正確性驗證:");
        System.out.println("選擇排序結果正確: " + isSorted(selectionArray));
        System.out.println("氣泡排序結果正確: " + isSorted(bubbleArray));
        
        System.out.println("\n效能分析:");
        
        if (selectionStats.comparisons < bubbleStats.comparisons) {
            System.out.printf("比較次數: 選擇排序較少 (%d < %d)%n", 
                            selectionStats.comparisons, bubbleStats.comparisons);
        } else if (selectionStats.comparisons > bubbleStats.comparisons) {
            System.out.printf("比較次數: 氣泡排序較少 (%d < %d)%n", 
                            bubbleStats.comparisons, selectionStats.comparisons);
        } else {
            System.out.printf("比較次數: 相同 (%d)%n", selectionStats.comparisons);
        }
        
        if (selectionStats.swaps < bubbleStats.swaps) {
            System.out.printf("交換次數: 選擇排序較少 (%d < %d)%n", 
                            selectionStats.swaps, bubbleStats.swaps);
        } else if (selectionStats.swaps > bubbleStats.swaps) {
            System.out.printf("交換次數: 氣泡排序較少 (%d < %d)%n", 
                            bubbleStats.swaps, selectionStats.swaps);
        } else {
            System.out.printf("交換次數: 相同 (%d)%n", selectionStats.swaps);
        }
        
        if (selectionStats.executionTime < bubbleStats.executionTime) {
            System.out.printf("執行時間: 選擇排序較快 (%d ns < %d ns)%n", 
                            selectionStats.executionTime, bubbleStats.executionTime);
        } else if (selectionStats.executionTime > bubbleStats.executionTime) {
            System.out.printf("執行時間: 氣泡排序較快 (%d ns < %d ns)%n", 
                            bubbleStats.executionTime, selectionStats.executionTime);
        } else {
            System.out.printf("執行時間: 相近 (%d ns)%n", selectionStats.executionTime);
        }
        
        System.out.println("----------------------------------------");
    }
    
    public static void batchPerformanceTest() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("           批量效能測試");
        System.out.println("══════════════════════════════════════════");
        
        int[] testSizes = {10, 50, 100, 500};
        
        for (int size : testSizes) {
            System.out.printf("\n--- 陣列大小: %d ---\n", size);
            
            System.out.println("隨機陣列:");
            int[] randomArray = generateRandomArray(size, 1000);
            performanceComparison(randomArray);
            
            System.out.println("\n最壞情況（逆序陣列）:");
            int[] worstArray = generateWorstCaseArray(size);
            performanceComparison(worstArray);
            
            System.out.println("\n最佳情況（已排序陣列）:");
            int[] bestArray = generateBestCaseArray(size);
            performanceComparison(bestArray);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("          選擇排序演算法測試");
        System.out.println("════════════════════════════════════════");
        
        int[] testArray1 = {64, 34, 25, 12, 22, 11, 90};
        int[] testArray2 = {5, 2, 4, 6, 1, 3};
        
        System.out.println("1. 基本選擇排序測試:");
        System.out.println("----------------------------------------");
        
        int[] array1 = copyArray(testArray1);
        SortStatistics stats1 = new SortStatistics("選擇排序");
        selectionSort(array1, true, stats1);
        System.out.println("統計資料: " + stats1);
        
        System.out.println("\n2. 小陣列排序過程:");
        System.out.println("----------------------------------------");
        
        int[] array2 = copyArray(testArray2);
        SortStatistics stats2 = new SortStatistics("選擇排序");
        selectionSort(array2, true, stats2);
        System.out.println("統計資料: " + stats2);
        
        System.out.println("\n3. 與氣泡排序效能比較:");
        System.out.println("----------------------------------------");
        
        performanceComparison(testArray1);
        
        System.out.println("\n4. 特殊情況測試:");
        System.out.println("----------------------------------------");
        
        int[] emptyArray = {};
        System.out.println("空陣列測試:");
        selectionSort(emptyArray);
        System.out.println("處理完成（無錯誤）");
        
        int[] singleElement = {42};
        System.out.println("\n單一元素陣列測試:");
        System.out.print("排序前: ");
        printArray(singleElement);
        selectionSort(singleElement);
        System.out.print("排序後: ");
        printArray(singleElement);
        
        int[] sortedArray = {1, 2, 3, 4, 5};
        System.out.println("\n已排序陣列測試:");
        SortStatistics sortedStats = new SortStatistics("選擇排序");
        int[] sortedCopy = copyArray(sortedArray);
        selectionSort(sortedCopy, true, sortedStats);
        System.out.println("統計資料: " + sortedStats);
        
        int[] reverseArray = {5, 4, 3, 2, 1};
        System.out.println("\n逆序陣列測試:");
        SortStatistics reverseStats = new SortStatistics("選擇排序");
        int[] reverseCopy = copyArray(reverseArray);
        selectionSort(reverseCopy, true, reverseStats);
        System.out.println("統計資料: " + reverseStats);
        
        System.out.println("\n5. 理論分析:");
        System.out.println("----------------------------------------");
        System.out.println("選擇排序特性:");
        System.out.println("• 時間複雜度: O(n²) - 無論最佳、平均或最壞情況");
        System.out.println("• 空間複雜度: O(1) - 原地排序");
        System.out.println("• 穩定性: 不穩定 - 相同元素可能改變相對順序");
        System.out.println("• 交換次數: 最多 n-1 次");
        System.out.println("• 比較次數: 總是 n(n-1)/2 次");
        
        System.out.println("\n與氣泡排序比較:");
        System.out.println("• 選擇排序交換次數較少，但比較次數相同");
        System.out.println("• 選擇排序性能較穩定，不受資料分布影響");
        System.out.println("• 氣泡排序在最佳情況下可能提前結束");
        
        System.out.print("\n是否執行批量效能測試？這可能需要一些時間... (輸入 'yes' 執行): ");
        System.out.println("執行簡化版批量測試:");
        
        int[] mediumArray = generateRandomArray(20, 100);
        System.out.println("\n中型隨機陣列測試 (大小: 20):");
        performanceComparison(mediumArray);
        
        System.out.println("\n════════════════════════════════════════");
        System.out.println("            測試完成");
        System.out.println("════════════════════════════════════════");
    }
}