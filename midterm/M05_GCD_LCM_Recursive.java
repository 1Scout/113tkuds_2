import java.util.*;

public class M05_GCD_LCM_Recursive {

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }

    public static long lcm(long a, long b) {
        long gcdValue = gcd(a, b);

        return (a / gcdValue) * b;
    }

    public static long gcdIterative(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().trim().split("\\s+");
        long a = Long.parseLong(input[0]);
        long b = Long.parseLong(input[1]);
        long gcdResult = gcd(a, b);
        long lcmResult = lcm(a, b);
        System.out.println("GCD: " + gcdResult);
        System.out.println("LCM: " + lcmResult);
        
        scanner.close();
    }
}

//Time Complexity: O(log(min(a,b)))
//歐幾里得演算法的時間複雜度：O(log(min(a,b)))
//證明：每次遞迴，最大數至少减半
//如果 a >= b，則 gcd(a,b) = gcd(b, a%b)
//由于 a%b < b <= a/2（当a >= b時），所以規模至少减半
//最多需要 log₂(min(a,b)) 次遞迴
