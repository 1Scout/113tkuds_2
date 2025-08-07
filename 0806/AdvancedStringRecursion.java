import java.util.*;
public class AdvancedStringRecursion {

    public static List<String> generatePermutations(String str) {
        List<String> result = new ArrayList<>();
        if (str == null) return result;
        generatePermutationsHelper("", str, result);
        return result;
    }
    
    private static void generatePermutationsHelper(String prefix, String remaining, List<String> result) {
        if (remaining.length() == 0) {
            result.add(prefix);
            return;
        }
        
        for (int i = 0; i < remaining.length(); i++) {
            char current = remaining.charAt(i);
            String newPrefix = prefix + current;
            String newRemaining = remaining.substring(0, i) + remaining.substring(i + 1);
            generatePermutationsHelper(newPrefix, newRemaining, result);
        }
    }
    
    public static boolean recursiveStringMatch(String text, String pattern) {
        if (pattern.isEmpty()) return true;
        if (text.isEmpty()) return false;
        
        return recursiveStringMatchHelper(text, pattern, 0, 0);
    }
    
    private static boolean recursiveStringMatchHelper(String text, String pattern, int textIndex, int patternIndex) {

        if (patternIndex == pattern.length()) {
            return true;
        }
        
        if (textIndex == text.length()) {
            return false;
        }
        
        if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
            return recursiveStringMatchHelper(text, pattern, textIndex + 1, patternIndex + 1);
        } else {

            return recursiveStringMatchHelper(text, pattern, textIndex + 1, 0);
        }
    }
    
    public static boolean simpleRecursiveMatch(String text, String pattern) {
        if (pattern.isEmpty()) return true;
        if (text.isEmpty()) return false;
        
        if (matchesAt(text, pattern, 0)) {
            return true;
        }
        
        return simpleRecursiveMatch(text.substring(1), pattern);
    }
    
    private static boolean matchesAt(String text, String pattern, int index) {
        if (index == pattern.length()) return true;
        if (index >= text.length()) return false;
        
        if (text.charAt(index) == pattern.charAt(index)) {
            return matchesAt(text, pattern, index + 1);
        }
        return false;
    }
    public static String removeDuplicates(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return removeDuplicatesHelper(str, 0, new HashSet<>(), new StringBuilder()).toString();
    }
    
    private static StringBuilder removeDuplicatesHelper(String str, int index, Set<Character> seen, StringBuilder result) {
        if (index == str.length()) {
            return result;
        }
        
        char current = str.charAt(index);
        if (!seen.contains(current)) {
            seen.add(current);
            result.append(current);
        }
        
        return removeDuplicatesHelper(str, index + 1, seen, result);
    }

    public static String removeDuplicatesSimple(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        char firstChar = str.charAt(0);
        String remaining = str.substring(1);
        
        String processedRemaining = removeDuplicatesSimple(remaining);
        if (processedRemaining.indexOf(firstChar) != -1) {
            return processedRemaining;
        } else {
            return firstChar + processedRemaining;
        }
    }
    public static List<String> generateAllSubstrings(String str) {
        List<String> result = new ArrayList<>();
        if (str == null) return result;
        generateAllSubstringsHelper(str, 0, result);
        return result;
    }
    
    private static void generateAllSubstringsHelper(String str, int start, List<String> result) {
        if (start >= str.length()) {
            return;
        }
        
        for (int end = start + 1; end <= str.length(); end++) {
            result.add(str.substring(start, end));
        }
        
        generateAllSubstringsHelper(str, start + 1, result);
    }
    
    public static List<String> generateSubstringsRecursive(String str) {
        List<String> result = new ArrayList<>();
        if (str == null || str.isEmpty()) return result;
        
        generateSubstringsRecursiveHelper(str, 0, 0, result);
        return result;
    }
    
    private static void generateSubstringsRecursiveHelper(String str, int start, int end, List<String> result) {
        if (end > str.length()) {
            if (start < str.length() - 1) {
                generateSubstringsRecursiveHelper(str, start + 1, start + 1, result);
            }
            return;
        }
        
        if (end > start) {
            result.add(str.substring(start, end));
        }
        
        generateSubstringsRecursiveHelper(str, start, end + 1, result);
    }

    public static void printList(List<String> list, String title) {
        System.out.println("\n" + title + ":");
        System.out.println("總數: " + list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.print("\"" + list.get(i) + "\"");
            if (i < list.size() - 1) System.out.print(", ");
            if ((i + 1) % 10 == 0) System.out.println();
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== 高級遞迴字串處理演示 ===");
        
        String testStr1 = "ABC";
        List<String> permutations = generatePermutations(testStr1);
        printList(permutations, "字串 \"" + testStr1 + "\" 的所有排列組合");
        
        String text = "ABABCABABA";
        String pattern = "ABAB";
        System.out.println("\n字串匹配測試:");
        System.out.println("文本: \"" + text + "\"");
        System.out.println("模式: \"" + pattern + "\"");
        System.out.println("遞迴匹配結果: " + recursiveStringMatch(text, pattern));
        System.out.println("簡單遞迴匹配結果: " + simpleRecursiveMatch(text, pattern));
        
        String testStr3 = "programming";
        System.out.println("\n移除重複字符測試:");
        System.out.println("原字串: \"" + testStr3 + "\"");
        System.out.println("移除重複後: \"" + removeDuplicates(testStr3) + "\"");
        System.out.println("簡單方法結果: \"" + removeDuplicatesSimple(testStr3) + "\"");
        String testStr4 = "abc";
        List<String> substrings1 = generateAllSubstrings(testStr4);
        printList(substrings1, "字串 \"" + testStr4 + "\" 的所有子字串（方法1）");
        
        List<String> substrings2 = generateSubstringsRecursive(testStr4);
        printList(substrings2, "字串 \"" + testStr4 + "\" 的所有子字串（方法2）");
        
        System.out.println("\n=== 額外測試 ===");
        String longStr = "hello";
        System.out.println("測試字串: \"" + longStr + "\"");
        System.out.println("移除重複字符: \"" + removeDuplicates(longStr) + "\"");
        System.out.println("排列組合數量: " + generatePermutations(longStr.substring(0, 4)).size());
        System.out.println("子字串數量: " + generateAllSubstrings(longStr).size());
    }
}