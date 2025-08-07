public class NumberArrayProcessor {
    public static int[] removeDuplicates(int[] array) {
        if (array == null || array.length == 0) {
            return new int[0];
        }
        
        int uniqueCount = 0;
        for (int i = 0; i < array.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < i; j++) {
                if (array[i] == array[j]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueCount++;
            }
        }
        
        int[] result = new int[uniqueCount];
        int index = 0;
        
        for (int i = 0; i < array.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < i; j++) {
                if (array[i] == array[j]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                result[index++] = array[i];
            }
        }
        
        return result;
    }
    
    public static int[] mergeSortedArrays(int[] array1, int[] array2) {
        if (array1 == null) array1 = new int[0];
        if (array2 == null) array2 = new int[0];
        
        int[] result = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;
        
        while (i < array1.length && j < array2.length) {
            if (array1[i] <= array2[j]) {
                result[k++] = array1[i++];
            } else {
                result[k++] = array2[j++];
            }
        }
        
        while (i < array1.length) {
            result[k++] = array1[i++];
        }
        
        while (j < array2.length) {
            result[k++] = array2[j++];
        }
        
        return result;
    }
    
    public static int findMostFrequentElement(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("陣列不能為空！");
        }
        
        int mostFrequent = array[0];
        int maxFrequency = 1;
        
        for (int i = 0; i < array.length; i++) {
            int currentFrequency = 1;
            
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    currentFrequency++;
                }
            }
            
            if (currentFrequency > maxFrequency) {
                maxFrequency = currentFrequency;
                mostFrequent = array[i];
            }
        }
        
        return mostFrequent;
    }
    
    public static void printFrequencyAnalysis(int[] array) {
        if (array == null || array.length == 0) {
            System.out.println("陣列為空，無法分析頻率。");
            return;
        }
        
        System.out.println("頻率分析：");
        int[] processed = new int[array.length];
        int processedCount = 0;
        
        for (int i = 0; i < array.length; i++) {
            boolean alreadyProcessed = false;
            for (int k = 0; k < processedCount; k++) {
                if (processed[k] == array[i]) {
                    alreadyProcessed = true;
                    break;
                }
            }
            
            if (!alreadyProcessed) {
                int frequency = 0;
                for (int j = 0; j < array.length; j++) {
                    if (array[i] == array[j]) {
                        frequency++;
                    }
                }
                System.out.printf("元素 %d 出現 %d 次%n", array[i], frequency);
                processed[processedCount++] = array[i];
            }
        }
    }
    
    public static int[][] splitArrayInHalf(int[] array) {
        if (array == null || array.length == 0) {
            return new int[2][0];
        }
        
        int midpoint = array.length / 2;
        int[][] result = new int[2][];
        
        result[0] = new int[midpoint];
        for (int i = 0; i < midpoint; i++) {
            result[0][i] = array[i];
        }
        
        int secondSize = array.length - midpoint;
        result[1] = new int[secondSize];
        for (int i = 0; i < secondSize; i++) {
            result[1][i] = array[midpoint + i];
        }
        
        return result;
    }
    
    public static int[][] splitArrayBySum(int[] array) {
        if (array == null || array.length == 0) {
            return new int[2][0];
        }
        
        int totalSum = 0;
        for (int num : array) {
            totalSum += num;
        }
        
        int targetSum = totalSum / 2;
        int currentSum = 0;
        int splitIndex = 0;
        
        for (int i = 0; i < array.length; i++) {
            currentSum += array[i];
            if (currentSum >= targetSum) {
                splitIndex = i + 1;
                break;
            }
        }
        
        int[][] result = new int[2][];
        result[0] = new int[splitIndex];
        result[1] = new int[array.length - splitIndex];
        
        for (int i = 0; i < splitIndex; i++) {
            result[0][i] = array[i];
        }
        
        for (int i = 0; i < result[1].length; i++) {
            result[1][i] = array[splitIndex + i];
        }
        
        return result;
    }
    
    public static int calculateSum(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
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
    
    public static void printArrayInfo(int[] array, String name) {
        System.out.printf("%s 長度：%d，內容：", name, array.length);
        printArray(array, "");
    }
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("           數字陣列處理器測試");
        System.out.println("════════════════════════════════════════");
        
        int[] testArray1 = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9};
        int[] testArray2 = {2, 4, 6, 8, 10};
        int[] testArray3 = {1, 3, 5, 7, 9, 11};
        
        printArray(testArray1, "測試陣列 1");
        printArray(testArray2, "測試陣列 2 (已排序)");
        printArray(testArray3, "測試陣列 3 (已排序)");
        
        System.out.println("\n----------------------------------------");
        System.out.println("1. 移除重複元素測試：");
        System.out.println("----------------------------------------");
        
        int[] uniqueArray = removeDuplicates(testArray1);
        printArray(uniqueArray, "移除重複後的陣列");
        System.out.printf("原始陣列長度：%d，移除重複後長度：%d%n", testArray1.length, uniqueArray.length);
        
        System.out.println("\n----------------------------------------");
        System.out.println("2. 合併已排序陣列測試：");
        System.out.println("----------------------------------------");
        
        int[] mergedArray = mergeSortedArrays(testArray2, testArray3);
        printArray(mergedArray, "合併後的陣列");
        System.out.printf("陣列 2 長度：%d，陣列 3 長度：%d，合併後長度：%d%n", 
                         testArray2.length, testArray3.length, mergedArray.length);
        
        System.out.println("\n----------------------------------------");
        System.out.println("3. 找出頻率最高元素測試：");
        System.out.println("----------------------------------------");
        
        int mostFrequent = findMostFrequentElement(testArray1);
        System.out.println("頻率最高的元素：" + mostFrequent);
        printFrequencyAnalysis(testArray1);
        
        System.out.println("\n----------------------------------------");
        System.out.println("4. 陣列分割測試：");
        System.out.println("----------------------------------------");
        
        System.out.println("4.1 按位置分割（分成兩半）：");
        int[][] splitByPosition = splitArrayInHalf(testArray1);
        printArray(splitByPosition[0], "第一個子陣列");
        printArray(splitByPosition[1], "第二個子陣列");
        System.out.printf("第一個子陣列總和：%d，第二個子陣列總和：%d%n", 
                         calculateSum(splitByPosition[0]), calculateSum(splitByPosition[1]));
        
        System.out.println("\n4.2 按總和分割（嘗試讓總和相近）：");
        int[][] splitBySum = splitArrayBySum(testArray1);
        printArray(splitBySum[0], "第一個子陣列");
        printArray(splitBySum[1], "第二個子陣列");
        int sum1 = calculateSum(splitBySum[0]);
        int sum2 = calculateSum(splitBySum[1]);
        System.out.printf("第一個子陣列總和：%d，第二個子陣列總和：%d，差值：%d%n", 
                         sum1, sum2, Math.abs(sum1 - sum2));
        
        System.out.println("\n----------------------------------------");
        System.out.println("5. 邊界情況測試：");
        System.out.println("----------------------------------------");
        
        int[] emptyArray = {};
        int[] uniqueEmpty = removeDuplicates(emptyArray);
        printArray(uniqueEmpty, "空陣列移除重複");
        
        int[] singleElement = {42};
        int[][] splitSingle = splitArrayInHalf(singleElement);
        printArray(splitSingle[0], "單一元素陣列分割-第一部分");
        printArray(splitSingle[1], "單一元素陣列分割-第二部分");
        
        int[] sameElements = {7, 7, 7, 7, 7};
        int[] uniqueSame = removeDuplicates(sameElements);
        printArray(uniqueSame, "相同元素陣列移除重複");
        int mostFrequentSame = findMostFrequentElement(sameElements);
        System.out.println("相同元素陣列的最高頻率元素：" + mostFrequentSame);
        
        System.out.println("\n----------------------------------------");
        System.out.println("6. 大陣列測試：");
        System.out.println("----------------------------------------");
        
        int[] largeArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
                           5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        printArray(largeArray, "大陣列");
        
        int[] uniqueLarge = removeDuplicates(largeArray);
        printArray(uniqueLarge, "大陣列移除重複");
        
        int[][] splitLarge = splitArrayInHalf(largeArray);
        System.out.printf("大陣列分割：第一部分長度 %d，第二部分長度 %d%n", 
                         splitLarge[0].length, splitLarge[1].length);
        
        System.out.println("\n════════════════════════════════════════");
        System.out.println("            測試完成");
        System.out.println("════════════════════════════════════════");
    }
}