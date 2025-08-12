public class ValidMaxHeapChecker {
    public static boolean isValidMaxHeap(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }
        for (int i = 0; i <= (arr.length - 2) / 2; i++) {
            if (!isValidParent(arr, i)) {
                return false;
            }
        }
        
        return true;
    }
    public static HeapCheckResult checkMaxHeapWithDetails(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return new HeapCheckResult(true, -1, "陣列為空或只有一個元素，符合 Max Heap 性質");
        }
        for (int i = 0; i <= (arr.length - 2) / 2; i++) {
            if (!isValidParent(arr, i)) {
                return new HeapCheckResult(false, i, 
                    String.format("索引 %d 的節點值 %d 違反 Max Heap 性質", i, arr[i]));
            }
        }
        
        return new HeapCheckResult(true, -1, "陣列符合 Max Heap 性質");
    }
    private static boolean isValidParent(int[] arr, int parentIndex) {
        int leftChildIndex = 2 * parentIndex + 1;
        int rightChildIndex = 2 * parentIndex + 2;
        int parentValue = arr[parentIndex];

        if (leftChildIndex < arr.length) {
            if (parentValue < arr[leftChildIndex]) {
                return false;
            }
        }
        if (rightChildIndex < arr.length) {
            if (parentValue < arr[rightChildIndex]) {
                return false;
            }
        }
        
        return true;
    }
    public static void printHeapStructure(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("空陣列");
            return;
        }
        
        System.out.println("陣列: " + java.util.Arrays.toString(arr));
        System.out.println("樹狀結構:");
        
        int level = 0;
        int levelSize = 1;
        int index = 0;
        
        while (index < arr.length) {
            for (int i = 0; i < (int) Math.pow(2, Math.max(0, 3 - level)); i++) {
                System.out.print("  ");
            }
            for (int i = 0; i < levelSize && index < arr.length; i++) {
                System.out.print(arr[index]);
                if (i < levelSize - 1 && index + 1 < arr.length) {
                    System.out.print("    ");
                }
                index++;
            }
            System.out.println();
            
            level++;
            levelSize *= 2;
        }
        System.out.println();
    }
    public static void analyzeHeap(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("空陣列，符合 Max Heap 性質");
            return;
        }
        
        System.out.println("=== Heap 分析 ===");
        printHeapStructure(arr);
        
        System.out.println("父子關係檢查:");
        for (int i = 0; i <= (arr.length - 2) / 2; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            
            System.out.printf("父節點 [%d]: %d", i, arr[i]);
            
            if (leftChild < arr.length) {
                System.out.printf(" | 左子 [%d]: %d", leftChild, arr[leftChild]);
                if (arr[i] < arr[leftChild]) {
                    System.out.print("違反規則!");
                } else {
                    System.out.print(" ✓");
                }
            }
            
            if (rightChild < arr.length) {
                System.out.printf(" | 右子 [%d]: %d", rightChild, arr[rightChild]);
                if (arr[i] < arr[rightChild]) {
                    System.out.print("違反規則!");
                } else {
                    System.out.print(" ✓");
                }
            }
            
            System.out.println();
        }
        
        HeapCheckResult result = checkMaxHeapWithDetails(arr);
        System.out.printf("\n結論: %s\n", result.getMessage());
    }
    public static class HeapCheckResult {
        private final boolean isValid;
        private final int violationIndex;
        private final String message;
        
        public HeapCheckResult(boolean isValid, int violationIndex, String message) {
            this.isValid = isValid;
            this.violationIndex = violationIndex;
            this.message = message;
        }
        
        public boolean isValid() {
            return isValid;
        }
        
        public int getViolationIndex() {
            return violationIndex;
        }
        
        public String getMessage() {
            return message;
        }
        
        @Override
        public String toString() {
            return String.format("HeapCheckResult{isValid=%s, violationIndex=%d, message='%s'}", 
                    isValid, violationIndex, message);
        }
    }
    public static void main(String[] args) {
        System.out.println("=== Max Heap 驗證器測試 ===\n");
        
        int[][] testCases = {
            {100, 90, 80, 70, 60, 75, 65},
            {100, 90, 80, 95, 60, 75, 65},
            {50},                         
            {},                         
            {10, 20, 15},                 
            {100, 90, 85, 70, 80, 75, 60}, 
            {1, 2, 3, 4, 5, 6, 7}      
        };
        
        for (int i = 0; i < testCases.length; i++) {
            int[] testCase = testCases[i];
            System.out.printf("測試案例 %d: %s\n", i + 1, java.util.Arrays.toString(testCase));
            boolean isValid = isValidMaxHeap(testCase);
            System.out.printf("簡單檢查結果: %s\n", isValid);
            HeapCheckResult result = checkMaxHeapWithDetails(testCase);
            System.out.printf("詳細檢查結果: %s\n", result.getMessage());
            
            if (!result.isValid()) {
                System.out.printf("違反位置: 索引 %d\n", result.getViolationIndex());
            }
            if (testCase.length > 0 && testCase.length <= 7) {
                analyzeHeap(testCase);
            }
            
            System.out.println("─".repeat(50));
        }
        System.out.println("\n=== 特殊測試：較大的陣列 ===");
        int[] largeValidHeap = {100, 90, 85, 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30, 25};
        System.out.println("大型有效 Max Heap: " + java.util.Arrays.toString(largeValidHeap));
        System.out.println("檢查結果: " + isValidMaxHeap(largeValidHeap));
        
        int[] largeInvalidHeap = {100, 90, 85, 80, 95, 70, 65, 60, 55, 50, 45, 40, 35, 30, 25};
        System.out.println("大型無效 Max Heap: " + java.util.Arrays.toString(largeInvalidHeap));
        HeapCheckResult largeResult = checkMaxHeapWithDetails(largeInvalidHeap);
        System.out.println("檢查結果: " + largeResult.getMessage());
    }
}