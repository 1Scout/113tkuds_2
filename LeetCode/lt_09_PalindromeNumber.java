
class lt_09_PalindromeNumber {
    public boolean isPalindrome(int x) {
        // Negative numbers and numbers ending with 0 (except 0 itself) are not palindromes
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        
        // Reverse only half of the number to avoid overflow
        int reversedHalf = 0;
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }
        
        // For even length numbers: x == reversedHalf
        // For odd length numbers: x == reversedHalf / 10 (middle digit doesn't matter)
        return x == reversedHalf || x == reversedHalf / 10;
    }
}
