import java.util.*;

/**
 * LC33_SearchRotated_RentHot
 * 題目：在旋轉排序陣列中找 target 的索引，找不到回 -1。
 * 方法：改良二分，每次判斷左半或右半是否有序。
 * 時間複雜度：O(log n)
 */
public class LC33_SearchRotated_RentHot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        sc.close();

        int ans = search(nums, target);
        System.out.println(ans);
    }

    // 改良二分搜尋
    static int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) return mid;

            // 左半有序
            if (nums[l] <= nums[mid]) {
                if (nums[l] <= target && target < nums[mid]) {
                    r = mid - 1; // target 在左半
                } else {
                    l = mid + 1; // target 在右半
                }
            }
            // 右半有序
            else {
                if (nums[mid] < target && target <= nums[r]) {
                    l = mid + 1; // target 在右半
                } else {
                    r = mid - 1; // target 在左半
                }
            }
        }
        return -1;
    }
}
