import java.util.*;

public class M12_MergeKTimeTables {
    static class TimeEntry {
        int time;
        int listIndex;
        int elementIndex;
        
        TimeEntry(int time, int listIndex, int elementIndex) {
            this.time = time;
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        
        List<List<Integer>> timeTables = new ArrayList<>();
        
        for (int i = 0; i < k; i++) {
            int len = sc.nextInt();
            List<Integer> timeTable = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                timeTable.add(sc.nextInt());
            }
            timeTables.add(timeTable);
        }
        
        List<Integer> merged = mergeKTimeTables(timeTables);
        
        for (int i = 0; i < merged.size(); i++) {
            System.out.print(merged.get(i));
            if (i < merged.size() - 1) System.out.print(" ");
        }
        System.out.println();
        
        sc.close();
    }
    
    private static List<Integer> mergeKTimeTables(List<List<Integer>> timeTables) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<TimeEntry> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.time, b.time));
        
        for (int i = 0; i < timeTables.size(); i++) {
            if (!timeTables.get(i).isEmpty()) {
                minHeap.offer(new TimeEntry(timeTables.get(i).get(0), i, 0));
            }
        }
        
        while (!minHeap.isEmpty()) {
            TimeEntry current = minHeap.poll();
            result.add(current.time);
            
            int nextIndex = current.elementIndex + 1;
            if (nextIndex < timeTables.get(current.listIndex).size()) {
                int nextTime = timeTables.get(current.listIndex).get(nextIndex);
                minHeap.offer(new TimeEntry(nextTime, current.listIndex, nextIndex));
            }
        }
        
        return result;
    }
}
