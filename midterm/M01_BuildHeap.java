import java.util.*;

public class M01_BuildHeap {

    private static void heapifyDown(int[] arr, int n, int i, boolean isMaxHeap) {
        int target = i;
        int left = 2 * i + 1; 
        int right = 2 * i + 2;  
        
        if (left < n && compare(arr[left], arr[target], isMaxHeap)) {
            target = left;
        }
        
        if (right < n && compare(arr[right], arr[target], isMaxHeap)) {
            target = right;
        }
        
        if (target != i) {
            swap(arr, i, target);
            heapifyDown(arr, n, target, isMaxHeap);
        }
    }

    private static boolean compare(int a, int b, boolean isMaxHeap) {
        return isMaxHeap ? (a > b) : (a < b);
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void buildHeap(int[] arr, int n, boolean isMaxHeap) {

        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapifyDown(arr, n, i, isMaxHeap);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String type = scanner.nextLine().trim();
        boolean isMaxHeap = type.equals("max");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        String[] values = scanner.nextLine().trim().split("\\s+");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(values[i]);
        }
        
        buildHeap(arr, n, isMaxHeap);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(" ");
            sb.append(arr[i]);
        }
        System.out.println(sb.toString());
        
        scanner.close();
    }
}
//時間複雜度：
//高度為 h 的點最多有 n/2^(h+1) 個
//每個高度为為h的點最多需要 h 次操作
//O(n)
