import java.util.HashMap;
import java.util.Map;
public class RecursiveMathCalculator {
    private static Map<String, Long> combinationCache = new HashMap<>();
    private static Map<Integer, Long> catalanCache = new HashMap<>();
    private static Map<Integer, Long> hanoiCache = new HashMap<>();
    
    private static int combinationCalls = 0;
    private static int catalanCalls = 0;
    private static int hanoiCalls = 0;
    private static int palindromeCalls = 0;
    
    public static long combination(int n, int k) {
        combinationCalls++;
        
        if (k == 0 || k == n) {
            return 1;
        }
        
        if (k > n || n < 0 || k < 0) {
            return 0;
        }
        
        if (k > n - k) {
            k = n - k;
        }
        
        return combination(n - 1, k - 1) + combination(n - 1, k);
    }
    
    public static long combinationMemoized(int n, int k) {
        combinationCalls++;
        
        if (k == 0 || k == n) {
            return 1;
        }
        
        if (k > n || n < 0 || k < 0) {
            return 0;
        }
        
        if (k > n - k) {
            k = n - k;
        }
        
        String key = n + "," + k;
        if (combinationCache.containsKey(key)) {
            return combinationCache.get(key);
        }
        
        long result = combinationMemoized(n - 1, k - 1) + combinationMemoized(n - 1, k);
        combinationCache.put(key, result);
        
        return result;
    }

    public static long catalanNumber(int n) {
        catalanCalls++;
        if (n <= 1) {
            return 1;
        }
        
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += catalanNumber(i) * catalanNumber(n - 1 - i);
        }
        
