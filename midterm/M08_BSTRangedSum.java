import java.util.Scanner;

public class M08_BSTRangedSum {
    static class TreeNode {
        int val;
        TreeNode left, right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
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
        
        int L = sc.nextInt();
        int R = sc.nextInt();
        
        int sum = rangeSumBST(nodes[0], L, R);
        System.out.println("Sum: " + sum);
        
        sc.close();
    }
    
    private static int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        
        if (root.val < L) {
            return rangeSumBST(root.right, L, R);
        }
        
        if (root.val > R) {
            return rangeSumBST(root.left, L, R);
        }
        
        return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }
}