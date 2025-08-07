import java.util.*;

public class BSTRangeQuerySystem {
        static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class BST {
        private TreeNode root;
        
        public BST() {
            this.root = null;
        }

        public void insert(int val) {
            root = insertHelper(root, val);
        }
        
        private TreeNode insertHelper(TreeNode node, int val) {
            if (node == null) {
                return new TreeNode(val);
            }
            
            if (val < node.val) {
                node.left = insertHelper(node.left, val);
            } else if (val > node.val) {
                node.right = insertHelper(node.right, val);
            }           
            return node;
        }

        public boolean search(int val) {
            return searchHelper(root, val);
        }
        
        private boolean searchHelper(TreeNode node, int val) {
            if (node == null) {
                return false;
            }
            
            if (val == node.val) {
                return true;
            } else if (val < node.val) {
                return searchHelper(node.left, val);
            } else {
                return searchHelper(node.right, val);
            }
        }

        public List<Integer> rangeQuery(int min, int max) {
            List<Integer> result = new ArrayList<>();
            rangeQueryHelper(root, min, max, result);
            return result;
        }
        
        private void rangeQueryHelper(TreeNode node, int min, int max, List<Integer> result) {
            if (node == null) {
                return;
            }
            
            if (node.val >= min && node.val <= max) {
                result.add(node.val);
            }
            
            if (node.val > min) {
                rangeQueryHelper(node.left, min, max, result);
            }
            if (node.val < max) {
                rangeQueryHelper(node.right, min, max, result);
            }
        }

        public List<Integer> rangeQuerySorted(int min, int max) {
            List<Integer> result = new ArrayList<>();
            rangeQuerySortedHelper(root, min, max, result);
            return result;
        }
        
        private void rangeQuerySortedHelper(TreeNode node, int min, int max, List<Integer> result) {
            if (node == null) {
                return;
            }
            
            if (node.val > min) {
                rangeQuerySortedHelper(node.left, min, max, result);
            }
            
            if (node.val >= min && node.val <= max) {
                result.add(node.val);
            }
            
            if (node.val < max) {
                rangeQuerySortedHelper(node.right, min, max, result);
            }
        }
        public int rangeCount(int min, int max) {
            return rangeCountHelper(root, min, max);
        }
        
        private int rangeCountHelper(TreeNode node, int min, int max) {
            if (node == null) {
                return 0;
            }
            
            int count = 0;
            
            if (node.val >= min && node.val <= max) {
                count++;
            }
            
            if (node.val > min) {
                count += rangeCountHelper(node.left, min, max);
            }
            if (node.val < max) {
                count += rangeCountHelper(node.right, min, max);
            }
            
            return count;
        }

        public int rangeSum(int min, int max) {
            return rangeSumHelper(root, min, max);
        }
        
        private int rangeSumHelper(TreeNode node, int min, int max) {
            if (node == null) {
                return 0;
            }
            
            int sum = 0;
            
            if (node.val >= min && node.val <= max) {
                sum += node.val;
            }

            if (node.val > min) {
                sum += rangeSumHelper(node.left, min, max);
            }
            if (node.val < max) {
                sum += rangeSumHelper(node.right, min, max);
            }
            
            return sum;
        }

        public Integer findClosest(int target) {
            if (root == null) {
                return null;
            }
            return findClosestHelper(root, target, root.val);
        }
        
        private int findClosestHelper(TreeNode node, int target, int closest) {
            if (node == null) {
                return closest;
            }
            
            if (Math.abs(node.val - target) < Math.abs(closest - target)) {
                closest = node.val;
            }
            
            if (target < node.val) {
                return findClosestHelper(node.left, target, closest);
            } else if (target > node.val) {
                return findClosestHelper(node.right, target, closest);
            } else {
                return node.val;
            }
        }

        public List<Integer> findKClosest(int target, int k) {
            List<Integer> result = new ArrayList<>();
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> 
                Integer.compare(Math.abs(b - target), Math.abs(a - target)));
            
            findKClosestHelper(root, target, k, maxHeap);
            
            result.addAll(maxHeap);
            Collections.sort(result);
            return result;
        }
        
        private void findKClosestHelper(TreeNode node, int target, int k, PriorityQueue<Integer> maxHeap) {
            if (node == null) {
                return;
            }
            
            if (maxHeap.size() < k) {
                maxHeap.offer(node.val);
            } else if (Math.abs(node.val - target) < Math.abs(maxHeap.peek() - target)) {
                maxHeap.poll();
                maxHeap.offer(node.val);
            }
            
            findKClosestHelper(node.left, target, k, maxHeap);
            findKClosestHelper(node.right, target, k, maxHeap);
        }
        
        public List<Integer> inorderTraversal() {
            List<Integer> result = new ArrayList<>();
            inorderHelper(root, result);
            return result;
        }
        
        private void inorderHelper(TreeNode node, List<Integer> result) {
            if (node != null) {
                inorderHelper(node.left, result);
                result.add(node.val);
                inorderHelper(node.right, result);
            }
        }

