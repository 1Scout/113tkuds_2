import java.util.*;
public class MovingAverageStream {

    public static class MovingAverage {
        private final int maxSize;           
        private final Queue<Integer> window; 
        private long sum;                   
        private final PriorityQueue<Integer> maxHeap;
        private final PriorityQueue<Integer> minHeap;
        private final Deque<Integer> minDeque;
        private final Deque<Integer> maxDeque;
        private final Map<Integer, Integer> toRemove;
        
        public MovingAverage(int size) {
            this.maxSize = size;
            this.window = new LinkedList<>();
            this.sum = 0;
            
            this.maxHeap = new PriorityQueue<>((a, b) -> b - a);
            this.minHeap = new PriorityQueue<>();
            
            this.minDeque = new ArrayDeque<>();
            this.maxDeque = new ArrayDeque<>();
            this.toRemove = new HashMap<>();
        }

        public double next(int val) {
            if (window.size() == maxSize) {
                int removed = window.poll();
                sum -= removed;

                toRemove.put(removed, toRemove.getOrDefault(removed, 0) + 1);

                removeFromDeque(minDeque, removed);
                removeFromDeque(maxDeque, removed);
            }
            window.offer(val);
            sum += val;
            addToHeaps(val);
            addToMinDeque(val);
            addToMaxDeque(val);
            
            return (double) sum / window.size();
        }

        public double getMedian() {
            if (window.isEmpty()) {
                throw new IllegalStateException("視窗為空");
            }
            
            cleanHeaps();
            
            int size = window.size();
            if (size % 2 == 1) {
                return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
            } else {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            }
        }

        public int getMin() {
            if (window.isEmpty()) {
                throw new IllegalStateException("視窗為空");
            }
            return minDeque.peekFirst();
        }
        public int getMax() {
            if (window.isEmpty()) {
                throw new IllegalStateException("視窗為空");
            }
            return maxDeque.peekFirst();
        }
        private void addToHeaps(int val) {
            if (maxHeap.isEmpty() || val <= maxHeap.peek()) {
                maxHeap.offer(val);
            } else {
                minHeap.offer(val);
            }
            balanceHeaps();
        }
        private void balanceHeaps() {
            if (maxHeap.size() > minHeap.size() + 1) {
                minHeap.offer(maxHeap.poll());
            } else if (minHeap.size() > maxHeap.size() + 1) {
                maxHeap.offer(minHeap.poll());
            }
        }

        private void cleanHeaps() {
            cleanHeap(maxHeap);
            cleanHeap(minHeap);
            balanceHeaps();
        }

        private void cleanHeap(PriorityQueue<Integer> heap) {
            while (!heap.isEmpty() && toRemove.containsKey(heap.peek()) && toRemove.get(heap.peek()) > 0) {
                int removed = heap.poll();
                int count = toRemove.get(removed);
                if (count == 1) {
                    toRemove.remove(removed);
                } else {
                    toRemove.put(removed, count - 1);
                }
            }
        }
        private void addToMinDeque(int val) {
            while (!minDeque.isEmpty() && minDeque.peekLast() > val) {
                minDeque.pollLast();
            }
            minDeque.offerLast(val);
        }
        private void addToMaxDeque(int val) {
            while (!maxDeque.isEmpty() && maxDeque.peekLast() < val) {
                maxDeque.pollLast();
            }
            maxDeque.offerLast(val);
        }

        private void removeFromDeque(Deque<Integer> deque, int val) {
            if (!deque.isEmpty() && deque.peekFirst().equals(val)) {
                deque.pollFirst();
            }
        }
        public int size() {
            return window.size();
        }

        public boolean isEmpty() {
            return window.isEmpty();
        }
        public List<Integer> getCurrentWindow() {
            return new ArrayList<>(window);
        }
    }

    public static class MovingAverageAdvanced {
        private final int maxSize;
        private final Queue<Integer> window;
        private long sum;
        private final TreeMap<Integer, Integer> sortedCount;
        private final Deque<Integer> minDeque;
        private final Deque<Integer> maxDeque;
        
        public MovingAverageAdvanced(int size) {
            this.maxSize = size;
            this.window = new LinkedList<>();
            this.sum = 0;
            this.sortedCount = new TreeMap<>();
            this.minDeque = new ArrayDeque<>();
            this.maxDeque = new ArrayDeque<>();
        }
        
        public double next(int val) {
            if (window.size() == maxSize) {
                int removed = window.poll();
                sum -= removed;
                removeFromSortedCount(removed);
                removeFromDeque(minDeque, removed);
                removeFromDeque(maxDeque, removed);
            }
            window.offer(val);
            sum += val;
            addToSortedCount(val);
            addToMinDeque(val);
            addToMaxDeque(val);
            
            return (double) sum / window.size();
        }
        
        public double getMedian() {
            if (window.isEmpty()) {
                throw new IllegalStateException("視窗為空");
            }
            
            int size = window.size();
            int mid = size / 2;
            
            List<Integer> elements = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : sortedCount.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    elements.add(entry.getKey());
                }
            }
            
