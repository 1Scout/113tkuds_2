class Solution {
    public int divide(int dividend, int divisor) {
        // 特殊情況：-2^31 / -1 會超出 int 範圍 (因為答案是 2^31 > Integer.MAX_VALUE)
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 判斷結果正負號
        boolean negative = (dividend < 0) ^ (divisor < 0);

        // 轉換為 long 避免溢位，並且取絕對值
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);

        int result = 0;

        // 透過「倍增減法」模擬除法
        while (dvd >= dvs) {
            long temp = dvs;  // 當前的除數
            int multiple = 1; // 當前倍數

            // 不斷倍增，直到超過被除數
            while (dvd >= (temp << 1)) {
                temp <<= 1;         // 除數倍增
                multiple <<= 1;     // 倍數也跟著倍增
            }

            // 減去這一段倍增的值
            dvd -= temp;
            result += multiple;
        }

        // 根據正負號決定最後答案
        return negative ? -result : result;
    }
}
