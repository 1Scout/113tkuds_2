/**
 * AVLBasicExercise.java
 * 簡化版的 AVL 樹實現
 * 包含插入、搜尋、高度計算和平衡檢查功能
 */

// AVL 節點類
class AVLNode {
    int data;
    AVLNode left, right;
    int height;
    
    public AVLNode(int data) {
        this.data = data;
        this.height = 1; // 新節點的高度為1
        this.left = null;
        this.right = null;
    }
}

public class AVLBasicExercise {
    private AVLNode root;
    
    // 構造函數
    public AVLBasicExercise() {
        this.root = null;
    }
    
    // 1. 插入節點（標準 BST 插入 + 高度更新）
    public void insert(int data) {
        root = insertNode(root, data);
    }
    
    private AVLNode insertNode(AVLNode node, int data) {
        // 標準 BST 插入
        if (node == null) {
            return new AVLNode(data);
        }
        
        if (data < node.data) {
            node.left = insertNode(node.left, data);
        } else if (data > node.data) {
            node.right = insertNode(node.right, data);
        } else {
            // 不允許重複值
            return node;
        }
        
        // 更新節點高度
        updateHeight(node);
        
        return node;
    }
    
    // 2. 搜尋節點
    public boolean search(int data) {
        return searchNode(root, data);
    }
    
    private boolean searchNode(AVLNode node, int data) {
        // 基本情況：節點為空
        if (node == null) {
            return false;
        }
        
        // 找到目標值
        if (data == node.data) {
            return true;
        }
        
        // 遞迴搜尋左子樹或右子樹
        if (data < node.data) {
            return searchNode(node.left, data);
        } else {
            return searchNode(node.right, data);
        }
    }
    
    // 3. 計算樹的高度（遞迴方式）
    public int getTreeHeight() {
        return calculateHeight(root);
    }
    
    private int calculateHeight(AVLNode node) {
        // 基本情況：空節點高度為0
        if (node == null) {
            return 0;
        }
        
        // 遞迴計算左右子樹高度
        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);
        
        // 返回較大的子樹高度 + 1
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    // 獲取節點高度（輔助方法）
    private int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
    
    // 更新節點高度
    private void updateHeight(AVLNode node) {
        if (node != null) {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            node.height = Math.max(leftHeight, rightHeight) + 1;
        }
    }
    
    // 計算平衡因子
    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }
    
    // 4. 檢查是否為有效的 AVL 樹
    public boolean isValidAVL() {
        return checkAVLProperty(root);
    }
    
    private boolean checkAVLProperty(AVLNode node) {
        // 基本情況：空節點符合 AVL 性質
        if (node == null) {
            return true;
        }
        
        // 檢查當前節點的平衡因子
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor < -1 || balanceFactor > 1) {
            return false;
        }
        
        // 遞迴檢查左右子樹
        return checkAVLProperty(node.left) && checkAVLProperty(node.right);
    }
    
    // 檢查是否為有效的 BST（輔助驗證方法）
    public boolean isValidBST() {
        return checkBSTProperty(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean checkBSTProperty(AVLNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        
        if (node.data <= min || node.data >= max) {
            return false;
        }
        
        return checkBSTProperty(node.left, min, node.data) && 
               checkBSTProperty(node.right, node.data, max);
    }
    
    // 中序遍歷（用於驗證 BST 性質）
    public void inorderTraversal() {
        System.out.print("中序遍歷: ");
        inorder(root);
        System.out.println();
    }
    
    private void inorder(AVLNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
    }
    
    // 顯示樹的結構和平衡信息
    public void displayTreeInfo() {
        System.out.println("=== 樹的詳細信息 ===");
        System.out.println("樹的高度: " + getTreeHeight());
        System.out.println("是否為有效的 BST: " + isValidBST());
        System.out.println("是否為有效的 AVL 樹: " + isValidAVL());
        displayTree(root, "", true);
    }
    
    private void displayTree(AVLNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + 
                             node.data + " (h:" + node.height + 
                             ", bf:" + getBalanceFactor(node) + ")");
            
            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    displayTree(node.right, prefix + (isLast ? "    " : "│   "), node.left == null);
                }
                if (node.left != null) {
                    displayTree(node.left, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }
    
    // 測試方法
    public static void main(String[] args) {
        AVLBasicExercise tree = new AVLBasicExercise();
        
        System.out.println("=== AVL 基本練習測試 ===\n");
        
        // 測試1: 插入數據（會形成不平衡的樹）
        System.out.println("1. 插入測試");
        int[] values = {10, 5, 15, 3, 7, 12, 20, 1};
        System.out.println("插入數據: " + java.util.Arrays.toString(values));
        
        for (int value : values) {
            tree.insert(value);
            System.out.println("插入 " + value + " - 樹高度: " + tree.getTreeHeight() + 
                             ", 是否為有效 AVL: " + tree.isValidAVL());
        }
        
        System.out.println();
        tree.displayTreeInfo();
        tree.inorderTraversal();
        
        // 測試2: 搜尋功能
        System.out.println("\n2. 搜尋測試");
        int[] searchValues = {7, 12, 25, 1, 100};
        for (int value : searchValues) {
            boolean found = tree.search(value);
            System.out.println("搜尋 " + value + ": " + (found ? "找到" : "未找到"));
        }
        
        // 測試3: 創建一個會導致不平衡的樹
        System.out.println("\n3. 不平衡樹測試");
        AVLBasicExercise unbalancedTree = new AVLBasicExercise();
        int[] unbalancedValues = {1, 2, 3, 4, 5, 6, 7}; // 這會創建一個類似鏈表的樹
        
        System.out.println("插入遞增序列: " + java.util.Arrays.toString(unbalancedValues));
        for (int value : unbalancedValues) {
            unbalancedTree.insert(value);
        }
        
        System.out.println("不平衡樹信息:");
        unbalancedTree.displayTreeInfo();
        
        // 測試4: 高度計算驗證
        System.out.println("\n4. 高度計算驗證");
        AVLBasicExercise smallTree = new AVLBasicExercise();
        smallTree.insert(10);
        System.out.println("單節點樹高度: " + smallTree.getTreeHeight());
        
        smallTree.insert(5);
        smallTree.insert(15);
        System.out.println("三節點完全二元樹高度: " + smallTree.getTreeHeight());
        
        System.out.println("\n=== 測試完成 ===");
    }
}