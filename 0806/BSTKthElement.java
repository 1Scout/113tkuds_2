import java.util.*;
public class BSTKthElement {
    static class TreeNode {
        int val;
        TreeNode left, right;
        int size;
        
        TreeNode(int val) {
            this.val = val;
            this.size = 1;
        }
    }
    
    private TreeNode root;

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
        updateSize(node);
        return node;
    }

    public void delete(int val) {
        root = deleteHelper(root, val);
    }
    
    private TreeNode deleteHelper(TreeNode node, int val) {
        if (node == null) {
            return null;
        }
        
        if (val < node.val) {
            node.left = deleteHelper(node.left, val);
        } else if (val > node.val) {
            node.right = deleteHelper(node.right, val);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                TreeNode minNode = findMin(node.right);
                node.val = minNode.val;
                node.right = deleteHelper(node.right, minNode.val);
            }
        }
        updateSize(node);
        return node;
    }
    
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void updateSize(TreeNode node) {
        if (node != null) {
            node.size = 1 + getSize(node.left) + getSize(node.right);
        }
    }
    
    private int getSize(TreeNode node) {
        return node == null ? 0 : node.size;
    }

    public Integer kthSmallest(int k) {
        if (k <= 0 || k > getSize(root)) {
            return null;
        }
        return kthSmallestHelper(root, k);
    }
    
    private int kthSmallestHelper(TreeNode node, int k) {
        int leftSize = getSize(node.left);
        
        if (k == leftSize + 1) {
            return node.val;
        } else if (k <= leftSize) {
            return kthSmallestHelper(node.left, k);
        } else {
            return kthSmallestHelper(node.right, k - leftSize - 1);
        }
    }

    public Integer kthLargest(int k) {
        int totalSize = getSize(root);
        if (k <= 0 || k > totalSize) {
            return null;
        }
        return kthSmallest(totalSize - k + 1);
    }

    public List<Integer> rangeKthSmallest(int k, int j) {
        List<Integer> result = new ArrayList<>();
        int totalSize = getSize(root);
        
        if (k <= 0 || j <= 0 || k > j || j > totalSize) {
            return result;
        }
        
        rangeKthHelper(root, k, j, result);
        return result;
    }
    
    private void rangeKthHelper(TreeNode node, int k, int j, List<Integer> result) {
        if (node == null) return;
        
        int leftSize = getSize(node.left);
        int currentRank = leftSize + 1;
        
        if (currentRank >= k && currentRank <= j) {
            if (k < currentRank) {
                rangeKthHelper(node.left, k, Math.min(j, currentRank - 1), result);
            }

            result.add(node.val);

            if (j > currentRank) {
                rangeKthHelper(node.right, Math.max(k - currentRank, 1), j - currentRank, result);
            }
        } else if (currentRank > j) {
            rangeKthHelper(node.left, k, j, result);
        } else {
            rangeKthHelper(node.right, k - currentRank, j - currentRank, result);
        }
    }
    
    public int size() {
        return getSize(root);
    }
    
    public boolean isEmpty() {
        return root == null;
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

    public static void main(String[] args) {
        BSTKthElement bst = new BSTKthElement();
        
        int[] values = {5, 3, 7, 2, 4, 6, 8, 1, 9};
        System.out.println("插入元素: " + Arrays.toString(values));
        for (int val : values) {
            bst.insert(val);
        }
        
        System.out.println("BST大小: " + bst.size());
        System.out.println("中序遍歷: " + bst.inorderTraversal());
        
        System.out.println("\n=== 第k小元素查詢 ===");
        for (int k = 1; k <= bst.size(); k++) {
            System.out.println("第" + k + "小的元素: " + bst.kthSmallest(k));
        }
        
        System.out.println("\n=== 第k大元素查詢 ===");
        for (int k = 1; k <= bst.size(); k++) {
            System.out.println("第" + k + "大的元素: " + bst.kthLargest(k));
        }

        System.out.println("\n=== 範圍查詢測試 ===");
        System.out.println("第2小到第5小的元素: " + bst.rangeKthSmallest(2, 5));
        System.out.println("第3小到第7小的元素: " + bst.rangeKthSmallest(3, 7));
        
        System.out.println("\n=== 動態刪除測試 ===");
        System.out.println("刪除元素5之前:");
        System.out.println("第3小的元素: " + bst.kthSmallest(3));
        System.out.println("第3大的元素: " + bst.kthLargest(3));
        
        bst.delete(5);
        System.out.println("刪除元素5之後:");
        System.out.println("BST大小: " + bst.size());
        System.out.println("中序遍歷: " + bst.inorderTraversal());
        System.out.println("第3小的元素: " + bst.kthSmallest(3));
        System.out.println("第3大的元素: " + bst.kthLargest(3));
        
        System.out.println("\n=== 動態插入測試 ===");
        bst.insert(10);
        System.out.println("插入元素10之後:");
        System.out.println("BST大小: " + bst.size());
        System.out.println("中序遍歷: " + bst.inorderTraversal());
        System.out.println("第9小的元素: " + bst.kthSmallest(9));
        System.out.println("第1大的元素: " + bst.kthLargest(1));
        
        System.out.println("\n=== 邊界測試 ===");
        System.out.println("第0小的元素: " + bst.kthSmallest(0));
        System.out.println("第" + (bst.size() + 1) + "小的元素: " + bst.kthSmallest(bst.size() + 1));
        System.out.println("第2小到第1小的元素: " + bst.rangeKthSmallest(2, 1));
    }
}