import java.util.*;

public class M04_TieredTaxSimple {
    

    private static final long[] TAX_BRACKETS = {0, 120001, 500001, 1000001};

    private static final double[] TAX_RATES = {0.05, 0.12, 0.20, 0.30};

    private static final long[] BRACKET_LIMITS = {120000, 500000, 1000000, Long.MAX_VALUE};
    

    private static long calculateTax(long income) {
        if (income <= 0) {
            return 0;
        }
        
        long totalTax = 0;
        long remainingIncome = income;

        for (int i = 0; i < TAX_BRACKETS.length; i++) {

            long bracketStart = TAX_BRACKETS[i];

            long bracketEnd = BRACKET_LIMITS[i];

            double rate = TAX_RATES[i];

            if (income <= bracketStart) {
                break;
            }
            
            long taxableInThisBracket;
            if (income <= bracketEnd) {
                taxableInThisBracket = income - bracketStart;
            } else {
                taxableInThisBracket = bracketEnd - bracketStart + 1;
            }
            totalTax += (long) (taxableInThisBracket * rate);
        }
        
        return totalTax;
    }
    private static long calculateTaxTraditional(long income) {
        if (income <= 0) return 0;
        
        long tax = 0;
        
        if (income <= 120000) {
            tax = (long) (income * 0.05);
        } else if (income <= 500000) {
            tax = (long) (120000 * 0.05 + (income - 120000) * 0.12);
        } else if (income <= 1000000) {
            tax = (long) (120000 * 0.05 + 380000 * 0.12 + (income - 500000) * 0.20);
        } else {
            tax = (long) (120000 * 0.05 + 380000 * 0.12 + 500000 * 0.20 + (income - 1000000) * 0.30);
        }
        
        return tax;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        long[] taxes = new long[n];
        long totalTax = 0;
        
        for (int i = 0; i < n; i++) {
            long income = Long.parseLong(scanner.nextLine().trim());
            taxes[i] = calculateTax(income);
            totalTax += taxes[i];
        }
        
        for (int i = 0; i < n; i++) {
            System.out.println("Tax: " + taxes[i]);
        }
        
        long averageTax = Math.round((double) totalTax / n);
        System.out.println("Average: " + averageTax);
        
        scanner.close();
    }
}
//時間複雜度分析：
//calculateTax：O(1)
//處理n個收入：O(n × 1) = O(n)
//計算平均：O(1)
//時間複雜度：O(n)