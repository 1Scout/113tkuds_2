import java.util.*;

public class TreeMirrorAndSymmetry {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
        public TreeNode deepCopy() {
            if (this == null) return null;
            
            TreeNode newNode = new TreeNode(this.val);
            newNode.left = (this.left != null) ? this.left.deepCopy() : null;
            newNode.right = (this.right != null) ? this.right.deepCopy() : null;
            return newNode;
        }
    }

    public static boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricHelper(root.left, root.right);
    }
    private static boolean isSymmetricHelper(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        return isSymmetricHelper(left.left, right.right) && 
               isSymmetricHelper(left.right, right.left);
    }
    public static boolean isSymmetricIterative(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);
        
        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            
            if (left == null && right == null) {
                continue;
            }
            
            if (left == null || right == null || left.val != right.val) {
                return false;
            }
            queue.offer(left.left);
            queue.offer(right.right);
            queue.offer(left.right);
            queue.offer(right.left);
        }
        
        return true;
    }

    public static TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
    
        mirrorTree(root.left);
        mirrorTree(root.right);
        
        return root;
    }

    public static TreeNode createMirrorCopy(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode newRoot = new TreeNode(root.val);

        newRoot.left = createMirrorCopy(root.right);
        newRoot.right = createMirrorCopy(root.left);
        
        return newRoot;
    }
    
    public static TreeNode createMirrorIterative(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        
        return root;
    }

    public static boolean areMirrors(TreeNode tree1, TreeNode tree2) {
        return areMirrorsHelper(tree1, tree2);
    }
    
    private static boolean areMirrorsHelper(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        if (node1 == null || node2 == null) {
            return false;
        }
        
        if (node1.val != node2.val) {
            return false;
        }

        return areMirrorsHelper(node1.left, node2.right) && 
               areMirrorsHelper(node1.right, node2.left);
    }

    public static boolean areMirrorsIterative(TreeNode tree1, TreeNode tree2) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(tree1);
        queue.offer(tree2);
        
        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();
            
            if (node1 == null && node2 == null) {
                continue;
            }
            
            if (node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }
            queue.offer(node1.left);
            queue.offer(node2.right);
            queue.offer(node1.right);
            queue.offer(node2.left);
        }
        
        return true;
    }

    public static boolean isSubtree(TreeNode tree1, TreeNode tree2) {
        if (tree2 == null) {
            return true;
        }
        if (tree1 == null) {
            return false;
        }
        
        return isSameTree(tree1, tree2) || 
               isSubtree(tree1.left, tree2) || 
               isSubtree(tree1.right, tree2);
    }

    private static boolean isSameTree(TreeNode tree1, TreeNode tree2) {
        if (tree1 == null && tree2 == null) {
            return true;
        }
        if (tree1 == null || tree2 == null) {
            return false;
        }
        
        return tree1.val == tree2.val && 
               isSameTree(tree1.left, tree2.left) && 
               isSameTree(tree1.right, tree2.right);
    }

    public static boolean isSubtreeByString(TreeNode tree1, TreeNode tree2) {
        String s1 = serialize(tree1);
        String s2 = serialize(tree2);
        return s1.contains(s2);
    }

    private static String serialize(TreeNode root) {
        if (root == null) {
            return "#";
        }
        return "^" + root.val + serialize(root.left) + serialize(root.right);
    }
    public static void printTree(TreeNode root, String treeName) {
        System.out.println(treeName + ":");
        if (root == null) {
            System.out.println("  空樹");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            System.out.print("  第 " + level + " 層: ");
            
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    System.out.print(node.val + " ");
                    queue.offer(node.left);
                    queue.offer(node.right);
                } else {
                    System.out.print("null ");
                }
            }
            System.out.println();
            level++;
            
            boolean hasNonNull = false;
            for (TreeNode node : queue) {
                if (node != null) {
                    hasNonNull = true;
                    break;
                }
            }
            if (!hasNonNull) break;
        }
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private static void inorderHelper(TreeNode node, List<Integer> result) {
        if (node != null) {
            inorderHelper(node.left, result);
            result.add(node.val);
            inorderHelper(node.right, result);
        }
    }

    public static int getTreeHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(getTreeHeight(root.left), getTreeHeight(root.right));
    }

    public static int getNodeCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + getNodeCount(root.left) + getNodeCount(root.right);
    }
    
    public static void main(String[] args) {
        System.out.println("🪞 樹的鏡像和對稱操作系統");
        System.out.println("=" + "=".repeat(50));
        
        System.out.println("\n 1. 對稱樹判斷測試");
        System.out.println("-".repeat(40));

        TreeNode symmetricTree = new TreeNode(1);
        symmetricTree.left = new TreeNode(2);
        symmetricTree.right = new TreeNode(2);
        symmetricTree.left.left = new TreeNode(3);
        symmetricTree.left.right = new TreeNode(4);
        symmetricTree.right.left = new TreeNode(4);
        symmetricTree.right.right = new TreeNode(3);
        
        TreeNode asymmetricTree = new TreeNode(1);
        asymmetricTree.left = new TreeNode(2);
        asymmetricTree.right = new TreeNode(2);
        asymmetricTree.left.right = new TreeNode(3);
        asymmetricTree.right.right = new TreeNode(3);
        
        printTree(symmetricTree, "對稱樹");
        System.out.println("遞迴方法判斷: " + isSymmetric(symmetricTree));
        System.out.println("迭代方法判斷: " + isSymmetricIterative(symmetricTree));
        
        System.out.println();
        printTree(asymmetricTree, "非對稱樹");
        System.out.println("遞迴方法判斷: " + isSymmetric(asymmetricTree));
        System.out.println("迭代方法判斷: " + isSymmetricIterative(asymmetricTree));
        
        System.out.println("\n 2. 樹的鏡像轉換測試");
        System.out.println("-".repeat(40));

        TreeNode originalTree = new TreeNode(1);
        originalTree.left = new TreeNode(2);
        originalTree.right = new TreeNode(3);
        originalTree.left.left = new TreeNode(4);
        originalTree.left.right = new TreeNode(5);
        originalTree.right.left = new TreeNode(6);
        originalTree.right.right = new TreeNode(7);
        
        printTree(originalTree, "原始樹");
        System.out.println("中序遍歷: " + inorderTraversal(originalTree));
        
        TreeNode mirrorCopy = createMirrorCopy(originalTree);
        printTree(mirrorCopy, "鏡像副本");
        System.out.println("鏡像樹中序遍歷: " + inorderTraversal(mirrorCopy));
        
        TreeNode copyForInPlace = originalTree.deepCopy();
        mirrorTree(copyForInPlace);
        printTree(copyForInPlace, "原地轉換後的鏡像樹");
        
        System.out.println("\n 3. 互為鏡像判斷測試");
        System.out.println("-".repeat(40));
        
        TreeNode tree1 = new TreeNode(1);
        tree1.left = new TreeNode(2);
        tree1.right = new TreeNode(3);
        tree1.left.left = new TreeNode(4);
        tree1.left.right = new TreeNode(5);
        
        TreeNode tree2 = new TreeNode(1);
        tree2.left = new TreeNode(3);
        tree2.right = new TreeNode(2);
        tree2.right.left = new TreeNode(5);
        tree2.right.right = new TreeNode(4);
        
        TreeNode tree3 = new TreeNode(1);
        tree3.left = new TreeNode(2);
        tree3.right = new TreeNode(3);
        
        printTree(tree1, "樹1");
        printTree(tree2, "樹2（樹1的鏡像）");
        printTree(tree3, "樹3（不是樹1的鏡像）");
        
        System.out.println("樹1和樹2是否互為鏡像: " + areMirrors(tree1, tree2));
        System.out.println("樹1和樹3是否互為鏡像: " + areMirrors(tree1, tree3));
        System.out.println("迭代方法 - 樹1和樹2: " + areMirrorsIterative(tree1, tree2));
        
        System.out.println("\n 4. 子樹檢查測試");
        System.out.println("-".repeat(40));

        TreeNode mainTree = new TreeNode(3);
        mainTree.left = new TreeNode(4);
        mainTree.right = new TreeNode(5);
        mainTree.left.left = new TreeNode(1);
        mainTree.left.right = new TreeNode(2);

        TreeNode subTree1 = new TreeNode(4);
        subTree1.left = new TreeNode(1);
        subTree1.right = new TreeNode(2);
        
        TreeNode subTree2 = new TreeNode(4);
        subTree2.left = new TreeNode(1);
        subTree2.right = new TreeNode(3);
        
        TreeNode subTree3 = new TreeNode(2);
        
        printTree(mainTree, "主樹");
        printTree(subTree1, "子樹1");
        printTree(subTree2, "子樹2");
        printTree(subTree3, "子樹3（單節點）");
        
        System.out.println("子樹1是主樹的子樹: " + isSubtree(mainTree, subTree1));
        System.out.println("子樹2是主樹的子樹: " + isSubtree(mainTree, subTree2));
        System.out.println("子樹3是主樹的子樹: " + isSubtree(mainTree, subTree3));
        
        System.out.println("字符串方法 - 子樹1: " + isSubtreeByString(mainTree, subTree1));
        System.out.println("字符串方法 - 子樹2: " + isSubtreeByString(mainTree, subTree2));
        
        System.out.println("\n 5. 綜合測試案例");
        System.out.println("-".repeat(40));
        
        System.out.println("邊界測試:");
        System.out.println("空樹是否對稱: " + isSymmetric(null));
        System.out.println("空樹互為鏡像: " + areMirrors(null, null));
        System.out.println("空樹是任何樹的子樹: " + isSubtree(mainTree, null));
        System.out.println("任何樹不是空樹的子樹: " + isSubtree(null, subTree1));
        
        TreeNode singleNode = new TreeNode(1);
        System.out.println("單節點樹是否對稱: " + isSymmetric(singleNode));
        System.out.println("兩個相同單節點樹互為鏡像: " + areMirrors(singleNode, new TreeNode(1)));
        
        System.out.println("\n 效能測試:");       
        TreeNode largeTree = createLargeTree(1000);
        TreeNode largeMirror = createMirrorCopy(largeTree);
        
        long startTime = System.nanoTime();
        boolean result = areMirrors(largeTree, largeMirror);
        long endTime = System.nanoTime();
        
        System.out.println("大樹鏡像判斷結果: " + result);
        System.out.println("執行時間: " + (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("樹的節點數: " + getNodeCount(largeTree));
        System.out.println("樹的高度: " + getTreeHeight(largeTree));
        
        System.out.println("\n 所有測試完成！");
    }
    private static TreeNode createLargeTree(int maxNodes) {
        if (maxNodes <= 0) return null;
        
        TreeNode root = new TreeNode(1);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int nodeCount = 1;
        int value = 2;
        
        while (!queue.isEmpty() && nodeCount < maxNodes) {
            TreeNode node = queue.poll();
            
            if (nodeCount < maxNodes) {
                node.left = new TreeNode(value++);
                queue.offer(node.left);
                nodeCount++;
            }
            
            if (nodeCount < maxNodes) {
                node.right = new TreeNode(value++);
                queue.offer(node.right);
                nodeCount++;
            }
        }
        
        return root;
    }
}