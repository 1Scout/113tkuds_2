import java.util.*;

public class BSTConversionAndBalance {

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
    
    static class DoublyListNode {
        int val;
        DoublyListNode prev;
        DoublyListNode next;
        
        DoublyListNode(int val) {
            this.val = val;
        }
        
        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
    

    static class BalanceInfo {
        boolean isBalanced;
        int height;
        
        BalanceInfo(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }
    

    private static DoublyListNode head = null;
    private static DoublyListNode prev = null;
    
    public static DoublyListNode bstToDoublyLinkedList(TreeNode root) {
        if (root == null) return null;
        
        head = null;
        prev = null;
        
        inorderConversion(root);
        
        return head;
    }
    
    private static void inorderConversion(TreeNode node) {
        if (node == null) return;

        inorderConversion(node.left);
        
        DoublyListNode current = new DoublyListNode(node.val);
        
        if (prev == null) {
            head = current;
        } else {
            prev.next = current;
            current.prev = prev;
        }
        prev = current;
        
        inorderConversion(node.right);
    }
    
    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        
        return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
    }
    
    private static TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
        if (left > right) return null;
        
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        
        root.left = sortedArrayToBSTHelper(nums, left, mid - 1);
        root.right = sortedArrayToBSTHelper(nums, mid + 1, right);
        
        return root;
    }
    
    public static boolean isBalanced(TreeNode root) {
        return checkBalance(root).isBalanced;
    }
    
    private static BalanceInfo checkBalance(TreeNode node) {
        if (node == null) {
            return new BalanceInfo(true, 0);
        }
        
        BalanceInfo leftInfo = checkBalance(node.left);
        if (!leftInfo.isBalanced) {
            return new BalanceInfo(false, -1);
        }
        BalanceInfo rightInfo = checkBalance(node.right);
        if (!rightInfo.isBalanced) {
            return new BalanceInfo(false, -1);
        }
        
        int heightDiff = Math.abs(leftInfo.height - rightInfo.height);
        boolean isBalanced = heightDiff <= 1;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        
        return new BalanceInfo(isBalanced, height);
    }
    
    public static Map<Integer, Integer> getBalanceFactors(TreeNode root) {
        Map<Integer, Integer> balanceFactors = new HashMap<>();
        calculateBalanceFactors(root, balanceFactors);
        return balanceFactors;
    }
    
    private static int calculateBalanceFactors(TreeNode node, Map<Integer, Integer> factors) {
        if (node == null) return 0;
        
        int leftHeight = calculateBalanceFactors(node.left, factors);
        int rightHeight = calculateBalanceFactors(node.right, factors);
        
        int balanceFactor = leftHeight - rightHeight;
        factors.put(node.val, balanceFactor);
        
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static TreeNode convertToGreaterSumTree(TreeNode root) {
        int[] sum = {0};
        convertToGreaterSumHelper(root, sum);
        return root;
    }
    
    private static void convertToGreaterSumHelper(TreeNode node, int[] sum) {
        if (node == null) return;
        
        convertToGreaterSumHelper(node.right, sum);
    
        sum[0] += node.val;
        node.val = sum[0];
        
        convertToGreaterSumHelper(node.left, sum);
    }
    
    public static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        
        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }
        
        return root;
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
    
    public static void printDoublyLinkedList(DoublyListNode head) {
        if (head == null) {
            System.out.println("空串列");
            return;
        }
        
        System.out.print("正向：");
        DoublyListNode current = head;
        DoublyListNode tail = null;
        
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            tail = current;
            current = current.next;
        }
        System.out.println();
        
        System.out.print("反向：");
        current = tail;
        while (current != null) {
            System.out.print(current.val);
            if (current.prev != null) {
                System.out.print(" <-> ");
            }
            current = current.prev;
        }
        System.out.println();
    }
    
    public static TreeNode copyTree(TreeNode root) {
        if (root == null) return null;
        
        TreeNode newRoot = new TreeNode(root.val);
        newRoot.left = copyTree(root.left);
        newRoot.right = copyTree(root.right);
        
        return newRoot;
    }
    
    public static void main(String[] args) {
        System.out.println("=== BST轉換和平衡操作測試 ===\n");
        
        TreeNode bst = null;
        int[] values = {10, 5, 15, 3, 7, 12, 18, 1, 4, 6, 8, 11, 13, 16, 20};
        
        for (int val : values) {
            bst = insertIntoBST(bst, val);
        }
        
        System.out.println("原始BST結構：");
        printTree(bst);
        System.out.println("中序走訪：" + inorderTraversal(bst));
        System.out.println();
        
        System.out.println("1. BST轉換為排序的雙向鏈結串列：");
        DoublyListNode doublyList = bstToDoublyLinkedList(bst);
        printDoublyLinkedList(doublyList);
        System.out.println();
        
        System.out.println("2. 排序陣列轉換為平衡BST：");
        int[] sortedArray = {1, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 15, 16, 18, 20};
        System.out.println("排序陣列：" + Arrays.toString(sortedArray));
        
        TreeNode balancedBST = sortedArrayToBST(sortedArray);
        System.out.println("平衡BST結構：");
        printTree(balancedBST);
        System.out.println("中序走訪：" + inorderTraversal(balancedBST));
        System.out.println();
        

        System.out.println("3. 檢查BST平衡性：");
        

        System.out.println("原始BST是否平衡：" + isBalanced(bst));
        Map<Integer, Integer> originalFactors = getBalanceFactors(bst);
        System.out.println("原始BST平衡因子：");
        originalFactors.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("  節點 " + entry.getKey() + ": " + entry.getValue()));
        
        System.out.println("平衡BST是否平衡：" + isBalanced(balancedBST));
        Map<Integer, Integer> balancedFactors = getBalanceFactors(balancedBST);
        System.out.println("平衡BST平衡因子：");
        balancedFactors.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("  節點 " + entry.getKey() + ": " + entry.getValue()));
        System.out.println();
        
        System.out.println("4. 轉換為大於等於總和樹：");
        TreeNode originalCopy = copyTree(bst);
        System.out.println("轉換前BST：" + inorderTraversal(originalCopy));
        
        TreeNode greaterSumTree = convertToGreaterSumTree(originalCopy);
        System.out.println("轉換後BST：" + inorderTraversal(greaterSumTree));
        System.out.println("轉換後BST結構：");
        printTree(greaterSumTree);
        System.out.println();
        System.out.println("5. 額外測試：不平衡樹");
        TreeNode unbalancedTree = null;
        int[] unbalancedValues = {1, 2, 3, 4, 5, 6, 7};
        
        for (int val : unbalancedValues) {
            unbalancedTree = insertIntoBST(unbalancedTree, val);
        }
        
        System.out.println("不平衡樹結構：");
        printTree(unbalancedTree);
        System.out.println("是否平衡：" + isBalanced(unbalancedTree));
        
        Map<Integer, Integer> unbalancedFactors = getBalanceFactors(unbalancedTree);
        System.out.println("平衡因子：");
        unbalancedFactors.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("  節點 " + entry.getKey() + ": " + entry.getValue()));
        
        System.out.println("\n=== 測試完成 ===");
    }
}