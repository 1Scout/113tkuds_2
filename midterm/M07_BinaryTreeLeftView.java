import java.util.*;

public class M07_BinaryTreeLeftView {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    public static TreeNode buildTreeFromLevelOrder(int[] levelOrder) {
        if (levelOrder.length == 0 || levelOrder[0] == -1) {
            return null;
        }

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int index = 1; 
        
        while (!queue.isEmpty() && index < levelOrder.length) {
            TreeNode current = queue.poll();

            if (index < levelOrder.length) {
                if (levelOrder[index] != -1) {
                    current.left = new TreeNode(levelOrder[index]);
                    queue.offer(current.left);
                }
                index++;
            }

            if (index < levelOrder.length) {
                if (levelOrder[index] != -1) {
                    current.right = new TreeNode(levelOrder[index]);
                    queue.offer(current.right);
                }
                index++;
            }
        }
        
        return root;
    }

    public static List<Integer> getLeftView(TreeNode root) {
        List<Integer> leftView = new ArrayList<>();
        
        if (root == null) {
            return leftView;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); 

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                if (i == 0) {
                    leftView.add(current.val);
                }

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
        }
        
        return leftView;
    }

    public static List<Integer> getLeftViewDFS(TreeNode root) {
        List<Integer> leftView = new ArrayList<>();
        dfsLeftView(root, 0, leftView);
        return leftView;
    }
    
    private static void dfsLeftView(TreeNode node, int depth, List<Integer> leftView) {
        if (node == null) {
            return;
        }

        if (depth == leftView.size()) {
            leftView.add(node.val);
        }

        dfsLeftView(node.left, depth + 1, leftView);
        dfsLeftView(node.right, depth + 1, leftView);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine().trim());
        String[] values = scanner.nextLine().trim().split("\\s+");
        
        int[] levelOrder = new int[n];
        for (int i = 0; i < n; i++) {
            levelOrder[i] = Integer.parseInt(values[i]);
        }
        
        TreeNode root = buildTreeFromLevelOrder(levelOrder);

        List<Integer> leftView = getLeftView(root);

        System.out.print("LeftView:");
        for (int val : leftView) {
            System.out.print(" " + val);
        }
        System.out.println();
        
        scanner.close();
    }
}
//Time Complexity: O(n
//建樹階段：O(n)
//遍歷輸入：O(n)
//每個節點創建：O(1)
//建立父子關係：O(1)
//總計：O(n)
//BFS左視圖遍歷：O(n)
//每個點入隊一次：O(1) × n = O(n)
//每個點出隊一次：O(1) × n = O(n)
//每層第一個點加到结果：O(1) × h = O(h)，其中h是樹高
//由於h ≤ n，O(n)
//時間複雜度：O(n + n) = O(n)