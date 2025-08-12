import java.util.*;

public class PriorityQueueWithHeap {
    public static class Task implements Comparable<Task> {
        private String name;
        private int priority;
        private long timestamp;
        
        public Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.timestamp = System.nanoTime();
        }
        
        public Task(String name, int priority, long timestamp) {
            this.name = name;
            this.priority = priority;
            this.timestamp = timestamp;
        }
        
        public String getName() {
            return name;
        }
        
        public int getPriority() {
            return priority;
        }
        
        public long getTimestamp() {
            return timestamp;
        }
        
        public void setPriority(int priority) {
            this.priority = priority;
        }
        
        @Override
        public int compareTo(Task other) {
            if (this.priority != other.priority) {
                return Integer.compare(other.priority, this.priority);
            }
            return Long.compare(this.timestamp, other.timestamp);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Task task = (Task) obj;
            return Objects.equals(name, task.name);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
        
        @Override
        public String toString() {
            return String.format("Task{name='%s', priority=%d, timestamp=%d}", 
                    name, priority, timestamp);
        }
    }
    
    private PriorityQueue<Task> taskQueue;
    private Map<String, Task> taskMap;
    
    public PriorityQueueWithHeap() {
        this.taskQueue = new PriorityQueue<>();
        this.taskMap = new HashMap<>();
    }

    public void addTask(String name, int priority) {
        if (taskMap.containsKey(name)) {
            changePriority(name, priority);
            return;
        }
        
        Task task = new Task(name, priority);
        taskQueue.offer(task);
        taskMap.put(name, task);
        
        System.out.printf("已添加任務: %s (優先級: %d)\n", name, priority);
    }

    public Task executeNext() {
        if (taskQueue.isEmpty()) {
            System.out.println("沒有任務可執行");
            return null;
        }
        
        Task task = taskQueue.poll();
        taskMap.remove(task.getName());
        
        System.out.printf("執行任務: %s (優先級: %d)\n", task.getName(), task.getPriority());
        return task;
    }

    public Task peek() {
        if (taskQueue.isEmpty()) {
            System.out.println("沒有任務在佇列中");
            return null;
        }
        
        Task task = taskQueue.peek();
        System.out.printf("下一個任務: %s (優先級: %d)\n", task.getName(), task.getPriority());
        return task;
    }

    public boolean changePriority(String name, int newPriority) {
        Task existingTask = taskMap.get(name);
        if (existingTask == null) {
            System.out.printf("任務 '%s' 不存在\n", name);
            return false;
        }
        
        int oldPriority = existingTask.getPriority();

        taskQueue.remove(existingTask);

        Task updatedTask = new Task(name, newPriority, existingTask.getTimestamp());

        taskQueue.offer(updatedTask);
        taskMap.put(name, updatedTask);
        
        System.out.printf("已更新任務 '%s' 的優先級: %d → %d\n", name, oldPriority, newPriority);
        return true;
    }

    public int size() {
        return taskQueue.size();
    }
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>(taskQueue);
        tasks.sort(Task::compareTo);
        return tasks;
    }
    public boolean containsTask(String name) {
        return taskMap.containsKey(name);
    }

    public int getTaskPriority(String name) {
        Task task = taskMap.get(name);
        return task != null ? task.getPriority() : -1;
    }

    public void displayQueue() {
        if (taskQueue.isEmpty()) {
            System.out.println("任務佇列為空");
            return;
        }
        
        System.out.println("當前任務佇列狀態:");
        List<Task> sortedTasks = getAllTasks();
        for (int i = 0; i < sortedTasks.size(); i++) {
            Task task = sortedTasks.get(i);
            System.out.printf("   %d. %s (優先級: %d)\n", 
                    i + 1, task.getName(), task.getPriority());
        }
    }
    public void clear() {
        taskQueue.clear();
        taskMap.clear();
        System.out.println("已清空所有任務");
    }

    public static void main(String[] args) {
        PriorityQueueWithHeap pq = new PriorityQueueWithHeap();
        
        System.out.println("=== 優先級任務佇列測試 ===\n");
        
        System.out.println("添加任務測試");
        pq.addTask("備份", 1);
        pq.addTask("緊急修復", 5);
        pq.addTask("更新", 3);
        pq.addTask("日常維護", 2);
        pq.addTask("緊急部署", 5);
        pq.displayQueue();
        System.out.println();
        
        System.out.println("查看下一個任務");
        pq.peek();
        System.out.println();
        
        System.out.println("執行任務測試");
        System.out.println("預期執行順序：緊急修復 (5) → 緊急部署 (5) → 更新 (3) → 日常維護 (2) → 備份 (1)");
        System.out.println("實際執行順序：");
        
        int count = 1;
        while (!pq.isEmpty()) {
            System.out.printf("%d. ", count++);
            pq.executeNext();
        }
        System.out.println();
        System.out.println("修改優先級測試");
        pq.addTask("任務A", 1);
        pq.addTask("任務B", 3);
        pq.addTask("任務C", 2);
        pq.displayQueue();
        
        System.out.println("\n將任務A的優先級從1改為4:");
        pq.changePriority("任務A", 4);
        pq.displayQueue();
        System.out.println();
        
        System.out.println("邊界情況測試");
        pq.clear();
        pq.peek();
        pq.executeNext();
        pq.changePriority("不存在的任務", 5);
        System.out.println();
        
        System.out.println("🔁 6. 重複任務名稱測試");
        pq.addTask("重複任務", 2);
        pq.displayQueue();
        System.out.println("再次添加相同名稱的任務（會更新優先級）:");
        pq.addTask("重複任務", 4);
        pq.displayQueue();
        System.out.println();
        System.out.println("⚡ 7. 效能測試");
        pq.clear();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            pq.addTask("任務" + i, (int) (Math.random() * 100));
        }
        while (!pq.isEmpty()) {
            pq.executeNext();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.printf("處理 10000 個任務耗時: %d ms\n", endTime - startTime);
        
        System.out.println("\n=== 測試完成 ===");
    }
}