        public void printTree() {
            if (root == null) {
                System.out.println("空樹");
                return;
            }
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int level = 1;
            
            while (!queue.isEmpty()) {
                int size = queue.size();
                System.out.print("第 " + level + " 層: ");
                
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    System.out.print(node.val + " ");
                    
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                System.out.println();
                level++;
            }
        }

        public void printStatistics() {
            List<Integer> inorder = inorderTraversal();
            if (inorder.isEmpty()) {
                System.out.println("樹為空");
                return;
            }
            
            System.out.println("節點總數: " + inorder.size());
            System.out.println("最小值: " + inorder.get(0));
            System.out.println("最大值: " + inorder.get(inorder.size() - 1));
            System.out.println("所有節點: " + inorder);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(" BST範圍查詢系統");
        System.out.println("=" + "=".repeat(50));
        
        // 建立BST並插入測試資料
        BST bst = new BST();
        int[] values = {15, 10, 20, 8, 12, 17, 25, 6, 11, 13, 22, 27};
        
        System.out.println("\n 建立BST");
        System.out.println("-".repeat(30));
        System.out.print("插入順序: ");
        for (int val : values) {
            bst.insert(val);
            System.out.print(val + " ");
        }
        System.out.println();
        
        System.out.println("\nBST結構:");
        bst.printTree();
        
        System.out.println("\nBST統計資訊:");
        bst.printStatistics();
        
        // ===== 測試1：範圍查詢 =====
        System.out.println("\n🔍 1. 範圍查詢測試");
        System.out.println("-".repeat(30));
        
        int[][] testRanges = {{10, 20}, 
        {12, 18}, 
        {5, 15}, 
        {20, 30}};
        
        for (int[] range : testRanges) {
            int min = range[0], max = range[1];
            
            List<Integer> rangeResult = bst.rangeQuery(min, max);
            List<Integer> sortedResult = bst.rangeQuerySorted(min, max);
            
            System.out.println("範圍 [" + min + ", " + max + "]:");
            System.out.println("  查詢結果: " + rangeResult);
            System.out.println("  有序結果: " + sortedResult);
        }
        
        // ===== 測試2：範圍計數 =====
        System.out.println("\n📊 2. 範圍計數測試");
        System.out.println("-".repeat(30));
        
        for (int[] range : testRanges) {
            int min = range[0], max = range[1];
            int count = bst.rangeCount(min, max);
            System.out.println("範圍 [" + min + ", " + max + "] 節點數量: " + count);
        }
        
        System.out.println("\n 3. 範圍總和測試");
        System.out.println("-".repeat(30));
        
        for (int[] range : testRanges) {
            int min = range[0], max = range[1];
            int sum = bst.rangeSum(min, max);
            List<Integer> nodes = bst.rangeQuerySorted(min, max);
            
            System.out.println("範圍 [" + min + ", " + max + "]:");
            System.out.println("  節點: " + nodes);
            System.out.println("  總和: " + sum);
        }
        
        System.out.println("\n 4. 最接近查詢測試");
        System.out.println("-".repeat(30));
        
        int[] targets = {9, 14, 16, 23, 5, 30};
        
        for (int target : targets) {
            Integer closest = bst.findClosest(target);
            List<Integer> kClosest = bst.findKClosest(target, 3);
            
            System.out.println("目標值 " + target + ":");
            System.out.println("  最接近的節點: " + closest);
            System.out.println("  最接近的3個節點: " + kClosest);
            
            if (closest != null) {
                int distance = Math.abs(closest - target);
                System.out.println("  距離: " + distance);
            }
            System.out.println();
        }
        
        System.out.println(" 5. 綜合測試案例");
        System.out.println("-".repeat(30));
        System.out.println("邊界情況測試:");
        
        List<Integer> emptyRange = bst.rangeQuery(1, 3);
        System.out.println("範圍 [1, 3] (空範圍): " + emptyRange);
        
        List<Integer> allNodes = bst.rangeQuery(0, 100);
        System.out.println("範圍 [0, 100] (全部節點): " + allNodes);
        
        List<Integer> singlePoint = bst.rangeQuery(15, 15);
        System.out.println("單點查詢 [15, 15]: " + singlePoint);
        
        System.out.println("\n 效能分析:");
        
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            bst.rangeQuery(10, 20);
        }
        long endTime = System.nanoTime();
        double avgTime = (endTime - startTime) / 1000000.0 / 1000;
        System.out.println("1000次範圍查詢平均時間: " + String.format("%.4f", avgTime) + " ms");
        
        System.out.println("\n 壓力測試:");
        BST largeBST = new BST();
        Random random = new Random(42);
        
        Set<Integer> inserted = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            int val = random.nextInt(10000);
            if (inserted.add(val)) {
                largeBST.insert(val);
            }
        }
        
        System.out.println("大型BST節點數: " + largeBST.inorderTraversal().size());
        
        startTime = System.nanoTime();
        int largeRangeCount = largeBST.rangeCount(2000, 8000);
        endTime = System.nanoTime();
        
        System.out.println("大範圍查詢 [2000, 8000] 結果: " + largeRangeCount + " 個節點");
        System.out.println("查詢時間: " + (endTime - startTime) / 1000000.0 + " ms");
        
        System.out.println("\n 所有測試完成！");
    }
}