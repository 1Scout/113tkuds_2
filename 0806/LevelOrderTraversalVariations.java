import java.util.*;

public class LevelOrderTraversalVariations {
    

    static class TreeNode {
        int val;
        TreeNode left, right;
        
        TreeNode(int val) {
            this.val = val;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }

    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    currentLevel.add(node.val);
                } else {
                    currentLevel.add(0, node.val);
                }
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(currentLevel);
            leftToRight = !leftToRight;
        }
        
        return result;
    }

    public static List<List<Integer>> zigzagLevelOrderDeque(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offerFirst(root);
        boolean leftToRight = true;
        
        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node;
                
                if (leftToRight) {
                    node = deque.pollFirst();
                    currentLevel.add(node.val);
                    
                    if (node.left != null) deque.offerLast(node.left);
                    if (node.right != null) deque.offerLast(node.right);
                } else {
                    node = deque.pollLast();
                    currentLevel.add(node.val);
                    
                    if (node.right != null) deque.offerFirst(node.right);
                    if (node.left != null) deque.offerFirst(node.left);
                }
            }
            
            result.add(currentLevel);
            leftToRight = !leftToRight;
        }
        
        return result;
    }

    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (i == levelSize - 1) {
                    result.add(node.val);
                }
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return result;
    }

    public static List<Integer> leftSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (i == 0) {
                    result.add(node.val);
                }
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return result;
    }

    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Map<Integer, List<Integer>> columnMap = new TreeMap<>();
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();
        
        nodeQueue.offer(root);
        columnQueue.offer(0); 
        
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int column = columnQueue.poll();

            columnMap.computeIfAbsent(column, k -> new ArrayList<>()).add(node.val);
            
            if (node.left != null) {
                nodeQueue.offer(node.left);
                columnQueue.offer(column - 1);
            }
            
            if (node.right != null) {
                nodeQueue.offer(node.right);
                columnQueue.offer(column + 1);
            }
        }
        
        for (List<Integer> column : columnMap.values()) {
            result.add(column);
        }
        
        return result;
    }

    static class VerticalNode {
        TreeNode node;
        int row, col;
        
        VerticalNode(TreeNode node, int row, int col) {
            this.node = node;
            this.row = row;
            this.col = col;
        }
    }
    
    public static List<List<Integer>> verticalOrderSorted(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        List<VerticalNode> nodes = new ArrayList<>();
        Queue<VerticalNode> queue = new LinkedList<>();
        queue.offer(new VerticalNode(root, 0, 0));
        
        while (!queue.isEmpty()) {
            VerticalNode current = queue.poll();
            nodes.add(current);
            
            if (current.node.left != null) {
                queue.offer(new VerticalNode(current.node.left, current.row + 1, current.col - 1));
            }
            
            if (current.node.right != null) {
                queue.offer(new VerticalNode(current.node.right, current.row + 1, current.col + 1));
            }
        }

        nodes.sort((a, b) -> {
            if (a.col != b.col) return Integer.compare(a.col, b.col);
            if (a.row != b.row) return Integer.compare(a.row, b.row);
            return Integer.compare(a.node.val, b.node.val);
        });

        Map<Integer, List<Integer>> columnMap = new LinkedHashMap<>();
        for (VerticalNode vNode : nodes) {
            columnMap.computeIfAbsent(vNode.col, k -> new ArrayList<>()).add(vNode.node.val);
        }
        
        for (List<Integer> column : columnMap.values()) {
            result.add(column);
        }
        
        return result;
    }

    public static TreeNode buildTestTree() {

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        return root;
    }

    public static TreeNode buildComplexTestTree() {
  
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(4);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(1);
        root.right.left.left = new TreeNode(5);
        root.right.left.right = new TreeNode(2);
        return root;
    }
    

    public static void print2DList(List<List<Integer>> list, String title) {
        System.out.println("\n=== " + title + " ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("層級/列 " + i + ": " + list.get(i));
        }
    }
    

    public static void printList(List<Integer> list, String title) {
        System.out.println("\n=== " + title + " ===");
        System.out.println(list);
    }

    public static void main(String[] args) {
        TreeNode root1 = buildTestTree();
        System.out.println("測試樹1結構:");
        System.out.println("    3");
        System.out.println("   / \\");
        System.out.println("  9   20");
        System.out.println("     /  \\");
        System.out.println("    15   7");
        
        print2DList(levelOrder(root1), "標準層序走訪");
        
        print2DList(zigzagLevelOrder(root1), "之字形層序走訪");
        print2DList(zigzagLevelOrderDeque(root1), "之字形層序走訪(雙端佇列版本)");
        
        printList(rightSideView(root1), "右側視圖(每層最後一個節點)");
        printList(leftSideView(root1), "左側視圖(每層第一個節點)");
        
        print2DList(verticalOrder(root1), "垂直層序走訪");
        
        System.out.println("\n" + "=".repeat(50));
        
        TreeNode root2 = buildComplexTestTree();
        System.out.println("測試樹2結構:");
        System.out.println("     3");
        System.out.println("    / \\");
        System.out.println("   9   8");
        System.out.println("  /   / \\");
        System.out.println(" 4   0   1");
        System.out.println("    / \\");
        System.out.println("   5   2");
        

        print2DList(levelOrder(root2), "標準層序走訪");
        print2DList(zigzagLevelOrder(root2), "之字形層序走訪");
        printList(rightSideView(root2), "右側視圖");
        printList(leftSideView(root2), "左側視圖");
        print2DList(verticalOrder(root2), "垂直層序走訪");
        print2DList(verticalOrderSorted(root2), "垂直層序走訪(排序版本)");
        System.out.println("\n=== 邊界測試(空樹) ===");
        print2DList(levelOrder(null), "空樹標準層序走訪");
        print2DList(zigzagLevelOrder(null), "空樹之字形走訪");
        printList(rightSideView(null), "空樹右側視圖");
        print2DList(verticalOrder(null), "空樹垂直走訪");
        
        TreeNode singleNode = new TreeNode(1);
        System.out.println("\n=== 單節點測試 ===");
        print2DList(levelOrder(singleNode), "單節點標準層序走訪");
        print2DList(zigzagLevelOrder(singleNode), "單節點之字形走訪");
        printList(rightSideView(singleNode), "單節點右側視圖");
        print2DList(verticalOrder(singleNode), "單節點垂直走訪");
    }
}