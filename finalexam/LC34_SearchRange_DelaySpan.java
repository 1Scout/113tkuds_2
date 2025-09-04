import java.util.*;

/**
 * LC34_SearchRange_DelaySpan
 * 題目：在已排序陣列中找到 target 的首尾索引。
 * 若不存在，輸出 -1 -1。
 * 方法：二分搜尋兩次 (lower_bound / upper_bound)。
 * 時間複雜度：O(log n)，空間 O(1)。
 */
public class LC34_SearchRange_DelaySpan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        sc.close();

        int[] ans = searchRange(nums, target);
        System.out.println(ans[0] + " " + ans[1]);
    }

    // 回傳 [firstIndex, lastIndex]
    static int[] searchRange(int[] nums, int target) {
        int left = lowerBound(nums, target);
        if (left == nums.length || nums[left] != target) {
            return new int[]{-1, -1}; // target 不存在
        }
        int right = lowerBound(nums, target + 1) - 1;
        return new int[]{left, right};
    }

    // lowerBound: 傳回第一個 >= target 的索引
    static int lowerBound(int[] nums, int target) {
        int l = 0, r = nums.length; // 注意 r = n（開區間）
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }
}
