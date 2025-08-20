import java.util.*;

public class M03_TopKConvenience {
    static class Product {
        String name;
        int quantity;
        int order;
        
        public Product(String name, int quantity, int order) {
            this.name = name;
            this.quantity = quantity;
            this.order = order;
        }
    }
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String[] firstLine = scanner.nextLine().trim().split("\\s+");
        int n = Integer.parseInt(firstLine[0]);
        int k = Integer.parseInt(firstLine[1]);
        
        List<Product> topK = findTopKWithMinHeap(scanner, n, k);
        
        for (Product product : topK) {
            System.out.println(product.name + " " + product.quantity);
        }
        
        scanner.close();
    }

    private static List<Product> findTopKWithMinHeap(Scanner scanner, int n, int k) {
        PriorityQueue<Product> minHeap = new PriorityQueue<>((a, b) -> {
            if (a.quantity != b.quantity) {
                return Integer.compare(a.quantity, b.quantity); 
            }
            return Integer.compare(b.order, a.order);
        });
        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().trim().split("\\s+");
            String name = line[0];
            int quantity = Integer.parseInt(line[1]);
            Product product = new Product(name, quantity, i);
            
            if (minHeap.size() < k) {
                minHeap.offer(product);
            } else if (product.quantity > minHeap.peek().quantity || 
                      (product.quantity == minHeap.peek().quantity && 
                       product.order < minHeap.peek().order)) {
                minHeap.poll();
                minHeap.offer(product);
            }
        }
        List<Product> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }

        Collections.reverse(result);
        
        return result;
    }

    private static List<Product> findTopKWithSorting(Scanner scanner, int n, int k) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().trim().split("\\s+");
            String name = line[0];
            int quantity = Integer.parseInt(line[1]);
            products.add(new Product(name, quantity, i));
        }
        Collections.sort(products, (a, b) -> {
            if (a.quantity != b.quantity) {
                return Integer.compare(b.quantity, a.quantity); 
            }
            return Integer.compare(a.order, b.order);
        });
        return products.subList(0, Math.min(k, products.size()));
    }
}
//使用Min-Heap（K << n）：
//建立大小為K的Min-Heap：O(K)
//處理商品：O(n * log K)
//如果堆不滿，直接插入：O(log K)
//如果堆滿且商品銷量大於堆，替換後調整：O(log K)
//從堆中取出K個元素並排序：O(K log K)
//時間複雜度：O(n log K + K log K) = O(n log K)
//方法2 - 直接排序（k接近n時）：
//排序所有商品：O(n log n)
//取前K個：O(K)
//時間複雜度：O(n log n)
//优势分析：
//當K << n時，Min-Heap方法更好：O(n log K) << O(n log n)
//Min-Heap方法：1000 * 6 = 6000 vs 直接排序：1000 * 10 = 10000
   