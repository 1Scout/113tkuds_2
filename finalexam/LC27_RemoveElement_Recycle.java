import java.util.*;

public class LC27_RemoveElement_Recycle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 讀取 n 與 val
        int n = sc.nextInt();
        int val = sc.nextInt();

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        // 呼叫處理函式
        int newLen = removeElement(nums, val);

        // 輸出結果
        System.out.println(newLen);
        for (int i = 0; i < newLen; i++) {
            System.out.print(nums[i] + (i == newLen - 1 ? "" : " "));
        }
    }

    // 單指針覆寫法
    public static int removeElement(int[] nums, int val) {
        int write = 0; // 指向寫入位置
        for (int x : nums) {
            if (x != val) {
                nums[write++] = x;
            }
        }
        return write; // 新長度
    }
}