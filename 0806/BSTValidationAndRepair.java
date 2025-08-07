import java.util.*;

public class BSTValidationAndRepair {

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

    public static boolean isValidBST(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);

        for (int i = 1; i < inorder.size(); i++) {
            if (inorder.get(i) <= inorder.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidBSTRange(TreeNode root) {
        return validateRange(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private static boolean validateRange(TreeNode node, long minVal, long maxVal) {
        if (node == null) return true;
        
        if (node.val <= minVal || node.val >= maxVal) {
            return false;
        }
        
        return validateRange(node.left, minVal, node.val) && 
               validateRange(node.right, node.val, maxVal);
    }

    private static TreeNode prev = null;
    
    public static boolean isValidBSTInorder(TreeNode root) {
        prev = null;
        return inorderValidate(root);
    }
    
    private static boolean inorderValidate(TreeNode node) {
        if (node == null) return true;
        
        if (!inorderValidate(node.left)) return false;
        
        if (prev != null && prev.val >= node.val) {
            return false;
        }
        prev = node;
        
        return inorderValidate(node.right);
    }

    public static List<TreeNode> findInvalidNodes(TreeNode root) {
        List<TreeNode> invalidNodes = new ArrayList<>();
        findInvalidNodesHelper(root, Long.MIN_VALUE, Long.MAX_VALUE, invalidNodes);
        return invalidNodes;
    }
    
    private static void findInvalidNodesHelper(TreeNode node, long minVal, long maxVal, 
                                             List<TreeNode> invalidNodes) {
        if (node == null) return;

        if (node.val <= minVal || node.val >= maxVal) {
            invalidNodes.add(node);
        }

        findInvalidNodesHelper(node.left, minVal, node.val, invalidNodes);
        findInvalidNodesHelper(node.right, node.val, maxVal, invalidNodes);
    }

    public static List<TreeNode> findInvalidNodesInorder(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        inorderCollectNodes(root, nodes, values);
        
        List<TreeNode> invalidNodes = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            boolean isInvalid = false;

            if (i > 0 && values.get(i) <= values.get(i - 1)) {
                isInvalid = true;
            }

            if (i < values.size() - 1 && values.get(i) >= values.get(i + 1)) {
                isInvalid = true;
            }
            
            if (isInvalid && !invalidNodes.contains(nodes.get(i))) {
                invalidNodes.add(nodes.get(i));
            }
        }
        
        return invalidNodes;
    }
    
    private static void inorderCollectNodes(TreeNode node, List<TreeNode> nodes, List<Integer> values) {
        if (node == null) return;
        
        inorderCollectNodes(node.left, nodes, values);
        nodes.add(node);
        values.add(node.val);
        inorderCollectNodes(node.right, nodes, values);
    }

    public static void recoverTree(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        inorderCollectNodes(root, nodes, values);

        Collections.sort(values);

        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).val = values.get(i);
        }
    }

    public static void recoverTreeOptimal(TreeNode root) {
        TreeNode[] wrongNodes = findTwoSwappedNodes(root);
        if (wrongNodes[0] != null && wrongNodes[1] != null) {

            int temp = wrongNodes[0].val;
            wrongNodes[0].val = wrongNodes[1].val;
            wrongNodes[1].val = temp;
        }
    }

    private static TreeNode[] findTwoSwappedNodes(TreeNode root) {
        TreeNode[] result = new TreeNode[2];
        TreeNode[] prev = new TreeNode[1];
        findSwappedHelper(root, prev, result);
        return result;
    }
    
    private static void findSwappedHelper(TreeNode node, TreeNode[] prev, TreeNode[] wrongNodes) {
        if (node == null) return;
        
        findSwappedHelper(node.left, prev, wrongNodes);
        
        if (prev[0] != null && prev[0].val > node.val) {
            if (wrongNodes[0] == null) {
                wrongNodes[0] = prev[0];
                wrongNodes[1] = node;
            } else {
                wrongNodes[1] = node;
            }
        }
        
        prev[0] = node;
        findSwappedHelper(node.right, prev, wrongNodes);
    }

    public static int minRemovalToMakeBST(TreeNode root) {
        List<Integer> inorderValues = new ArrayList<>();
        inorderTraversal(root, inorderValues);

        int lisLength = longestIncreasingSubsequence(inorderValues);

        return inorderValues.size() - lisLength;
    }

    private static int longestIncreasingSubsequence(List<Integer> nums) {
        if (nums.isEmpty()) return 0;
        
        List<Integer> lis = new ArrayList<>();
        
        for (int num : nums) {
            int pos = Collections.binarySearch(lis, num);
            if (pos < 0) {
                pos = -(pos + 1);
            }
            
            if (pos == lis.size()) {
                lis.add(num);
            } else {
                lis.set(pos, num);
            }
        }
        
        return lis.size();
    }

    public static int minRemovalToMakeBSTAlternative(TreeNode root) {
        Set<TreeNode> nodesToRemove = new HashSet<>();
        findNodesToRemove(root, Long.MIN_VALUE, Long.MAX_VALUE, nodesToRemove);
        return nodesToRemove.size();
    }
    
    private static boolean findNodesToRemove(TreeNode node, long minVal, long maxVal, 
                                          Set<TreeNode> nodesToRemove) {
        if (node == null) return true;

        if (node.val <= minVal || node.val >= maxVal) {
            nodesToRemove.add(node);
            return false;
        }

        boolean leftValid = findNodesToRemove(node.left, minVal, node.val, nodesToRemove);
        boolean rightValid = findNodesToRemove(node.right, node.val, maxVal, nodesToRemove);

        if (!leftValid || !rightValid) {
        }
        
        return true;
    }

    private static void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        
        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }
    

