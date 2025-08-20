import java.util.Scanner;

public class M09_AVLValidate {
    static class TreeNode {
        int val;
        TreeNode left, right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    private static boolean isValidBST = true;
    private static boolean isValidAVL = true;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        if (n == 0) {
            System.out.println("Valid");
            sc.close();
            return;
        }
        
        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            if (val != -1) {
                nodes[i] = new TreeNode(val);
            }
        }
        
        for (int i = 0; i < n; i++) {
            if (nodes[i] != null) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                
                if (left < n) {
                    nodes[i].left = nodes[left];
                }
                if (right < n) {
                    nodes[i].right = nodes[right];
                }
            }
        }
        
        isValidBST = true;
        isValidAVL = true;
        
        validateBST(nodes[0], Long.MIN_VALUE, Long.MAX_VALUE);
        
        if (!isValidBST) {
            System.out.println("Invalid BST");
        } else {
            validateAVL(nodes[0]);
            if (!isValidAVL) {
                System.out.println("Invalid AVL");
            } else {
                System.out.println("Valid");
            }
        }
        
        sc.close();
    }
    
    private static boolean validateBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        
        if (node.val <= min || node.val >= max) {
            isValidBST = false;
            return false;
        }
        
        return validateBST(node.left, min, node.val) && validateBST(node.right, node.val, max);
    }
    
    private static int validateAVL(TreeNode node) {
        if (node == null) return 0;
        
        int leftHeight = validateAVL(node.left);
        int rightHeight = validateAVL(node.right);
        
        if (leftHeight == -1 || rightHeight == -1) {
            return -1;
        }
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            isValidAVL = false;
            return -1;
        }
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：validateBST函數遍歷每個節點一次檢查BST性質，時間複雜度O(n)
 * validateAVL函數也遍歷每個節點一次計算高度並檢查平衡因子，時間複雜度O(n)
 * 整體時間複雜度為O(n)，其中n為樹中節點數量
 */
