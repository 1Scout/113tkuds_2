/**
 * AVLRangeQueryExercise.java
 * AVL 樹範圍查詢功能實現
 * 利用 BST 性質進行高效的範圍搜尋和剪枝優化
 */

import java.util.*;

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

public class AVLRangeQueryExercise {
    private AVLNode root;
    
    public AVLRangeQueryExercise() {
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
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateHeight(x);
        updateHeight(y);
        
        return y;
    }
    
    // 插入操作
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
            return node; // 不允許重複值
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
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        // RL
        if (balance < -1 && data < node.right.data) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    // ========================================
    // 核心範圍查詢功能
    // ========================================
    
    /**
     * 範圍查詢 - 找出 [min, max] 範圍內的所有元素
     * 時間複雜度: O(log n + k)，其中 k 是結果數量
     * 
     * @param min 範圍最小值（包含）
     * @param max 範圍最大值（包含）
     * @return 範圍內的所有元素，按升序排列
     */
    public List<Integer> rangeQuery(int min, int max) {
        System.out.println("執行範圍查詢 [" + min + ", " + max + "]");
        
        if (min > max) {
            System.out.println("無效範圍：min > max");
            return new ArrayList<>();
        }
        
        List<Integer> result = new ArrayList<>();
        int[] visitedCount = {0}; // 使用陣列來追蹤訪問節點數
        
        rangeQueryHelper(root, min, max, result, visitedCount);
        
        System.out.println("訪問節點數: " + visitedCount[0] + " / 總節點數: " + getSize());
        System.out.println("找到 " + result.size() + " 個元素: " + result);
        
        return result;
    }
    
    /**
     * 範圍查詢輔助方法 - 利用 BST 性質進行剪枝
     * 
     * @param node 當前節點
     * @param min 範圍最小值
     * @param max 範圍最大值
     * @param result 結果列表
     * @param visitedCount 訪問節點計數
     */
    private void rangeQueryHelper(AVLNode node, int min, int max, 
                                 List<Integer> result, int[] visitedCount) {
        if (node == null) {
            return;
        }
        
        visitedCount[0]++; // 增加訪問計數
        System.out.println("  訪問節點: " + node.data);
        
        // 關鍵剪枝邏輯：利用 BST 的有序性質
        
        // 1. 如果當前節點值小於範圍最小值，則左子樹都小於範圍，只需搜索右子樹
        if (node.data < min) {
            System.out.println("    節點 " + node.data + " < " + min + "，跳過左子樹");
            rangeQueryHelper(node.right, min, max, result, visitedCount);
        }
        // 2. 如果當前節點值大於範圍最大值，則右子樹都大於範圍，只需搜索左子樹
        else if (node.data > max) {
            System.out.println("    節點 " + node.data + " > " + max + "，跳過右子樹");
            rangeQueryHelper(node.left, min, max, result, visitedCount);
        }
        // 3. 如果當前節點在範圍內，則需要搜索兩個子樹
        else {
            System.out.println("    節點 " + node.data + " 在範圍內，加入結果");
            
            // 先遍歷左子樹（保證結果按升序排列）
            rangeQueryHelper(node.left, min, max, result, visitedCount);
            
            // 將當前節點加入結果
            result.add(node.data);
            
            // 再遍歷右子樹
            rangeQueryHelper(node.right, min, max, result, visitedCount);
        }
    }
    
    // ========================================
    // 其他範圍查詢相關方法
    // ========================================
    
    /**
     * 計算範圍內元素數量（不返回具體元素）
     * 更節省空間的版本
     */
    public int countInRange(int min, int max) {
        System.out.println("計算範圍 [" + min + ", " + max + "] 內的元素數量");
        
        if (min > max) {
            return 0;
        }
        
        int[] count = {0};
        int[] visitedCount = {0};
        countInRangeHelper(root, min, max, count, visitedCount);
        
        System.out.println("訪問節點數: " + visitedCount[0] + " / 總節點數: " + getSize());
        System.out.println("範圍內元素數量: " + count[0]);
        
        return count[0];
    }
    
