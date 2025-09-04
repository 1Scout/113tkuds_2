import java.util.*;

public class LC01_TwoSum_THSRHoliday {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入
        int n = scanner.nextInt();
        int target = scanner.nextInt();
        
        int[] seats = new int[n];
        for (int i = 0; i < n; i++) {
            seats[i] = scanner.nextInt();
        }
        
        // 解決 Two Sum 問題
        int[] result = twoSum(seats, target);
        
        // 輸出結果
        System.out.println(result[0] + " " + result[1]);
        
        scanner.close();
    }
    
    /**
     * 使用 HashMap 解決 Two Sum 問題
     * 時間複雜度: O(n)
     * 空間複雜度: O(n)
     * 
     * @param nums 班次座位數陣列
     * @param target 目標座位數總和
     * @return 兩個索引陣列，若無解則返回 [-1, -1]
     */
    public static int[] twoSum(int[] nums, int target) {
        // HashMap 儲存 <需要的數值, 索引>
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int needed = target - current;
            
            // 檢查是否已經有符合的數值存在
            if (map.containsKey(current)) {
                // 找到解！返回之前記錄的索引和當前索引
                return new int[]{map.get(current), i};
            }
            
            // 記錄當前需要的數值和索引
            map.put(needed, i);
        }
        
        // 沒有找到解
        return new int[]{-1, -1};
    }
}