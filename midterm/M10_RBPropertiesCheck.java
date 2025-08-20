import java.util.Scanner;

public class M10_RBPropertiesCheck {
    static class RBNode {
        int val;
        char color;
        RBNode left, right;
        
        RBNode(int val, char color) {
            this.val = val;
            this.color = color;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        if (n == 0) {
            System.out.println("RB Valid");
            sc.close();
            return;
        }
        
        RBNode[] nodes = new RBNode[n];
        for (int i = 0; i < n; i++) {
            int val = sc.nextInt();
            String colorStr = sc.next();
            char color = colorStr.charAt(0);
            
            if (val != -1) {
                nodes[i] = new RBNode(val, color);
            } else {
                nodes[i] = new RBNode(-1, 'B');
            }
        }
        
        for (int i = 0; i < n; i++) {
            if (nodes[i] != null && nodes[i].val != -1) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                
                if (left < n) {
                    nodes[i].left = nodes[left].val == -1 ? null : nodes[left];
                }
                if (right < n) {
                    nodes[i].right = nodes[right].val == -1 ? null : nodes[right];
                }
            }
        }
        
        RBNode root = nodes[0].val == -1 ? null : nodes[0];
        
        if (root != null && root.color != 'B') {
            System.out.println("RootNotBlack");
            sc.close();
            return;
        }
        
        for (int i = 0; i < n; i++) {
            if (nodes[i] != null && nodes[i].val != -1 && nodes[i].color == 'R') {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                
                if ((left < n && nodes[left] != null && nodes[left].val != -1 && nodes[left].color == 'R') ||
                    (right < n && nodes[right] != null && nodes[right].val != -1 && nodes[right].color == 'R')) {
                    System.out.println("RedRedViolation at index " + i);
                    sc.close();
                    return;
                }
            }
        }
        
        int expectedBlackHeight = getBlackHeight(root);
        if (expectedBlackHeight == -1 || !checkBlackHeight(root, expectedBlackHeight, 0)) {
            System.out.println("BlackHeightMismatch");
        } else {
            System.out.println("RB Valid");
        }
        
        sc.close();
    }
    
    private static int getBlackHeight(RBNode node) {
        if (node == null) return 1;
        
        int leftHeight = getBlackHeight(node.left);
        if (leftHeight == -1) return -1;
        
        return leftHeight + (node.color == 'B' ? 1 : 0);
    }
    
    private static boolean checkBlackHeight(RBNode node, int expectedHeight, int currentHeight) {
        if (node == null) {
            return (currentHeight + 1) == expectedHeight;
        }
        
        int newHeight = currentHeight + (node.color == 'B' ? 1 : 0);
        
        return checkBlackHeight(node.left, expectedHeight, newHeight) &&
               checkBlackHeight(node.right, expectedHeight, newHeight);
    }
}
