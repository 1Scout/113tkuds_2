import java.util.*;

/**
 * LC18_4Sum_Procurement
 * 題目：採購限額 4Sum
 * 給定 n 個數字與 target，輸出所有不重複的四元組，使其總和等於 target。
 */
public class LC18_4Sum_Procurement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();     // 數列長度
        long target = sc.nextLong(); // 預算 (可能非常大，故用 long)
        long[] nums = new long[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextLong();
        }
        sc.close();

        // 排序，方便後續去重與雙指針操作
        Arrays.sort(nums);

        List<List<Long>> res = new ArrayList<>();

        // 固定前兩層 i, j
        for (int i = 0; i < n - 3; i++) {
            // 去重：若與前一個數值相同，則略過
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < n - 2; j++) {
                // 去重：避免相同 j 重複計算
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int L = j + 1, R = n - 1;
                while (L < R) {
                    long sum = nums[i] + nums[j] + nums[L] + nums[R];
                    if (sum == target) {
                        // 找到合法組合
                        res.add(Arrays.asList(nums[i], nums[j], nums[L], nums[R]));

                        // 移動 L，略過重複值
                        long lastL = nums[L];
                        while (L < R && nums[L] == lastL) L++;

                        // 移動 R，略過重複值
                        long lastR = nums[R];
                        while (L < R && nums[R] == lastR) R--;
                    } else if (sum < target) {
                        L++;
                    } else {
                        R--;
                    }
                }
            }
        }

        // 輸出結果，每行一組，升序四元組
        for (List<Long> quad : res) {
            for (int k = 0; k < quad.size(); k++) {
                System.out.print(quad.get(k));
                if (k < quad.size() - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }
}
