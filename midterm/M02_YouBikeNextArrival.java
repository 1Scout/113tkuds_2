import java.util.*;

public class M02_YouBikeNextArrival {

    private static int timeToMinutes(String timeStr) {
        String[] parts = timeStr.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
    private static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }
    private static int binarySearchUpperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine().trim());

        int[] arrivalMinutes = new int[n];
        for (int i = 0; i < n; i++) {
            String timeStr = scanner.nextLine().trim();
            arrivalMinutes[i] = timeToMinutes(timeStr);
        }

        String queryTimeStr = scanner.nextLine().trim();
        int queryMinutes = timeToMinutes(queryTimeStr);

        int resultIndex = binarySearchUpperBound(arrivalMinutes, queryMinutes);

        if (resultIndex < n) {
            System.out.println(minutesToTime(arrivalMinutes[resultIndex]));
        } else {
            System.out.println("No bike");
        }
        
        scanner.close();
    }
}
