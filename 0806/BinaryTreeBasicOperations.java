import java.util.*;

public class BinaryTreeBasicOperations {
    
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

    public static int calculateSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return root.val + calculateSum(root.left) + calculateSum(root.right);
    }

    public static int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    public static double calculateAverage(TreeNode root) {
        if (root == null) {
            return 0.0;
        }
        int sum = calculateSum(root);
        int count = countNodes(root);
        return (double) sum / count;
    }

    public static TreeNode findMaxNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        TreeNode maxNode = root;
        TreeNode leftMax = findMaxNode(root.left);
        TreeNode rightMax = findMaxNode(root.right);
        
        if (leftMax != null && leftMax.val > maxNode.val) {
            maxNode = leftMax;
        }
        if (rightMax != null && rightMax.val > maxNode.val) {
            maxNode = rightMax;
        }
        
        return maxNode;
    }

    public static TreeNode findMinNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        TreeNode minNode = root;
        TreeNode leftMin = findMinNode(root.left);
        TreeNode rightMin = findMinNode(root.right);
        
        if (leftMin != null && leftMin.val < minNode.val) {
            minNode = leftMin;
        }
        if (rightMin != null && rightMin.val < minNode.val) {
            minNode = rightMin;
        }
        
        return minNode;
    }
    public static int calculateWidth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int maxWidth = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            maxWidth = Math.max(maxWidth, levelSize);
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return maxWidth;
    }

    public static List<Integer> getLevelWidths(TreeNode root) {
        List<Integer> widths = new ArrayList<>();
        if (root == null) {
            return widths;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            widths.add(levelSize);
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return widths;
    }

    public static boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean foundNull = false;
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node == null) {
                foundNull = true;
            } else {
                if (foundNull) {
                    return false;
                }
                
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        return true;
    }

    public static boolean isCompleteTreeByIndex(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        int totalNodes = countNodes(root);
        return isCompleteHelper(root, 0, totalNodes);
    }
    
    private static boolean isCompleteHelper(TreeNode node, int index, int totalNodes) {
        if (node == null) {
            return true;
        }
        
        if (index >= totalNodes) {
            return false;
        }

        return isCompleteHelper(node.left, 2 * index + 1, totalNodes) &&
               isCompleteHelper(node.right, 2 * index + 2, totalNodes);
    }

    public static void printTreeByLevel(TreeNode root) {
        if (root == null) {
            System.out.println("空樹");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 1;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("第 " + level + " 層: ");
            
            for (int i = 0; i < levelSize; i++) {
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
    
    public static void inorderTraversal(TreeNode root, List<Integer> result) {
        if (root != null) {
            inorderTraversal(root.left, result);
            result.add(root.val);
            inorderTraversal(root.right, result);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(" 二元樹基本操作範例");
        System.out.println("=" + "=".repeat(50));
        
        System.out.println("\n 建立測試樹結構");
        System.out.println("-".repeat(30));

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(20);
        root.left.left.left = new TreeNode(1);
        root.left.right.left = new TreeNode(6);
        root.right.left.right = new TreeNode(14);
        
        System.out.println("樹結構（層序遍歷）：");
        printTreeByLevel(root);
        
        System.out.println("\n 1. 節點值統計");
        System.out.println("-".repeat(30));
        
        int sum = calculateSum(root);
        int nodeCount = countNodes(root);
        double average = calculateAverage(root);
        
        System.out.println("節點總數: " + nodeCount);
        System.out.println("節點值總和: " + sum);
        System.out.println("節點值平均: " + String.format("%.2f", average));
        
        List<Integer> inorderResult = new ArrayList<>();
        inorderTraversal(root, inorderResult);
        System.out.println("中序遍歷: " + inorderResult);
        
        System.out.println("\n 2. 最大值與最小值節點");
        System.out.println("-".repeat(30));
        
        TreeNode maxNode = findMaxNode(root);
        TreeNode minNode = findMinNode(root);
        
        System.out.println("最大值節點: " + (maxNode != null ? maxNode.val : "null"));
        System.out.println("最小值節點: " + (minNode != null ? minNode.val : "null"));
        
        System.out.println("\n📏 3. 樹的寬度分析");
        System.out.println("-".repeat(30));
        
        int width = calculateWidth(root);
        List<Integer> levelWidths = getLevelWidths(root);
        
        System.out.println("樹的最大寬度: " + width);
        System.out.println("各層寬度: " + levelWidths);
        
        for (int i = 0; i < levelWidths.size(); i++) {
            System.out.println("  第 " + (i + 1) + " 層: " + levelWidths.get(i) + " 個節點");
        }
        
        System.out.println("\n 4. 完全二元樹判斷");
        System.out.println("-".repeat(30));
        
        boolean isComplete1 = isCompleteTree(root);
        boolean isComplete2 = isCompleteTreeByIndex(root);
        
        System.out.println("是否為完全二元樹 (方法1): " + (isComplete1 ? "是" : "否"));
        System.out.println("是否為完全二元樹 (方法2): " + (isComplete2 ? "是" : "否"));
        
        System.out.println("\n 額外測試：完全二元樹範例");
        System.out.println("-".repeat(30));
        
        TreeNode completeTree = new TreeNode(1);
        completeTree.left = new TreeNode(2);
        completeTree.right = new TreeNode(3);
        completeTree.left.left = new TreeNode(4);
        completeTree.left.right = new TreeNode(5);
        completeTree.right.left = new TreeNode(6);
        
        System.out.println("完全二元樹結構：");
        printTreeByLevel(completeTree);
        
        boolean isCompleteTest = isCompleteTree(completeTree);
        System.out.println("是否為完全二元樹: " + (isCompleteTest ? "是" : "否"));
        
        // 統計資訊
        System.out.println("完全二元樹統計:");
        System.out.println("  節點總數: " + countNodes(completeTree));
        System.out.println("  節點值總和: " + calculateSum(completeTree));
        System.out.println("  平均值: " + String.format("%.2f", calculateAverage(completeTree)));
        System.out.println("  最大寬度: " + calculateWidth(completeTree));
        System.out.println("  各層寬度: " + getLevelWidths(completeTree));
        
        System.out.println("\n 邊界情況測試");
        System.out.println("-".repeat(30));
        
        System.out.println("空樹測試:");
        System.out.println("  節點總數: " + countNodes(null));
        System.out.println("  節點值總和: " + calculateSum(null));
        System.out.println("  是否完全二元樹: " + isCompleteTree(null));
        
        TreeNode singleNode = new TreeNode(42);
        System.out.println("\n單節點樹測試:");
        System.out.println("  節點值: " + singleNode.val);
        System.out.println("  是否完全二元樹: " + isCompleteTree(singleNode));
        System.out.println("  樹寬度: " + calculateWidth(singleNode));
        
        System.out.println("\n 所有測試完成！");
    }
}