import java.util.*;

public class M11_HeapSortWithTie {
    static class Element {
        int score;
        int index;
        
        Element(int score, int index) {
            this.score = score;
            this.index = index;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        Element[] elements = new Element[n];
        for (int i = 0; i < n; i++) {
            int score = sc.nextInt();
            elements[i] = new Element(score, i);
        }
        
        heapSort(elements);
        
        for (int i = 0; i < n; i++) {
            System.out.print(elements[i].score);
            if (i < n - 1) System.out.print(" ");
        }
        System.out.println();
        
        sc.close();
    }
    
    private static void heapSort(Element[] arr) {
        int n = arr.length;
        
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }
        
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapifyDown(arr, i, 0);
        }
    }
    
    private static void heapifyDown(Element[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && compare(arr[left], arr[largest]) > 0) {
            largest = left;
        }
        
        if (right < n && compare(arr[right], arr[largest]) > 0) {
            largest = right;
        }
        
        if (largest != i) {
            swap(arr, i, largest);
            heapifyDown(arr, n, largest);
        }
    }
    
    private static int compare(Element a, Element b) {
        if (a.score != b.score) {
            return Integer.compare(a.score, b.score);
        }
        return Integer.compare(b.index, a.index);
    }
    
    private static void swap(Element[] arr, int i, int j) {
        Element temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

/*
 * Time Complexity: O(n log n)
 * 說明：建立max heap需要O(n)時間，接著進行n-1次extract-max操作
 * 每次extract-max包含交換元素到堆尾和heapify-down，heapify-down需要O(log n)時間
 * 因此整體時間複雜度為O(n) + O(n log n) = O(n log n)
 */