    public static void printInorder(TreeNode root, String title) {
        System.out.print(title + ": ");
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        System.out.println(inorder);
    }

    public static TreeNode createValidBST() {

        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);
        return root;
    }

    public static TreeNode createInvalidBST() {

        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(6);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);
        return root;
    }

    public static TreeNode createSeverelyInvalidBST() {

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(12);
        root.right.left = new TreeNode(8);
        root.right.right = new TreeNode(20);
        return root;
    }

    public static void main(String[] args) {
        System.out.println("=== BST驗證與修復功能測試 ===\n");

        TreeNode validBST = createValidBST();
        System.out.println("1. 測試有效BST:");
        printInorder(validBST, "原始樹");
        System.out.println("是否為有效BST (方法1): " + isValidBST(validBST));
        System.out.println("是否為有效BST (方法2): " + isValidBSTRange(validBST));
        System.out.println("是否為有效BST (方法3): " + isValidBSTInorder(validBST));

        TreeNode invalidBST = createInvalidBST();
        System.out.println("\n2. 測試無效BST (兩個節點交換):");
        printInorder(invalidBST, "原始樹");
        System.out.println("是否為有效BST: " + isValidBST(invalidBST));
        
        List<TreeNode> invalidNodes = findInvalidNodesInorder(invalidBST);
        System.out.print("找到的無效節點: ");
        for (TreeNode node : invalidNodes) {
            System.out.print(node.val + " ");
        }
        System.out.println();

        System.out.println("\n3. 修復BST:");
        TreeNode bstToRecover = createInvalidBST();
        printInorder(bstToRecover, "修復前");
        recoverTreeOptimal(bstToRecover);
        printInorder(bstToRecover, "修復後");
        System.out.println("修復後是否有效: " + isValidBST(bstToRecover));

        TreeNode severelyInvalidBST = createSeverelyInvalidBST();
        System.out.println("\n4. 計算需要移除的節點數:");
        printInorder(severelyInvalidBST, "嚴重違規的樹");
        System.out.println("是否為有效BST: " + isValidBST(severelyInvalidBST));
        
        int removalCount = minRemovalToMakeBST(severelyInvalidBST);
        System.out.println("需要移除的節點數 (LIS方法): " + removalCount);
        
        int removalCountAlt = minRemovalToMakeBSTAlternative(severelyInvalidBST);
        System.out.println("需要移除的節點數 (直接方法): " + removalCountAlt);
        
        List<TreeNode> allInvalidNodes = findInvalidNodes(severelyInvalidBST);
        System.out.print("所有違規節點: ");
        for (TreeNode node : allInvalidNodes) {
            System.out.print(node.val + " ");
        }
        System.out.println();

        System.out.println("\n5. 邊界測試:");
        System.out.println("空樹是否為有效BST: " + isValidBST(null));
        
        TreeNode singleNode = new TreeNode(1);
        System.out.println("單節點樹是否為有效BST: " + isValidBST(singleNode));

        TreeNode twoNodes = new TreeNode(2);
        twoNodes.left = new TreeNode(1);
        System.out.println("兩節點有效樹是否為有效BST: " + isValidBST(twoNodes));
        
        TreeNode twoNodesInvalid = new TreeNode(1);
        twoNodesInvalid.left = new TreeNode(2);
        System.out.println("兩節點無效樹是否為有效BST: " + isValidBST(twoNodesInvalid));
        
        System.out.println("\n=== 測試完成 ===");
    }
}