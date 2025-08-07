import java.util.*;

public class RecursionVsIteration {
    public static long binomialCoefficientRecursive(int n, int k) {
        if (k == 0 || k == n) return 1;
        if (k > n || k < 0) return 0;

        return binomialCoefficientRecursive(n - 1, k - 1) + binomialCoefficientRecursive(n - 1, k);
    }
    public static long binomialCoefficientRecursiveMemo(int n, int k) {
        return binomialCoefficientRecursiveMemoHelper(n, k, new HashMap<>());
    }
    
    private static long binomialCoefficientRecursiveMemoHelper(int n, int k, Map<String, Long> memo) {
        if (k == 0 || k == n) return 1;
        if (k > n || k < 0) return 0;
        
        String key = n + "," + k;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        long result = binomialCoefficientRecursiveMemoHelper(n - 1, k - 1, memo) + 
                     binomialCoefficientRecursiveMemoHelper(n - 1, k, memo);
        memo.put(key, result);
        return result;
    }
    public static long binomialCoefficientIterative(int n, int k) {
        if (k > n || k < 0) return 0;
        if (k == 0 || k == n) return 1;
        
        k = Math.min(k, n - k);
        
        long[][] dp = new long[n + 1][k + 1];
        
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }
        
        return dp[n][k];
    }

    public static long binomialCoefficientIterativeOptimized(int n, int k) {
        if (k > n || k < 0) return 0;
        if (k == 0 || k == n) return 1;
        
        k = Math.min(k, n - k);
        
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        return result;
    }
    public static long arrayProductRecursive(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        return arrayProductRecursiveHelper(arr, 0);
    }
    
    private static long arrayProductRecursiveHelper(int[] arr, int index) {
        if (index == arr.length) return 1;
        return arr[index] * arrayProductRecursiveHelper(arr, index + 1);
    }
    
    public static long arrayProductIterative(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        long product = 1;
        for (int num : arr) {
            product *= num;
        }
        return product;
    }

    public static int countVowelsRecursive(String str) {
        if (str == null || str.isEmpty()) return 0;
        return countVowelsRecursiveHelper(str.toLowerCase(), 0);
    }
    
    private static int countVowelsRecursiveHelper(String str, int index) {
        if (index == str.length()) return 0;
        char c = str.charAt(index);
        int currentCount = (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') ? 1 : 0;
        return currentCount + countVowelsRecursiveHelper(str, index + 1);
    }
    public static int countVowelsIterative(String str) {
        if (str == null || str.isEmpty()) return 0;
        
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
        int count = 0;
        
        for (char c : str.toLowerCase().toCharArray()) {
            if (vowels.contains(c)) {
                count++;
            }
        }
        
        return count;
    }
    public static boolean isBalancedParenthesesRecursive(String str) {
        if (str == null) return true;
        return isBalancedRecursiveHelper(str, 0, 0);
    }
    
    private static boolean isBalancedRecursiveHelper(String str, int index, int openCount) {
        if (openCount < 0) return false;
        
        if (index == str.length()) {
            return openCount == 0;
        }
        
        char c = str.charAt(index);
        if (c == '(') {
            return isBalancedRecursiveHelper(str, index + 1, openCount + 1);
        } else if (c == ')') {
            return isBalancedRecursiveHelper(str, index + 1, openCount - 1);
        } else {
            return isBalancedRecursiveHelper(str, index + 1, openCount);
        }
    }

    public static boolean isBalancedParenthesesIterative(String str) {
        if (str == null) return true;
        
        int openCount = 0;
        for (char c : str.toCharArray()) {
            if (c == '(') {
                openCount++;
            } else if (c == ')') {
                openCount--;
                if (openCount < 0) return false;
            }
        }
        
        return openCount == 0;
    }
    
    public static boolean isBalancedMultiParenthesesRecursive(String str) {
        if (str == null) return true;
        return isBalancedMultiHelper(str, 0, new Stack<>());
    }
    
    private static boolean isBalancedMultiHelper(String str, int index, Stack<Character> stack) {
        if (index == str.length()) {
            return stack.isEmpty();
        }
        
        char c = str.charAt(index);
        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
            return isBalancedMultiHelper(str, index + 1, stack);
        } else if (c == ')' || c == ']' || c == '}') {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (!isMatchingPair(top, c)) return false;
            return isBalancedMultiHelper(str, index + 1, stack);
        } else {
            return isBalancedMultiHelper(str, index + 1, stack);
        }
    }
    
    public static boolean isBalancedMultiParenthesesIterative(String str) {
        if (str == null) return true;
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : str.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (!isMatchingPair(top, c)) return false;
            }
        }
        
        return stack.isEmpty();
    }
    
    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }  
    public static void performanceTest() {
        System.out.println("=== 效能比較測試 ===\n");
        
        System.out.println("1. 二項式係數 C(n, k) 效能測試:");
        testBinomialCoefficient();
        
        System.out.println("\n2. 陣列乘積效能測試:");
        testArrayProduct();
        
        System.out.println("\n3. 元音計數效能測試:");
        testVowelCount();
        
        System.out.println("\n4. 括號配對效能測試:");
        testParenthesesBalance();
    }
    
    private static void testBinomialCoefficient() {
        int n = 15, k = 7; 
        
        long start = System.nanoTime();
        long result1 = binomialCoefficientRecursive(n, k);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        long result2 = binomialCoefficientRecursiveMemo(n, k);
        long time2 = System.nanoTime() - start;
        
        start = System.nanoTime();
        long result3 = binomialCoefficientIterative(n, k);
        long time3 = System.nanoTime() - start;

        start = System.nanoTime();
        long result4 = binomialCoefficientIterativeOptimized(n, k);
        long time4 = System.nanoTime() - start;
        
        System.out.printf("   C(%d, %d) = %d\n", n, k, result1);
        System.out.printf("   遞迴版本(基本):     %,10d ns\n", time1);
        System.out.printf("   遞迴版本(備忘錄):   %,10d ns\n", time2);
        System.out.printf("   迭代版本(DP):       %,10d ns\n", time3);
        System.out.printf("   迭代版本(優化):     %,10d ns\n", time4);
        System.out.printf("   結果驗證: %s\n", (result1 == result2 && result2 == result3 && result3 == result4) ? "✓" : "✗");
    }
    
    private static void testArrayProduct() {
        int[] testArray = new int[1000000];
        Arrays.fill(testArray, 2);
        
        long start = System.nanoTime();
        long result1 = arrayProductRecursive(Arrays.copyOf(testArray, 1000));
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        long result2 = arrayProductIterative(testArray);
        long time2 = System.nanoTime() - start;
        
        System.out.printf("   陣列大小: %d 元素\n", testArray.length);
        System.out.printf("   遞迴版本: %,10d ns (只測試前1000個元素)\n", time1);
        System.out.printf("   迭代版本: %,10d ns (全部元素)\n", time2);
        System.out.printf("   效能比較: 迭代版本快 %.2fx\n", (double)time1 / time2);
    }
    
    private static void testVowelCount() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append("Hello World Programming Java ");
        }
        String testString = sb.toString();
        
        long start = System.nanoTime();
        int result1 = countVowelsRecursive(testString);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result2 = countVowelsIterative(testString);
        long time2 = System.nanoTime() - start;
        
        System.out.printf("   字串長度: %,d 字符\n", testString.length());
        System.out.printf("   元音數量: %d\n", result1);
        System.out.printf("   遞迴版本: %,10d ns\n", time1);
        System.out.printf("   迭代版本: %,10d ns\n", time2);
        System.out.printf("   結果驗證: %s\n", (result1 == result2) ? "✓" : "✗");
        System.out.printf("   效能比較: 迭代版本快 %.2fx\n", (double)time1 / time2);
    }
    
    private static void testParenthesesBalance() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50000; i++) {
            sb.append("((()))");
        }
        String testString = sb.toString();
        
        long start = System.nanoTime();
        boolean result1 = isBalancedParenthesesRecursive(testString);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        boolean result2 = isBalancedParenthesesIterative(testString);
        long time2 = System.nanoTime() - start;
        
        System.out.printf("   字串長度: %,d 字符\n", testString.length());
        System.out.printf("   配對結果: %s\n", result1);
        System.out.printf("   遞迴版本: %,10d ns\n", time1);
        System.out.printf("   迭代版本: %,10d ns\n", time2);
        System.out.printf("   結果驗證: %s\n", (result1 == result2) ? "✓" : "✗");
        System.out.printf("   效能比較: 迭代版本快 %.2fx\n", (double)time1 / time2);
    }

    public static void main(String[] args) {
        System.out.println("=== 遞迴 vs 迭代 比較程式 ===\n");
        
        functionalityTest();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        performanceTest();
        
        System.out.println("\n=== 總結 ===");
        System.out.println("1. 二項式係數：迭代版本明顯優於遞迴版本，記憶化可大幅改善遞迴效能");
        System.out.println("2. 陣列乘積：迭代版本在大數據上有絕對優勢，無堆疊溢位風險");
        System.out.println("3. 元音計數：迭代版本更高效，遞迴版本有函數呼叫開銷");
        System.out.println("4. 括號配對：迭代版本更快，遞迴版本較直觀但有堆疊深度限制");
        System.out.println("\n建議：對於大數據處理，優先選擇迭代；對於複雜邏輯，可考慮遞迴的可讀性優勢。");
    }
    
    private static void functionalityTest() {
        System.out.println("=== 功能驗證測試 ===\n");
        
        System.out.println("1. 二項式係數測試:");
        int n = 10, k = 4;
        System.out.printf("   C(%d, %d):\n", n, k);
        System.out.printf("   遞迴版本: %d\n", binomialCoefficientRecursive(n, k));
        System.out.printf("   迭代版本: %d\n", binomialCoefficientIterative(n, k));
        
        System.out.println("\n2. 陣列乘積測試:");
        int[] testArr = {2, 3, 4, 5};
        System.out.printf("   陣列: %s\n", Arrays.toString(testArr));
        System.out.printf("   遞迴版本: %d\n", arrayProductRecursive(testArr));
        System.out.printf("   迭代版本: %d\n", arrayProductIterative(testArr));
        
        System.out.println("\n3. 元音計數測試:");
        String testStr = "Hello Programming World";
        System.out.printf("   字串: \"%s\"\n", testStr);
        System.out.printf("   遞迴版本: %d\n", countVowelsRecursive(testStr));
        System.out.printf("   迭代版本: %d\n", countVowelsIterative(testStr));
        
        System.out.println("\n4. 括號配對測試:");
        String[] testCases = {"((()))", "((())", "())(", "{[()]}", "{[(])}"};
        for (String test : testCases) {
            System.out.printf("   \"%s\": 遞迴=%s, 迭代=%s\n", 
                test, 
                isBalancedParenthesesRecursive(test),
                isBalancedParenthesesIterative(test));
        }
        
        System.out.println("\n   多種括號測試:");
        for (String test : testCases) {
            System.out.printf("   \"%s\": 遞迴=%s, 迭代=%s\n", 
                test, 
                isBalancedMultiParenthesesRecursive(test),
                isBalancedMultiParenthesesIterative(test));
        }
    }
}