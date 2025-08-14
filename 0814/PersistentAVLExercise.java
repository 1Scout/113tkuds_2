import java.util.*;

/**
 * 支援版本控制的持久性 AVL 樹實作
 * 使用路徑複製技術，每次修改產生新版本，歷史版本間共享不變節點
 */
public class PersistentAVLExercise {
    
    /**
     * 不可變的 AVL 樹節點
     */
    private static class Node {
        final int key;
        final int value;
        final Node left;
        final Node right;
        final int height;
        
        Node(int key, int value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.height = 1 + Math.max(getHeight(left), getHeight(right));
        }
        
        Node(int key, int value) {
            this(key, value, null, null);
        }
    }
    
    /**
     * AVL 樹版本
     */
    public static class AVLVersion {
        private final Node root;
        private final int versionNumber;
        
        AVLVersion(Node root, int versionNumber) {
            this.root = root;
            this.versionNumber = versionNumber;
        }
        
        public int getVersionNumber() {
            return versionNumber;
        }
        
        /**
         * 在當前版本中查找值
         */
        public Integer search(int key) {
            return search(root, key);
        }
        
        private Integer search(Node node, int key) {
            if (node == null) return null;
            
            if (key == node.key) return node.value;
            else if (key < node.key) return search(node.left, key);
            else return search(node.right, key);
        }
        
        /**
         * 取得當前版本的所有鍵值對
         */
        public List<Map.Entry<Integer, Integer>> getAllEntries() {
            List<Map.Entry<Integer, Integer>> entries = new ArrayList<>();
            inorderTraversal(root, entries);
            return entries;
        }
        
        private void inorderTraversal(Node node, List<Map.Entry<Integer, Integer>> entries) {
            if (node != null) {
                inorderTraversal(node.left, entries);
                entries.add(new AbstractMap.SimpleEntry<>(node.key, node.value));
                inorderTraversal(node.right, entries);
            }
        }
        
        /**
         * 獲取樹的大小
         */
        public int size() {
            return size(root);
        }
        
        private int size(Node node) {
            if (node == null) return 0;
            return 1 + size(node.left) + size(node.right);
        }
        
        /**
         * 打印樹結構
         */
        public void printTree() {
            System.out.println("Version " + versionNumber + ":");
            printTree(root, "", true);
        }
        
        private void printTree(Node node, String prefix, boolean isTail) {
            if (node != null) {
                System.out.println(prefix + (isTail ? "└── " : "├── ") + 
                    node.key + "(" + node.value + ")[h=" + node.height + "]");
                if (node.left != null || node.right != null) {
                    if (node.right != null) {
                        printTree(node.right, prefix + (isTail ? "    " : "│   "), false);
                    }
                    if (node.left != null) {
                        printTree(node.left, prefix + (isTail ? "    " : "│   "), true);
                    }
                }
            }
        }
    }
    
    // 版本歷史
    private List<AVLVersion> versions;
    private int currentVersionNumber;
    
    public PersistentAVLExercise() {
        versions = new ArrayList<>();
        currentVersionNumber = 0;
        // 初始空版本
        versions.add(new AVLVersion(null, 0));
    }
    
    /**
     * 插入操作，產生新版本
     */
    public AVLVersion insert(int key, int value) {
        Node currentRoot = getCurrentVersion().root;
        Node newRoot = insert(currentRoot, key, value);
        
        currentVersionNumber++;
        AVLVersion newVersion = new AVLVersion(newRoot, currentVersionNumber);
        versions.add(newVersion);
        
        return newVersion;
    }
    
    /**
     * 遞迴插入節點，使用路徑複製
     */
    private Node insert(Node node, int key, int value) {
        // 基礎情況：空節點
        if (node == null) {
            return new Node(key, value);
        }
        
        // 找到插入位置，創建新節點（路徑複製）
        Node newNode;
        if (key < node.key) {
            newNode = new Node(node.key, node.value, 
                             insert(node.left, key, value), node.right);
        } else if (key > node.key) {
            newNode = new Node(node.key, node.value, 
                             node.left, insert(node.right, key, value));
        } else {
            // 更新現有鍵的值
            newNode = new Node(key, value, node.left, node.right);
        }
        
        // 重新平衡
        return rebalance(newNode);
    }
    
    /**
     * 重新平衡節點
     */
    private Node rebalance(Node node) {
        // 更新高度（構造函數已處理）
        
        // 計算平衡因子
        int balance = getBalance(node);
        
        // Left Left 情況
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        
        // Right Right 情況
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        
        // Left Right 情況
        if (balance > 1 && getBalance(node.left) < 0) {
            Node newLeft = rotateLeft(node.left);
            Node newNode = new Node(node.key, node.value, newLeft, node.right);
            return rotateRight(newNode);
        }
        
        // Right Left 情況
        if (balance < -1 && getBalance(node.right) > 0) {
            Node newRight = rotateRight(node.right);
            Node newNode = new Node(node.key, node.value, node.left, newRight);
            return rotateLeft(newNode);
        }
        
        return node;
    }
    
    /**
     * 右旋轉
     */
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        
        // 執行旋轉
        Node newY = new Node(y.key, y.value, T2, y.right);
        Node newX = new Node(x.key, x.value, x.left, newY);
        
