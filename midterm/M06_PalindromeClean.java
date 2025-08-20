import java.util.*;

public class M06_PalindromeClean {
    

    public static boolean isPalindromeWithTwoPointers(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {

            while (left < right && !Character.isLetter(s.charAt(left))) {
                left++;
            }

            while (left < right && !Character.isLetter(s.charAt(right))) {
                right--;
            }

            if (Character.toLowerCase(s.charAt(left)) != 
                Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }

   

    private static boolean isPalindromeRecursive(String s, int left, int right) {

        if (left >= right) {
            return true;
        }
        
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        
        return isPalindromeRecursive(s, left + 1, right - 1);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        boolean result = isPalindromeWithTwoPointers(input);

        System.out.println(result ? "Yes" : "No");
        
        scanner.close();
    }
}
//時間複雜度：
//雙指標直接判斷：
//最多遍歷字符串一次
//每个字符最多被檢查常數次

