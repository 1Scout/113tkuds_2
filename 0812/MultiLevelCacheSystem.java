import java.util.*;
public class MultiLevelCacheSystem {
    static class CacheItem {
        String key;
        String value;
        long accessCount;       
        long lastAccessTime;     
        long createTime;         
        int currentLevel;        
        double score;         
        
        public CacheItem(String key, String value, int level) {
            this.key = key;
            this.value = value;
            this.accessCount = 0;
            this.lastAccessTime = System.nanoTime();
            this.createTime = System.nanoTime();
            this.currentLevel = level;
            this.score = 0.0;
        }
        
        public void updateAccess() {
            this.accessCount++;
            this.lastAccessTime = System.nanoTime();
        }
        
        public void updateScore(long currentTime, int level) {
            double accessFrequency = (double) accessCount;
            double timeDecay = Math.exp(-(currentTime - lastAccessTime) / 1_000_000_000.0);
            double levelWeight = Math.pow(2, 3 - level); 
            
            this.score = accessFrequency * timeDecay * levelWeight;
        }
        
        @Override
        public String toString() {
            return String.format("%s:%s(L%d,score=%.2f,count=%d)", 
                                key, value, currentLevel, score, accessCount);
        }
    }
    static class CacheLevel {
        int level;
        int capacity;              
        int accessCost;       
        
        Map<String, CacheItem> data;
        LinkedHashMap<String, CacheItem> lruOrder;

        PriorityQueue<CacheItem> minHeap;
        PriorityQueue<CacheItem> maxHeap;
        
