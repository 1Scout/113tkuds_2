import java.util.*;

public class MeetingRoomScheduler {
    static class Meeting {
        int start, end;
        
        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        public int duration() {
            return end - start;
        }
        
        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }
    static class Event {
        int time;
        int type;
        public Event(int time, int type) {
            this.time = time;
            this.type = type;
        }
    }
    public static int minMeetingRooms(int[][] meetings) {
        if (meetings == null || meetings.length == 0) {
            return 0;
        }
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        
        for (int[] meeting : meetings) {
            int start = meeting[0];
            int end = meeting[1];
            if (!heap.isEmpty() && heap.peek() <= start) {
                heap.poll();
            }
            heap.offer(end);
        }
        
        return heap.size();
    }
    public static int minMeetingRoomsWithEvents(int[][] meetings) {
        if (meetings == null || meetings.length == 0) {
            return 0;
        }
        
        List<Event> events = new ArrayList<>();
        
        for (int[] meeting : meetings) {
            events.add(new Event(meeting[0], 1)); 
            events.add(new Event(meeting[1], -1)); 
        }

        events.sort((a, b) -> {
            if (a.time != b.time) {
                return a.time - b.time;
            }
            return a.type - b.type;
        });
        
        int currentRooms = 0;
        int maxRooms = 0;
        
        for (Event event : events) {
            currentRooms += event.type;
            maxRooms = Math.max(maxRooms, currentRooms);
        }
        
        return maxRooms;
    }

    public static int maxMeetingTimeWithLimitedRooms(int[][] meetings, int numRooms) {
        if (meetings == null || meetings.length == 0 || numRooms <= 0) {
            return 0;
        }
        
        Meeting[] meetingArray = new Meeting[meetings.length];
        for (int i = 0; i < meetings.length; i++) {
            meetingArray[i] = new Meeting(meetings[i][0], meetings[i][1]);
        }
        Arrays.sort(meetingArray, (a, b) -> a.end - b.end);
        
        int n = meetingArray.length;
        int[][] dp = new int[n + 1][numRooms + 1];
        
        for (int i = 1; i <= n; i++) {
            Meeting current = meetingArray[i - 1];
            
            for (int j = 0; j <= numRooms; j++) {
                dp[i][j] = dp[i - 1][j];

                if (j > 0) {
                    int latestNonConflict = findLatestNonConflict(meetingArray, i - 1);
                    int timeWithCurrent = current.duration() + dp[latestNonConflict][j - 1];
                    dp[i][j] = Math.max(dp[i][j], timeWithCurrent);
                }
            }
        }
        
        return dp[n][numRooms];
    }
    private static int findLatestNonConflict(Meeting[] meetings, int index) {
        int start = meetings[index].start;
        int left = 0, right = index - 1;
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (meetings[mid].end <= start) {
                result = mid + 1;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    public static List<Meeting> greedySchedule(int[][] meetings, int numRooms) {
        Meeting[] meetingArray = new Meeting[meetings.length];
        for (int i = 0; i < meetings.length; i++) {
            meetingArray[i] = new Meeting(meetings[i][0], meetings[i][1]);
        }

        Arrays.sort(meetingArray, (a, b) -> a.end - b.end);
        
        List<Meeting> scheduled = new ArrayList<>();
        int[] roomEndTimes = new int[numRooms];
        Arrays.fill(roomEndTimes, -1);
        
        for (Meeting meeting : meetingArray) {
            for (int i = 0; i < numRooms; i++) {
                if (roomEndTimes[i] <= meeting.start) {
                    scheduled.add(meeting);
                    roomEndTimes[i] = meeting.end;
                    break;
                }
            }
        }
        
        return scheduled;
    }
    public static void main(String[] args) {
        System.out.println("=== 會議室調度器測試 ===\n");
        
        int[][] meetings1 = {{0,30}, {5,10}, {15,20}};
        System.out.println("測試案例1：" + Arrays.deepToString(meetings1));
        System.out.println("最少會議室數量（Min Heap）：" + minMeetingRooms(meetings1));
        System.out.println("最少會議室數量（事件排序）：" + minMeetingRoomsWithEvents(meetings1));
        System.out.println();

        int[][] meetings2 = {{9,10}, {4,9}, {4,17}};
        System.out.println("測試案例2：" + Arrays.deepToString(meetings2));
        System.out.println("最少會議室數量（Min Heap）：" + minMeetingRooms(meetings2));
        System.out.println("最少會議室數量（事件排序）：" + minMeetingRoomsWithEvents(meetings2));
        System.out.println();

        int[][] meetings3 = {{1,5}, {8,9}, {8,9}};
        System.out.println("測試案例3：" + Arrays.deepToString(meetings3));
        System.out.println("最少會議室數量（Min Heap）：" + minMeetingRooms(meetings3));
        System.out.println("最少會議室數量（事件排序）：" + minMeetingRoomsWithEvents(meetings3));
        System.out.println();
        
        int[][] meetings4 = {{1,4}, {2,3}, {4,6}};
        System.out.println("測試案例4（有限會議室）：" + Arrays.deepToString(meetings4));
        System.out.println("1個會議室最大會議時間：" + maxMeetingTimeWithLimitedRooms(meetings4, 1));
        
        List<Meeting> greedyResult = greedySchedule(meetings4, 1);
        System.out.print("貪心演算法選擇的會議：");
        for (Meeting m : greedyResult) {
            System.out.print(m + " ");
        }
        System.out.println();
        
        int totalTime = greedyResult.stream().mapToInt(Meeting::duration).sum();
        System.out.println("貪心演算法總會議時間：" + totalTime);
        System.out.println();
        
        int[][] meetings5 = {{0,2}, {1,3}, {2,4}, {3,5}};
        System.out.println("額外測試：" + Arrays.deepToString(meetings5));
        System.out.println("最少會議室數量：" + minMeetingRooms(meetings5));
        System.out.println("2個會議室最大會議時間：" + maxMeetingTimeWithLimitedRooms(meetings5, 2));
        System.out.println("1個會議室最大會議時間：" + maxMeetingTimeWithLimitedRooms(meetings5, 1));
    }
}