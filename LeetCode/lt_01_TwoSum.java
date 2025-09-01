import java.util.*;

public class lt_01_TwoSum {
    
    /**
     * Find two indices in nums array that sum to target.
     * 
     * @param nums Array of integers
     * @param target Target sum value
     * @return Array of two indices [i, j] where nums[i] + nums[j] == target
     */
    public static int[] twoSum(int[] nums, int target) {
        // HashMap to store value -> index mapping
        Map<Integer, Integer> numToIndex = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            // Calculate what number we need to find
            int complement = target - nums[i];
            
            // Check if complement exists in our HashMap
            if (numToIndex.containsKey(complement)) {
                // Found the pair! Return indices
                return new int[]{numToIndex.get(complement), i};
            }
            
            // Store current number and its index for future lookups
            numToIndex.put(nums[i], i);
        }
        
        // This shouldn't happen given problem constraints
        return new int[]{};
    }
    
    // Test method
    public static void main(String[] args) {
        System.out.println("Testing Two Sum Solution:");
        System.out.println("------------------------------");
        
        // Example 1
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] result1 = twoSum(nums1, target1);
        System.out.println("Example 1: nums = " + Arrays.toString(nums1) + ", target = " + target1);
        System.out.println("Output: " + Arrays.toString(result1));
        System.out.println("Verification: nums[" + result1[0] + "] + nums[" + result1[1] + "] = " 
                         + nums1[result1[0]] + " + " + nums1[result1[1]] + " = " 
                         + (nums1[result1[0]] + nums1[result1[1]]));
        System.out.println();
        
        // Example 2
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        int[] result2 = twoSum(nums2, target2);
        System.out.println("Example 2: nums = " + Arrays.toString(nums2) + ", target = " + target2);
        System.out.println("Output: " + Arrays.toString(result2));
        System.out.println("Verification: nums[" + result2[0] + "] + nums[" + result2[1] + "] = " 
                         + nums2[result2[0]] + " + " + nums2[result2[1]] + " = " 
                         + (nums2[result2[0]] + nums2[result2[1]]));
        System.out.println();
        
        // Example 3
        int[] nums3 = {3, 3};
        int target3 = 6;
        int[] result3 = twoSum(nums3, target3);
        System.out.println("Example 3: nums = " + Arrays.toString(nums3) + ", target = " + target3);
        System.out.println("Output: " + Arrays.toString(result3));
        System.out.println("Verification: nums[" + result3[0] + "] + nums[" + result3[1] + "] = " 
                         + nums3[result3[0]] + " + " + nums3[result3[1]] + " = " 
                         + (nums3[result3[0]] + nums3[result3[1]]));
    }
}