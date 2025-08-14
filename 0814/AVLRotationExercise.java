/**
 * AVLRotationExercise.java
 * AVL 樹旋轉操作實現和測試
 * 包含四種旋轉：左旋、右旋、左右旋、右左旋
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

public class AVLRotationExercise {
    private AVLNode root;
    
    public AVLRotationExercise() {
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
    
    // 1. 右旋轉 (Right Rotation) - 處理 LL 情況
    /**
     * 右旋轉示意圖：
     *       y                    x
     *      / \                  / \
     *     x   T3    右旋轉-->    T1  y
     *    / \                      / \
     *   T1  T2                   T2  T3
     */
    private AVLNode rotateRight(AVLNode y) {
        System.out.println("執行右旋轉，根節點: " + y.data);
        
        // 保存節點引用
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        // 執行旋轉
        x.right = y;
        y.left = T2;
        
        // 更新高度（順序很重要：先更新 y，再更新 x）
        updateHeight(y);
        updateHeight(x);
        
        System.out.println("右旋轉完成，新根節點: " + x.data);
        return x; // x 成為新的根節點
    }
    
    // 2. 左旋轉 (Left Rotation) - 處理 RR 情況
    /**
     * 左旋轉示意圖：
     *     x                        y
     *    / \                      / \
     *   T1  y      左旋轉-->      x   T3
     *      / \                  / \
     *     T2  T3               T1  T2
     */
    private AVLNode rotateLeft(AVLNode x) {
        System.out.println("執行左旋轉，根節點: " + x.data);
        
        // 保存節點引用
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        // 執行旋轉
        y.left = x;
        x.right = T2;
        
        // 更新高度（順序很重要：先更新 x，再更新 y）
        updateHeight(x);
        updateHeight(y);
        
        System.out.println("左旋轉完成，新根節點: " + y.data);
        return y; // y 成為新的根節點
    }
    
    // 3. 左右旋轉 (Left-Right Rotation) - 處理 LR 情況
    /**
     * 左右旋轉示意圖：
     * 第一步：對 x 進行左旋轉
     *       z                    z
     *      / \                  / \
     *     x   T4               y   T4
     *    / \        -->       / \
     *   T1  y                x   T3
     *      / \              / \
     *     T2  T3           T1  T2
     * 
     * 第二步：對 z 進行右旋轉
     *       z                    y
     *      / \                  / \
     *     y