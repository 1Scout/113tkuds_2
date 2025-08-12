import java.util.*;

public class MergeKSortedArrays {
    public static class HeapNode implements Comparable<HeapNode> {
        int value;      
        int arrayIndex; 
        int elementIndex;
        
        public HeapNode(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
        
        @Override
        public int compareTo(HeapNode other) {
            return Integer.compare(this.value, other.value);
        }
        
        @Override
        public String toString() {
            return String.format("HeapNode{value=%d, array=%d, index=%d}", 
                    value, arrayIndex, elementIndex);
        }
    }
    public static int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }
        int totalLength = 0;
        for (int[] array : arrays) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        
        if (totalLength == 0) {
            return new int[0];
        }

        PriorityQueue<HeapNode> minHeap = new PriorityQueue<>();
        
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null && arrays[i].length > 0) {
                minHeap.offer(new HeapNode(arrays[i][0], i, 0));
            }
        }
        
        int[] result = new int[totalLength];
        int resultIndex = 0;
        
        while (!minHeap.isEmpty()) {
            HeapNode minNode = minHeap.poll();
            
            result[resultIndex++] = minNode.value;
            
            int nextElementIndex = minNode.elementIndex + 1;
            if (nextElementIndex < arrays[minNode.arrayIndex].length) {
                int nextValue = arrays[minNode.arrayIndex][nextElementIndex];
                minHeap.offer(new HeapNode(nextValue, minNode.arrayIndex, nextElementIndex));
            }
        }
        
        return result;
    }
    public static int[] mergeKSortedArraysDivideConquer(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        List<int[]> nonEmptyArrays = new ArrayList<>();
        for (int[] array : arrays) {
            if (array != null && array.length > 0) {
                nonEmptyArrays.add(array);
            }
        }
        
        if (nonEmptyArrays.isEmpty()) {
            return new int[0];
        }
        
        return mergeKArraysHelper(nonEmptyArrays);
    }
    
    private static int[] mergeKArraysHelper(List<int[]> arrays) {
        if (arrays.size() == 1) {
            return arrays.get(0);
        }

        int mid = arrays.size() / 2;
        List<int[]> left = arrays.subList(0, mid);
        List<int[]> right = arrays.subList(mid, arrays.size());
        int[] leftMerged = mergeKArraysHelper(left);
        int[] rightMerged = mergeKArraysHelper(right);

        return mergeTwoSortedArrays(leftMerged, rightMerged);
    }

    private static int[] mergeTwoSortedArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }

        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        
        return result;
    }
    public static int[] mergeKSortedArraysBrute(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }
        
        List<Integer> allElements = new ArrayList<>();
        
        for (int[] array : arrays) {
            if (array != null) {
                for (int element : array) {
                    allElements.add(element);
                }
            }
        }
        
        Collections.sort(allElements);
        
        return allElements.stream().mapToInt(Integer::intValue).toArray();
    }

    public static class PerformanceComparator {
        
        public static void compareAllMethods(int[][] arrays) {
            System.out.println("=== 輸入陣列 ===");
            for (int i = 0; i < arrays.length; i++) {
                System.out.printf("陣列 %d: %s\n", i, Arrays.toString(arrays[i]));
            }
            System.out.println();

            int[][][] testArrays = new int[3][][];
            for (int i = 0; i < 3; i++) {
                testArrays[i] = deepCopyArrays(arrays);
            }

            long startTime = System.nanoTime();
            int[] result1 = mergeKSortedArrays(testArrays[0]);
            long time1 = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            int[] result2 = mergeKSortedArraysDivideConquer(testArrays[1]);
            long time2 = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            int[] result3 = mergeKSortedArraysBrute(testArrays[2]);
            long time3 = System.nanoTime() - startTime;

            System.out.println("=== 結果比較 ===");
            System.out.printf("Min Heap 結果:    %s\n", Arrays.toString(result1));
            System.out.printf("分治法結果:       %s\n", Arrays.toString(result2));
            System.out.printf("暴力法結果:       %s\n", Arrays.toString(result3));

            if (Arrays.equals(result1, result2) && Arrays.equals(result2, result3)) {
                System.out.println("所有方法結果一致");
            } else {
                System.out.println("結果不一致！");
            }

            System.out.println("\n=== 效能比較 ===");
            System.out.printf("Min Heap:    %,10d ns (推薦)\n", time1);
            System.out.printf("分治法:      %,10d ns\n", time2);
            System.out.printf("暴力法:      %,10d ns\n", time3);

            long minTime = Math.min(time1, Math.min(time2, time3));
            if (time1 == minTime) {
                System.out.println("Min Heap 最快");
            } else if (time2 == minTime) {
                System.out.println("分治法最快");
            } else {
                System.out.println("暴力法最快");
            }
            
            System.out.println("─".repeat(60));
        }
        
        private static int[][] deepCopyArrays(int[][] original) {
            if (original == null) return null;
            
            int[][] copy = new int[original.length][];
            for (int i = 0; i < original.length; i++) {
                if (original[i] != null) {
                    copy[i] = Arrays.copyOf(original[i], original[i].length);
                }
            }
            return copy;
        }
        
        public static void largeSacleTest() {
            System.out.println("=== 大規模效能測試 ===");
            
            int[] kValues = {10, 50, 100};
            int[] arraySizes = {100, 500, 1000};
            
            for (int k : kValues) {
                for (int size : arraySizes) {
                    System.out.printf("\n測試規模: K = %d 個陣列, 每個陣列 %d 元素\n", k, size);

                    int[][] testArrays = generateSortedArrays(k, size);
                    long start = System.nanoTime();
                    int[] result = mergeKSortedArrays(testArrays);
                    long time = System.nanoTime() - start;
                    
                    System.out.printf("Min Heap: %,d ns (結果長度: %,d)\n", time, result.length);

                    boolean isSorted = isSortedArray(result);
                    System.out.printf("結果正確性: %s\n", isSorted ? "正確" : "錯誤");
                }
            }
        }
        
        private static int[][] generateSortedArrays(int k, int size) {
            Random random = new Random(42);
            int[][] arrays = new int[k][];
            
            for (int i = 0; i < k; i++) {
                arrays[i] = new int[size];
                int start = random.nextInt(100);
                
                for (int j = 0; j < size; j++) {
                    arrays[i][j] = start + random.nextInt(10);
                    start = arrays[i][j];
                }
            }
            
            return arrays;
        }
        
        private static boolean isSortedArray(int[] arr) {
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] < arr[i-1]) {
                    return false;
                }
            }
            return true;
        }
    }

    public static int[] mergeKSortedArraysWithTrace(int[][] arrays) {
        System.out.println("=== 詳細步驟追蹤 ===");
        
        if (arrays == null || arrays.length == 0) {
            System.out.println("輸入為空，返回空陣列");
            return new int[0];
        }

        int totalLength = 0;
        for (int[] array : arrays) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        
        if (totalLength == 0) {
            System.out.println("所有陣列都為空，返回空陣列");
            return new int[0];
        }
        
        PriorityQueue<HeapNode> minHeap = new PriorityQueue<>();
        System.out.println("初始化 Min Heap:");
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null && arrays[i].length > 0) {
                HeapNode node = new HeapNode(arrays[i][0], i, 0);
                minHeap.offer(node);
                System.out.printf("  加入: %s\n", node);
            }
        }
        
        int[] result = new int[totalLength];
        int resultIndex = 0;
        
        System.out.println("\n合併過程:");
        while (!minHeap.isEmpty()) {
            HeapNode minNode = minHeap.poll();
            result[resultIndex++] = minNode.value;
            
            System.out.printf("步驟 %d: 取出 %d (來自陣列 %d)\n", 
                    resultIndex, minNode.value, minNode.arrayIndex);
            int nextIndex = minNode.elementIndex + 1;
            if (nextIndex < arrays[minNode.arrayIndex].length) {
                int nextValue = arrays[minNode.arrayIndex][nextIndex];
                HeapNode nextNode = new HeapNode(nextValue, minNode.arrayIndex, nextIndex);
                minHeap.offer(nextNode);
                System.out.printf("       加入下一個: %s\n", nextNode);
            }
            if (!minHeap.isEmpty()) {
                System.out.printf("       當前 heap 最小值: %d\n", minHeap.peek().value);
            }
        }
        
        System.out.printf("最終結果: %s\n", Arrays.toString(result));
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== 合併 K 個有序陣列 ===\n");
        
        System.out.println("測試案例1:");
        int[][] arrays1 = {{1,4,5}, {1,3,4}, {2,6}};
        PerformanceComparator.compareAllMethods(arrays1);
        
        System.out.println("測試案例2:");
        int[][] arrays2 = {{1,2,3}, {4,5,6}, {7,8,9}};
        PerformanceComparator.compareAllMethods(arrays2);
        
        System.out.println("測試案例3:");
        int[][] arrays3 = {{1}, {0}};
        PerformanceComparator.compareAllMethods(arrays3);

        System.out.println("邊界測試:");
        int[][] arrays4 = {{}, {1,2}, {3,4,5}};
        PerformanceComparator.compareAllMethods(arrays4);

        System.out.println("詳細步驟追蹤:");
        int[][] smallCase = {{1,4}, {2,3}, {5}};
        mergeKSortedArraysWithTrace(smallCase);
        System.out.println("─".repeat(60));

        PerformanceComparator.largeSacleTest();

        System.out.println("\n=== 算法複雜度總結 ===");
        System.out.println("方法1 (Min Heap):");
        System.out.println("  - 時間複雜度: O(N log K)");
        System.out.println("  - 空間複雜度: O(K) for heap + O(N) for result");
        System.out.println("  - 適用: 最佳選擇，特別是當 K 較大時");
        
        System.out.println("\n方法2 (分治法):");
        System.out.println("  - 時間複雜度: O(N log K)");
        System.out.println("  - 空間複雜度: O(N) + O(log K) 遞迴棧");
        System.out.println("  - 適用: 理論上與 Min Heap 相當");
        
        System.out.println("\n方法3 (暴力法):");
        System.out.println("  - 時間複雜度: O(N log N)");
        System.out.println("  - 空間複雜度: O(N)");
        System.out.println("  - 適用: 僅用於比較，不推薦實際使用");
        
        System.out.println("\n🎯 推薦: 使用 Min Heap 方法，時間效率最佳！");
    }
}