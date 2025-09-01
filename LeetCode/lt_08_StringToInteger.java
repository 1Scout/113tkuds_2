
class lt_08_StringToInteger {
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) return 0;
        
        int index = 0;
        int n = s.length();
        
        // Skip leading whitespace
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }
        
        if (index == n) return 0;
        
        // Check for sign
        int sign = 1;
        if (s.charAt(index) == '+' || s.charAt(index) == '-') {
            sign = s.charAt(index) == '-' ? -1 : 1;
            index++;
        }
        
        // Read digits and build number
        long result = 0;
        while (index < n && Character.isDigit(s.charAt(index))) {
            result = result * 10 + (s.charAt(index) - '0');
            
            // Check for overflow
            if (sign == 1 && result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if (sign == -1 && -result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            
            index++;
        }
        
        return (int) (sign * result);
    }
}
