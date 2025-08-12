import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BasicMinHeapPractice {
    private ArrayList<Integer> heap;
    
    public BasicMinHeapPractice() {
        heap = new ArrayList<>();
    }
    public void insert(int val) {
        heap.add(val);
        heapifyUp(heap.size() - 1);
    }
    public int extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        
        int min = heap.get(0);
        int lastElement = heap.get(heap.size() - 1);
        heap.set(0, lastElement);
        heap.remove(heap.size() - 1);
        
        if (!isEmpty()) {
            heapifyDown(0);
        }
        
        return min;
    }
    public int getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }
    public int size() {
        return heap.size();
    }
    public boolean isEmpty() {
        return heap.size() == 0;
    }
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            
            if (heap.get(index) >= heap.get(parentIndex)) {
                break;
            }
            
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    private void heapifyDown(int index) {
        int size = heap.size();
        
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;
            if (leftChild < size && heap.get(leftChild) < heap.get(smallest)) {
                smallest = leftChild;
            }
            
            if (rightChild < size && heap.get(rightChild) < heap.get(smallest)) {
                smallest = rightChild;
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);
            index = smallest;
        }
    }
    
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    public void printHeap() {
        System.out.println("Heap: " + heap);
    }
    public static void main(String[] args) {
        BasicMinHeapPractice minHeap = new BasicMinHeapPractice();
        
        System.out.println("=== 測試插入操作 ===");
        int[] testData = {15, 10, 20, 8, 25, 5};
        
        for (int val : testData) {
            minHeap.insert(val);
            System.out.println("插入 " + val + " 後的 heap:");
            minHeap.printHeap();
            System.out.println("當前最小值: " + minHeap.getMin());
            System.out.println();
        }
        
        System.out.println("=== 測試 extractMin 操作 ===");
        System.out.println("期望的 extractMin 順序：5, 8, 10, 15, 20, 25");
        System.out.print("實際的 extractMin 順序：");
        
        while (!minHeap.isEmpty()) {
            int min = minHeap.extractMin();
            System.out.print(min);
            if (!minHeap.isEmpty()) {
                System.out.print(", ");
            }
        }
        System.out.println();
        
        System.out.println("\n=== 測試邊界情況 ===");
        System.out.println("Heap 是否為空: " + minHeap.isEmpty());
        System.out.println("Heap 大小: " + minHeap.size());
        
        try {
            minHeap.getMin();
        } catch (NoSuchElementException e) {
            System.out.println("正確捕獲異常：" + e.getMessage());
        }
        
        try {
            minHeap.extractMin();
        } catch (NoSuchElementException e) {
            System.out.println("正確捕獲異常：" + e.getMessage());
        }
        
        System.out.println("\n=== 再次測試 ===");
        minHeap.insert(100);
        minHeap.insert(50);
        minHeap.insert(200);
        System.out.println("插入 100, 50, 200 後:");
        minHeap.printHeap();
        System.out.println("取出最小值: " + minHeap.extractMin());
        System.out.println("取出最小值: " + minHeap.extractMin());
        System.out.println("剩餘最小值: " + minHeap.getMin());
    }
}