    private void countInRangeHelper(AVLNode node, int min, int max, 
                                   int[] count, int[] visitedCount) {
        if (node == null) {
            return;
        }
        
        visitedCount[0]++;
        
        if (node.data < min) {
            countInRangeHelper(node.right, min, max, count, visitedCount);
        } else if (node.data > max) {
            countInRangeHelper(node.left, min, max, count, visitedCount);
        } else {
            countInRangeHelper(node.left, min, max, count, visitedCount);
            count[0]++;
            countInRangeHelper(node.right, min, max, count, visitedCount);
        }
    }
    
    /**
     * 找出範圍內的最小值
     */
    public Integer findMinInRange(int min, int max) {
        System.out.println("查找範圍 [" + min + ", " + max + "] 內的最小值");
        
        Integer result = findMinInRangeHelper(root, min, max);
        System.out.println("範圍內最小值: " + result);
        return result;
    }
    
    private Integer findMinInRangeHelper(AVLNode node, int min, int max) {
        if (node == null) {
            return null;
        }
        
        // 如果當前節點小於範圍，到右子樹找
        if (node.data < min) {
            return findMinInRangeHelper(node.right, min, max);
        }
        
        // 如果當前節點大於範圍，到左子樹找
        if (node.data > max) {
            return findMinInRangeHelper(node.left, min, max);
        }
        
        // 當前節點在範圍內
        // 先檢查左子樹是否有更小的值在範圍內
        Integer leftMin = findMinInRangeHelper(node.left, min, max);
        return (leftMin != null) ? leftMin : node.data;
    }
    
    /**
     * 找出範圍內的最大值
     */
    public Integer findMaxInRange(int min, int max) {
        System.out.println("查找範圍 [" + min + ", " + max + "] 內的最大值");
        
        Integer result = findMaxInRangeHelper(root, min, max);
        System.out.println("範圍內最大值: " + result);
        return result;
    }
    
    private Integer findMaxInRangeHelper(AVLNode node, int min, int max) {
        if (node == null) {
            return null;
        }
        
        // 如果當前節點小於範圍，到右子樹找
        if (node.data < min) {
            return findMaxInRangeHelper(node.right, min, max);
        }
        
        // 如果當前節點大於範圍，到左子樹找
        if (node.data > max) {
            return findMaxInRangeHelper(node.left, min, max);
        }
        
        // 當前節點在範圍內
        // 先檢查右子樹是否有更大的值在範圍內
        Integer rightMax = findMaxInRangeHelper(node.right, min, max);
        return (rightMax != null) ? rightMax : node.data;
    }
    
    // ========================================
    // 比較方法：樸素範圍查詢（無剪枝）
    // ========================================
    
    /**
     * 樸素範圍查詢 - 不使用剪枝，訪問所有節點
     * 用於比較效率差異
     */
    public List<Integer> naiveRangeQuery(int min, int max) {
        System.out.println("執行樸素範圍查詢 [" + min + ", " + max + "]（無剪枝）");
        
        List<Integer> result = new ArrayList<>();
        int[] visitedCount = {0};
        
        naiveRangeQueryHelper(root, min, max, result, visitedCount);
        
        System.out.println("樸素方法訪問節點數: " + visitedCount[0] + " / 總節點數: " + getSize());
        System.out.println("找到 " + result.size() + " 個元素: " + result);
        
        return result;
    }
    
    private void naiveRangeQueryHelper(AVLNode node, int min, int max, 
                                      List<Integer> result, int[] visitedCount) {
        if (node == null) {
            return;
        }
        
        visitedCount[0]++;
        
        // 無剪枝：總是訪問左子樹、當前節點、右子樹
        naiveRangeQueryHelper(node.left, min, max, result, visitedCount);
        
        if (node.data >= min && node.data <= max) {
            result.add(node.data);
        }
        
        naiveRangeQueryHelper(node.right, min, max, result, visitedCount);
    }
    
    // ========================================
    // 輔助方法
    // ========================================
    
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
        AVLRangeQueryExercise avl = new AVLRangeQueryExercise();
        
        System.out.println("========================================");
        System.out.println("         AVL 樹範圍查詢測試");
        System.out.println("========================================");
        
