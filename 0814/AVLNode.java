
public class AVLNode {
    int data;
    AVLNode left, right;
    int height;
    
    public AVLNode(int data) {
        this.data = data;
        this.height = 1;
    }
    
    // 計算平衡因子
    public int getBalance() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        return leftHeight - rightHeight;
    }
    
    // 更新節點高度
    public void updateHeight() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }

    public static void main(String[] args) {
        // 建立節點
        AVLNode root = new AVLNode(10);
        root.left = new AVLNode(5);
        root.right = new AVLNode(15);

        // 更新左右子節點高度
        root.left.updateHeight();
        root.right.updateHeight();

        // 更新根節點高度
        root.updateHeight();

        // 顯示節點資訊
        System.out.println("Root Data: " + root.data);
        System.out.println("Root Height: " + root.height);
        System.out.println("Root Balance Factor: " + root.getBalance());

        System.out.println("Left Child: " + root.left.data + ", Height: " + root.left.height);
        System.out.println("Right Child: " + root.right.data + ", Height: " + root.right.height);
    }
}