        return result;
    }

    public static long catalanNumberMemoized(int n) {
        catalanCalls++;
        
        if (n <= 1) {
            return 1;
        }
        
        if (catalanCache.containsKey(n)) {
            return catalanCache.get(n);
        }
        
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += catalanNumberMemoized(i) * catalanNumberMemoized(n - 1 - i);
        }
        
        catalanCache.put(n, result);
        
        return result;
    }
    
    public static long hanoiSteps(int n) {
        hanoiCalls++;
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return 2 * hanoiSteps(n - 1) + 1;
    }
    public static long hanoiStepsMemoized(int n) {
        hanoiCalls++;
        
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        
        if (hanoiCache.containsKey(n)) {
            return hanoiCache.get(n);
        }
        
        long result = 2 * hanoiStepsMemoized(n - 1) + 1;
        hanoiCache.put(n, result);
        
        return result;
    }
    
    public static boolean isPalindrome(long number) {
        palindromeCalls++;
        if (number < 0) {
            return false;
        }
        
        String numStr = String.valueOf(number);
        return isPalindromeHelper(numStr, 0, numStr.length() - 1);
    }
    
    private static boolean isPalindromeHelper(String str, int left, int right) {
        palindromeCalls++;
        
        if (left >= right) {
            return true;
        }
        
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        
        return isPalindromeHelper(str, left + 1, right - 1);
    }
    
    public static boolean isPalindromeMath(long number) {
        palindromeCalls++;
        
        if (number < 0) {
            return false;
        }
        
        return isPalindromeMathHelper(number, getDigitCount(number));
    }
    
    private static boolean isPalindromeMathHelper(long number, int digitCount) {
        palindromeCalls++;
        
        if (digitCount <= 1) {
            return true;
        }
        
        long powerOf10 = (long) Math.pow(10, digitCount - 1);
        long firstDigit = number / powerOf10;
        long lastDigit = number % 10;
        
        if (firstDigit != lastDigit) {
            return false;
        }
        
        long middleNumber = (number % powerOf10) / 10;
        return isPalindromeMathHelper(middleNumber, digitCount - 2);
    }
    
    private static int getDigitCount(long number) {
        if (number == 0) {
            return 1;
        }
        
        int count = 0;
        while (number > 0) {
            count++;
            number /= 10;
        }
        return count;
    }
    
    public static void resetStatistics() {
        combinationCalls = 0;
        catalanCalls = 0;
        hanoiCalls = 0;
        palindromeCalls = 0;
        
        combinationCache.clear();
        catalanCache.clear();
        hanoiCache.clear();
    }

    public static void showStatistics() {
        System.out.println("\n=== 遞迴呼叫統計 ===");
        System.out.println("組合數計算呼叫次數: " + combinationCalls);
        System.out.println("卡塔蘭數計算呼叫次數: " + catalanCalls);
        System.out.println("漢諾塔計算呼叫次數: " + hanoiCalls);
        System.out.println("回文判斷呼叫次數: " + palindromeCalls);
        System.out.println("==================");
    }
    
    public static void testCombination() {
        System.out.println("=== 組合數 C(n, k) 測試 ===");
        
        int[][] testCases = {{5, 2}, {6, 3}, {10, 4}, {8, 0}, {7, 7}};
        
        for (int[] testCase : testCases) {
            int n = testCase[0];
            int k = testCase[1];
            
            resetStatistics();
            long result = combination(n, k);
            int calls = combinationCalls;
            
            resetStatistics();
            long resultMemo = combinationMemoized(n, k);
            int callsMemo = combinationCalls;
            
            System.out.printf("C(%d, %d) = %d (呼叫次數: %d, 記憶化: %d)%n", 
                            n, k, result, calls, callsMemo);
        }
        
        System.out.println("\n組合數性質驗證:");
        System.out.println("C(5, 2) = C(5, 3): " + (combination(5, 2) == combination(5, 3)));
        System.out.println("C(6, 0) = 1: " + (combination(6, 0) == 1));
        System.out.println("C(6, 6) = 1: " + (combination(6, 6) == 1));
    }
    
    public static void testCatalanNumber() {
        System.out.println("\n=== 卡塔蘭數 C(n) 測試 ===");
        
        System.out.println("前10個卡塔蘭數:");
        for (int i = 0; i <= 9; i++) {
            resetStatistics();
            long result = catalanNumber(i);
            int calls = catalanCalls;
            
            resetStatistics();
            long resultMemo = catalanNumberMemoized(i);
            int callsMemo = catalanCalls;
            
            System.out.printf("C(%d) = %d (呼叫次數: %d, 記憶化: %d)%n", 
                            i, result, calls, callsMemo);
        }
        
        System.out.println("\n卡塔蘭數應用範例:");
        System.out.println("• 二元樹的形狀數量");
        System.out.println("• 括號序列的合法配對方式");
        System.out.println("• 多邊形三角剖分的方法數");
    }
    
    public static void testHanoiSteps() {
        System.out.println("\n=== 漢諾塔移動步數 測試 ===");
        
        for (int n = 1; n <= 10; n++) {
            resetStatistics();
            long result = hanoiSteps(n);
            int calls = hanoiCalls;
            
            resetStatistics();
            long resultMemo = hanoiStepsMemoized(n);
            int callsMemo = hanoiCalls;
            
            System.out.printf("漢諾塔(%d層) = %d 步 (呼叫次數: %d, 記憶化: %d)%n", 
                            n, result, calls, callsMemo);
        }
        
        System.out.println("\n漢諾塔公式驗證:");
        System.out.println("hanoi(n) = 2^n - 1");
        for (int n = 1; n <= 5; n++) {
            long recursive = hanoiSteps(n);
            long formula = (long) Math.pow(2, n) - 1;
            System.out.printf("n=%d: 遞迴=%d, 公式=%d, 相等=%b%n", 
                            n, recursive, formula, recursive == formula);
        }
    }
    
    public static void testPalindrome() {
        System.out.println("\n=== 回文數判斷測試 ===");
        
        long[] testNumbers = {12321, 1234, 7, 121, 1221, 12345, 0, -121, 1234321};
        
        for (long number : testNumbers) {
            resetStatistics();
            boolean result1 = isPalindrome(number);
            int calls1 = palindromeCalls;
            
            resetStatistics();
            boolean result2 = isPalindromeMath(number);
            int calls2 = palindromeCalls;
            
            System.out.printf("%d: 字串方法=%b(%d次), 數學方法=%b(%d次)%n", 
                            number, result1, calls1, result2, calls2);
        }
        
        System.out.println("\n特殊回文數測試:");
        long[] specialPalindromes = {1, 11, 101, 111, 1001, 1111, 10001, 10101, 11111};
        
        for (long num : specialPalindromes) {
            System.out.printf("%d: %b%n", num, isPalindrome(num));
        }
    }
    
    public static void performanceComparison() {
        System.out.println("\n=== 效能比較測試 ===");
        
        System.out.println("組合數效能比較 C(20, 10):");
        
        long startTime = System.nanoTime();
        resetStatistics();
        long result1 = combination(20, 10);
        int calls1 = combinationCalls;
        long time1 = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        resetStatistics();
        long result2 = combinationMemoized(20, 10);
        int calls2 = combinationCalls;
        long time2 = System.nanoTime() - startTime;
        
        System.out.printf("一般遞迴: 結果=%d, 呼叫=%d次, 時間=%d ns%n", result1, calls1, time1);
        System.out.printf("記憶化遞迴: 結果=%d, 呼叫=%d次, 時間=%d ns%n", result2, calls2, time2);
        System.out.printf("效能提升: %.2fx%n", (double) time1 / time2);
    }
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("          遞迴數學計算器測試");
        System.out.println("════════════════════════════════════════");
        
        testCombination();
        testCatalanNumber();
        testHanoiSteps();
        testPalindrome();
        
        performanceComparison();

        System.out.println("\n=== 遞迴深度測試 ===");
        
        System.out.println("深度回文測試:");
        boolean result = isPalindrome(1234554321L);
        System.out.println("1234554321 是回文數: " + result);
        showStatistics();
        
        System.out.println("\n數學理論驗證:");
        System.out.println("----------------------------------------");
        
        System.out.println("帕斯卡三角形的第6行:");
        for (int k = 0; k <= 6; k++) {
            System.out.print(combination(6, k) + " ");
        }
        System.out.println();
        
        System.out.println("\n卡塔蘭數增長率:");
        for (int i = 1; i <= 6; i++) {
            long current = catalanNumberMemoized(i);
            long previous = catalanNumberMemoized(i - 1);
            double ratio = (double) current / previous;
            System.out.printf("C(%d)/C(%d) = %.3f%n", i, i - 1, ratio);
        }
        
        System.out.println("\n漢諾塔步數的指數增長:");
        for (int n = 1; n <= 8; n++) {
            long steps = hanoiStepsMemoized(n);
            System.out.printf("%d層塔需要 %d 步 (2^%d - 1 = %d)%n", 
                            n, steps, n, (long) Math.pow(2, n) - 1);
        }
        
        System.out.println("\n=== 應用範例 ===");
        System.out.println("1. 組合數：從10個人中選3個人的方法數 = " + combination(10, 3));
        System.out.println("2. 卡塔蘭數：5個節點的二元樹形狀數 = " + catalanNumberMemoized(5));
        System.out.println("3. 漢諾塔：移動8層塔需要 " + hanoiStepsMemoized(8) + " 步");
        System.out.println("4. 回文數：檢查車牌號碼 12321 = " + isPalindrome(12321));
        
        System.out.println("\n════════════════════════════════════════");
        System.out.println("            測試完成");
        System.out.println("════════════════════════════════════════");
    }
}