        // 建立測試樹
        System.out.println("\n【建立測試樹】");
        int[] values = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 35, 55, 65, 70, 85, 90};
        System.out.println("插入數據: " + java.util.Arrays.toString(values));
        
        for (int value : values) {
            avl.insert(value);
        }
        
        avl.displayTree();
        avl.inorderTraversal();
        
        // 測試1: 基本範圍查詢
        System.out.println("\n【測試1: 基本範圍查詢】");
        
        System.out.println("\n1.1 查詢 [20, 40]:");
        List<Integer> result1 = avl.rangeQuery(20, 40);
        
        System.out.println("\n1.2 查詢 [60, 80]:");
        List<Integer> result2 = avl.rangeQuery(60, 80);
        
        System.out.println("\n1.3 查詢 [45, 55]:");
        List<Integer> result3 = avl.rangeQuery(45, 55);
        
        // 測試2: 邊界情況
        System.out.println("\n【測試2: 邊界情況】");
        
        System.out.println("\n2.1 查詢整個範圍 [0, 100]:");
        List<Integer> result4 = avl.rangeQuery(0, 100);
        
        System.out.println("\n2.2 查詢空範圍 [95, 100]:");
        List<Integer> result5 = avl.rangeQuery(95, 100);
        
        System.out.println("\n2.3 查詢單點 [50, 50]:");
        List<Integer> result6 = avl.rangeQuery(50, 50);
        
        System.out.println("\n2.4 無效範圍 [80, 20]:");
        List<Integer> result7 = avl.rangeQuery(80, 20);
        
        // 測試3: 效率比較
        System.out.println("\n【測試3: 效率比較 - 剪枝 vs 樸素】");
        
        System.out.println("\n3.1 小範圍查詢 [26, 29]:");
        System.out.println("--- 剪枝版本 ---");
        List<Integer> efficient = avl.rangeQuery(26, 29);
        System.out.println("--- 樸素版本 ---");
        List<Integer> naive = avl.naiveRangeQuery(26, 29);
        System.out.println("結果是否相同: " + efficient.equals(naive));
        
        System.out.println("\n3.2 大範圍查詢 [10, 90]:");
        System.out.println("--- 剪枝版本 ---");
        efficient = avl.rangeQuery(10, 90);
        System.out.println("--- 樸素版本 ---");
        naive = avl.naiveRangeQuery(10, 90);
        System.out.println("結果是否相同: " + efficient.equals(naive));
        
        // 測試4: 其他範圍查詢功能
        System.out.println("\n【測試4: 其他範圍查詢功能】");
        
        System.out.println("\n4.1 範圍內元素計數:");
        int count1 = avl.countInRange(20, 40);
        int count2 = avl.countInRange(60, 80);
        
        System.out.println("\n4.2 範圍內最小值和最大值:");
        Integer min1 = avl.findMinInRange(20, 40);
        Integer max1 = avl.findMaxInRange(20, 40);
        
        Integer min2 = avl.findMinInRange(60, 80);
        Integer max2 = avl.findMaxInRange(60, 80);
        
        // 測試5: 極端情況
        System.out.println("\n【測試5: 極端情況】");
        
        // 創建一個更大的樹來測試效率
        System.out.println("\n5.1 大規模數據測試:");
        AVLRangeQueryExercise bigAvl = new AVLRangeQueryExercise();
        
        System.out.println("插入 1000 個隨機數...");
        Random random = new Random(42); // 固定種子確保可重現
        Set<Integer> inserted = new HashSet<>();
        
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(2000); // 0-1999 範圍
            if (inserted.add(value)) {
                bigAvl.insert(value);
            }
        }
        
        System.out.println("實際插入節點數: " + bigAvl.getSize());
        System.out.println("樹的高度: " + bigAvl.getHeight(bigAvl.root));
        
        System.out.println("\n大樹範圍查詢測試:");
        System.out.println("查詢範圍 [500, 600]:");
        long startTime = System.nanoTime();
        List<Integer> bigResult = bigAvl.rangeQuery(500, 600);
        long endTime = System.nanoTime();
        System.out.println("查詢耗時: " + (endTime - startTime) / 1000000.0 + " 毫秒");
        
        System.out.println("\n========================================");
        System.out.println("           測試完成");
        System.out.println("========================================");
        
        // 總結
        System.out.println("\n【總結】");
        System.out.println("✅ 範圍查詢利用 BST 性質進行剪枝，大幅減少訪問節點數");
        System.out.println("✅ 時間複雜度達到 O(log n + k)，其中 k 是結果數量");
        System.out.println("✅ 相比樸素方法的 O(n)，剪枝方法效率顯著提升");
        System.out.println("✅ 支持多種範圍查詢操作：查詢、計數、最值查找");
    }
}