        public CacheLevel(int level, int capacity, int accessCost) {
            this.level = level;
            this.capacity = capacity;
            this.accessCost = accessCost;
            this.data = new HashMap<>();
            this.lruOrder = new LinkedHashMap<>(capacity + 1, 0.75f, true);
            
            this.minHeap = new PriorityQueue<>((a, b) -> Double.compare(a.score, b.score));
            this.maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b.score, a.score));
        }
        
        public boolean containsKey(String key) {
            return data.containsKey(key);
        }
        
        public CacheItem get(String key) {
            CacheItem item = data.get(key);
            if (item != null) {
                item.updateAccess();
                lruOrder.get(key);
                updateHeaps(item);
            }
            return item;
        }
        
        public void put(CacheItem item) {
            data.put(item.key, item);
            lruOrder.put(item.key, item);
            item.currentLevel = level;
            updateHeaps(item);
        }
        
        public CacheItem remove(String key) {
            CacheItem item = data.remove(key);
            if (item != null) {
                lruOrder.remove(key);
                minHeap.remove(item);
                maxHeap.remove(item);
            }
            return item;
        }
        
        public boolean isFull() {
            return data.size() >= capacity;
        }
        
        public CacheItem getLRU() {
            if (lruOrder.isEmpty()) return null;
            String lruKey = lruOrder.keySet().iterator().next();
            return data.get(lruKey);
        }
        
        public CacheItem getLowestScore() {
            cleanHeap(minHeap);
            return minHeap.isEmpty() ? null : minHeap.peek();
        }
        
        public CacheItem getHighestScore() {
            cleanHeap(maxHeap);
            return maxHeap.isEmpty() ? null : maxHeap.peek();
        }
        
        private void updateHeaps(CacheItem item) {
            minHeap.remove(item);
            maxHeap.remove(item);
            item.updateScore(System.nanoTime(), level);
            minHeap.offer(item);
            maxHeap.offer(item);
        }
        
        private void cleanHeap(PriorityQueue<CacheItem> heap) {
            while (!heap.isEmpty() && !data.containsKey(heap.peek().key)) {
                heap.poll();
            }
        }
        
        public void updateAllScores() {
            long currentTime = System.nanoTime();
            minHeap.clear();
            maxHeap.clear();
            
            for (CacheItem item : data.values()) {
                item.updateScore(currentTime, level);
                minHeap.offer(item);
                maxHeap.offer(item);
            }
        }
        
        public List<CacheItem> getAllItems() {
            return new ArrayList<>(data.values());
        }
        
        public int size() {
            return data.size();
        }
        
        @Override
        public String toString() {
            return String.format("L%d[%d/%d]: %s", level, data.size(), capacity, 
                               data.values().toString());
        }
    }
    public static class MultiLevelCache {
        private final CacheLevel[] levels;
        private final int numLevels;

        private long totalGets = 0;
        private long totalPuts = 0;
        private long[] levelHits;
        private long totalMigrations = 0;
        
        public MultiLevelCache(int[] capacities, int[] accessCosts) {
            if (capacities.length != accessCosts.length) {
                throw new IllegalArgumentException("容量和成本陣列長度必須相等");
            }
            
            numLevels = capacities.length;
            levels = new CacheLevel[numLevels];
            levelHits = new long[numLevels];
            
            for (int i = 0; i < numLevels; i++) {
                levels[i] = new CacheLevel(i + 1, capacities[i], accessCosts[i]);
            }
        }
        public String get(String key) {
            totalGets++;

            for (int i = 0; i < numLevels; i++) {
                if (levels[i].containsKey(key)) {
                    levelHits[i]++;
                    CacheItem item = levels[i].get(key);

                    considerMigration(item, true);
                    
                    return item.value;
                }
            }
            
            return null;
        }

        public void put(String key, String value) {
            totalPuts++;
            for (int i = 0; i < numLevels; i++) {
                if (levels[i].containsKey(key)) {
                    CacheItem item = levels[i].get(key);
                    item.value = value;
                    item.updateAccess();
                    considerMigration(item, true);
                    return;
                }
            }
            insertNewItem(key, value);
        }

        private void insertNewItem(String key, String value) {
            CacheItem newItem = new CacheItem(key, value, 1);

            if (!levels[0].isFull()) {
                levels[0].put(newItem);
                return;
            }
            makeSpaceAndInsert(newItem, 0);
        }

        private void makeSpaceAndInsert(CacheItem newItem, int targetLevel) {
            CacheLevel level = levels[targetLevel];
            
            if (!level.isFull()) {
                level.put(newItem);
                return;
            }
            
            CacheItem victimItem = selectVictim(level);
            
            if (victimItem != null) {
                level.remove(victimItem.key);
                if (targetLevel < numLevels - 1) {
                    victimItem.currentLevel = targetLevel + 2;
                    makeSpaceAndInsert(victimItem, targetLevel + 1);
                }
                
                totalMigrations++;
            }

            level.put(newItem);
        }

        private CacheItem selectVictim(CacheLevel level) {
            level.updateAllScores();

            CacheItem lowestScore = level.getLowestScore();
            if (lowestScore != null) {
                return lowestScore;
            }

            return level.getLRU();
        }

        private void considerMigration(CacheItem item, boolean accessBased) {
            int currentLevel = item.currentLevel - 1;

            for (CacheLevel level : levels) {
                level.updateAllScores();
            }

            if (currentLevel > 0 && shouldMigrateUp(item)) {
                migrateItemUp(item, currentLevel - 1);
            }
            else if (currentLevel < numLevels - 1 && shouldMigrateDown(item)) {
                migrateItemDown(item, currentLevel + 1);
            }
        }
        private boolean shouldMigrateUp(CacheItem item) {
            int currentLevel = item.currentLevel - 1;
            if (currentLevel == 0) return false;
            
            CacheLevel upperLevel = levels[currentLevel - 1];
            if (!upperLevel.isFull()) return true; 
            
            CacheItem upperLowest = upperLevel.getLowestScore();
            return upperLowest != null && item.score > upperLowest.score * 1.2; 
        }
        

        private boolean shouldMigrateDown(CacheItem item) {
            int currentLevel = item.currentLevel - 1;
            if (currentLevel == numLevels - 1) return false; 
            
            CacheLevel currentLevelObj = levels[currentLevel];
            if (!currentLevelObj.isFull()) return false;
            
            CacheItem currentLowest = currentLevelObj.getLowestScore();
            return currentLowest != null && item.key.equals(currentLowest.key);
        }
        
        private void migrateItemUp(CacheItem item, int targetLevel) {
            CacheLevel currentLevelObj = levels[item.currentLevel - 1];
            CacheLevel targetLevelObj = levels[targetLevel];

            currentLevelObj.remove(item.key);
            
            makeSpaceAndInsert(item, targetLevel);
            totalMigrations++;
        }

        private void migrateItemDown(CacheItem item, int targetLevel) {
            CacheLevel currentLevelObj = levels[item.currentLevel - 1];
            
            currentLevelObj.remove(item.key);
            
            makeSpaceAndInsert(item, targetLevel);
            totalMigrations++;
        }

        public void printStatistics() {
            System.out.println("=== 快取統計資訊 ===");
            System.out.printf("總查詢次數: %d, 總插入次數: %d\n", totalGets, totalPuts);
            System.out.printf("總遷移次數: %d\n", totalMigrations);
            
            for (int i = 0; i < numLevels; i++) {
                double hitRate = totalGets > 0 ? (double) levelHits[i] / totalGets * 100 : 0;
                System.out.printf("L%d 命中率: %.1f%% (%d/%d)\n", 
                                 i + 1, hitRate, levelHits[i], totalGets);
            }
            
            System.out.println("\n各層級狀態:");
            for (CacheLevel level : levels) {
                System.out.println(level);
            }
        }

        public Map<String, Object> getState() {
            Map<String, Object> state = new HashMap<>();
            
            for (int i = 0; i < numLevels; i++) {
                List<String> levelData = new ArrayList<>();
                for (CacheItem item : levels[i].getAllItems()) {
                    levelData.add(item.key + ":" + item.value);
                }
                state.put("L" + (i + 1), levelData);
            }
            
            return state;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 多層級快取系統測試 ===\n");

        int[] capacities = {2, 5, 10}; 
        int[] accessCosts = {1, 3, 10};
        
        MultiLevelCache cache = new MultiLevelCache(capacities, accessCosts);

        basicFunctionalityTest(cache);

        migrationTest(cache);
        performanceTest();
    }
    
    private static void basicFunctionalityTest(MultiLevelCache cache) {
        System.out.println("1. 基本功能測試:");
        cache.put("1", "A");
        cache.put("2", "B");
        cache.put("3", "C");
        System.out.println("插入 1:A, 2:B, 3:C");
        printCacheState(cache);
        System.out.println("\n頻繁存取項目 1:");
        cache.get("1");
        cache.get("1");
        cache.get("2");
        printCacheState(cache);
        System.out.println("\n繼續插入 4:D, 5:E, 6:F:");
        cache.put("4", "D");
        cache.put("5", "E");
        cache.put("6", "F");
        printCacheState(cache);
        
        cache.printStatistics();
        System.out.println();
    }
    
    private static void migrationTest(MultiLevelCache cache) {
        System.out.println("2. 遷移策略測試:");
        int[] capacities = {2, 3, 5};
        int[] accessCosts = {1, 3, 10};
        MultiLevelCache testCache = new MultiLevelCache(capacities, accessCosts);
        for (int i = 1; i <= 10; i++) {
            testCache.put(String.valueOf(i), "Value" + i);
        }
        
        System.out.println("填滿所有層級:");
        printCacheState(testCache);
        System.out.println("\n頻繁存取項目 8, 9, 10:");
        for (int i = 0; i < 5; i++) {
            testCache.get("8");
            testCache.get("9");
            testCache.get("10");
        }
        
        printCacheState(testCache);
        testCache.printStatistics();
        System.out.println();
    }
    
    private static void performanceTest() {
        System.out.println("3. 性能測試:");
        
        int[] capacities = {10, 50, 100};
        int[] accessCosts = {1, 5, 20};
        MultiLevelCache cache = new MultiLevelCache(capacities, accessCosts);
        
        Random random = new Random(42);
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 1000; i++) {
            if (random.nextBoolean()) {
                cache.put(String.valueOf(random.nextInt(200)), "Value" + i);
            } else {
                cache.get(String.valueOf(random.nextInt(200)));
            }
        }
        
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;
        
        System.out.printf("1000次隨機操作耗時: %.2f ms\n", executionTime);
        cache.printStatistics();
    }
    
    private static void printCacheState(MultiLevelCache cache) {
        Map<String, Object> state = cache.getState();
        for (String level : Arrays.asList("L1", "L2", "L3")) {
            System.out.printf("%s: %s\n", level, state.get(level));
        }
    }
}