            if (size % 2 == 1) {
                return elements.get(mid);
            } else {
                return (elements.get(mid - 1) + elements.get(mid)) / 2.0;
            }
        }
        
        public int getMin() {
            if (window.isEmpty()) {
                throw new IllegalStateException("視窗為空");
            }
            return minDeque.peekFirst();
        }
        
        public int getMax() {
            if (window.isEmpty()) {
                throw new IllegalStateException("視窗為空");
            }
            return maxDeque.peekFirst();
        }
        
        private void addToSortedCount(int val) {
            sortedCount.put(val, sortedCount.getOrDefault(val, 0) + 1);
        }
        
        private void removeFromSortedCount(int val) {
            int count = sortedCount.get(val);
            if (count == 1) {
                sortedCount.remove(val);
            } else {
                sortedCount.put(val, count - 1);
            }
        }
        
        private void addToMinDeque(int val) {
            while (!minDeque.isEmpty() && minDeque.peekLast() > val) {
                minDeque.pollLast();
            }
            minDeque.offerLast(val);
        }
        
        private void addToMaxDeque(int val) {
            while (!maxDeque.isEmpty() && maxDeque.peekLast() < val) {
                maxDeque.pollLast();
            }
            maxDeque.offerLast(val);
        }
        
        private void removeFromDeque(Deque<Integer> deque, int val) {
            if (!deque.isEmpty() && deque.peekFirst().equals(val)) {
                deque.pollFirst();
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("=== 移動平均數資料流測試 ===\n");
        testBasicFunctionality();
        testEdgeCases();
        performanceComparison();
    }
    
    private static void testBasicFunctionality() {
        System.out.println("1. 基本功能測試：");
        MovingAverage ma = new MovingAverage(3);
        
        System.out.printf("ma.next(1) = %.1f\n", ma.next(1));
        System.out.printf("ma.next(10) = %.1f\n", ma.next(10));
        System.out.printf("ma.next(3) = %.2f\n", ma.next(3));
        System.out.printf("ma.next(5) = %.1f\n", ma.next(5));
        
        System.out.printf("當前視窗: %s\n", ma.getCurrentWindow());
        System.out.printf("getMedian() = %.1f\n", ma.getMedian());
        System.out.printf("getMin() = %d\n", ma.getMin());
        System.out.printf("getMax() = %d\n", ma.getMax());
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("2. 邊界情況測試：");
        MovingAverage ma1 = new MovingAverage(1);
        System.out.printf("單元素視窗: %.1f\n", ma1.next(42));
        System.out.printf("中位數: %.1f, 最小值: %d, 最大值: %d\n", 
                         ma1.getMedian(), ma1.getMin(), ma1.getMax());

        MovingAverage ma2 = new MovingAverage(4);
        ma2.next(5);
        ma2.next(5);
        ma2.next(5);
        ma2.next(5);
        System.out.printf("重複元素視窗: %s\n", ma2.getCurrentWindow());
        System.out.printf("中位數: %.1f, 最小值: %d, 最大值: %d\n", 
                         ma2.getMedian(), ma2.getMin(), ma2.getMax());

        MovingAverage ma3 = new MovingAverage(3);
        ma3.next(-1);
        ma3.next(-5);
        ma3.next(3);
        System.out.printf("包含負數視窗: %s\n", ma3.getCurrentWindow());
        System.out.printf("平均數: %.2f, 中位數: %.1f, 最小值: %d, 最大值: %d\n", 
                         ma3.next(0), ma3.getMedian(), ma3.getMin(), ma3.getMax());
        System.out.println();
    }
    
    private static void performanceComparison() {
        System.out.println("3. 性能對比測試：");
        
        int windowSize = 1000;
        int testCount = 10000;
        long startTime = System.nanoTime();
        MovingAverage ma1 = new MovingAverage(windowSize);
        Random random = new Random(42);
        
        for (int i = 0; i < testCount; i++) {
            ma1.next(random.nextInt(1000));
            if (i % 1000 == 0 && i > 0) {
                ma1.getMedian();
                ma1.getMin();
                ma1.getMax();
            }
        }
        long basicTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        MovingAverageAdvanced ma2 = new MovingAverageAdvanced(windowSize);
        random = new Random(42);
        
        for (int i = 0; i < testCount; i++) {
            ma2.next(random.nextInt(1000));
            if (i % 1000 == 0 && i > 0) {
                ma2.getMedian();
                ma2.getMin();
                ma2.getMax();
            }
        }
        long advancedTime = System.nanoTime() - startTime;
        
        System.out.printf("基本版本耗時: %.2f ms\n", basicTime / 1_000_000.0);
        System.out.printf("進階版本耗時: %.2f ms\n", advancedTime / 1_000_000.0);
        System.out.printf("性能提升: %.1fx\n", (double) basicTime / advancedTime);
    }
}