        return newX;
    }
    
    /**
     * 左旋轉
     */
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        
        // 執行旋轉
        Node newX = new Node(x.key, x.value, x.left, T2);
        Node newY = new Node(y.key, y.value, newX, y.right);
        
        return newY;
    }
    
    /**
     * 取得節點高度
     */
    private static int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }
    
    /**
     * 取得平衡因子
     */
    private int getBalance(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }
    
    /**
     * 取得當前版本
     */
    public AVLVersion getCurrentVersion() {
        return versions.get(versions.size() - 1);
    }
    
    /**
     * 取得指定版本
     */
    public AVLVersion getVersion(int versionNumber) {
        if (versionNumber < 0 || versionNumber >= versions.size()) {
            throw new IndexOutOfBoundsException("Version " + versionNumber + " not found");
        }
        return versions.get(versionNumber);
    }
    
    /**
     * 取得所有版本號
     */
    public List<Integer> getAllVersionNumbers() {
        List<Integer> versionNumbers = new ArrayList<>();
        for (AVLVersion version : versions) {
            versionNumbers.add(version.getVersionNumber());
        }
        return versionNumbers;
    }
    
    /**
     * 取得版本總數
     */
    public int getVersionCount() {
        return versions.size();
    }
    
    /**
     * 比較兩個版本的差異
     */
    public void compareVersions(int version1, int version2) {
        AVLVersion v1 = getVersion(version1);
        AVLVersion v2 = getVersion(version2);
        
        System.out.println("比較版本 " + version1 + " 和版本 " + version2 + ":");
        
        Set<Integer> keys1 = new HashSet<>();
        Set<Integer> keys2 = new HashSet<>();
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        
        for (Map.Entry<Integer, Integer> entry : v1.getAllEntries()) {
            keys1.add(entry.getKey());
            map1.put(entry.getKey(), entry.getValue());
        }
        
        for (Map.Entry<Integer, Integer> entry : v2.getAllEntries()) {
            keys2.add(entry.getKey());
            map2.put(entry.getKey(), entry.getValue());
        }
        
        // 只在版本1中的鍵
        Set<Integer> only1 = new HashSet<>(keys1);
        only1.removeAll(keys2);
        if (!only1.isEmpty()) {
            System.out.println("只在版本 " + version1 + " 中: " + only1);
        }
        
        // 只在版本2中的鍵
        Set<Integer> only2 = new HashSet<>(keys2);
        only2.removeAll(keys1);
        if (!only2.isEmpty()) {
            System.out.println("只在版本 " + version2 + " 中: " + only2);
        }
        
        // 值不同的鍵
        Set<Integer> common = new HashSet<>(keys1);
        common.retainAll(keys2);
        for (Integer key : common) {
            if (!map1.get(key).equals(map2.get(key))) {
                System.out.println("鍵 " + key + " 的值變化: " + 
                    map1.get(key) + " -> " + map2.get(key));
            }
        }
    }
    
    /**
     * 示範和測試方法
     */
    public static void main(String[] args) {
        PersistentAVLExercise tree = new PersistentAVLExercise();
        
        System.out.println("=== 持久性 AVL 樹演示 ===\n");
        
        // 插入一系列值
        System.out.println("插入值: 10, 20, 30, 40, 50, 25");
        tree.insert(10, 100);
        tree.insert(20, 200);
        tree.insert(30, 300);
        tree.insert(40, 400);
        tree.insert(50, 500);
        tree.insert(25, 250);
        
        // 顯示當前版本
        System.out.println("\n當前版本:");
        AVLVersion current = tree.getCurrentVersion();
        current.printTree();
        
        // 查詢操作
        System.out.println("\n查詢操作:");
        System.out.println("查找鍵 30: " + current.search(30));
        System.out.println("查找鍵 35: " + current.search(35));
        
        // 顯示所有版本
        System.out.println("\n版本歷史:");
        for (int i = 0; i < tree.getVersionCount(); i++) {
            AVLVersion version = tree.getVersion(i);
            System.out.println("版本 " + i + " - 大小: " + version.size() + 
                             ", 內容: " + version.getAllEntries());
        }
        
        // 查詢歷史版本
        System.out.println("\n歷史版本查詢:");
        AVLVersion version3 = tree.getVersion(3);
        System.out.println("版本 3 內容:");
        version3.printTree();
        
        // 比較版本
        System.out.println("\n版本比較:");
        tree.compareVersions(2, 6);
        
        // 演示空間優化 - 節點共享
        System.out.println("\n=== 節點共享演示 ===");
        System.out.println("注意：歷史版本之間會共享不變的子樹，實現空間優化");
        
        // 創建新樹進行更詳細的測試
        System.out.println("\n=== 平衡性測試 ===");
        PersistentAVLExercise balanceTest = new PersistentAVLExercise();
        
        // 插入順序數據測試平衡性
        for (int i = 1; i <= 7; i++) {
            balanceTest.insert(i, i * 10);
        }
        
        System.out.println("插入 1-7 後的樹結構（應該保持平衡）:");
        balanceTest.getCurrentVersion().printTree();
        
        System.out.println("\n演示完成！");
    }
}