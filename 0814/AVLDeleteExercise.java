/**
 * AVLDeleteExercise.java
 * AVL 樹刪除操作實現和測試
 * 處理三種刪除情況：葉子節點、單子節點、雙子節點
 */

// AVL 節點類
class AVLNode {
    int data;
    AVLNode left, right;
    int height;
    
    public AVLNode(int data) {
        this.data = data;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

public class AVLDeleteExercise {
    private AVLNode root;
    
    public AVLDeleteExercise() {
        this.root = null;
    }
    
    // 獲取節點高度
    private int getHeight(AVLNode node) {
        return (node == null) ? 0 : node.height;
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
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }
    
    // 右旋轉
    private AVLNode rotateRight(AVLNode y) {
        System.out.println("    執行右旋轉，根節點: " + y.data);
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        updateHeight(y);
        updateHeight(x);
        
        return x;
    }
    
    // 左旋轉
    private AVLNode rotateLeft(AVLNode x) {
        System.out.println("    執行左旋轉，根節點: " + x.data);
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateHeight(x);
        updateHeight(y);
        
        return y;
    }
    
    // 左右旋轉
    private AVLNode rotateLeftRight(AVLNode z) {
        System.out.println("    執行左右旋轉，根節點: " + z.data);
        z.left = rotateLeft(z.left);
        return rotateRight(z);
    }
    
    // 右左旋轉
    private AVLNode rotateRightLeft(AVLNode x) {
        System.out.println("    執行右左旋轉，根節點: " + x.data);
        x.right = rotateRight(x.right);
        return rotateLeft(x);
    }
    
    // 插入操作（用於建立測試樹）
    public void insert(int data) {
        root = insertNode(root, data);
    }
    
    private AVLNode insertNode(AVLNode node, int data) {
        if (node == null) {
            return new AVLNode(data);
        }
        
        if (data < node.data) {
            node.left = insertNode(node.left, data);
        } else if (data > node.data) {
            node.right = insertNode(node.right, data);
        } else {
            return node;
        }
        
        updateHeight(node);
        
        int balance = getBalanceFactor(node);
        
        // LL
        if (balance > 1 && data < node.left.data) {
            return rotateRight(node);
        }
        
        // RR
        if (balance < -1 && data > node.right.data) {
            return rotateLeft(node);
        }
        
        // LR
        if (balance > 1 && data > node.left.data) {
            return rotateLeftRight(node);
        }
        
        // RL
        if (balance < -1 && data < node.right.data) {
            return rotateRightLeft(node);
        }
        
        return node;
    }
    
    // 主要刪除方法
    public boolean delete(int data) {
        System.out.println("\n--- 刪除 " + data + " ---");
        int originalSize = getSize();
        root = deleteNode(root, data);
        int newSize = getSize();
        
        boolean deleted = (newSize < originalSize);
        if (deleted) {
            System.out.println("刪除成功");
        } else {
            System.out.println("未找到要刪除的節點");
        }
        System.out.println("刪除操作完成\n");
        return deleted;
    }
    
    // 遞迴刪除節點
    private AVLNode deleteNode(AVLNode node, int data) {
        // 1. 標準 BST 刪除
        if (node == null) {
            System.out.println("  節點不存在: " + data);
            return null;
        }
        
        if (data < node.data) {
            System.out.println("  向左子樹搜尋");
            node.left = deleteNode(node.left, data);
        } else if (data > node.data) {
            System.out.println("  向右子樹搜尋");
            node.right = deleteNode(node.right, data);
        } else {
            // 找到要刪除的節點
            System.out.println("  找到要刪除的節點: " + data);
            
            // 情況1: 葉子節點（沒有子節點）
            if (node.left == null && node.right == null) {
                System.out.println("  情況1: 刪除葉子節點");
                return null;
            }
            
            // 情況2: 只有一個子節點
            else if (node.left == null) {
                System.out.println("  情況2a: 只有右子節點，用右子節點替代");
                return node.right;
            } else if (node.right == null) {
                System.out.println("  情況2b: 只有左子節點，用左子節點替代");
                return node.left;
            }
            
            // 情況3: 有兩個子節點
            else {
                System.out.println("  情況3: 有兩個子節點");
                
                // 方法：找右子樹的最小節點（中序後繼）
                AVLNode successor = findMin(node.right);
                System.out.println("  找到中序後繼: " + successor.data);
                
                // 用後繼節點的值替代當前節點
                node.data = successor.data;
                System.out.println("  用後繼節點值 " + successor.data + " 替代");
                
                // 刪除後繼節點（後繼節點最多只有一個右子節點）
                System.out.println("  遞迴刪除後繼節點");
                node.right = deleteNode(node.right, successor.data);
            }
        }
        
        // 2. 更新高度
        updateHeight(node);
        
        // 3. 檢查平衡並重新平衡
        int balance = getBalanceFactor(node);
        System.out.println("  節點 " + node.data + " 的平衡因子: " + balance);
        
        // 檢查是否需要旋轉
        if (Math.abs(balance) > 1) {
            System.out.println("  檢測到不平衡，需要重新平衡");
        }
        
        // Left Heavy
        if (balance > 1) {
            int leftBalance = getBalanceFactor(node.left);
            System.out.println("  左子樹平衡因子: " + leftBalance);
            
            // LL 情況
            if (leftBalance >= 0) {
                System.out.println("  LL 情況，執行右旋轉");
                return rotateRight(node);
            }
            // LR 情況
            else {
                System.out.println("  LR 情況，執行左右旋轉");
                return rotateLeftRight(node);
            }
        }
        
        // Right Heavy
        if (balance < -1) {
            int rightBalance = getBalanceFactor(node.right);
            System.out.println("  右子樹平衡因子: " + rightBalance);
            
            // RR 情況
            if (rightBalance <= 0) {
                System.out.println("  RR 情況，執行左旋轉");
                return rotateLeft(node);
            }
            // RL 情況
            else {
                System.out.println("  RL 情況，執行右左旋轉");
                return rotateRightLeft(node);
            }
        }
        
        return node;
    }
    
    // 找最小值節點（最左節點）
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // 找最大值節點（最右節點）- 可用於找前驅
    private AVLNode findMax(AVLNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    // 搜尋節點
    public boolean search(int data) {
        return searchNode(root, data);
    }
    
    private boolean searchNode(AVLNode node, int data) {
        if (node == null) {
            return false;
        }
        
        if (data == node.data) {
            return true;
        } else if (data < node.data) {
            return searchNode(node.left, data);
        } else {
            return searchNode(node.right, data);
        }
    }
    
    // 計算樹的節點數量
    public int getSize() {
        return calculateSize(root);
    }
    
    private int calculateSize(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + calculateSize(node.left) + calculateSize(node.right);
    }
    
    // 檢查是否為有效的 AVL 樹
    public boolean isValidAVL() {
        return checkAVLProperty(root);
    }
    
    private boolean checkAVLProperty(AVLNode node) {
        if (node == null) {
            return true;
        }
        
        int balance = getBalanceFactor(node);
        if (balance < -1 || balance > 1) {
            return false;
        }
        
        return checkAVLProperty(node.left) && checkAVLProperty(node.right);
    }
    
    // 顯示樹的結構
    public void displayTree() {
        System.out.println("=== 樹的結構 ===");
        if (root == null) {
            System.out.println("空樹");
            return;
        }
        displayTree(root, "", true);
        System.out.println("樹的高度: " + getHeight(root));
        System.out.println("節點總數: " + getSize());
        System.out.println("是否為有效 AVL 樹: " + isValidAVL());
        System.out.println();
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
    
    // 中序遍歷
    public void inorderTraversal() {
        System.out.print("中序遍歷: ");
        if (root == null) {
            System.out.println("空樹");
            return;
        }
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
    
    // 測試方法
    public static void main(String[] args) {
        AVLDeleteExercise avl = new AVLDeleteExercise();
        
        System.out.println("========================================");
        System.out.println("         AVL 樹刪除操作測試");
        System.out.println("========================================");
        
        // 建立測試樹
        System.out.println("\n【建立測試樹】");
        int[] values = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 35, 55, 65, 70, 90};
        System.out.println("插入順序: " + java.util.Arrays.toString(values));
        
        for (int value : values) {
            avl.insert(value);
        }
        
        System.out.println("\n初始樹結構:");
        avl.displayTree();
        avl.inorderTraversal();
        
        // 測試1: 刪除葉子節點
        System.out.println("\n【測試1: 刪除葉子節點】");
        System.out.println("刪除葉子節點 5, 35, 90");
        
        avl.delete(5);  // 左葉子
        avl.displayTree();
        avl.inorderTraversal();
        
        avl.delete(35); // 右葉子
        avl.displayTree();
        avl.inorderTraversal();
        
        avl.delete(90); // 最右葉子
        avl.displayTree();
        avl.inorderTraversal();
        
        // 測試2: 刪除只有一個子節點的節點
        System.out.println("\n【測試2: 刪除只有一個子節點的節點】");
        
        // 先插入一些節點來創造單子節點情況
        avl.insert(85);
        System.out.println("插入 85 以創造單子節點情況");
        avl.displayTree();
        
        System.out.println("刪除只有右子節點的 80:");
        avl.delete(80);
        avl.displayTree();
        avl.inorderTraversal();
        
        // 測試3: 刪除有兩個子節點的節點
        System.out.println("\n【測試3: 刪除有兩個子節點的節點】");
        System.out.println("刪除根節點 50 (有兩個子節點):");
        avl.delete(50);
        avl.displayTree();
        avl.inorderTraversal();
        
        System.out.println("刪除節點 75 (有兩個子節點):");
        avl.delete(75);
        avl.displayTree();
        avl.inorderTraversal();
        
        System.out.println("刪除節點 25 (有兩個子節點):");
        avl.delete(25);
        avl.displayTree();
        avl.inorderTraversal();
        
        // 測試4: 刪除會引起重新平衡的情況
        System.out.println("\n【測試4: 刪除引起重新平衡】");
        
        // 重建一個特定的樹來測試重新平衡
        avl = new AVLDeleteExercise();
        int[] rebalanceTest = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 35, 55, 65, 70};
        System.out.println("重建測試樹: " + java.util.Arrays.toString(rebalanceTest));
        
        for (int value : rebalanceTest) {
            avl.insert(value);
        }
        
        avl.displayTree();
        
        System.out.println("刪除 10，可能引起重新平衡:");
        avl.delete(10);
        avl.displayTree();
        avl.inorderTraversal();
        
        System.out.println("刪除 25，可能引起重新平衡:");
        avl.delete(25);
        avl.displayTree();
        avl.inorderTraversal();
        
        // 測試5: 邊界情況
        System.out.println("\n【測試5: 邊界情況】");
        
        // 刪除不存在的節點
        System.out.println("嘗試刪除不存在的節點 999:");
        avl.delete(999);
        
        // 刪除所有節點直到空樹
        System.out.println("逐個刪除所有節點:");
        while (avl.getSize() > 0) {
            avl.inorderTraversal();
            // 刪除根節點
            if (avl.root != null) {
                int rootData = avl.root.data;
                avl.delete(rootData);
                System.out.println("剩餘節點數: " + avl.getSize());
            }
        }
        
        System.out.println("最終樹狀態:");
        avl.displayTree();
        
        // 對空樹進行刪除操作
        System.out.println("對空樹刪除節點 42:");
        avl.delete(42);
        
        System.out.println("\n========================================");
        System.out.println("           測試完成");
        System.out.println("========================================");
    }
}