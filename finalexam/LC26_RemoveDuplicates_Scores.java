import java.util.*;

/**
 * LC26_RemoveDuplicates_Scores
 * 題目：移除排序陣列中的重複元素，使每個值只出現一次。
 * 方法：雙指針（write 指向可寫位置，i 遍歷）。
 * 複雜度：O(n)，空間 O(1) 額外空間。
 */
public class LC26_RemoveDuplicates_Scores {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.hasNextInt() ? sc.nextInt() : 0;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();

        int newLen = removeDuplicates(arr, n);

        // 輸出壓縮後長度
        System.out.println(newLen);

        // 輸出前段有效結果
        for (int i = 0; i < newLen; i++) {
            System.out.print(arr[i]);
            if (i < newLen - 1) System.out.print(" ");
        }
        if (newLen > 0) System.out.println();
    }

    // 移除重複，返回新長度
    static int removeDuplicates(int[] nums, int n) {
        if (n == 0) return 0;

        int write = 1; // 第一個數必保留
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[write - 1]) {
                nums[write] = nums[i];
                write++;
            }
        }
        return write;
    }
}
