import java.util.*;

public class StockMaximizer {
    public static class Trade implements Comparable<Trade> {
        int buyDay;     
        int sellDay; 
        int buyPrice;   
        int sellPrice;  
        int profit;     
        
        public Trade(int buyDay, int buyPrice, int sellDay, int sellPrice) {
            this.buyDay = buyDay;
            this.buyPrice = buyPrice;
            this.sellDay = sellDay;
            this.sellPrice = sellPrice;
            this.profit = sellPrice - buyPrice;
        }
        
        @Override
        public int compareTo(Trade other) {
            return Integer.compare(other.profit, this.profit);
        }
        
        @Override
        public String toString() {
            return String.format("Trade{買入:第%d天價格%d, 賣出:第%d天價格%d, 利潤:%d}", 
                    buyDay, buyPrice, sellDay, sellPrice, profit);
        }
    }
    public static int maxProfitWithHeap(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k <= 0) {
            return 0;
        }
        
        int n = prices.length;
        if (k >= n / 2) {
            return quickSolve(prices);
        }
    
        PriorityQueue<Trade> maxHeap = new PriorityQueue<>();
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (prices[j] > prices[i]) {
                    maxHeap.offer(new Trade(i, prices[i], j, prices[j]));
                }
            }
        }
        return selectBestTrades(maxHeap, k);
    }
    private static int quickSolve(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    private static int selectBestTrades(PriorityQueue<Trade> trades, int k) {
        List<Trade> selectedTrades = new ArrayList<>();
        int totalProfit = 0;
        
        while (!trades.isEmpty() && selectedTrades.size() < k) {
            Trade currentTrade = trades.poll();
            if (!hasConflict(currentTrade, selectedTrades)) {
                selectedTrades.add(currentTrade);
                totalProfit += currentTrade.profit;
            }
        }
        
        return totalProfit;
    }

    private static boolean hasConflict(Trade newTrade, List<Trade> existingTrades) {
        for (Trade existing : existingTrades) {
            if (!(newTrade.sellDay < existing.buyDay || newTrade.buyDay > existing.sellDay)) {
                return true;
            }
        }
        return false;
    }
    public static int maxProfitDP(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k <= 0) {
            return 0;
        }
        
        int n = prices.length;
        if (k >= n / 2) {
            return quickSolve(prices);
        }
        int[] buy = new int[k + 1];
        int[] sell = new int[k + 1];
        
        Arrays.fill(buy, -prices[0]);
        Arrays.fill(sell, 0);
        
        for (int i = 1; i < n; i++) {
            for (int j = k; j >= 1; j--) {
                sell[j] = Math.max(sell[j], buy[j] + prices[i]);
                buy[j] = Math.max(buy[j], sell[j - 1] - prices[i]);
            }
        }
        
        return sell[k];
    }

    public static int maxProfitWithDualHeap(int[] prices, int k) {
        if (prices == null || prices.length <= 1 || k <= 0) {
            return 0;
        }
        
        int n = prices.length;
        if (k >= n / 2) {
            return quickSolve(prices);
        }
        
        PriorityQueue<Integer> buyPrices = new PriorityQueue<>();
        PriorityQueue<Integer> sellPrices = new PriorityQueue<>(Collections.reverseOrder());      
        List<Integer> profits = new ArrayList<>();
        
        for (int i = 0; i < n - 1; i++) {

            if (i == 0 || (prices[i] <= prices[i - 1] && prices[i] < prices[i + 1])) {
                buyPrices.offer(prices[i]);
            }

            else if (prices[i] > prices[i - 1] && (i == n - 1 || prices[i] >= prices[i + 1])) {
                sellPrices.offer(prices[i]);
            }
        }

        if (prices[n - 1] > prices[n - 2]) {
            sellPrices.offer(prices[n - 1]);
        }

        while (!buyPrices.isEmpty() && !sellPrices.isEmpty()) {
            int buyPrice = buyPrices.poll();
            int sellPrice = sellPrices.poll();
            if (sellPrice > buyPrice) {
                profits.add(sellPrice - buyPrice);
            }
        }

        profits.sort(Collections.reverseOrder());
        
        int totalProfit = 0;
        for (int i = 0; i < Math.min(k, profits.size()); i++) {
            totalProfit += profits.get(i);
        }
        
        return totalProfit;
    }

    public static class TradingAnalyzer {
        
        public static void analyzeWithTrace(int[] prices, int k) {
            System.out.println("=== 股票交易分析 ===");
            System.out.printf("價格陣列: %s\n", Arrays.toString(prices));
            System.out.printf("最大交易次數: %d\n", k);
            System.out.println();
            
            if (prices == null || prices.length <= 1 || k <= 0) {
                System.out.println("無效輸入，最大利潤為 0");
                return;
            }

            System.out.println("價格趨勢分析:");
            for (int i = 0; i < prices.length; i++) {
                System.out.printf("第%d天: 價格%d", i, prices[i]);
                if (i > 0) {
                    int change = prices[i] - prices[i - 1];
                    System.out.printf(" (變化: %+d)", change);
                }
                System.out.println();
            }
            System.out.println();

            System.out.println("所有可能的有利交易:");
            PriorityQueue<Trade> allTrades = new PriorityQueue<>();
            
            for (int i = 0; i < prices.length - 1; i++) {
                for (int j = i + 1; j < prices.length; j++) {
                    if (prices[j] > prices[i]) {
                        Trade trade = new Trade(i, prices[i], j, prices[j]);
                        allTrades.offer(trade);
                        System.out.printf("  %s\n", trade);
                    }
                }
            }
            System.out.println();

            int result1 = maxProfitWithHeap(prices, k);
            int result2 = maxProfitDP(prices, k);
            int result3 = maxProfitWithDualHeap(prices, k);
            
            System.out.println("不同方法的結果:");
            System.out.printf("Heap + 貪心:     %d\n", result1);
            System.out.printf("DP 標準解法:     %d\n", result2);
            System.out.printf("雙 Heap 優化:    %d\n", result3);
            
            if (result1 == result2 && result2 == result3) {
                System.out.println("所有方法結果一致");
            } else {
                System.out.println("結果不一致，需要檢查算法");
            }
            
            System.out.printf("最終答案: %d\n", result2);
            System.out.println("─".repeat(50));
        }
        
        public static void findOptimalStrategy(int[] prices, int k) {
            System.out.println("=== 最佳交易策略分析 ===");
            
            if (prices == null || prices.length <= 1 || k <= 0) {
                System.out.println("無法進行交易");
                return;
            }

            int n = prices.length;
            if (k >= n / 2) {
                System.out.println("K 足夠大，可以進行所有有利交易:");
                int totalProfit = 0;
                for (int i = 1; i < n; i++) {
                    if (prices[i] > prices[i - 1]) {
                        System.out.printf("第%d天買入(價格%d) → 第%d天賣出(價格%d), 利潤: %d\n", 
                                i - 1, prices[i - 1], i, prices[i], prices[i] - prices[i - 1]);
                        totalProfit += prices[i] - prices[i - 1];
                    }
                }
                System.out.printf("總利潤: %d\n", totalProfit);
                return;
            }

            List<Integer> buyPoints = new ArrayList<>();
            List<Integer> sellPoints = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                boolean isBuyPoint = (i == 0 || prices[i] <= prices[i - 1]) && 
                                   (i == n - 1 || prices[i] < prices[i + 1]);
                boolean isSellPoint = (i == 0 || prices[i] > prices[i - 1]) && 
                                    (i == n - 1 || prices[i] >= prices[i + 1]);
                
                if (isBuyPoint) buyPoints.add(i);
                if (isSellPoint) sellPoints.add(i);
            }
            
            System.out.println("建議的交易策略:");
            System.out.printf("潛在買入點: %s\n", buyPoints.stream()
                    .map(i -> String.format("第%d天(價格%d)", i, prices[i]))
                    .reduce((a, b) -> a + ", " + b).orElse("無"));
            System.out.printf("潛在賣出點: %s\n", sellPoints.stream()
                    .map(i -> String.format("第%d天(價格%d)", i, prices[i]))
                    .reduce((a, b) -> a + ", " + b).orElse("無"));
        }
    }

    public static class PerformanceComparator {
        
        public static void compareAlgorithms(int[] prices, int k) {
            System.out.printf("=== 效能比較 (N=%d, K=%d) ===\n", prices.length, k);

            long start1 = System.nanoTime();
            int result1 = maxProfitWithHeap(prices, k);
            long time1 = System.nanoTime() - start1;

            long start2 = System.nanoTime();
            int result2 = maxProfitDP(prices, k);
            long time2 = System.nanoTime() - start2;

            long start3 = System.nanoTime();
            int result3 = maxProfitWithDualHeap(prices, k);
            long time3 = System.nanoTime() - start3;
            
            System.out.printf("Heap + 貪心:  結果=%d, 時間=%,d ns\n", result1, time1);
            System.out.printf("DP 標準解法:  結果=%d, 時間=%,d ns (推薦)\n", result2, time2);
            System.out.printf("雙 Heap:      結果=%d, 時間=%,d ns\n", result3, time3);

            long minTime = Math.min(time1, Math.min(time2, time3));
            if (time1 == minTime) System.out.println("🏆 Heap + 貪心最快");
            else if (time2 == minTime) System.out.println("🏆 DP 標準解法最快");
            else System.out.println("🏆 雙 Heap 最快");
            
            System.out.println("─".repeat(40));
        }
        
        public static void largeSacleTest() {
            System.out.println("=== 大規模效能測試 ===");
            
            int[] nValues = {100, 500, 1000};
            int[] kValues = {2, 5, 10, 50};
            
            for (int n : nValues) {
                for (int k : kValues) {
                    if (k > n / 2) continue;
                    
                    System.out.printf("\n測試規模: N=%d, K=%d\n", n, k);

                    int[] prices = generateRandomPrices(n);

                    long start = System.nanoTime();
                    int result = maxProfitDP(prices, k);
                    long time = System.nanoTime() - start;
                    
                    System.out.printf("DP 結果: %d, 時間: %,d ns\n", result, time);
                }
            }
        }
        
        private static int[] generateRandomPrices(int n) {
            Random random = new Random(42);
            int[] prices = new int[n];
            int basePrice = 50;
            
            for (int i = 0; i < n; i++) {
                basePrice += random.nextInt(21) - 10; 
                prices[i] = Math.max(1, basePrice);
            }
            
            return prices;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 股票交易最大利潤計算器 ===\n");

        System.out.println("測試案例1:");
        int[] prices1 = {2, 4, 1};
        int k1 = 2;
        TradingAnalyzer.analyzeWithTrace(prices1, k1);
        TradingAnalyzer.findOptimalStrategy(prices1, k1);

        System.out.println("測試案例2:");
        int[] prices2 = {3, 2, 6, 5, 0, 3};
        int k2 = 2;
        TradingAnalyzer.analyzeWithTrace(prices2, k2);
        TradingAnalyzer.findOptimalStrategy(prices2, k2);

        System.out.println("測試案例3:");
        int[] prices3 = {1, 2, 3, 4, 5};
        int k3 = 2;
        TradingAnalyzer.analyzeWithTrace(prices3, k3);
        TradingAnalyzer.findOptimalStrategy(prices3, k3);
        System.out.println("效能比較:");
        PerformanceComparator.compareAlgorithms(prices2, k2);
        PerformanceComparator.compareAlgorithms(new int[]{1,5,3,6,4,8,2,9}, 3);

        PerformanceComparator.largeSacleTest();

        System.out.println("\n=== 算法複雜度總結 ===");
        System.out.println("方法1 (Heap + 貪心):");
        System.out.println("  - 時間複雜度: O(N² log N)");
        System.out.println("  - 空間複雜度: O(N²)");
        System.out.println("  - 適用: 小規模數據，直觀理解");
        
        System.out.println("\n方法2 (DP 標準解法) - 推薦:");
        System.out.println("  - 時間複雜度: O(N × K)");
        System.out.println("  - 空間複雜度: O(K)");
        System.out.println("  - 適用: 所有情況，效率最高");
        
        System.out.println("\n方法3 (雙 Heap 優化):");
        System.out.println("  - 時間複雜度: O(N log N)");
        System.out.println("  - 空間複雜度: O(N)");
        System.out.println("  - 適用: 特殊情況優化");
        
        System.out.println("\n實際應用建議:");
        System.out.println("- 小規模數據 (N < 100): 任何方法都可以");
        System.out.println("- 中規模數據 (100 ≤ N < 1000): 推薦 DP 方法");
        System.out.println("- 大規模數據 (N ≥ 1000): 必須使用 DP 方法");
        System.out.println("- K 很大時 (K ≥ N/2): 自動使用貪心優化");
    }
}