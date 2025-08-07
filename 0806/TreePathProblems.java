import java.util.*;
public class TreePathProblems {

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
        
        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
    static class PathResult {
        List<Integer> path;
        int sum;
        
        PathResult(List<Integer> path, int sum) {
            this.path = new ArrayList<>(path);
            this.sum = sum;
        }
        
        @Override
        public String toString() {
            return "路徑: " + path + ", 總和: " + sum;
        }
    }

    public static List<List<Integer>> rootToLeafPaths(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        List<Integer> currentPath = new ArrayList<>();
        findAllPaths(root, currentPath, result);
        return result;
    }
    
    private static void findAllPaths(TreeNode node, List<Integer> currentPath, 
                                   List<List<Integer>> result) {
        if (node == null) return;
        currentPath.add(node.val);
        if (node.left == null && node.right == null) {
            result.add(new ArrayList<>(currentPath));
        } else {
            findAllPaths(node.left, currentPath, result);
            findAllPaths(node.right, currentPath, result);
        }
        currentPath.remove(currentPath.size() - 1);
    }
    public static List<String> rootToLeafPathsString(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        
        findAllPathsString(root, "", result);
        return result;
    }
    
    private static void findAllPathsString(TreeNode node, String currentPath, 
                                         List<String> result) {
        if (node == null) return;
        
        String newPath = currentPath.isEmpty() ? 
            String.valueOf(node.val) : currentPath + "->" + node.val;
        
        if (node.left == null && node.right == null) {
            result.add(newPath);
        } else {
            findAllPathsString(node.left, newPath, result);
            findAllPathsString(node.right, newPath, result);
        }
    }
    
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        int remainingSum = targetSum - root.val;
        return hasPathSum(root.left, remainingSum) || 
               hasPathSum(root.right, remainingSum);
    }
    public static List<List<Integer>> pathSumAll(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        List<Integer> currentPath = new ArrayList<>();
        findTargetPaths(root, targetSum, currentPath, result);
        return result;
    }
    
    private static void findTargetPaths(TreeNode node, int targetSum, 
                                      List<Integer> currentPath, 
                                      List<List<Integer>> result) {
        if (node == null) return;
        
        currentPath.add(node.val);
        
        if (node.left == null && node.right == null && node.val == targetSum) {
            result.add(new ArrayList<>(currentPath));
        } else {
            int remainingSum = targetSum - node.val;
            findTargetPaths(node.left, remainingSum, currentPath, result);
            findTargetPaths(node.right, remainingSum, currentPath, result);
        }
        currentPath.remove(currentPath.size() - 1);
    }
    public static PathResult maxPathSumRootToLeaf(TreeNode root) {
        if (root == null) return new PathResult(new ArrayList<>(), 0);
        
        PathResult[] maxResult = new PathResult[1];
        maxResult[0] = new PathResult(new ArrayList<>(), Integer.MIN_VALUE);
        
        List<Integer> currentPath = new ArrayList<>();
        findMaxPathSum(root, 0, currentPath, maxResult);
        return maxResult[0];
    }
    
    private static void findMaxPathSum(TreeNode node, int currentSum, 
                                     List<Integer> currentPath, 
                                     PathResult[] maxResult) {
        if (node == null) return;
        
        currentPath.add(node.val);
        currentSum += node.val;

        if (node.left == null && node.right == null) {
            if (currentSum > maxResult[0].sum) {
                maxResult[0] = new PathResult(currentPath, currentSum);
            }
        } else {
            findMaxPathSum(node.left, currentSum, currentPath, maxResult);
            findMaxPathSum(node.right, currentSum, currentPath, maxResult);
        }
        
        currentPath.remove(currentPath.size() - 1);
    }
    public static int maxPathSumRootToLeafSimple(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) {
            return root.val;
        }
        
        int maxSum = Integer.MIN_VALUE;
        if (root.left != null) {
            maxSum = Math.max(maxSum, maxPathSumRootToLeafSimple(root.left));
        }
        if (root.right != null) {
            maxSum = Math.max(maxSum, maxPathSumRootToLeafSimple(root.right));
        }
        
        return root.val + maxSum;
    }
    public static int maxPathSum(TreeNode root) {
        int[] maxSum = {Integer.MIN_VALUE};
        maxPathSumHelper(root, maxSum);
        return maxSum[0];
    }
    
    private static int maxPathSumHelper(TreeNode node, int[] maxSum) {
        if (node == null) return 0;
        int leftGain = Math.max(maxPathSumHelper(node.left, maxSum), 0);
        int rightGain = Math.max(maxPathSumHelper(node.right, maxSum), 0);
        int currentMaxPath = node.val + leftGain + rightGain;
        maxSum[0] = Math.max(maxSum[0], currentMaxPath);
        return node.val + Math.max(leftGain, rightGain);
    }

    static class MaxPathResult {
        int maxSum;
        List<Integer> path;
        TreeNode startNode;
        TreeNode endNode;
        
        MaxPathResult(int maxSum, List<Integer> path) {
            this.maxSum = maxSum;
            this.path = path;
        }
        
        @Override
        public String toString() {
            return "最大路徑和: " + maxSum + ", 路徑: " + path;
        }
    }
    
    public static MaxPathResult maxPathSumWithPath(TreeNode root) {
        MaxPathResult[] result = new MaxPathResult[1];
        result[0] = new MaxPathResult(Integer.MIN_VALUE, new ArrayList<>());
        
        maxPathSumWithPathHelper(root, result);
        return result[0];
    }
    
    private static int maxPathSumWithPathHelper(TreeNode node, MaxPathResult[] result) {
        if (node == null) return 0;
        
        int leftGain = Math.max(maxPathSumWithPathHelper(node.left, result), 0);
        int rightGain = Math.max(maxPathSumWithPathHelper(node.right, result), 0);
        
        int currentMaxPath = node.val + leftGain + rightGain;
        
        if (currentMaxPath > result[0].maxSum) {
            result[0].maxSum = currentMaxPath;

            List<Integer> path = new ArrayList<>();
            if (leftGain > 0) path.addAll(getPathFromLeaf(node.left, leftGain));
            path.add(node.val);
            if (rightGain > 0) path.addAll(getPathFromRoot(node.right, rightGain));
            
            result[0].path = path;
        }
        
        return node.val + Math.max(leftGain, rightGain);
    }
    
    private static List<Integer> getPathFromLeaf(TreeNode node, int targetSum) {
        List<Integer> path = new ArrayList<>();
        if (node != null) {
            path.add(node.val);
        }
        return path;
    }
    
    private static List<Integer> getPathFromRoot(TreeNode node, int targetSum) {
        List<Integer> path = new ArrayList<>();
        if (node != null) {
            path.add(node.val);
        }
        return path;
    }

    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static void printTree(TreeNode root, String prefix, boolean isLast) {
        if (root == null) return;
        
        System.out.println(prefix + (isLast ? "└── " : "├── ") + root.val);
        
        List<TreeNode> children = new ArrayList<>();
        if (root.left != null) children.add(root.left);
        if (root.right != null) children.add(root.right);
        
        for (int i = 0; i < children.size(); i++) {
            boolean isLastChild = (i == children.size() - 1);
            String newPrefix = prefix + (isLast ? "    " : "│   ");
            
            if (children.get(i) == root.left) {
                printTree(root.left, newPrefix, root.right == null);
            } else {
                printTree(root.right, newPrefix, true);
            }
        }
    }

    public static TreeNode createTestTree1() {

        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.right.right = new TreeNode(1);
        return root;
    }

    public static TreeNode createTestTree2() {

        TreeNode root = new TreeNode(-10);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        return root;
    }

    public static TreeNode createTestTree3() {

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        return root;
    }

    public static void main(String[] args) {
        System.out.println("=== 樹的路徑問題解決方案測試 ===\n");

        TreeNode tree1 = createTestTree1();
        System.out.println("測試樹1結構:");
        printTree(tree1, "", true);
        System.out.println();

        System.out.println("1. 所有根到葉路徑:");
        List<List<Integer>> allPaths = rootToLeafPaths(tree1);
        for (int i = 0; i < allPaths.size(); i++) {
            System.out.println("路徑 " + (i + 1) + ": " + allPaths.get(i));
        }
        
        System.out.println("\n字串格式路徑:");
        List<String> pathStrings = rootToLeafPathsString(tree1);
        for (String path : pathStrings) {
            System.out.println(path);
        }

        System.out.println("\n2. 路徑和測試:");
        int targetSum = 22;
        System.out.println("是否存在和為 " + targetSum + " 的路徑: " + hasPathSum(tree1, targetSum));
        
        List<List<Integer>> targetPaths = pathSumAll(tree1, targetSum);
        System.out.println("所有和為 " + targetSum + " 的路徑:");
        for (List<Integer> path : targetPaths) {
            System.out.println(path);
        }

        System.out.println("\n3. 最大根到葉路徑和:");
        PathResult maxRootToLeaf = maxPathSumRootToLeaf(tree1);
        System.out.println(maxRootToLeaf);
        
        int maxRootToLeafSimple = maxPathSumRootToLeafSimple(tree1);
        System.out.println("最大根到葉路徑和（簡單版）: " + maxRootToLeafSimple);

        System.out.println("\n4. 任意路徑最大和:");
        int maxPath = maxPathSum(tree1);
        System.out.println("任意路徑最大和: " + maxPath);
        
        MaxPathResult maxPathWithPath = maxPathSumWithPath(tree1);
        System.out.println(maxPathWithPath);
        
        System.out.println("\n" + "=".repeat(50));

        TreeNode tree2 = createTestTree2();
        System.out.println("\n測試樹2（包含負數）:");
        printTree(tree2, "", true);
        
        System.out.println("\n樹2的測試結果:");
        System.out.println("任意路徑最大和: " + maxPathSum(tree2));
        System.out.println("最大根到葉路徑和: " + maxPathSumRootToLeafSimple(tree2));

        TreeNode tree3 = createTestTree3();
        System.out.println("\n測試樹3（簡單樹）:");
        printTree(tree3, "", true);
        
        System.out.println("\n樹3的測試結果:");
        System.out.println("所有根到葉路徑: " + rootToLeafPaths(tree3));
        System.out.println("任意路徑最大和: " + maxPathSum(tree3));
        System.out.println("最大根到葉路徑和: " + maxPathSumRootToLeafSimple(tree3));

        System.out.println("\n=== 邊界測試 ===");
        System.out.println("空樹路徑數: " + rootToLeafPaths(null).size());
        System.out.println("空樹是否有路徑和0: " + hasPathSum(null, 0));
        
        TreeNode singleNode = new TreeNode(5);
        System.out.println("單節點樹路徑: " + rootToLeafPaths(singleNode));
        System.out.println("單節點樹最大路徑和: " + maxPathSum(singleNode));
        
        System.out.println("\n=== 測試完成 ===");
    }
}