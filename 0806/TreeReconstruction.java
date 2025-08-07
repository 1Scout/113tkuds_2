import java.util.*;

public class TreeReconstruction {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
        
        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
        public static TreeNode buildTreeFromPreorderInorder(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        
        return buildPreInHelper(preorder, 0, preorder.length - 1, 
                               inorder, 0, inorder.length - 1, inorderMap);
    }
    
    private static TreeNode buildPreInHelper(int[] preorder, int preStart, int preEnd,
                                           int[] inorder, int inStart, int inEnd,
                                           Map<Integer, Integer> inorderMap) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);
        
        int rootIndex = inorderMap.get(rootVal);
        int leftSubtreeSize = rootIndex - inStart;
        root.left = buildPreInHelper(preorder, preStart + 1, preStart + leftSubtreeSize,
                                   inorder, inStart, rootIndex - 1, inorderMap);
        root.right = buildPreInHelper(preorder, preStart + leftSubtreeSize + 1, preEnd,
                                    inorder, rootIndex + 1, inEnd, inorderMap);
        
        return root;
    }
    
    public static TreeNode buildTreeFromPostorderInorder(int[] postorder, int[] inorder) {
        if (postorder == null || inorder == null || postorder.length != inorder.length) {
            return null;
        }
        
        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        
        return buildPostInHelper(postorder, 0, postorder.length - 1,
                               inorder, 0, inorder.length - 1, inorderMap);
    }
    
    private static TreeNode buildPostInHelper(int[] postorder, int postStart, int postEnd,
                                            int[] inorder, int inStart, int inEnd,
                                            Map<Integer, Integer> inorderMap) {
        if (postStart > postEnd || inStart > inEnd) {
            return null;
        }
    
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);
        
        int rootIndex = inorderMap.get(rootVal);
        int leftSubtreeSize = rootIndex - inStart;
        root.left = buildPostInHelper(postorder, postStart, postStart + leftSubtreeSize - 1,
                                    inorder, inStart, rootIndex - 1, inorderMap);
        root.right = buildPostInHelper(postorder, postStart + leftSubtreeSize, postEnd - 1,
                                     inorder, rootIndex + 1, inEnd, inorderMap);
        
        return root;
    }
    public static TreeNode buildTreeFromLevelOrder(Integer[] levelOrder) {
        if (levelOrder == null || levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < levelOrder.length) {
            TreeNode current = queue.poll();
            if (i < levelOrder.length && levelOrder[i] != null) {
                current.left = new TreeNode(levelOrder[i]);
                queue.offer(current.left);
            }
            i++;
            if (i < levelOrder.length && levelOrder[i] != null) {
                current.right = new TreeNode(levelOrder[i]);
                queue.offer(current.right);
            }
            i++;
        }
        
        return root;
    }
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }
    
    private static void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private static void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }
    
    private static void postorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }
    public static List<Integer> levelOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            result.add(current.val);
            
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        
        return result;
    }
    
    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("空樹");
            return;
        }
        printTreeHelper(root, "", true);
    }
    
    private static void printTreeHelper(TreeNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);
            
            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    printTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                }
                if (node.right != null) {
                    printTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 二元樹重建測試 ===\n");
        System.out.println("1. 前序和中序重建測試：");
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        
        System.out.println("前序走訪：" + Arrays.toString(preorder));
        System.out.println("中序走訪：" + Arrays.toString(inorder));
        
        TreeNode tree1 = buildTreeFromPreorderInorder(preorder, inorder);
        System.out.println("重建的樹結構：");
        printTree(tree1);
        
        System.out.println("驗證結果：");
        System.out.println("前序走訪：" + preorderTraversal(tree1));
        System.out.println("中序走訪：" + inorderTraversal(tree1));
        System.out.println("後序走訪：" + postorderTraversal(tree1));
        System.out.println("層序走訪：" + levelOrderTraversal(tree1));
        System.out.println();
        
        System.out.println("2. 後序和中序重建測試：");
        int[] postorder = {9, 15, 7, 20, 3};
        
        System.out.println("後序走訪：" + Arrays.toString(postorder));
        System.out.println("中序走訪：" + Arrays.toString(inorder));
        
        TreeNode tree2 = buildTreeFromPostorderInorder(postorder, inorder);
        System.out.println("重建的樹結構：");
        printTree(tree2);
        
        System.out.println("驗證結果：");
        System.out.println("前序走訪：" + preorderTraversal(tree2));
        System.out.println("中序走訪：" + inorderTraversal(tree2));
        System.out.println("後序走訪：" + postorderTraversal(tree2));
        System.out.println("層序走訪：" + levelOrderTraversal(tree2));
        System.out.println();
        
        System.out.println("3. 層序重建完全二元樹測試：");
        Integer[] levelOrderArray = {1, 2, 3, 4, 5, 6, 7};
        
        System.out.println("層序走訪：" + Arrays.toString(levelOrderArray));
        
        TreeNode tree3 = buildTreeFromLevelOrder(levelOrderArray);
        System.out.println("重建的樹結構：");
        printTree(tree3);
        
        System.out.println("驗證結果：");
        System.out.println("前序走訪：" + preorderTraversal(tree3));
        System.out.println("中序走訪：" + inorderTraversal(tree3));
        System.out.println("後序走訪：" + postorderTraversal(tree3));
        System.out.println("層序走訪：" + levelOrderTraversal(tree3));
        System.out.println();

        System.out.println("4. 複雜樹的層序重建測試（包含null節點）：");
        Integer[] complexLevelOrder = {1, 2, 3, null, 4, null, 5};
        
        System.out.println("層序走訪：" + Arrays.toString(complexLevelOrder));
        
        TreeNode tree4 = buildTreeFromLevelOrder(complexLevelOrder);
        System.out.println("重建的樹結構：");
        printTree(tree4);
        
        System.out.println("驗證結果：");
        System.out.println("前序走訪：" + preorderTraversal(tree4));
        System.out.println("中序走訪：" + inorderTraversal(tree4));
        System.out.println("後序走訪：" + postorderTraversal(tree4));
        System.out.println("層序走訪：" + levelOrderTraversal(tree4));
        
        System.out.println("\n=== 測試完成 ===");
    }
}