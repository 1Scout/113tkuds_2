import java.util.*;

public class LC15_3Sum_THSRStops {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入
        int n = scanner.nextInt();
        int[] nums = new int[n];
        
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        
        // 找到所有和為0的三元組
        List<List<Integer>> result = threeSum(nums);
        
        // 輸出結果
        for (List<Integer> triplet : result) {
            System.out.println(triplet.get(0) + " " + triplet.get(1) + " " + triplet.get(2));
        }
        
        scanner.close();
    }
    
    /**
     * 找到所有和為0的不重複三元組
     * 時間複雜度: O(n²)
     * 空間複雜度: O(1) (不計算結果空間)
     * 
     * @param nums 站點調整量陣列
     * @return 所有和為0的三元組列表
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // 第一步：排序陣列，便於去重和雙指針操作
        Arrays.sort(nums);
        
        // 第二步：固定第一個數，用雙指針找剩下兩個數
        for (int i = 0; i < nums.length - 2; i++) {
            // 優化：如果第一個數已經大於0，後面不可能有和為0的組合
            if (nums[i] > 0) {
                break;
            }
            
            // 跳過重複的第一個數
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 雙指針尋找和為 -nums[i] 的兩數組合
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    // 找到一組解
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳過重複的left值
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 跳過重複的right值
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    // 移動指針繼續尋找
                    left++;
                    right--;
                } else if (sum < target) {
                    // 和太小，需要增大左邊的數
                    left++;
                } else {
                    // 和太大，需要減小右邊的數
                    right--;
                }
            }
        }
        
        return result;
    }
}